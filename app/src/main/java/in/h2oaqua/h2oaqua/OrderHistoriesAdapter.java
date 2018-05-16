package in.h2oaqua.h2oaqua;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ranjeet on 09-01-2018.
 */

public class OrderHistoriesAdapter extends RecyclerView.Adapter<OrderHistoriesAdapter.viewHolder> {
    private List<OrderHistory> ordersList;
    private Context mContext;


    OrderHistoriesAdapter(List<OrderHistory> ordersList, Context context) {
        this.ordersList = ordersList;
        this.mContext = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        OrderHistory orderhistory = ordersList.get(position);
        holder.orderNoWaterCans.setText(String.valueOf(orderhistory.getNoOfWaterCans()));
        holder.orderNoEmptyCans.setText(String.valueOf(orderhistory.getNoOfEmptyCans()));
        holder.orderAmount.setText(String.valueOf(orderhistory.getAmount()));
        holder.orderDate.setText(orderhistory.getDate());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView orderNoWaterCans;
        TextView orderNoEmptyCans;
        TextView orderAmount;
        TextView orderDate;

        public viewHolder(View itemView) {
            super(itemView);

            orderNoWaterCans = itemView.findViewById(R.id.order_watercans_TextView);
            orderNoEmptyCans = itemView.findViewById(R.id.order_empty_cans_TextView);
            orderAmount = itemView.findViewById(R.id.order_amount_TextView);
            orderDate = itemView.findViewById(R.id.order_date_TextView);

        }
    }
}
