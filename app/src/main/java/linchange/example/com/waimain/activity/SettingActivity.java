package linchange.example.com.waimain.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.utils.AppApplication;
import linchange.example.com.waimain.widget.section.SectionTextItemView;

public class SettingActivity extends AppCompatActivity {
    SectionTextItemView tv_checkUpdate,tv_feedback;
    TextView btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //添加到activity列表中
        AppApplication.getApp().addActivity(this);
        initViews();
        initEvents();
    }

    private void initEvents(){

        //检查更新按钮监听器
        tv_checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert=new AlertDialog.Builder(SettingActivity.this).create();
                alert.setIcon(R.drawable.ic_lanucher);
                alert.setTitle("系统提示");
                alert.setMessage("当前版本已经是最新版本");
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
            }
        });

        //监听点击反馈按钮
        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,UserFeedBackActivity.class);
                startActivity(intent);
            }
        });

        //监听退出系统按钮
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加到activity列表中
                AppApplication.getApp().exit();
            }
        });

    }
    private void initViews(){
        tv_checkUpdate =(SectionTextItemView)findViewById(R.id.tv_checkUpdate);
        tv_feedback = (SectionTextItemView)findViewById(R.id.tv_feedback);
        btn_logout = (TextView)findViewById(R.id.btn_logout);
    }
}
