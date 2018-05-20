package linchange.example.com.waimain.activity;

import android.annotation.SuppressLint;
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

public class SetNickNameActivity extends AppCompatActivity {
    //编辑昵称
    private EditText etNickName;
    //提交编辑按钮
    private Button submit;
    private String nickName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nick_name);
        //初始化控件
        initViews();
        initEvents();
    }
    private void initEvents(){
        //获取当前用户信息
        user = (User)ObjectSaveUtil.readObject(SetNickNameActivity.this);
        //显示原本昵称
        etNickName.setText(user.getNickname());

        //点击修改监听器
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickName = etNickName.getText().toString();
                if(TextUtils.isEmpty(nickName)){
                    //当输入昵称为空时提示无法为空
                    Toast.makeText(SetNickNameActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                }else {

                    //向服务器发送修改昵称请求
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(AppConfig.SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserService userService =retrofit.create(UserService.class);
                    Call<Boolean> updateNickNameCll = userService.updateNickName(user.getId(),nickName);
                    updateNickNameCll.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean b= response.body();
                            if(b){
                                Toast.makeText(SetNickNameActivity.this,"修改昵称成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(SetNickNameActivity.this,"修改昵称失败",Toast.LENGTH_SHORT).show();
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
    private void initViews(){
        etNickName = (EditText) findViewById(R.id.et_nickName);
        submit = (Button) findViewById(R.id.bt_setNickName);
    }
}
