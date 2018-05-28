package linchange.example.com.waimain.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import linchange.example.com.waimain.R;
import linchange.example.com.waimain.activity.OrderDetailActivity;
import linchange.example.com.waimain.entity.Evaluate;
import linchange.example.com.waimain.entity.Order;

/**
 * Created by Administrator on 2018/5/27.
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.LinearViewHolder>{
    private Activity mContext;
    private List<Evaluate> evaluates;
    private Order order=new Order();

    public EvaluateAdapter(List<Evaluate> evaluates, Activity activity){
        mContext = activity;
        this.evaluates = evaluates;
    }

    public void setData(List<Evaluate> evaluates){
        this.evaluates = evaluates;
        notifyDataSetChanged();
    }

    @Override
    public LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateAdapter.LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.evaluate_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LinearViewHolder holder, int position) {
        final Evaluate evaluate =evaluates.get(position);
        holder.evaluateId.setText(evaluate.getOrderId().toString());
        holder.evaluateDetail.setText(evaluate.getDetail().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                order.setId(evaluate.getOrderId());
                intent.putExtra("order",order);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return evaluates == null ? 0 : evaluates.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView evaluateId,evaluateDetail;

        public LinearViewHolder(View itemView) {
            super(itemView);
            evaluateId = (TextView) itemView.findViewById(R.id.evaluate_id);
            evaluateDetail = (TextView) itemView.findViewById(R.id.evaluate_detail);

        }
    }
}
