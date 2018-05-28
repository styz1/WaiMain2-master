package linchange.example.com.waimain.frament;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.activity.BaiduMapActivity;
import linchange.example.com.waimain.adapter.ShopAdapter;
import linchange.example.com.waimain.context.AppConfig;
import linchange.example.com.waimain.entity.Business;
import linchange.example.com.waimain.entity.Shop;
import linchange.example.com.waimain.myInterface.BusinessService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/4/17.
 */


//商户列表界面
@SuppressLint("ValidFragment")
public class shopFragment extends Fragment {
    private ImageView map;
    private RecyclerView recyclerView;
    private ImageView search;
    private EditText et_shopName;
    private ShopAdapter mLinearAdapter;
    private PullToRefreshLayout refreshLayout;
    private List<Shop> shops = new ArrayList<>();

    public static shopFragment newInstance(String content) {
        shopFragment myFragment = new shopFragment();
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //获取fragment的布局文件
        View view = inflater.inflate(R.layout.fg_content, container, false);

        //下拉组件
        refreshLayout=(PullToRefreshLayout)view.findViewById(R.id.refresh);
        refreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
                        refreshLayout.finishRefresh();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(AppConfig.SERVER_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        BusinessService businessService = retrofit.create(BusinessService.class);
                        Call<List<Business>> businessCall = businessService.getBusiness();
                        businessCall.enqueue(new Callback<List<Business>>() {
                            @Override
                            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
                                List<Business> result = response.body();
                                shops.clear();
                                for (Business business : result) {
                                    Shop shop = new Shop();
                                    shop.setId(business.getId());
                                    shop.setAddress(business.getAddress());
                                    shop.setBulletin(business.getBulletin());
                                    shop.setPhone(business.getPhone());
                                    shop.setName(business.getName());
                                    shop.setSubTitle(business.getBulletin());
                                    shop.setIcon(business.getIcon());
                                    shops.add(shop);
                                }
                                mLinearAdapter.setData(shops);
                            }

                            @Override
                            public void onFailure(Call<List<Business>> call, Throwable t) {
                                Log.d("net: ", "failure");
                            }
                        });
                        Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束加载更多
                        refreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });

        //得到里面的recycleView
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mLinearAdapter = new ShopAdapter(shops);
        recyclerView.setAdapter(mLinearAdapter);

        System.out.println("Start");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BusinessService businessService = retrofit.create(BusinessService.class);
        Call<List<Business>> businessCall = businessService.getBusiness();
        businessCall.enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
                List<Business> result = response.body();
                shops.clear();
                for (Business business : result) {
                    Shop shop = new Shop();
                    shop.setId(business.getId());
                    shop.setAddress(business.getAddress());
                    shop.setBulletin(business.getBulletin());
                    shop.setPhone(business.getPhone());
                    shop.setName(business.getName());
                    shop.setSubTitle(business.getBulletin());
                    shop.setIcon(business.getIcon());
                    shops.add(shop);
                }
                mLinearAdapter.setData(shops);
            }

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                Log.d("net: ", "failure");
            }
        });
        map = (ImageView)view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), BaiduMapActivity.class);
                startActivity(intent);
            }
        });

        //获得要搜索的商店名称
        et_shopName =(EditText)view.findViewById(R.id.search_edit_text);

        //搜索按钮监听
        search = (ImageView)view.findViewById(R.id.img_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConfig.SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                BusinessService businessService = retrofit.create(BusinessService.class);
                if(!TextUtils.isEmpty(et_shopName.getText())) {
                    Call<List<Business>> businessCall = businessService.getBusinessByName(et_shopName.getText().toString());
                    businessCall.enqueue(new Callback<List<Business>>() {
                        @Override
                        public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
                            List<Business> result = response.body();
                            shops.clear();
                            for (Business business : result) {
                                Shop shop = new Shop();
                                shop.setId(business.getId());
                                shop.setAddress(business.getAddress());
                                shop.setBulletin(business.getBulletin());
                                shop.setPhone(business.getPhone());
                                shop.setName(business.getName());
                                shop.setSubTitle(business.getBulletin());
                                shop.setIcon(business.getIcon());
                                shops.add(shop);
                            }
                            mLinearAdapter.setData(shops);
                        }

                        @Override
                        public void onFailure(Call<List<Business>> call, Throwable t) {

                        }
                    });
                }
            }
        });


        return view;
    }
}
