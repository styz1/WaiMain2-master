package linchange.example.com.waimain.frament;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.activity.Setting;
import linchange.example.com.waimain.activity.UserProfileActivity;
import linchange.example.com.waimain.activity.MyAddressActivity;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import linchange.example.com.waimain.widget.section.SectionTextItemView;

/**
 * Created by Administrator on 2018/4/17.
 */

//个人信息列表界面
@SuppressLint("ValidFragment")
public class UserCenterFrament extends Fragment {
    private LinearLayout linearLayout;
    private SectionTextItemView address,favorite,evaluate,setting;
    private User user;

    public static UserCenterFrament newInstance(String content){
        UserCenterFrament userCenterFrament= new UserCenterFrament();
        return  userCenterFrament;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_user_center,container,false);
//        Bundle bundle=getArguments();
//        user=(User)bundle.getSerializable("user");
        user=(User)ObjectSaveUtil.readObject(getActivity());
        TextView Name=(TextView)view.findViewById(R.id.txt_account_name1);
        TextView Mobile=(TextView)view.findViewById(R.id.txt_mobileN);
        Name.setText(user.getUsername());
        Mobile.setText(user.getMobile());
        linearLayout = (LinearLayout) view.findViewById(R.id.layout_login_before);
        //跳转到修改个人设置页面
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //跳转到地址页面
        address = (SectionTextItemView)view.findViewById(R.id.btn_my_address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyAddressActivity.class);
                intent.putExtra("user",user);
                getActivity().startActivity(intent);
            }
        });
        //跳转到设置页面
        setting=(SectionTextItemView)view.findViewById(R.id.btn_Setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Setting.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
    //初始化控件
    private void initViews(){

    }

}
