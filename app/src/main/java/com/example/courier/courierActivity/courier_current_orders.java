package com.example.courier.courierActivity;

// open when item clicked

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.courier.CourierHomePage;
import com.example.courier.R;
import com.example.courier.bd.addOrder;
import com.example.courier.show_courier_current_orders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class courier_current_orders extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<addOrder> listOrder;
    private DatabaseReference dbCouriers;
    private String COURIER_KEY = "ORDERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_dasboard);

        init();
        getDataFromDB();
        setOnClickItem();

    }

    private void init(){
        listView = findViewById(R.id.listViewReadOrdersCourier);
        listData = new ArrayList<>();
        listOrder = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(listData.size() > 0)listData.clear();
                if(listOrder.size() > 0)listOrder.clear();

                for(DataSnapshot ds : snapshot.getChildren()){
                    addOrder addOrder = ds.getValue(addOrder.class);
                    assert addOrder != null;
                        listData.add(addOrder.id);
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
                addOrder addOrder = listOrder.get(position);
                Intent i = new Intent(courier_current_orders.this, show_courier_current_orders.class);
                i.putExtra("order_id",addOrder.id);
                i.putExtra("order_cargoName",addOrder.cargoName);
                i.putExtra("order_deliveryFrom",addOrder.deliveryFrom);
                i.putExtra("order_deliveryTo",addOrder.deliveryTo);
                i.putExtra("order_cargoSize",addOrder.cargoSize);
                i.putExtra("order_cargoWeight",addOrder.cargoWeight);

                i.putExtra("root_order_path", addOrder.id);


                startActivity(i);
            }
        });
    }

}