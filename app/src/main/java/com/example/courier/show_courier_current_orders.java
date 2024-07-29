package com.example.courier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.courier.bd.addAcceptedOrders;
import com.example.courier.activity_courier_login;
import com.example.courier.bd.addOrder;
import com.example.courier.bd.showOrdersItemActivity;
import com.example.courier.courierActivity.courier_current_orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class show_courier_current_orders extends AppCompatActivity {

    private TextView tvOrderID, tvCargoName, tvDeliveryFrom, tvDeliveryTo, tvCargoSize, tvCargoWeight;
    private String rootOrderPath, deletingItemOrderTemp;
    private ConstraintLayout root;
    private Button btnCourierAcceptOrderCourierCurrentOrder;
    private String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());

    private DatabaseReference dbCouriers;
    private String tableACCEPTED_ORDERS = "ACCEPTED ORDERS";
    private String tableORDERS = "ORDERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_courier_current_orders);
        init();
        getIntentMain();

        btnCourierAcceptOrderCourierCurrentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // rewrite current order but add field "shippingBy = username"

                SharedPreferences sharedUsername = getSharedPreferences("sharedUsername", Context.MODE_PRIVATE);        // idk how it works but it works. there I get data from activity_courier_login: username and userid
                SharedPreferences sharedUserID = getSharedPreferences("sharedUserID", Context.MODE_PRIVATE);

                String userName = sharedUsername.getString("USERNAME", "");
                String userID = sharedUserID.getString("USERID", "");



                String cargoName = tvCargoName.getText().toString();
                String deliveryFrom = tvDeliveryFrom.getText().toString();
                String deliveryTo = tvDeliveryTo.getText().toString();
                String id = tvOrderID.getText().toString();
                String cargoSize = tvCargoSize.getText().toString();
                String cargoWeight = tvCargoWeight.getText().toString();

                String courierID = userID;
                String courierUsername = userName;
                String shippingBy = userName;

                addAcceptedOrders addAcceptedOrders = new addAcceptedOrders(cargoName, deliveryFrom, deliveryTo, id, cargoSize, cargoWeight, shippingBy, courierID, courierUsername);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(tableACCEPTED_ORDERS).child(cargoName).setValue(addAcceptedOrders);


                // deleting current order from db ORDERS cause we move it to bd ACCEPTED ORDERS


                dbCouriers = FirebaseDatabase.getInstance().getReference(tableORDERS);
                String deletingTmp = cargoName;
                dbCouriers.child(deletingTmp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            finish();
                        } else {
                            Snackbar.make(root,"Неизвестная ошибка", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });





            }
        });

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
        tvOrderID = findViewById(R.id.tvCargoNameCourierCurrentOrder);
        tvDeliveryFrom = findViewById(R.id.tvDeliveryFromCourierCurrentOrder);
        tvDeliveryTo = findViewById(R.id.tvDeliveryToCourierCurrentOrder);
        tvCargoSize = findViewById(R.id.tvCargoSizeCourierCurrentOrder);
        tvCargoWeight = findViewById(R.id.tvCargoWeightCourierCurrentOrder);
        tvCargoName = findViewById(R.id.tvCargoNameCourierCurrentOrder);

        btnCourierAcceptOrderCourierCurrentOrder = findViewById(R.id.btnCourierAcceptOrderCourierCurrentOrder);

        root = findViewById(R.id.rootCourierCurrentOrders);

        dbCouriers = FirebaseDatabase.getInstance().getReference(tableACCEPTED_ORDERS);
    }

    private void getIntentMain() {
        Intent i = getIntent();
        if (i != null) {
            tvOrderID.setText(i.getStringExtra("order_id"));
            tvDeliveryFrom.setText(i.getStringExtra("order_deliveryFrom"));
            tvDeliveryTo.setText(i.getStringExtra("order_deliveryTo"));
            tvCargoSize.setText(i.getStringExtra("order_cargoSize"));
            tvCargoWeight.setText(i.getStringExtra("order_cargoWeight"));
            rootOrderPath = (i.getStringExtra("root_order_path"));
        }
    }
}