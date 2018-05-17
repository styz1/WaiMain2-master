package linchange.example.com.waimain.myInterface;

import java.util.List;

import linchange.example.com.waimain.entity.Order;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface OrderService {

    @GET("getOrders/{id}")
    Call<List <Order>> getOrdersById(@Path("id") String id);

}
