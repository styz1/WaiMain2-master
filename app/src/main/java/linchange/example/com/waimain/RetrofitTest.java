package linchange.example.com.waimain;

import android.util.Log;

import java.io.File;
import java.util.List;

import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.entity.FeedBack;
import linchange.example.com.waimain.entity.Order;
import linchange.example.com.waimain.entity.Product;
import linchange.example.com.waimain.myInterface.AddressService;
import linchange.example.com.waimain.myInterface.FeedBackService;
import linchange.example.com.waimain.myInterface.OrderService;
import linchange.example.com.waimain.myInterface.ProductService;
import linchange.example.com.waimain.myInterface.UserService;
import okhttp3.MultipartBody;
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
        OrderService orderService = retrofit.create(OrderService.class);
        Call<Boolean> booleanCall = orderService.newOrder(1,1,"0","200","黄昊","15659131431",0,
                "0","","福州大学30号楼406","东北大菜馆","http://localhost:8080/img/2.jpeg");
        booleanCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean b =response.body();
                if(b){
                    System.out.println("提交订单成功");
                }else{
                    System.out.println("提交订单失败");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

//        MultipartBody multipartBody = MultipartBody.Part

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

//        UserService userService =retrofit.create(UserService.class);
//        Call<Boolean> booleanCall = userService.updatePassword("11","456","4567");
//        booleanCall.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Boolean b= response.body();
//                if(b){
//                    System.out.println("更改密码成功");
//                }else {
//                    System.out.println("更改密码失败，原密码错误");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
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

//        FeedBackService feedBackService =retrofit.create(FeedBackService.class);
//        Call<FeedBack> feedBackCall = feedBackService.newFeedback("634909510@qq.com","10","功能还有一些需要完善");
//        feedBackCall.enqueue(new Callback<FeedBack>() {
//            @Override
//            public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
//                FeedBack feedBack=response.body();
//            }
//
//            @Override
//            public void onFailure(Call<FeedBack> call, Throwable t) {
//
//            }
//        });

//        UserService userService =retrofit.create(UserService.class);
//        Call<Boolean> updateNickNameCll = userService.updateNickName("10","styz");
//        updateNickNameCll.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Boolean b= response.body();
//                if(b){
//                    System.out.println("修改昵称成功");
//                }else {
//                    System.out.println("修改昵称失败");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
//            }
//        });
//        UserService userService =retrofit.create(UserService.class);
//        Call<Boolean> updatePhoneCall = userService.updatePhone("10","18105085787");
//        updatePhoneCall.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Boolean b= response.body();
//                if(b){
//                    System.out.println("修改电话成功");
//                }else {
//                    System.out.println("修改电话失败");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
//            }
//        });

//
//        UserService userService =retrofit.create(UserService.class);
//        Call<Boolean> updateEmailCall = userService.updateEmail("10","905372349@qq.com");
//        updateEmailCall.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Boolean b= response.body();
//                if(b){
//                    System.out.println("修改邮箱成功");
//                }else {
//                    System.out.println("修改邮箱失败");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
//            }
//        });




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
