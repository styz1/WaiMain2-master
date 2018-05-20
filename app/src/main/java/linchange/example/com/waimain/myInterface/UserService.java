package linchange.example.com.waimain.myInterface;

import java.util.HashMap;
import java.util.List;

import linchange.example.com.waimain.bean.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/4/23.
 */

public interface UserService {

    //得到所用用户
    @GET("getUsers")
    Call<List<User>> getUsers();

    //登录验证
    @GET("login/{username}/{password}")
    Call<User> login(@Path("username")String username,@Path("password")String password);

    //注册用户
    @GET("newUser")
    Call<Boolean> register(@Query("username") String username, @Query("password") String password);

    //更新昵称
    @GET("updateNickName/{userId}/{nickName}")
    Call<Boolean> updateNickName(@Path("userId")String userId,@Path("nickName")String nickName);

    //更新手机号
    @GET("updatePhone/{userId}/{phone}")
    Call<Boolean> updatePhone(@Path("userId")String userId,@Path("phone")String phone);

    //更新邮箱
    @GET("updateEmail/{userId}/{email}")
    Call<Boolean> updateEmail(@Path("userId")String userId,@Path("email")String email);

    //更新密码
    @GET("updatePassword/{userId}/{oldPassword}/{newPassword}")
    Call<Boolean> updatePassword(@Path("userId")String userId,@Path("oldPassword")String oldPassword,@Path("newPassword")String newPassword);

    //上传头像
    @Multipart
    @POST("upload")
    Call<Boolean> uploadImage(@Part MultipartBody.Part file);



}
