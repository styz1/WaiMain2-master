package linchange.example.com.waimain.myInterface;



import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.entity.FeedBack;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/18.
 */

public interface FeedBackService {
    @GET("newFeedBack")
    Call<FeedBack> newFeedback(@Query("userContactWay") String userContactWay, @Query("userId") String userId, @Query("detail") String detail);
}
