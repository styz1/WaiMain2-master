package linchange.example.com.waimain.frament;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import linchange.example.com.waimain.LoginActivity;
import linchange.example.com.waimain.R;
import linchange.example.com.waimain.activity.MainActivity;
import linchange.example.com.waimain.activity.SettingActivity;
import linchange.example.com.waimain.activity.UserProfileActivity;
import linchange.example.com.waimain.activity.MyAddressActivity;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.myInterface.UserService;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import linchange.example.com.waimain.widget.section.SectionTextItemView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/4/17.
 */

//个人信息列表界面
@SuppressLint("ValidFragment")
public class UserCenterFrament extends Fragment {
    private LinearLayout linearLayout;
    private SectionTextItemView address,favorite,evaluate,setting;
    private ImageView touxiang;
    private User user;

    public static UserCenterFrament newInstance(String content){
        UserCenterFrament userCenterFrament= new UserCenterFrament();
        return  userCenterFrament;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_user_center,container,false);
//        Bundle bundle=getArguments();
//        user=(User)bundle.getSerializable("user");
        //获取当前用户信息
        user=(User)ObjectSaveUtil.readObject(getActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<User> loginCall = userService.getUserById(user.getId());
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user=response.body();
                ObjectSaveUtil.saveObject(getActivity(),user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        TextView Name=(TextView)view.findViewById(R.id.txt_account_name1);
        TextView Mobile=(TextView)view.findViewById(R.id.txt_mobileN);
        if(TextUtils.isEmpty(user.getNickname())){
            Name.setText("点击设置昵称");
        }else{
            Name.setText(user.getNickname());
        }

        Mobile.setText(user.getMobile());
        linearLayout = (LinearLayout) view.findViewById(R.id.layout_login_before);
        //跳转到修改个人设置页面
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //跳转到地址页面
        address = (SectionTextItemView)view.findViewById(R.id.btn_my_address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyAddressActivity.class);
                intent.putExtra("user",user);
                getActivity().startActivity(intent);
            }
        });
        //跳转到设置页面
        setting=(SectionTextItemView)view.findViewById(R.id.btn_Setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //假如有头像则载入头像
        if(!TextUtils.isEmpty(user.getIcon())) {
            touxiang = (ImageView) view.findViewById(R.id.img_account_avatar1);
            Glide.with(getActivity())
                    .load(user.getIcon())
                    .into(touxiang);
        }
        return view;
    }
    //初始化控件
    private void initViews(){

    }

}
