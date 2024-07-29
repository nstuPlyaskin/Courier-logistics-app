package com.example.courier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;

public class activity_courier_login extends AppCompatActivity {

    private EditText edit_Email, edit_Password;
    private Button btnLogin;
    private ConstraintLayout root;

    private DatabaseReference dbCouriers;
    private String currentUserID;
    private String COURIER_KEY = "COURIER";
    private String accountID = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_login);

        edit_Email = findViewById(R.id.edit_Email_Courier);
        edit_Password = findViewById(R.id.edit_Password_Courier);
        btnLogin = findViewById(R.id.btnLogin);

        root = findViewById(R.id.ConstraintLayout_Courier_Login);                                   // this is main layout of activity_courier_login
        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edit_Email.getText().toString())) {
                    Snackbar.make(root, "Email can't be empty!", Snackbar.LENGTH_LONG).show();
                    return;
                }                        // if email null then end if and code stop executing

                if (TextUtils.isEmpty(edit_Password.getText().toString())) {
                    Snackbar.make(root, "Password can't be empty!", Snackbar.LENGTH_LONG).show();
                    return;
                }                     // if password null then end if and code stop executing


                ArrayList<String> listUsernames = new ArrayList<>();
                ArrayList<String> listPasswords = new ArrayList<>();


                dbCouriers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            listUsernames.clear();
                            listPasswords.clear();

                            listUsernames.add(snapshot.getValue().toString());
                            listPasswords.add(snapshot.getValue().toString());

                            listUsernames.add(snapshot.child("username").getValue().toString());    // get all usernames from db
                            listPasswords.add(snapshot.child("password").getValue().toString());    // get all passwords from db

                           if (listUsernames.contains(edit_Email.getText().toString()) & listPasswords.contains(edit_Password.getText().toString())){            // searching for equals


                               currentUserID = (snapshot.child("id").getValue().toString());




                               SharedPreferences sharedUsername = getSharedPreferences("sharedUsername", Context.MODE_PRIVATE);
                               SharedPreferences sharedUserID = getSharedPreferences("sharedUserID", Context.MODE_PRIVATE);

                               SharedPreferences.Editor editorUsername = sharedUsername.edit();
                               SharedPreferences.Editor editorUserID = sharedUserID.edit();

                               editorUsername.putString("USERNAME",edit_Email.getText().toString());
                               editorUserID.putString("USERID",currentUserID);

                               editorUsername.apply();
                               editorUserID.apply();

                               Intent intent = new Intent(activity_courier_login.this, CourierHomePage.class);
                               intent.putExtra("account_username", edit_Email.getText().toString());
                               intent.putExtra("account_ID", currentUserID);
                               startActivity(intent);
                               finish();
                           }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Snackbar.make(root,"Error:  " + error, Snackbar.LENGTH_LONG).show();
                    }
                });



            }
        }

        );

    }


}