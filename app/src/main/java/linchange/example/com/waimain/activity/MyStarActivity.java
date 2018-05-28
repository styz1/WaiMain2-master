package linchange.example.com.waimain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.adapter.EvaluateAdapter;
import linchange.example.com.waimain.adapter.StarAdapter;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Order;
import linchange.example.com.waimain.myInterface.OrderService;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyStarActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Order> orders=new ArrayList<>();
    private StarAdapter starAdapter;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        //初始化控件
        initViews();
        //初始化数据
        initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyStarActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        starAdapter = new StarAdapter(orders, this);
        recyclerView.setAdapter(starAdapter);
    }

    private void initData(){
        user=(User) ObjectSaveUtil.readObject(MyStarActivity.this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrderService orderService = retrofit.create(OrderService.class);
        final Call<List<Order>> ordersCall = orderService.getOrdersById(user.getId());
        ordersCall.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                List<Order> result = response.body();
                orders.clear();
                for(Order order : result){
                    if(!order.getIsStar().equals("0")){
                        orders.add(order);
                    }
                }
                //订单降序排列，最新的在最前面
                Collections.sort(orders, new Comparator<Order>() {
                            @Override
                            public int compare(Order o1, Order o2) {
                                return o2.getId().compareTo(o1.getId());
                            }
                        }
                );
                starAdapter.setData(orders);
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("net: ", "failure");
            }
        });
    }
    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.my_stars);
    }
}
