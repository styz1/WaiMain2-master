package linchange.example.com.waimain.myInterface;

import java.util.List;

import linchange.example.com.waimain.entity.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2018/5/2.
 */

public interface ProductService {
    @GET("getProducts/{id}")
    Call<List<Product>> getProducts(@Path("id") Integer id);
}
