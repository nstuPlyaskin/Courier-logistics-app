package com.example.courier.courierActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import com.example.courier.OperatorHomePage;
import com.example.courier.R;
import com.example.courier.activity_operator_login;
import com.example.courier.bd.addAcceptedOrders;
import com.example.courier.bd.addCompletedOrders;
import com.example.courier.bd.addOrder;
import com.example.courier.message.activity_chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class show_courier_item_accepted_orders extends AppCompatActivity {

    private TextView tvOrderID, tvCargoName, tvDeliveryFrom, tvDeliveryTo, tvCargoSize, tvCargoWeight, tvShippingBy;
    private String rootOrderPath, deletingItemOrderTemp;
    private Button btnOrderComplete, btnChatWithOperator;
    private DatabaseReference dbCouriers;
    private String tableACCEPTED_ORDERS = "ACCEPTED ORDERS";
    private String tableCOMPLETED_ORDERS = "COMPLETED ORDERS";
    private String tableORDERS = "ORDERS";
    private String currentDate = new SimpleDateFormat("Число dd Месяц MM Год yy Время HH mm").format(Calendar.getInstance().getTime());
    private String hashDateID = new SimpleDateFormat("ddMMyyHHmmss").format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_courier_item_accepted_orders);
        init();
        getIntentMain();


        btnOrderComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                dialog = new Dialog(show_courier_item_accepted_orders.this);
                dialog.setContentView(R.layout.dialog_box_courier_complete_order);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);

                Button btnDialogYes = dialog.findViewById(R.id.btnDialogYes);
                Button btnDialogNo = dialog.findViewById(R.id.btnDialogNo);

                btnDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dbCouriers = FirebaseDatabase.getInstance().getReference(tableACCEPTED_ORDERS);
                        String deletingTmp = tvOrderID.getText().toString();
                        dbCouriers.child(deletingTmp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    SharedPreferences sharedUsername = getSharedPreferences("sharedUsername", Context.MODE_PRIVATE);        // idk how it works but it works. there I get data from activity_courier_login: username and userid
                                    SharedPreferences sharedUserID = getSharedPreferences("sharedUserID", Context.MODE_PRIVATE);

                                    String userName = sharedUsername.getString("USERNAME", "");
                                    String userID = sharedUserID.getString("USERID", "");


                                    String cargoName = tvOrderID.getText().toString();
                                    String deliveryFrom = tvDeliveryFrom.getText().toString();
                                    String deliveryTo = tvDeliveryTo.getText().toString();
                                    String id = tvOrderID.getText().toString();
                                    String cargoSize = tvCargoSize.getText().toString();
                                    String cargoWeight = tvCargoWeight.getText().toString();
                                    String finishTime = currentDate;

                                    String courierID = userID;
                                    String courierUsername = userName;
                                    String shippingBy = userName;

                                    addCompletedOrders addCompletedOrders = new addCompletedOrders(cargoName, deliveryFrom, deliveryTo, id, cargoSize, cargoWeight, shippingBy, courierID, courierUsername, finishTime);

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference.child(tableCOMPLETED_ORDERS).child(cargoName+currentDate.hashCode()).setValue(addCompletedOrders);



                                    dialog.cancel();
                                    finish();
                                }
                            }
                        });


                    }
                });

                btnDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
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


        btnChatWithOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(show_courier_item_accepted_orders.this, activity_chat.class);
                startActivity(intent);

            }
        });


}

    private void init() {
        tvOrderID = findViewById(R.id.tvCargoNameCourierAcceptedOrder);
        tvDeliveryFrom = findViewById(R.id.tvDeliveryFromCourierAcceptedOrder);
        tvDeliveryTo = findViewById(R.id.tvDeliveryToCourierAcceptedOrder);
        tvCargoSize = findViewById(R.id.tvCargoSizeCourierAcceptedOrder);
        tvCargoWeight = findViewById(R.id.tvCargoWeightCourierAcceptedOrder);
        tvShippingBy = findViewById(R.id.tvShippingByCourierAcceptedOrder);

        btnOrderComplete = findViewById(R.id.btnOrderComplete);
        btnChatWithOperator = findViewById(R.id.btnChatWithOperator);

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
