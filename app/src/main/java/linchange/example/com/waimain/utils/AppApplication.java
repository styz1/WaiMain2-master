package linchange.example.com.waimain.utils;


import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/29.
 */

public class AppApplication extends Application {
    private static AppApplication mAppApplication;
    /** 当前打开的activity列表 */
    public ArrayList<Activity> activityList;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mAppApplication = this;
    }

    /** 获取Application */
    public static AppApplication getApp() {
        if (mAppApplication == null) {
            mAppApplication = new AppApplication();
        }
        return mAppApplication;
    }

    /** 添加当前Activity 到列表中 */
    public void addActivity(Activity acitivity) {
        if(activityList == null){
            activityList = new ArrayList<Activity>();
        }
        activityList.add(acitivity);
    }

    /** 清空列表，取消引用*/
    public void clearActivity(){
        activityList.clear();
    }

    /** 遍历退出所有Activity */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        clearActivity();//千万记得清空取消引用。
        System.exit(0);
    }

}
