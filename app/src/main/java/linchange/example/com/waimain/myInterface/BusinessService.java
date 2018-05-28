package linchange.example.com.waimain.myInterface;

import java.util.List;

import linchange.example.com.waimain.entity.Business;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/4/25.
 */

public interface BusinessService {
    @GET("getBusiness")
    Call<List<Business>> getBusiness();

    @GET("getBusinessById/{id}")
    Call<Business> getBusinessById(@Path("id") Integer id);

    //模糊查询店铺
    @GET("getBusinessByName/{shopName}")
    Call<List<Business>> getBusinessByName(@Path("shopName")String shopName);
}
