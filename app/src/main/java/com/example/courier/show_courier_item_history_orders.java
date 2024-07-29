package com.example.courier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class show_courier_item_history_orders extends AppCompatActivity {

    private TextView tvOrderID, tvFinishTime, tvDeliveryFrom, tvDeliveryTo, tvCargoSize, tvCargoWeight, tvShippingBy;
    private DatabaseReference dbCouriers;
    private String tableCOMPLETED_ORDERS = "COMPLETED ORDERS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_courier_item_history_orders);

        init();
        getIntentMain();

    }
    private void init() {
        tvOrderID = findViewById(R.id.tvCargoNameCourierHistoryOrder);
        tvDeliveryFrom = findViewById(R.id.tvDeliveryFromCourierHistoryOrder);
        tvDeliveryTo = findViewById(R.id.tvDeliveryToCourierHistoryOrder);
        tvCargoSize = findViewById(R.id.tvCargoSizeCourierHistoryOrder);
        tvCargoWeight = findViewById(R.id.tvCargoWeightCourierHistoryOrder);
        tvShippingBy = findViewById(R.id.tvShippingByCourierHistoryOrder);
        tvFinishTime = findViewById(R.id.tvFinishTimeCourierHistoryOrder);



        dbCouriers = FirebaseDatabase.getInstance().getReference(tableCOMPLETED_ORDERS);
    }

    private void getIntentMain() {
        Intent i = getIntent();
        if (i != null) {
            tvOrderID.setText(i.getStringExtra("history_order_id"));
            tvDeliveryFrom.setText(i.getStringExtra("history_order_delivery_from"));
            tvDeliveryTo.setText(i.getStringExtra("history_order_delivery_to"));
            tvCargoSize.setText(i.getStringExtra("history_order_cargo_size"));
            tvCargoWeight.setText(i.getStringExtra("history_order_cargo_weight"));
            tvShippingBy.setText(i.getStringExtra("history_order_shipping_by"));
            tvFinishTime.setText(i.getStringExtra("history_order_finish_time"));

        }
    }
}