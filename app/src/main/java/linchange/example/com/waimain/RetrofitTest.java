package linchange.example.com.waimain;

import android.util.Log;

import java.util.List;

import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.entity.Order;
import linchange.example.com.waimain.entity.Product;
import linchange.example.com.waimain.myInterface.AddressService;
import linchange.example.com.waimain.myInterface.OrderService;
import linchange.example.com.waimain.myInterface.ProductService;
import linchange.example.com.waimain.myInterface.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by Administrator on 2018/4/23.
 */

public class RetrofitTest {
    public static void main(String[] args){
        System.out.println("Start");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        AddressService addressService =retrofit.create(AddressService.class);
//        Call<Boolean> booleanCall =addressService.deleteAddress("19");
//        booleanCall.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Boolean b= response.body();
//                if(b){
//                    System.out.println("删除成功");
//                }else {
//                    System.out.println("删除失败");
//                }
//            }
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//                Log.d("fail","fail to delete");
//            }
//        });
//        AddressService addressService =retrofit.create(AddressService.class);
//        Call<Address> addressCall = addressService.newAddress("黄昊","181058085787","福州大学","30楼406","0","1");
//        addressCall.enqueue(new Callback<Address>() {
//            @Override
//            public void onResponse(Call<Address> call, Response<Address> response) {
//                Address address = response.body();
//                System.out.println("OK");
//            }
//
//            @Override
//            public void onFailure(Call<Address> call, Throwable t) {
//
//            }
//        });

        AddressService addressService = retrofit.create(AddressService.class);
        Call<Boolean> updateAddressCall = addressService.updateAddress("26","黄昊","15659131431","福州大学","30楼406","0");
        updateAddressCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean b= response.body();
                if(b){
                    System.out.println("修改成功");
                }else {
                    System.out.println("修改失败");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });


//        AddressService addressService = retrofit.create(AddressService.class);
//        Call<List<Address>> addressCall = addressService.getAddress("1");
//        addressCall.enqueue(new Callback<List<Address>>() {
//            @Override
//            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
//                List<Address> addresses= response.body();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Address>> call, Throwable t) {
//            }
//        });
//        UserService userService = retrofit.create(UserService.class);
//        Call<Boolean> registerCall = userService.register("7818","90");
//        registerCall.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Boolean b= response.body();
//                if(b){
//                    System.out.println("成功注册");
//                }else {
//                    System.out.println("用户已存在");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
//            }
//        });

//        UserService userService = retrofit.create(UserService.class);
//        Call<Boolean> loginCall=userService.login("123","74177");
//        loginCall.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Boolean f=response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
//            }
//        });
//          ProductService productService =retrofit.create(ProductService.class);
//          Call<List<Product>> productsCall = productService.getProducts(1);
//          productsCall.enqueue(new Callback<List<Product>>() {
//              @Override
//              public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                  List<Product> products =response.body();
//                  Log.d("success","yes");
//              }
//
//              @Override
//              public void onFailure(Call<List<Product>> call, Throwable t) {
//
//              }
//          });
//        OrderService orderService = retrofit.create(OrderService.class);
//        final Call<List<OrderActivity>> ordersCall = orderService.getOrdersById(1);
//        ordersCall.enqueue(new Callback<List<OrderActivity>>() {
//            @Override
//            public void onResponse(Call<List<OrderActivity>> call, Response<List<OrderActivity>> response) {
//                List<OrderActivity> result = response.body();
//            }
//            @Override
//            public void onFailure(Call<List<OrderActivity>> call, Throwable t) {
//                Log.d("net: ", "failure");
//            }
//        });

//        UserService service =retrofit.create(UserService.class);
//        Call<List<linchange.example.com.waimain.entity.User>> call =service.getUsers();
//        call.enqueue(new Callback<List<linchange.example.com.waimain.entity.User>>() {
//            @Override
//            public void onResponse(Call<List<linchange.example.com.waimain.entity.User>> call, Response<List<linchange.example.com.waimain.entity.User>> response) {
//                List<linchange.example.com.waimain.entity.User> result= response.body();
//                for(linchange.example.com.waimain.entity.User user : result){
//                    System.out.println(user.getId());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<linchange.example.com.waimain.entity.User>> call, Throwable t) {
//
//            }
//        });

//        BusinessService businessService=retrofit.create(BusinessService.class);
//        Call<List<Business>> businessCall = businessService.getBusiness();
//        businessCall.enqueue(new Callback<List<Business>>() {
//            @Override
//            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
//                List<Business> result = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<List<Business>> call, Throwable t) {
//
//            }
//        });


    }
}
