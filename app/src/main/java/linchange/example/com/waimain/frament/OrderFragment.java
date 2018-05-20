package linchange.example.com.waimain.frament;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.adapter.OrderAdapter;
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

/**
 * Created by Administrator on 2018/4/17.
 */

//订单列表界面
public class OrderFragment extends Fragment {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orders =new ArrayList<>();
    public static OrderFragment newInstance(String content){
        OrderFragment orderFragment=new OrderFragment();
        return  orderFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_order_content,container,false);
//        Bundle bundle=getArguments();
//        User user=(User)bundle.getSerializable("user");
        //获取登录的个人信息
        User user=(User) ObjectSaveUtil.readObject(getActivity());
        //找到装配的recycleView
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order);
//        recyclerView.findViewById(R.id.re_view);
        //加入LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        orderAdapter=new OrderAdapter(orders);
        recyclerView.setAdapter(orderAdapter);

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
                    orders.add(order);
                }
                orderAdapter.setData(orders);
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("net: ", "failure");
            }
        });


        return view;
    }
}
