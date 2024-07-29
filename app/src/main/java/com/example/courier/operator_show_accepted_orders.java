package com.example.courier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.courier.bd.addAcceptedOrders;
import com.example.courier.courierActivity.courier_show_accepted_orders;
import com.example.courier.courierActivity.show_courier_item_accepted_orders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class operator_show_accepted_orders extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<addAcceptedOrders> listOrder;
    private DatabaseReference dbCouriers;
    private String COURIER_KEY = "ACCEPTED ORDERS";
    private ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_show_accepted_orders);

        init();
        getDataFromDB();
        setOnClickItem();
    }
    private void init(){
        listView = findViewById(R.id.listViewReadAcceptedOrdersOperator);
        listData = new ArrayList<>();
        listOrder = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);
        root = findViewById(R.id.root_operator_accepted_orders);
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                SharedPreferences sharedUsername = getSharedPreferences("sharedUsername", Context.MODE_PRIVATE);        // idk how it works but it works. there I get data from activity_courier_login: username and userid
                SharedPreferences sharedUserID = getSharedPreferences("sharedUserID", Context.MODE_PRIVATE);

                String userName = sharedUsername.getString("USERNAME", "");
                String userID = sharedUserID.getString("USERID", "");

                System.out.println("Account ACCEPTED username is: "+userName);
                System.out.println("Account ACCEPTED user ID is: "+userID);



                if(listData.size() > 0)listData.clear();
                if(listOrder.size() > 0)listOrder.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    addAcceptedOrders addOrder = ds.getValue(addAcceptedOrders.class);


                        listData.add(addOrder.cargoName);
                        listOrder.add(addOrder);




                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbCouriers.addValueEventListener(vListener);
    }

    private void setOnClickItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addAcceptedOrders addOrder = listOrder.get(position);
                Intent i = new Intent(operator_show_accepted_orders.this, operator_item_accepted_orders.class);
                i.putExtra("accepted_order_id",addOrder.id);
                i.putExtra("accepted_order_cargoName",addOrder.cargoName);
                i.putExtra("accepted_order_deliveryFrom",addOrder.deliveryFrom);
                i.putExtra("accepted_order_deliveryTo",addOrder.deliveryTo);
                i.putExtra("accepted_order_cargoSize",addOrder.cargoSize);
                i.putExtra("accepted_order_cargoWeight",addOrder.cargoWeight);
                i.putExtra("accepted_root_order_path", addOrder.id);


                i.putExtra("accepted_order_username", addOrder.courierUsername);
                i.putExtra("accepted_order_user_id", addOrder.courierID);



                startActivity(i);
            }
        });
    }
}