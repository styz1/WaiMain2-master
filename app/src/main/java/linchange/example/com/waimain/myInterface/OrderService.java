package linchange.example.com.waimain.myInterface;

import java.util.List;

import linchange.example.com.waimain.entity.Order;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface OrderService {

    @GET("getOrders/{id}")
    Call<List <Order>> getOrdersById(@Path("id") String id);

    @GET("newOrder")
    Call<Boolean> newOrder(@Query("businessId")Integer businessId, @Query("userId")Integer userId,@Query("status")String status,@Query("totalPrice")String totalPrice,
                           @Query("consignee")String consignee,@Query("phone")String phone,@Query("payMethod")Integer payMethod,@Query("isEvaluate")String isEvaluate,
                           @Query("detail")String detail,@Query("address")String address,@Query("businessName")String businessName,@Query("businessIcon")String businessIcon);
}
