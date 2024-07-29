package com.example.courier.bd;

import static com.example.courier.R.id.listView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.courier.R;
import com.example.courier.WelcomePage;
import com.example.courier.activity_operator_login;
import com.example.courier.dialogBoxes.operator_dialog_deleting_account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class readUser extends AppCompatActivity {

    private ListView listViewReadUsers;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<addUser> listTemp;

    private DatabaseReference dbCouriers;
    private String COURIER_KEY = "COURIER";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_read_users);
        init();
        getDataFromDB();
        setOnClickItem();
    }

    private void init(){
        listViewReadUsers = findViewById(R.id.listViewReadUsers);
        listData = new ArrayList<>();
        listTemp = new ArrayList<addUser>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listViewReadUsers.setAdapter(adapter);

        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);
    }

    private void getDataFromDB(){

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(listData.size() > 0 ) listData.clear();                                          // if listdata is not null then we clear and add new info
                if(listTemp.size() > 0 ) listTemp.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                   addUser user = ds.getValue(addUser.class);
                   assert user != null;
                   listData.add(user.username);
                   listTemp.add(user);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dbCouriers.addValueEventListener(vListener);

    }

    public void setOnClickItem(){
        listViewReadUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addUser user = listTemp.get(position);
                Intent i = new Intent(readUser.this, showUsersItemActivity.class);
                i.putExtra("user_type", user.id);
                i.putExtra("user_name", user.username);
                i.putExtra("user_email", user.email);
                i.putExtra("user_password", user.password);

                i.putExtra("root_user_path", user.username);
                startActivity(i);
            }
        });
    }


}
