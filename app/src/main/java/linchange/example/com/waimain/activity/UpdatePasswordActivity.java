package linchange.example.com.waimain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.myInterface.UserService;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText et_oldPassword,et_newPassword;
    private Button bt_password;
    private String oldPassword,newPassword;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initViews();
        initEvents();
    }

    //初始化事件
    private void initEvents(){
        //获取当前用户信息
        user = (User) ObjectSaveUtil.readObject(UpdatePasswordActivity.this);
        //点击确认按钮监听器
        bt_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断不为空
                if(InputIsNotEmpty()){
                    //获取旧密码
                    oldPassword = et_oldPassword.getText().toString();
                    //获取新密码
                    newPassword = et_newPassword.getText().toString();
                    //向服务器发起修改密码请求
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(AppConfig.SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserService userService =retrofit.create(UserService.class);
                    Call<Boolean> booleanCall = userService.updatePassword(user.getId(),oldPassword,newPassword);
                    booleanCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean b= response.body();
                            if(b){
                                Toast.makeText(UpdatePasswordActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(UpdatePasswordActivity.this,"修改密码失败，原密码错误",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });

                }else{
                    Toast.makeText(UpdatePasswordActivity.this,"输入密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //初始化控件
    private void initViews(){
        et_oldPassword = (EditText)findViewById(R.id.et_oldPassword);
        et_newPassword = (EditText)findViewById(R.id.et_newPassword);
        bt_password = (Button)findViewById(R.id.bt_password);
    }

    private boolean InputIsNotEmpty(){
        //获取旧密码
        oldPassword = et_oldPassword.getText().toString();
        //获取新密码
        newPassword = et_newPassword.getText().toString();
        //假如两个密码框有一个为空则返回false,否则返回true
        if(TextUtils.isEmpty(oldPassword)||TextUtils.isEmpty(newPassword)){
            return false;
        }else
            return true;
    }
}
