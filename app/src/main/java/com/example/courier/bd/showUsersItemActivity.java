package com.example.courier.bd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.courier.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class showUsersItemActivity extends AppCompatActivity {

    private TextView tvType, tvName, tvPassword, tvEmail, tvCourierID;

    private DatabaseReference dbCouriers;
    private String COURIER_KEY = "COURIER";
    private String rootUserPath;
    private ConstraintLayout userListLayout;

    private Button btnDeleteItemUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_user_list_layout);
        init();
        getIntentMain();

    }

    private void init() {
        tvType = findViewById(R.id.tvType);
        tvCourierID = findViewById(R.id.tvCourierID);
        tvName = findViewById(R.id.tvName);
        tvPassword = findViewById(R.id.tvPassword);
        tvEmail = findViewById(R.id.tvEmail);
        userListLayout = findViewById(R.id.userListLayout);

        btnDeleteItemUser = findViewById(R.id.btnDeleteItemUser);

        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);
    }

    private void getIntentMain() {
        Intent i = getIntent();
        if (i != null) {
            tvCourierID.setText(i.getStringExtra("user_type"));
            tvName.setText(i.getStringExtra("user_name"));
            tvEmail.setText(i.getStringExtra("user_email"));
            tvPassword.setText(i.getStringExtra("user_password"));

            rootUserPath = (i.getStringExtra("root_user_path"));
        }



    btnDeleteItemUser.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = rootUserPath;

            DatabaseReference deletingUsername = FirebaseDatabase.getInstance().getReference(name); // на выходе получаем путь до записи name в бд ORDERS

            if(!name.isEmpty()){
                deleteData(name);
                return;
            }
        }
    });

}

    private void deleteData(String name) {

        dbCouriers = FirebaseDatabase.getInstance().getReference("COURIER");
        String deletingTmp = name;
        dbCouriers.child(deletingTmp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                   finish();

                } else {

                }
            }
        });

    }


}
