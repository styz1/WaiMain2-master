package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Order;
import linchange.example.com.waimain.entity.Shop;
import linchange.example.com.waimain.myInterface.OrderService;
import linchange.example.com.waimain.utils.StringUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView shopName,orderId,orderStatus,orderUser,orderPhone,orderAddress,orderPayMethod;
    private Button evaluate,evaluateDetail,star;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initViews();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initEvents(){
        shopName.setText(order.getBusinessName());
        orderId.setText(order.getId().toString());
        //状态为0为已完成状态为1正在配送
        if(order.getStatus().equals("0")){
            orderStatus.setText("订单已完成");
        }else {
            orderStatus.setText("订单待配送");
        }
        orderUser.setText(order.getConsignee());
        orderPhone.setText(order.getPhone());
        orderAddress.setText(order.getAddress());
        //当payMend为1是为支付宝支付， 为2时为微信支付，为3时为QQ支付
        if(order.getPayMethod()==1){
            orderPayMethod.setText("支付宝支付");
        }else if(order.getPayMethod()==2){
            orderPayMethod.setText("微信支付");
        }else if(order.getPayMethod()==3){
            orderPayMethod.setText("QQ支付");
        }
        //该订单是否已经有评价,没有显示提交评价按钮，有则显示查看评价按钮
        if(order.getIsEvaluate().equals("0")){
            evaluateDetail.setVisibility(View.GONE);
            evaluate.setVisibility(View.VISIBLE);
        }else {
            evaluate.setVisibility(View.GONE);
            evaluateDetail.setVisibility(View.VISIBLE);
        }

        //收藏显示设置
        if(order.getIsStar().equals("0")){
            star.setText("收藏订单");
            star.setBackgroundColor(Color.parseColor("#3F51B5"));
        }else{
            star.setText("已收藏");
            star.setBackgroundColor(Color.GRAY);
        }
        //收藏按钮监听
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(star.getText().equals("收藏订单")){
                    star.setText("已收藏");
                    star.setBackgroundColor(Color.GRAY);
                    setStared();
                }else{
                    star.setText("收藏订单");
                    star.setBackgroundColor(Color.parseColor("#3F51B5"));
                    setUnStared();
                }
            }
        });

        //点击提交评价按钮监听器
        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this,EvaluateActivity.class);
                intent.putExtra("order",order);
                startActivity(intent);
            }
        });
        //查看订单评价按钮监听器
        evaluateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this,EvaluateDetailActivity.class);
                intent.putExtra("order",order);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        //初始化控件
        shopName = (TextView)findViewById(R.id.txt_shopName);
        orderId = (TextView)findViewById(R.id.txt_orderId);
        orderStatus = (TextView)findViewById(R.id.txt_orderStatus);
        orderUser = (TextView)findViewById(R.id.txt_orderUser);
        orderPhone = (TextView)findViewById(R.id.txt_orderPhone);
        orderAddress = (TextView)findViewById(R.id.txt_OrderAddress);
        orderPayMethod = (TextView)findViewById(R.id.txt_payMethod);
        evaluate =(Button)findViewById(R.id.btn_evaluate);
        evaluateDetail = (Button)findViewById(R.id.btn_evaluateDetail);
        star = (Button)findViewById(R.id.btn_star);
    }

    private void initData(){
        //获取order信息
        Bundle bundle=getIntent().getExtras();
        order=(Order) bundle.get("order");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderService orderService = retrofit.create(OrderService.class);
        Call<Order> ordersCall = orderService.getOrderByOrderId(order.getId());
        ordersCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                order = response.body();
                initEvents();
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });

    }

    private void setStared(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrderService orderService = retrofit.create(OrderService.class);
        Call<Boolean> booleanCall = orderService.setStared(order.getId());
        booleanCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(OrderDetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(OrderDetailActivity.this,"收藏失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setUnStared(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrderService orderService = retrofit.create(OrderService.class);
        Call<Boolean> booleanCall = orderService.setUnStared(order.getId());
        booleanCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(OrderDetailActivity.this,"取消收藏成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(OrderDetailActivity.this,"取消收藏失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
