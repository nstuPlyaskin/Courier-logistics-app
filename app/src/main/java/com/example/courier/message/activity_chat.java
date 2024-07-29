package com.example.courier.message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.courier.CourierHomePage;
import com.example.courier.R;
import com.example.courier.activity_courier_login;
import com.example.courier.bd.addOrder;
import com.example.courier.bd.addUser;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.rpc.context.AttributeContext;

import java.lang.invoke.ConstantCallSite;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.text.format.DateFormat;

import org.w3c.dom.Text;

public class activity_chat extends AppCompatActivity {

    private ImageView btnSendMessage;
    private EditText editChat;


    private TextView mess_time, mess_text, mess_user;
    private ListView listView;
    public ArrayAdapter<String> adapter;
    public List<String> listData;
    private DatabaseReference dbCouriers;
    private String BD_NAME = "CHAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnSendMessage = findViewById(R.id.btnSendMessage);
        editChat = findViewById(R.id.editChat);

        mess_text = findViewById(R.id.message_text);
        mess_user = findViewById(R.id.message_user);
        mess_time = findViewById(R.id.message_time);


        listView = findViewById(R.id.listViewReadAcceptedOrdersOperator);
        listData = new ArrayList<>();
        listView.setAdapter(adapter);

        ListAdapter adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item,
                R.id.message_text,
                listData
        );
        listView.setAdapter(adapter);


        SharedPreferences sharedUsername = getSharedPreferences("sharedUsername", Context.MODE_PRIVATE);

        String userName = sharedUsername.getString("USERNAME", "");

        listView.setAdapter(adapter);
        dbCouriers = FirebaseDatabase.getInstance().getReference(BD_NAME);

        getDataFromDB();

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle extras = getIntent().getExtras();
                String isOperator;


                if (editChat.length() == 0){
                        return;
                } else {
                    if (extras != null) {
                        isOperator = extras.getString("isOperator");
                        FirebaseDatabase.getInstance().getReference(BD_NAME).push().setValue(new Message("OPERATOR", editChat.getText().toString()));
                    } else {
                        FirebaseDatabase.getInstance().getReference(BD_NAME).push().setValue(new Message(userName, editChat.getText().toString()));
                    }
                    editChat.setText("");

                }



            }
        });

    }

    private void getDataFromDB()  {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(listData.size() > 0)listData.clear();

                for(DataSnapshot ds : snapshot.getChildren()){
                    Message Message = ds.getValue(Message.class);
                    assert Message != null;



                    SharedPreferences sharedUsername = getSharedPreferences("sharedUsername", Context.MODE_PRIVATE);

                    String userName = sharedUsername.getString("USERNAME", "");

                        listData.add(Message.getMessageTime() + " " + Message.getUsername() + ": " + Message.textMessage);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbCouriers.addValueEventListener(vListener);

    }


}





