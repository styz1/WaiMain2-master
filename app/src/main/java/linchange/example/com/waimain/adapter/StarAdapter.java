package linchange.example.com.waimain.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.activity.OrderDetailActivity;
import linchange.example.com.waimain.entity.Evaluate;
import linchange.example.com.waimain.entity.Order;

/**
 * Created by Administrator on 2018/5/28.
 */

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.LinearViewHolder>{
    private Activity mContext;
    private List<Order> orders;

    public StarAdapter(List<Order> orders, Activity activity){
        mContext = activity;
        this.orders = orders;
    }

    public void setData(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }
    @Override
    public LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.star_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LinearViewHolder holder, int position) {
        final Order order = orders.get(position);
        Glide.with(mContext)
                .load(order.getBusinessIcon())
                .into(holder.imageView);
        holder.shopName.setText(order.getBusinessName());
        if(order.getStatus().equals("0")){
            holder.status.setText("订单已完成");
        }else if(order.getStatus().equals("1")){
            holder.status.setText("订单待配送");
        }
        holder.address.setText(order.getAddress());
        holder.totalPrice.setText(order.getTotalPrice());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("order",order);
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("order",order);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView shopName, status, address ,totalPrice;

        public LinearViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_shop_icon);
            shopName = (TextView) itemView.findViewById(R.id.txt_shop);
            status = (TextView) itemView.findViewById(R.id.txt_status);
            address = (TextView) itemView.findViewById(R.id.txt_detail);
            totalPrice =(TextView) itemView.findViewById(R.id.txt_total_price);
        }
    }
}
