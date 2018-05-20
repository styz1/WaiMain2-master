package linchange.example.com.waimain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import linchange.example.com.waimain.activity.MainActivity;
import linchange.example.com.waimain.activity.RegisterActivity;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.myInterface.UserService;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import linchange.example.com.waimain.utils.ShareUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {

    private EditText etUsername; //用户名输入框
    private EditText etPassword; //密码输入框
    private TextView tvNameWrong; //用户名错误提示文字
    private TextView tvPssWrong; //密码错误提示文字
    private Button btnRegister; //注册按钮
    private Button btnLogin; //登录按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews(); //初始化页面控件
        initEvents(); //初始化控件事件
    }

    //初始化控件事件
    private void initEvents() {
        //给注册按钮设置点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                LoginActivity.this.finish(); //结束当前界面
            }
        });

        //给登陆按钮设置点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名输入框中的内容
                String username = etUsername.getText().toString();
                //获取密码输入框中的内容
                String password = etPassword.getText().toString();

                //判断用户名和密码是否正确
                if (!TextUtils.isEmpty(username)
                        && !TextUtils.isEmpty(password)) { //用户名和密码都不为空

                    hideNameAndPassError(); //隐藏用户名和密码错误提示
                    tryToLogin(username, password); //尝试登陆

                } else { //用户名或密码为空
                    showNameOrPassError(username, password); //显示用户名或密码错误文字提示
                }
            }
        });
    }

    //隐藏用户名和密码错误提示
    private void hideNameAndPassError() {
        tvNameWrong.setVisibility(View.INVISIBLE); //隐藏用户名错误提示文字
        tvPssWrong.setVisibility(View.INVISIBLE); //隐藏密码错误提示文字
    }

    //尝试登陆
    private void tryToLogin(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<User> loginCall = userService.login(etUsername.getText().toString(),etPassword.getText().toString());
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                    User user=response.body();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                    intent.putExtra("user",user);
                    ObjectSaveUtil.saveObject(LoginActivity.this,user);
                    startActivity(intent);
                    LoginActivity.this.finish(); //结束当前页面
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
            }
        });
//        Call<Boolean> loginCall=userService.login(etUsername.getText().toString(),etPassword.getText().toString());
//        loginCall.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Boolean f=response.body();
//                if(f){
//                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                    intent.putExtra("username",etUsername.getText().toString());
//                    startActivity(intent);
//                    LoginActivity.this.finish(); //结束当前页面
//                }else {
//                    Toast.makeText(LoginActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
//            }
//        });
//        //获取数据库中该用户名对应的密码
//        String realPassword = ShareUtils.getString(this, username, "");
//        if (password.equals(realPassword)) { //输入的密码与在数据库中的一致
//            //跳转到商品购买页面
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            LoginActivity.this.finish(); //结束当前页面
//        } else { //输入的密码与在数据库中的不一致
//            //提示用户名或密码不正确
//            Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
//        }
    }

    //显示用户名或密码错误文字提示
    private void showNameOrPassError(String username, String password) {
        if (TextUtils.isEmpty(username)) { //用户名为空
            tvNameWrong.setVisibility(View.VISIBLE); //显示用户名错误提示文字
        } else { //用户名不为空
            tvNameWrong.setVisibility(View.INVISIBLE); //用户名错误提示文字消失
        }

        if (TextUtils.isEmpty(password)) { //密码为空
            tvPssWrong.setVisibility(View.VISIBLE); //显示密码错误提示文字
        } else { //密码不为空
            tvNameWrong.setVisibility(View.INVISIBLE); //密码错误提示文字消失
        }
    }

    //初始化页面控件
    private void initViews() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvNameWrong = (TextView) findViewById(R.id.tv_name_wrong);
        tvPssWrong = (TextView) findViewById(R.id.tv_pass_wrong);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }
}
