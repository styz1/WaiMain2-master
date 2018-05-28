package linchange.example.com.waimain.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.activity.ShoppingActivity;
import linchange.example.com.waimain.entity.Shop;


/**
 * Created by Administrator on 2018/4/17.
 */


//商店列表适配器
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.LinearViewHolder> {

    private Context mContext;
    private List<Shop> shops;

    public ShopAdapter(List<Shop> shops) {
        this.shops = shops;
    }

    public void setData(List<Shop> shops) {
        this.shops = shops;
        notifyDataSetChanged();
    }

    @Override
    public ShopAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_linear_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ShopAdapter.LinearViewHolder holder, final int position) {
        final Shop shop = shops.get(position);
        if(TextUtils.isEmpty(shop.getIcon())){
            Glide.with(mContext)
                    .load(R.drawable.ic_lanucher)
                    .into(holder.imageView);
        }else {
            Glide.with(mContext)
                    .load(shop.getIcon())
                    .into(holder.imageView);
        }
        holder.textView1.setText(shop.getName());
        holder.textView2.setText(shop.getSubTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShoppingActivity.class);
                intent.putExtra("shop",shop);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shops == null ? 0 : shops.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView1, textView2;

        public LinearViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgtou);
            textView1 = (TextView) itemView.findViewById(R.id.name);
            textView2 = (TextView) itemView.findViewById(R.id.says);
        }
    }

}
