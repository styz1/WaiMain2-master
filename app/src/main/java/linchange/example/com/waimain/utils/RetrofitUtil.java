package linchange.example.com.waimain.utils;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/5/18.
 */

public class RetrofitUtil {
    private static RetrofitUtil mInstance;
    private Retrofit retrofit;

    public RetrofitUtil(){

    }

    public static RetrofitUtil getInstance(){
        if (mInstance == null){
            synchronized (RetrofitUtil.class){
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }


//    /**
//     * 自定义异常，当接口返回的{@link Response#code}不为{@link # OK}时，需要跑出此异常
//     * eg：登陆时验证码错误；参数为传递等
//     */
//    public static class APIException extends Exception {
//        public String code;
//        public String message;
//
//        public APIException(String code, String message) {
//            this.code = code;
//            this.message = message;
//        }
//
//        @Override
//        public String getMessage() {
//            return message;
//        }
//    }


    public  <T>T createRetrofitService(final Class<T> service,String baseUrl) {

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
//                    .client(getOkHttpClient())//指定网络执行器
                    .addConverterFactory(GsonConverterFactory.create())//指定 Gson 作为解析Json数据的Converter
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//指定使用RxJava 作为CallAdapter
                    .baseUrl(baseUrl)
                    .build();

        }


        return retrofit.create(service);
    }
//    public OkHttpClient getOkHttpClient() {
//        //日志显示级别 分为4类：NONE、BASIC、HEADERS、BODY。
//        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
//        //新建log拦截器
//        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.d("zcb","OkHttp====Message:"+message);
//            }
//        });
//        loggingInterceptor.setLevel(level);
//        //定制OkHttp
//        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
//                .Builder();
//        //OkHttp进行添加拦截器loggingInterceptor
//        // httpClientBuilder.addInterceptor(new HeaderInterceptor());
//        httpClientBuilder.addInterceptor(loggingInterceptor);
//
//        return httpClientBuilder.build();
//    }

}
