package linchange.example.com.waimain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Address;
import linchange.example.com.waimain.myInterface.AddressService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressCreateActivity extends AppCompatActivity {
    private EditText name,phone,summary,detail;
    private RadioButton male,female;
    private Button update;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_update);
        //初始化控件
        initViews();
        //获取User对象
        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer gender;
                if(male.isChecked()){
                    gender=0;
                }else {
                    gender=1;
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConfig.SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                AddressService addressService =retrofit.create(AddressService.class);
                Call<Address> addressCall = addressService.newAddress(name.getText().toString(),phone.getText().toString(),summary.getText().toString(),detail.getText().toString(),gender.toString(),user.getId());
                addressCall.enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        Address address = response.body();
                        Toast.makeText(AddressCreateActivity.this,"添加地址成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        Toast.makeText(AddressCreateActivity.this,"添加失败，请稍后再试",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void initViews(){
        //初始化控件
        name = (EditText)findViewById(R.id.et_input_name);
        phone = (EditText)findViewById(R.id.et_input_phone);
        summary = (EditText)findViewById(R.id.et_poi_address);
        detail = (EditText)findViewById(R.id.et_detail_address);
        male = (RadioButton)findViewById(R.id.rb_male);
        female = (RadioButton)findViewById(R.id.rb_female);
        update = (Button)findViewById(R.id.btn_submit);
    }
}
