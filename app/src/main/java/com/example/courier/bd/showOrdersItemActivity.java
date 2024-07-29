package com.example.courier.bd;

//

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.courier.R;
import com.example.courier.WelcomePage;
import com.example.courier.activity_operator_login;
import com.example.courier.activity_operator_read_opders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.List;

public class showOrdersItemActivity extends AppCompatActivity {
    private TextView tvOrderID, tvCargoName, tvDeliveryFrom, tvDeliveryTo, tvCargoSize, tvCargoWeight;
    private String rootOrderPath, deletingItemOrderTemp;
    private Button btnDeleteItemOrder;
    private DatabaseReference dbCouriers;
    private ConstraintLayout root, rootActivityOperatorReadOpders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_orders_item_activity);
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
        tvOrderID = findViewById(R.id.tvCargoName);
        tvCargoName = findViewById(R.id.tvOrderID);
        tvDeliveryFrom = findViewById(R.id.tvDeliveryFrom);
        tvDeliveryTo = findViewById(R.id.tvDeliveryTo);
        tvCargoSize = findViewById(R.id.tvCargoSize);
        tvCargoWeight = findViewById(R.id.tvCargoWeight);

        root = findViewById(R.id.rootShowOrdersItemActivity);
        rootActivityOperatorReadOpders = findViewById(R.id.rootActivityOperatorReadOpders);

        btnDeleteItemOrder = findViewById(R.id.btnDeleteItemOrder);


    }

    private void getIntentMain() {
        Intent i = getIntent();
        if (i != null) {
            tvOrderID.setText(i.getStringExtra("order_id"));
            tvCargoName.setText(i.getStringExtra("order_cargoName"));
            tvDeliveryFrom.setText(i.getStringExtra("order_deliveryFrom"));
            tvDeliveryTo.setText(i.getStringExtra("order_deliveryTo"));
            tvCargoSize.setText(i.getStringExtra("order_cargoSize"));
            tvCargoWeight.setText(i.getStringExtra("order_cargoWeight"));

            rootOrderPath = (i.getStringExtra("root_order_path"));
        }


           btnDeleteItemOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = rootOrderPath;

                    DatabaseReference deletingUsername = FirebaseDatabase.getInstance().getReference(name); // на выходе получаем путь до записи name в бд ORDERS

                    if(!name.isEmpty()){
                        deleteData(name);
                        return;
                    }
                }
            });

        }

     private void deleteData(String name) {

       dbCouriers = FirebaseDatabase.getInstance().getReference("ORDERS");
        String deletingTmp = name;
        dbCouriers.child(deletingTmp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
             public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){


                    finish();

                } else {
                    Snackbar.make(root,"Error", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

}

