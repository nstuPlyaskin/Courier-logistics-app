package com.example.courier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.courier.bd.addCompletedOrders;
import com.example.courier.courierActivity.show_courier_item_accepted_orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class operator_item_accepted_orders extends AppCompatActivity {

    private TextView tvOrderID, tvCargoName, tvDeliveryFrom, tvDeliveryTo, tvCargoSize, tvCargoWeight, tvShippingBy;
    private String rootOrderPath, deletingItemOrderTemp;
    private Button btnOrderComplete;
    private DatabaseReference dbCouriers;
    private String tableACCEPTED_ORDERS = "ACCEPTED ORDERS";
    private String tableCOMPLETED_ORDERS = "COMPLETED ORDERS";
    private String tableORDERS = "ORDERS";
    private String currentDate = new SimpleDateFormat("Число dd Месяц MM Год yy Время HH mm").format(Calendar.getInstance().getTime());
    private String hashDateID = new SimpleDateFormat("ddMMyyHHmmss").format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_item_accepted_orders);

        init();
        getIntentMain();

        tvDeliveryFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deliveryFrom = null;
                deliveryFrom = tvDeliveryFrom.getText().toString();
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+deliveryFrom+"&mode=d");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        tvDeliveryTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deliveryTo = null;
                deliveryTo = tvDeliveryTo.getText().toString();
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+deliveryTo+"&mode=d");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


    }

    private void init() {
        tvOrderID = findViewById(R.id.tvCargoNameOperatorAcceptedOrder);
        tvDeliveryFrom = findViewById(R.id.tvDeliveryFromOperatorAcceptedOrder);
        tvDeliveryTo = findViewById(R.id.tvDeliveryToOperatorAcceptedOrder);
        tvCargoSize = findViewById(R.id.tvCargoSizeOperatorAcceptedOrder);
        tvCargoWeight = findViewById(R.id.tvCargoWeightOperatorAcceptedOrder);
        tvShippingBy = findViewById(R.id.tvShippingByOperatorAcceptedOrder);

        btnOrderComplete = findViewById(R.id.btnOrderComplete);

        dbCouriers = FirebaseDatabase.getInstance().getReference(tableACCEPTED_ORDERS);
    }

    private void getIntentMain() {
        Intent i = getIntent();
        if (i != null) {
            tvOrderID.setText(i.getStringExtra("accepted_order_cargoName"));
            tvDeliveryFrom.setText(i.getStringExtra("accepted_order_deliveryFrom"));
            tvDeliveryTo.setText(i.getStringExtra("accepted_order_deliveryTo"));
            tvCargoSize.setText(i.getStringExtra("accepted_order_cargoSize"));
            tvCargoWeight.setText(i.getStringExtra("accepted_order_cargoWeight"));
            rootOrderPath = (i.getStringExtra("accepted_root_order_path"));


            tvShippingBy.setText(i.getStringExtra("accepted_order_username"));
        }
    }

    }


