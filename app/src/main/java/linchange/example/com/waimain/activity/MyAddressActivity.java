package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.adapter.AddressAdapter;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.myInterface.AddressService;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAddressActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private List<Address> addresses = new ArrayList<>();
    private LinearLayout upadateAddress;
    private User user;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AddressUpdateActivity.REQUEST_CODE: {
                    Toast.makeText(MyAddressActivity.this, "更新数据", Toast.LENGTH_SHORT).show();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://localhost:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AddressService addressService = retrofit.create(AddressService.class);
                    Call<List<Address>> addressCall = addressService.getAddress(user.getId());
                    addressCall.enqueue(new Callback<List<Address>>() {
                        @Override
                        public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                            List<Address> addresse = response.body();
                            addresses.clear();
                            for (Address address : addresse) {
                                addresses.add(address);
                            }
                            addressAdapter.setData(addresses);
                        }

                        @Override
                        public void onFailure(Call<List<Address>> call, Throwable t) {
                        }
                    });
                    addressAdapter.notifyDataSetChanged();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        initViews();
        initData();
        initEvents();
        //加入LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAddressActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        addressAdapter = new AddressAdapter(addresses, this);
        recyclerView.setAdapter(addressAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        //得到User
//        Intent intent = getIntent();
//        user = (User) intent.getSerializableExtra("user");
        user=(User) ObjectSaveUtil.readObject(MyAddressActivity.this);

        //从后台获取地址列表
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AddressService addressService = retrofit.create(AddressService.class);
        Call<List<Address>> addressCall = addressService.getAddress(user.getId());
        addressCall.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                List<Address> addresse = response.body();
                addresses.clear();
                for (Address address : addresse) {
                    addresses.add(address);
                }
                addressAdapter.setData(addresses);
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
            }
        });

    }

    private void initViews() {
        //找到装配的recycleView
        recyclerView = (RecyclerView) findViewById(R.id.address);
        //获取添加地址按钮
        upadateAddress = (LinearLayout) findViewById(R.id.btn_create_address);
    }

    private void initEvents() {

        //点击新增地址事件
        upadateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAddressActivity.this, AddressCreateActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

    }

}
