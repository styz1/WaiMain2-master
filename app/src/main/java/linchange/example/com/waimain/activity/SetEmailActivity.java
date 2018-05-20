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
import linchange.example.com.waimain.myInterface.UserService;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import linchange.example.com.waimain.utils.StringUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetEmailActivity extends AppCompatActivity {
    private EditText et_email;
    private Button bt_email;
    private String email;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_email);
        initViews();
        initEvents();
    }
    //初始化时间
    private void initEvents(){

        //获取当前用户信息
        user = (User) ObjectSaveUtil.readObject(SetEmailActivity.this);
        //显示更改前的邮箱号
        et_email.setText(user.getEmail());
        bt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SetEmailActivity.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
                }else if(!StringUtil.isEmail(email)){
                    Toast.makeText(SetEmailActivity.this,"邮箱格式错误",Toast.LENGTH_SHORT).show();
                }else{

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://localhost:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserService userService =retrofit.create(UserService.class);
                    Call<Boolean> updateEmailCall = userService.updateEmail(user.getId(),email);
                    updateEmailCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean b= response.body();
                            if(b){
                                Toast.makeText(SetEmailActivity.this,"修改邮箱成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(SetEmailActivity.this,"修改邮箱失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
    //初始化控件
    private void initViews(){
        et_email = (EditText)findViewById(R.id.et_email);
        bt_email = (Button)findViewById(R.id.bt_email);
    }
}
