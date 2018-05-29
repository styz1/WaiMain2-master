package linchange.example.com.waimain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.adapter.EvaluateAdapter;
import linchange.example.com.waimain.bean.User;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Evaluate;
import linchange.example.com.waimain.myInterface.EvaluateService;
import linchange.example.com.waimain.utils.AppApplication;
import linchange.example.com.waimain.utils.ObjectSaveUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyEvaluateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Evaluate> evaluates=new ArrayList<>();
    private EvaluateAdapter evaluateAdpter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluate);
        //添加到activity列表中
        AppApplication.getApp().addActivity(this);
        initViews();
        initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyEvaluateActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建适配器
        evaluateAdpter = new EvaluateAdapter(evaluates, this);
        recyclerView.setAdapter(evaluateAdpter);
    }
    private void initData(){
        user=(User) ObjectSaveUtil.readObject(MyEvaluateActivity.this);

        //从后台获取评价列表
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EvaluateService evaluateService = retrofit.create(EvaluateService.class);
        Call<List<Evaluate>> evaluateCall= evaluateService.getEvaluateByUserId(Integer.valueOf(user.getId()));
        evaluateCall.enqueue(new Callback<List<Evaluate>>() {
            @Override
            public void onResponse(Call<List<Evaluate>> call, Response<List<Evaluate>> response) {
                List<Evaluate> evaluateList= response.body();
                evaluates.clear();
                for(Evaluate evaluate : evaluateList){
                    evaluates.add(evaluate);
                }
                evaluateAdpter.setData(evaluates);
            }

            @Override
            public void onFailure(Call<List<Evaluate>> call, Throwable t) {
                Toast.makeText(MyEvaluateActivity.this,"请求服务器失败",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.my_evaluates);
    }
}
