package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.adapter.OrderProductsAdapter;
import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.entity.Product;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView product;
    private TextView shopName,originPrice,totalPrice,orderAddress,userName,userPhone;
    private Button chooseAddress,getAddress,submitOrder;
    private OrderProductsAdapter orderProductsAdapter;
    private ArrayList<Product> selectedProducts = new ArrayList<Product>(); //被选择的的商品数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String address = data.getStringExtra("address");
                    //修改地址
                    orderAddress.setText(address);
                    Log.d("address",address);
                    Toast.makeText(OrderActivity.this,"定位成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Address address =(Address)data.getSerializableExtra("address");
                    //修改信息
                    orderAddress.setText(address.getSummary()+address.getDetail());
                    userName.setText(address.getName());
                    userPhone.setText(address.getPhone());
                    Toast.makeText(OrderActivity.this,"选择地址成功",Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent otherIntent = getIntent(); //获取信息输入界面传来的意图
        selectedProducts = otherIntent.getParcelableArrayListExtra("selectedProducts"); //从意图中获取被选择的的商品数据
        //初始化控件
        initViews();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderActivity.this);
        product.setLayoutManager(linearLayoutManager);
        orderProductsAdapter=new OrderProductsAdapter(selectedProducts);
        product.setAdapter(orderProductsAdapter);

        //选择地址页面
        chooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this,MyAddressActivity.class);
                startActivityForResult(intent,2);
            }
        });

        //定位页面监听器
        getAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this,BaiduMapActivity.class);
                startActivityForResult(intent,1);
            }
        });

        //设置到商店名字
        shopName.setText(selectedProducts.get(0).getShopName());

        //设置订单的价格
        Double price = 0.0;
        for(Product product:selectedProducts){
            price =price + ((Double)product.getPrice()*product.getSelectedCount());
        }
        originPrice.setText(String.valueOf(price));
        totalPrice.setText(String.valueOf(price));

        //点击提交订单按钮事件
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this,"订单提交成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(OrderActivity.this,SuccessActivity.class);
                intent.putExtra("selectedProducts",selectedProducts);
                intent.putExtra("address",orderAddress.getText().toString());
                intent.putExtra("phone",userPhone.getText().toString());
                startActivity(intent);
            }
        });

    }

    private void initViews(){
        //得到里面的菜品recycleView
        product = (RecyclerView)findViewById(R.id.products);
        //得到选择地址按钮
        chooseAddress = (Button)findViewById(R.id.updateAddress);
        //得到定位Button
        getAddress=(Button)findViewById(R.id.getAddress);
        //得到商店名称TextView
        shopName=(TextView)findViewById(R.id.txt_name1);
        //得到订单价格
        originPrice=(TextView)findViewById(R.id.txt_origin_price);
        totalPrice=(TextView)findViewById(R.id.txt_total_price);
        //得到提交Button
        submitOrder=(Button)findViewById(R.id.btn_submit_order);
        //得到配送地址TextView
        orderAddress = (TextView)findViewById(R.id.tv_address);
        //得到订餐人姓名
        userName = (TextView)findViewById(R.id.user_name);
        //得到订餐人电话
        userPhone = (TextView)findViewById(R.id.user_phone);

    }

}
