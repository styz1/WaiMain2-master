package linchange.example.com.waimain.myInterface;

import java.util.List;

import linchange.example.com.waimain.bean.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/4/23.
 */

public interface UserService {
    @GET("getUsers")
    Call<List<User>> getUsers();

    @GET("login/{username}/{password}")
    Call<User> login(@Path("username")String username,@Path("password")String password);

    @GET("newUser")
    Call<Boolean> register(@Query("username") String username, @Query("password") String password);
}
