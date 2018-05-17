package linchange.example.com.waimain.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.frament.shopFragment;
import linchange.example.com.waimain.frament.OrderFragment;
import linchange.example.com.waimain.frament.UserCenterFrament;

public class MainActivity extends AppCompatActivity {

    private User user;
    private shopFragment fg1;
    private OrderFragment fg2;
    private UserCenterFrament fg3;
    private FragmentManager fragmentManager;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    if (fg1 == null) {
                        fg1 = shopFragment.newInstance("商家fragment");
                    }
                    fragmentManager.beginTransaction().replace(R.id.fl_content, fg1).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_order:
                    if(fg2 == null){
                        fg2 =OrderFragment.newInstance("订单fragment");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",user);
                        fg2.setArguments(bundle);
                    }
                    Log.d("order","订单点击了");
                    fragmentManager.beginTransaction().replace(R.id.fl_content,fg2).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_my:
                    if(fg3 == null){
                        fg3 =UserCenterFrament.newInstance("我的fragment");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user",user);
                        fg3.setArguments(bundle);
                    }
                    fragmentManager.beginTransaction().replace(R.id.fl_content,fg3).addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //接收user对象
        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getFragmentManager();
        if (fg1 == null) {
            fg1 = shopFragment.newInstance("商家fragment");
        }
        fragmentManager.beginTransaction().add(R.id.fl_content, fg1).addToBackStack(null).commitAllowingStateLoss();
    }


}
