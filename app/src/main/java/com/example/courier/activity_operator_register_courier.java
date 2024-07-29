package com.example.courier;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.courier.bd.addUser;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class activity_operator_register_courier extends AppCompatActivity {

    private EditText edit_Email, edit_Username, edit_Password, edit_Code;
    private Button btnRegister;

    private DatabaseReference dbCouriers;
    private String COURIER_KEY = "COURIER";

    private String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_register_courier);


        edit_Email = findViewById(R.id.edit_Email_Operator);
        edit_Username = findViewById(R.id.edit_Username_Operator);
        edit_Password = findViewById(R.id.edit_Password_Operator);
        edit_Code = findViewById(R.id.edit_CodeWord_Operator);
        btnRegister = findViewById(R.id.btnRegister);

        root = findViewById(R.id.root_element_operator);

        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_Password.getText().length() < 6){
                    Snackbar.make(root,"Пароль не может быть пустым!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edit_Email.getText().toString())){
                    Snackbar.make(root,"Email не может быть пустым!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edit_Username.getText().toString())){
                    Snackbar.make(root,"Имя пользователя не может быть пустым!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                String username = edit_Username.getText().toString();
                String  id = edit_Username.getText().toString() + currentDate.hashCode();
                String password = edit_Password.getText().toString();
                String email = edit_Email.getText().toString();


                addUser newCourier = new addUser(id.toString(), username, password, email);
                // writing user into database

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("COURIER").child(username).setValue(newCourier);

                Snackbar.make(root,"Аккаунт успешно зарегистрирован!", Snackbar.LENGTH_LONG).show();

                // after creating new account we clearing all fields

                edit_Email.setText("");
                edit_Username.setText("");
                edit_Password.setText("");
                edit_Code.setText("");
                }
        });

    }
}