package in.h2oaqua.h2oaqua;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class orderHistoryActivity extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        ArrayList<OrderHistory> orderHistories = new ArrayList<>();
        orderHistories.add(0,new OrderHistory(1, 1, 30, "1 jan"));
        orderHistories.add(0,new OrderHistory(2, 2, 50, "1 jan"));
        orderHistories.add(0, new OrderHistory(3, 3, 75, "1 jan"));


        RecyclerView orderRecyclerView = findViewById(R.id.orderHistoryRecyclerView);
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter adapter = new OrderHistoriesAdapter(orderHistories, this);
        orderRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
