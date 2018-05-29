package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.adapter.OrderProductsAdapter;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.entity.Product;
import linchange.example.com.waimain.entity.Shop;
import linchange.example.com.waimain.myInterface.OrderService;
import linchange.example.com.waimain.utils.AppApplication;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView product;
    private TextView shopName,originPrice,totalPrice,orderAddress,userName,userPhone;
    private Button chooseAddress,getAddress,submitOrder;
    private OrderProductsAdapter orderProductsAdapter;
    private ArrayList<Product> selectedProducts = new ArrayList<Product>(); //被选择的的商品数据
    private Spinner s_main_spinner;
    private Shop shop;
    private User user;
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
        //添加到activity列表中
        AppApplication.getApp().addActivity(this);
        Intent otherIntent = getIntent(); //获取信息输入界面传来的意图
        selectedProducts = otherIntent.getParcelableArrayListExtra("selectedProducts"); //从意图中获取被选择的的商品数据
        Bundle bundle=getIntent().getExtras();
        //获取到商店信息
        shop=(Shop) bundle.get("shop");
        //初始化控件
        initViews();
        //获取用户信息
        user =(User) ObjectSaveUtil.readObject(OrderActivity.this);

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
                if(shop!=null&&user!=null){
//                Integer shopId=shop.getId(),userId=Integer.valueOf(user.getId());
//                String toatoalprice=totalPrice.getText().toString();
//                String username=userName.getText().toString();
//                String userphone=userPhone.getText().toString();
//                Integer payWay=Integer.valueOf(s_main_spinner.getSelectedItemPosition()+1);
//                String orderaddress=orderAddress.getText().toString();
//                String shopname=shop.getName();
//                String icon=shop.getIcon();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConfig.SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                OrderService orderService =retrofit.create(OrderService.class);
//                    Call<Boolean> booleanCall = orderService.newOrder(shopId,userId,"0",toatoalprice,username,userphone,payWay,
//                            "0","",orderaddress,shopname,icon);

                Call<Boolean> booleanCall = orderService.newOrder(shop.getId(),Integer.valueOf(user.getId()),"0",totalPrice.getText().toString(),userName.getText().toString(),
                        userPhone.getText().toString(),Integer.valueOf(s_main_spinner.getSelectedItemPosition()+1),"0","",orderAddress.getText().toString(),
                        shop.getName(),shop.getIcon());
                booleanCall.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Boolean b = response.body();
                        if(b==null){
                            Toast.makeText(OrderActivity.this,"订单提交失败，请重新提交",Toast.LENGTH_SHORT).show();
                        }else if(b){
                            Toast.makeText(OrderActivity.this,"订单提交成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(OrderActivity.this,SuccessActivity.class);
                            intent.putExtra("selectedProducts",selectedProducts);
                            intent.putExtra("address",orderAddress.getText().toString());
                            intent.putExtra("phone",userPhone.getText().toString());
                            startActivity(intent);}
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(OrderActivity.this,"订单提交失败，请重新提交",Toast.LENGTH_SHORT).show();
                    }
                });


                }
            }
        });

        //给下拉列表设置适配器
        final String provinces[]={"支付宝","微信","QQ"};
        int images[]={R.drawable.ali_pay,R.drawable.wechat_pay,R.drawable.qq_pay};

        List<Map<String,Object>> list=new ArrayList<>();
        for (int i = 0; i < provinces.length; i++) {
            Map<String,Object> map=new HashMap<>();
            map.put("title",provinces[i]);
            map.put("image",images[i]);
            list.add(map);
        }

        //适配器
        //SimpleAdapter
        SimpleAdapter adapterSpinner=new SimpleAdapter(this,list,android.R.layout.activity_list_item,new String[]{"title","image"},new int[]{android.R.id.text1,android.R.id.icon});
        s_main_spinner.setAdapter(adapterSpinner);

        //给下列列表设置选择事件
        s_main_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        //得到支付方式
        s_main_spinner = (Spinner) findViewById(R.id.s_main_spinner);

    }

}
