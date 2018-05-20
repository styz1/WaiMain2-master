package linchange.example.com.waimain.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.activity.AddressUpdateActivity;
import linchange.example.com.waimain.activity.MyAddressActivity;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.myInterface.AddressService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Administrator on 2018/5/6.
 */


//用户地址适配器
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.LinearViewHolder> {
    private Activity mContext;
    private List<Address> addresses;

    public AddressAdapter(List<Address> addresses, Activity activity) {
        mContext = activity;
        this.addresses = addresses;
    }

    public void setData(List<Address> addresses) {
        this.addresses = addresses;
        notifyDataSetChanged();
    }

    @Override
    public LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final LinearViewHolder holder, final int position) {
        final Address address = addresses.get(position);
        if (address != null) {
            holder.userName.setText(address.getName());
            if (address.getGender() == 0) {
                holder.userGender.setText("先生");
            } else {
                holder.userGender.setText("女士");
            }
            holder.userMobile.setText(address.getPhone());
            holder.userAddress.setText(address.getSummary() + address.getDetail());

            //当点击删除图标时删除当前地址
            holder.imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert=new AlertDialog.Builder(mContext).create();
                    alert.setIcon(R.drawable.ic_lanucher);
                    alert.setTitle("系统提示");
                    alert.setMessage("确认删除该地址?");
                    alert.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override

                        //点击确认按钮确认删除
                        public void onClick(DialogInterface dialog, int which) {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(AppConfig.SERVER_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            AddressService addressService = retrofit.create(AddressService.class);
                            Call<Boolean> booleanCall = addressService.deleteAddress(address.getId());
                            booleanCall.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    Boolean b = response.body();
                                    if (b) {
                                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT);
                                        addresses.remove(addresses.get(position));
                                        notifyDataSetChanged();
                                        notifyItemRemoved(position);
                                    } else {
                                        Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Log.d("fail", "fail to delete");

                                }
                            });

                        }
                    });
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();
                }

            });

            //当点击修改图标时进入修改界面
            holder.imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddressUpdateActivity.class);
                    intent.putExtra("address",address);
                    mContext.startActivityForResult(intent, AddressUpdateActivity.REQUEST_CODE);
                }
            });

            //当点击item时选择该地址
            holder.chooseAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("address",address);
                    mContext.setResult(RESULT_OK,intent);
                    mContext.finish();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return addresses == null ? 0 : addresses.size();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imageButton1, imageButton2;
        private TextView userName, userGender, userMobile, userAddress;
        private LinearLayout chooseAddress;

        public LinearViewHolder(View itemView) {
            super(itemView);
            chooseAddress = (LinearLayout)itemView.findViewById(R.id.chooseAddress);
            userName = (TextView) itemView.findViewById(R.id.txt_name);
            userGender = (TextView) itemView.findViewById(R.id.txt_gender);
            userMobile = (TextView) itemView.findViewById(R.id.txt_mobile);
            userAddress = (TextView) itemView.findViewById(R.id.txt_address);
            imageButton1 = (ImageButton) itemView.findViewById(R.id.btn_delete);
            imageButton2 = (ImageButton) itemView.findViewById(R.id.btn_edit);
        }
    }
}
