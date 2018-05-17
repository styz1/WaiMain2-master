package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.widget.section.SectionTextItemView;

public class UserProfileActivity extends AppCompatActivity {
    private SectionTextItemView nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initviews();//初始化控件

    }
    //初始化控件
    private void initviews(){
        nickname=(SectionTextItemView)findViewById(R.id.item_nickname);
    }

    private void initecents(){
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfileActivity.this,SetNickNameActivity.class);
                startActivity(intent);
            }
        });
    }

}
