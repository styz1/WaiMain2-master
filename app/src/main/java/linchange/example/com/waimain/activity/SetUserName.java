package linchange.example.com.waimain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.utils.AppApplication;

public class SetUserName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_name);
        //添加到activity列表中
        AppApplication.getApp().addActivity(this);
    }
}
