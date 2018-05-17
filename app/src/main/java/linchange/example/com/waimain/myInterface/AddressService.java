package linchange.example.com.waimain.myInterface;

import java.util.List;

import linchange.example.com.waimain.entity.Address;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/6.
 */

public interface AddressService {
    @GET("getAddress/{id}")
    Call<List<Address>> getAddress(@Path("id")String id);

    @GET("newAddress")
    Call<Address> newAddress(@Query("name") String name, @Query("phone") String phone,
                           @Query("summary") String summary, @Query("detail") String detail,@Query("gender") String gender, @Query("userId") String userId);

    @GET("deleteAddress/{addressId}")
    Call<Boolean> deleteAddress(@Path("addressId")String id);

    @GET("UpdateAddress/{addressId}")
    Call<Boolean> updateAddress(@Path("addressId")String id,@Query("name") String name, @Query("phone") String phone,
                                @Query("summary") String summary, @Query("detail") String detail,@Query("gender") String gender);
}
