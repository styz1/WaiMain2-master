package linchange.example.com.waimain.myInterface;

import java.util.List;

import linchange.example.com.waimain.entity.Evaluate;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/20.
 */

public interface EvaluateService {

    //创建新评价
    @GET("newEvaluate")
    Call<Evaluate> newEvaluate(@Query("OrderId")Integer orderId,@Query("businessId")Integer businessId,@Query("userId")Integer userId,@Query("detail")String detail);

    //通过订单id查找评价
    @GET("getEvaluateByOrderId/{orderId}")
    Call<Evaluate> getEvaluateByOrderId(@Path("orderId") Integer OrderId);

    //通过用户id查找评价列表
    @GET("getEvaluateByUserId/{userId}")
    Call<List<Evaluate>> getEvaluateByUserId(@Path("userId")Integer userId);

    //通过商店id查找评价列表
    @GET("getEvaluateByBusinessId/{businessId}")
    Call<List<Evaluate>> getEvaluateByBusinessId(@Path("businessId")Integer businessId);

}

