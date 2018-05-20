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

public class SetPhoneActivity extends AppCompatActivity {
    private EditText et_setPhone;
    private Button bt_setPhone;
    private String phone;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone);
        initViews();
        initEvents();
    }

    //初始化事件
    private void initEvents(){
        //获取当前用户信息
        user =(User) ObjectSaveUtil.readObject(SetPhoneActivity.this);
        et_setPhone.setText(user.getMobile());

        bt_setPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=et_setPhone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(SetPhoneActivity.this,"电话不能为空",Toast.LENGTH_SHORT).show();
                }else  if(!StringUtil.isCellPhone(phone)){
                    Toast.makeText(SetPhoneActivity.this,"请输入正确的手机格式",Toast.LENGTH_SHORT).show();
                }else{

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://localhost:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserService userService =retrofit.create(UserService.class);
                    Call<Boolean> updatePhoneCall = userService.updatePhone(user.getId(),phone);
                    updatePhoneCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean b= response.body();
                            if(b){
                                Toast.makeText(SetPhoneActivity.this,"设置电话成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(SetPhoneActivity.this,"设置电话失败，请联系管理员",Toast.LENGTH_SHORT).show();
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
        et_setPhone = (EditText)findViewById(R.id.et_setPhone);
        bt_setPhone = (Button)findViewById(R.id.bt_setPhone);
    }
}
