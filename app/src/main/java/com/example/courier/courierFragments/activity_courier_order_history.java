package com.example.courier.courierFragments;

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

import com.example.courier.R;
import com.example.courier.bd.addAcceptedOrders;
import com.example.courier.bd.addOrder;
import com.example.courier.courierActivity.courier_current_orders;
import com.example.courier.courierActivity.courier_show_accepted_orders;
import com.example.courier.courierActivity.show_courier_item_accepted_orders;
import com.example.courier.show_courier_current_orders;
import com.example.courier.show_courier_item_history_orders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class activity_courier_order_history extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<addAcceptedOrders> listOrder;
    private DatabaseReference dbCouriers;
    private String COURIER_KEY = "COMPLETED ORDERS";
    private String currentDate = new SimpleDateFormat("dd.MM.yyyy Время HH:mm").format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_order_history);

        init();
        getDataFromDB();
        setOnClickItem();

    }
    private void init(){
        listView = findViewById(R.id.listViewCourierOrderHistory);
        listData = new ArrayList<>();
        listOrder = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                SharedPreferences sharedUsername = getSharedPreferences("sharedUsername", Context.MODE_PRIVATE);        // idk how it works but it works. there I get data from activity_courier_login: username and userid
                SharedPreferences sharedUserID = getSharedPreferences("sharedUserID", Context.MODE_PRIVATE);

                String userName = sharedUsername.getString("USERNAME", "");
                String userID = sharedUserID.getString("USERID", "");



                if(listData.size() > 0)listData.clear();
                if(listOrder.size() > 0)listOrder.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    addAcceptedOrders addOrder = ds.getValue(addAcceptedOrders.class);

                    if (addOrder.shippingBy.contains(userName)){
                        listData.add(addOrder.cargoName);
                        listOrder.add(addOrder);
                    }


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

                SharedPreferences sharedUsername = getSharedPreferences("sharedUsername", Context.MODE_PRIVATE);        // idk how it works but it works. there I get data from activity_courier_login: username and userid
                SharedPreferences sharedUserID = getSharedPreferences("sharedUserID", Context.MODE_PRIVATE);

                String userName = sharedUsername.getString("USERNAME", "");
                String userID = sharedUserID.getString("USERID", "");
                addAcceptedOrders addOrder = listOrder.get(position);
                Intent i = new Intent(activity_courier_order_history.this, show_courier_item_history_orders.class);

                i.putExtra("history_order_id", addOrder.cargoName);
                i.putExtra("history_order_delivery_from", addOrder.deliveryFrom);
                i.putExtra("history_order_delivery_to", addOrder.deliveryTo);
                i.putExtra("history_order_cargo_size", addOrder.cargoSize);
                i.putExtra("history_order_cargo_weight", addOrder.cargoWeight);
                i.putExtra("history_order_shipping_by", addOrder.shippingBy);

                String finishTime = currentDate;
                i.putExtra("history_order_finish_time", currentDate);



                i.putExtra("history_order_username", userName);
                i.putExtra("history_order_user_id", userID);



                startActivity(i);
            }
        });
    }


}