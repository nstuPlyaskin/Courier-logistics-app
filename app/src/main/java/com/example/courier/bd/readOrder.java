package com.example.courier.bd;

// read activity from YT guide neco

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.courier.R;
import com.example.courier.dialogBoxes.operator_dialog_deleting_account;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class readOrder extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private DatabaseReference dbCouriers;
    private String COURIER_KEY = "COURIER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_read_opders);
        init();
        getDataFromDB();
    }
    private void init(){
        listView = findViewById(R.id.listViewReadOrders);
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);
    }
    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(listData.size() > 0)listData.clear();

                for(DataSnapshot ds : snapshot.getChildren()){
                    addOrder addOrder = ds.getValue(addOrder.class);
                    assert addOrder != null;
                    listData.add(addOrder.cargoName);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbCouriers.addValueEventListener(vListener);
    }
}