package linchange.example.com.waimain.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.myInterface.AddressService;
import linchange.example.com.waimain.utils.AppApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressUpdateActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 10010;

    private EditText name,phone,summary,detail;
    private RadioButton male,female;
    private Button update;
    private Address address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_update2);
        //添加到activity列表中
        AppApplication.getApp().addActivity(this);
        initViews();
        initData();
        initEvent();
    }
    private void initEvent(){

        //添加按钮监听器
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert=new AlertDialog.Builder(AddressUpdateActivity.this).create();
                alert.setIcon(R.drawable.ic_lanucher);
                alert.setTitle("系统提示");
                alert.setMessage("确认修改该地址?");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    //点击确认按钮确认修改
                    public void onClick(DialogInterface dialog, int which) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(AppConfig.SERVER_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        AddressService addressService = retrofit.create(AddressService.class);
                        String gender="0";
                        if(female.isChecked()){
                            gender="1";
                        }
                        //获取当前修改值
                        Call<Boolean> updateAddressCall = addressService.updateAddress(address.getId(),name.getText().toString(),phone.getText().toString()
                                ,summary.getText().toString(),detail.getText().toString(),gender);
                        updateAddressCall.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Boolean b= response.body();
                                if(b){
                                    Toast.makeText(AddressUpdateActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(AddressUpdateActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });

                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        AddressUpdateActivity.this.finish();

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
    }
    private void initData(){
        //获取到MyAddressActivity传来的Address
        Intent intent=getIntent();
        address=(Address) intent.getSerializableExtra("address");
        name.setText(address.getName());
        phone.setText(address.getPhone());
        summary.setText(address.getSummary());
        detail.setText(address.getDetail());
        if(address.getGender()==0){
            male.setChecked(true);
        }else {
            female.setChecked(true);
        }
    }
    private void initViews(){
        name = (EditText)findViewById(R.id.et_input_name2);
        phone = (EditText)findViewById(R.id.et_input_phone2);
        summary = (EditText)findViewById(R.id.et_poi_address2);
        detail = (EditText)findViewById(R.id.et_detail_address2);
        male = (RadioButton)findViewById(R.id.rb_male2);
        female = (RadioButton)findViewById(R.id.rb_female2);
        update = (Button)findViewById(R.id.btn_submit2);

    }
}
