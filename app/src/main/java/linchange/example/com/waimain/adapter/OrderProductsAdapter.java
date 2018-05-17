package linchange.example.com.waimain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.entity.Product;

/**
 * Created by Administrator on 2018/5/10.
 */

//订单菜品适配器
public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.LinearViewHolder>{
    private Context mContext;
    private List<Product> products;
    public OrderProductsAdapter(List<Product> products) {
        this.products = products;
    }
    public void setData(List<Product> products){
        this.products=products;
    }

    @Override
    public OrderProductsAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new OrderProductsAdapter.LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_order_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(OrderProductsAdapter.LinearViewHolder holder, int position) {
        Product product=products.get(position);
        holder.product.setText(product.getName());
        String txt="×"+product.getSelectedCount()+"       "+String.valueOf(product.getPrice()*product.getSelectedCount());
        holder.price.setText(txt);
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{
        TextView product,price;
        public LinearViewHolder(View itemView) {
            super(itemView);
            product=(TextView)itemView.findViewById(R.id.product);
            price=(TextView)itemView.findViewById(R.id.price);
        }
    }
}
