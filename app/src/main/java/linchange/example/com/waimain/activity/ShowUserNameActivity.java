package linchange.example.com.waimain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.utils.AppApplication;
import linchange.example.com.waimain.utils.ObjectSaveUtil;

public class ShowUserNameActivity extends AppCompatActivity {
    private EditText et_name;
    private Button bt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_name);
        //添加到activity列表中
        AppApplication.getApp().addActivity(this);
        initViews();
        initEvents();
    }

    private void initEvents(){
        User user=(User) ObjectSaveUtil.readObject(ShowUserNameActivity.this);
        et_name.setText(user.getUsername());
        bt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前用户信息
                finish();
            }
        });
    }


    private void initViews(){
        et_name = (EditText)findViewById(R.id.et_name);
        bt_name = (Button)findViewById(R.id.bt_name);
    }
}
