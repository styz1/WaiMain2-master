package linchange.example.com.waimain.Helpter;

import android.provider.SyncStateContract;

import linchange.example.com.waimain.utils.RetrofitUtil;
import okhttp3.MediaType;

/**
 * Created by Administrator on 2018/5/18.
 */

public class ApiHelper extends RetrofitUtil {
    private static ApiHelper mInstance;
    public static MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
    private final int pageSize = 10;
    /**
     * 服务器地址
     */
    private static final String API_BASE = "";


    private static class SingleHolder{

    }
    public static ApiHelper getInstance(){
        if (mInstance == null){
            synchronized (ApiHelper.class){
                mInstance = new ApiHelper();
            }
        }
        return mInstance;
    }
}
