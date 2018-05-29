package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.utils.AppApplication;
import linchange.example.com.waimain.widget.section.SectionExtensionItemView;
import linchange.example.com.waimain.widget.section.SectionTextItemView;

public class UserProfileActivity extends AppCompatActivity {
    private SectionTextItemView nickName,userName,phone,email,password;
    private SectionExtensionItemView touXiang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //添加到activity列表中
        AppApplication.getApp().addActivity(this);
        initviews();//初始化控件
        initevents();
    }
    //初始化控件
    private void initviews(){
        nickName=(SectionTextItemView)findViewById(R.id.item_nickname);
        userName=(SectionTextItemView)findViewById(R.id.item_username);
        phone=(SectionTextItemView)findViewById(R.id.item_mobile);
        email=(SectionTextItemView)findViewById(R.id.item_email);
        password=(SectionTextItemView)findViewById(R.id.item_password);
        touXiang=(SectionExtensionItemView)findViewById(R.id.item_avatar);

    }

    private void initevents(){
        //点击昵称按钮监听器
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfileActivity.this,SetNickNameActivity.class);
                startActivity(intent);
            }
        });
        //点击用户名按钮监听器
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,ShowUserNameActivity.class);
                startActivity(intent);
            }
        });
        //点击手机按钮监听器
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserProfileActivity.this,SetPhoneActivity.class);
                startActivity(intent);
            }
        });
        //点击邮箱按钮监听器
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,SetEmailActivity.class);
                startActivity(intent);
            }
        });
        //点击密码按钮监听器
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });
        //点击头像按钮监听器
        touXiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,UploadHeadPortraitsActivity.class);
                startActivity(intent);
            }
        });

    }

}
