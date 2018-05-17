package linchange.example.com.waimain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.entity.Order;

/**
 * Created by Administrator on 2018/4/17.
 */

//订单列表适配器
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.LinearViewHolder> {

    private Context mContext;
    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrderAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_order_item, parent, false));
    }
    public void setData(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(OrderAdapter.LinearViewHolder holder, int position) {
        Order order = orders.get(position);
        Glide.with(mContext)
                .load(order.getBusinessIcon())
                .into(holder.imageView);
        holder.shopName.setText(order.getBusinessName());
        holder.status.setText(order.getStatus());
        holder.address.setText(order.getAddress());
        holder.totalPrice.setText(order.getTotalPrice());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            imageView = (ImageView) itemView.findViewById(R.id.shop_icon);
            shopName = (TextView) itemView.findViewById(R.id.shop);
            status = (TextView) itemView.findViewById(R.id.status);
            address = (TextView) itemView.findViewById(R.id.detail);
            totalPrice =(TextView) itemView.findViewById(R.id.total_price);
        }
    }

}
