package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.entity.Order;
import linchange.example.com.waimain.entity.Shop;
import linchange.example.com.waimain.utils.StringUtil;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView shopName,orderId,orderStatus,orderUser,orderPhone,orderAddress,orderPayMethod;
    private Button evaluate,evaluateDetail;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initViews();
        initEvents();
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
        }else {
            evaluate.setVisibility(View.GONE);
        }
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

            }
        });
    }

    private void initViews(){
        //获取order信息
        Bundle bundle=getIntent().getExtras();
        order=(Order) bundle.get("order");
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

    }
}
