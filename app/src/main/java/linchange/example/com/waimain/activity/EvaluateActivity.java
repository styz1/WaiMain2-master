package linchange.example.com.waimain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Evaluate;
import linchange.example.com.waimain.entity.Order;
import linchange.example.com.waimain.entity.Shop;
import linchange.example.com.waimain.myInterface.EvaluateService;
import linchange.example.com.waimain.myInterface.OrderService;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvaluateActivity extends AppCompatActivity {
    private EditText et_Evaluate;
    private Button bt_Evaluate;
    private Order order;
    private User user;
    private Evaluate evaluate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        initViews();
        initEvents();

    }

    private void initEvents(){
        bt_Evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_Evaluate.getText().toString())){
                    Toast.makeText(EvaluateActivity.this,"评价内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(AppConfig.SERVER_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    EvaluateService evaluateService = retrofit.create(EvaluateService.class);
                    Call<Evaluate> booleanCall =evaluateService.newEvaluate(order.getId(),order.getBusinessId(),order.getUserId(),et_Evaluate.getText().toString());
                    booleanCall.enqueue(new Callback<Evaluate>() {
                        @Override
                        public void onResponse(Call<Evaluate> call, Response<Evaluate> response) {
                            evaluate=response.body();
                            if(evaluate!=null){
                                Toast.makeText(EvaluateActivity.this,"成功创建评价",Toast.LENGTH_SHORT).show();
                                setEvluated();
                            }else {
                                Toast.makeText(EvaluateActivity.this,"无法提交评价",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Evaluate> call, Throwable t) {
                            Toast.makeText(EvaluateActivity.this,"无法提交评价",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    private void setEvluated(){
        if(evaluate!=null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            OrderService orderService = retrofit.create(OrderService.class);
            Call<Boolean> booleanCall12 = orderService.setEvaluated(evaluate.getId(), order.getId());
            booleanCall12.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Boolean b = response.body();
                    if (b) {
                        Toast.makeText(EvaluateActivity.this, "成功提交评价", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EvaluateActivity.this, "无法访问服务器，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(EvaluateActivity.this, "无法访问服务器，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initViews(){
        //获取order信息
        Bundle bundle=getIntent().getExtras();
        order=(Order) bundle.get("order");
        //获取当前用户信息
        user = (User) ObjectSaveUtil.readObject(EvaluateActivity.this);
        et_Evaluate = (EditText)findViewById(R.id.et_evaluate);
        bt_Evaluate = (Button)findViewById(R.id.bt_submit_evaluate);

    }
}
