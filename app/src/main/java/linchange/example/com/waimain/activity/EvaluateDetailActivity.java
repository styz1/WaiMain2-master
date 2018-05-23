package linchange.example.com.waimain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Evaluate;
import linchange.example.com.waimain.entity.Order;
import linchange.example.com.waimain.myInterface.EvaluateService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvaluateDetailActivity extends AppCompatActivity {
    private TextView myEvaluate;
    private Button checkEvaluate;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_detail);
        initViews();
        initEvaluate();
        initEvents();
    }
    //初始化事件
    private void initEvents(){
        checkEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initEvaluate(){
        if(order!=null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EvaluateService evaluateService = retrofit.create(EvaluateService.class);
            Call<Evaluate> evaluateCall = evaluateService.getEvaluateByOrderId(order.getId());
            evaluateCall.enqueue(new Callback<Evaluate>() {
                @Override
                public void onResponse(Call<Evaluate> call, Response<Evaluate> response) {
                    Evaluate evaluate = response.body();
                    if(evaluate!=null){
                        myEvaluate.setText(evaluate.getDetail());
                    }else {
                        Toast.makeText(EvaluateDetailActivity.this,"无法查看，请联系管理员",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Evaluate> call, Throwable t) {
                    Toast.makeText(EvaluateDetailActivity.this,"无法查看，请联系管理员",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    //初始化控件
    private void initViews(){
        //获取order信息
        Bundle bundle=getIntent().getExtras();
        order=(Order) bundle.get("order");
        myEvaluate = (TextView)findViewById(R.id.et_myEvaluate);
        checkEvaluate =(Button)findViewById(R.id.bt_check_evaluate);
    }

}
