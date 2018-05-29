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
import linchange.example.com.waimain.entity.FeedBack;
import linchange.example.com.waimain.myInterface.FeedBackService;
import linchange.example.com.waimain.utils.AppApplication;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserFeedBackActivity extends AppCompatActivity {
    private EditText et_feedBack,et_contactWay;
    private Button bt_feedback;
    private String detail,contratWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed_back);
        //添加到activity列表中
        AppApplication.getApp().addActivity(this);
        initViews();
        initEvents();
    }
    //初始化事件
    private void initEvents(){
        bt_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前用户信息
                User user=(User) ObjectSaveUtil.readObject(UserFeedBackActivity.this);
                detail=et_feedBack.getText().toString();
                contratWay=et_contactWay.getText().toString();
                if(TextUtils.isEmpty(detail)){
                    Toast.makeText(UserFeedBackActivity.this,"请输入要反馈的信息",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(contratWay)){
                    Toast.makeText(UserFeedBackActivity.this,"请输入您的联系方式",Toast.LENGTH_SHORT).show();
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(AppConfig.SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    FeedBackService feedBackService =retrofit.create(FeedBackService.class);
                    Call<FeedBack> feedBackCall = feedBackService.newFeedback(contratWay,user.getId(),detail);
                    feedBackCall.enqueue(new Callback<FeedBack>() {
                        @Override
                        public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
                            FeedBack feedBack=response.body();
                            Toast.makeText(UserFeedBackActivity.this,"提交成功，感谢您的反馈！",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<FeedBack> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
    //初始化控件
    private void initViews(){
        et_feedBack  =(EditText)findViewById(R.id.et_feedback);
        et_contactWay =(EditText)findViewById(R.id.edit_contact_way);
        bt_feedback =(Button)findViewById(R.id.bt_submit_feedback);
    }
}
