package com.example.courier.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.courier.R;
import com.example.courier.activity_operator_register_courier;
import com.example.courier.bd.addOrder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class activity_operator_add_order extends AppCompatActivity {


    private EditText edit_delivery_from;
    private EditText edit_delivery_to;
    private EditText edit_cargo_name;
    private EditText edit_cargo_size;
    private EditText edit_cargo_weight;
    ConstraintLayout root;
    private String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());

    private Button btnAddOrder;

    private DatabaseReference dbCouriers;
    private String COURIER_KEY = "ORDERS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_operator_add_order);

        btnAddOrder = findViewById(R.id.btnAddOrder);
        edit_delivery_from = findViewById(R.id.edit_delivery_from);
        edit_delivery_to = findViewById(R.id.edit_delivery_to);
        edit_cargo_name = findViewById(R.id.cargo_name);
        edit_cargo_size = findViewById(R.id.cargo_size);
        edit_cargo_weight = findViewById(R.id.cargo_weight);

        root = findViewById(R.id.rootAddOrder);

        dbCouriers = FirebaseDatabase.getInstance().getReference(COURIER_KEY);

       btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edit_delivery_from.getText().toString())) {
                    Snackbar.make(root, "Первый адрес не может быть пустым!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edit_delivery_to.getText().toString())) {
                    Snackbar.make(root, "Второй адрес не может быть пустым!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edit_cargo_name.getText().toString())) {
                    Snackbar.make(root, "Название груза не может быть пустым!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                String id = edit_cargo_name.getText().toString() + currentDate.hashCode();


                String deliveryFrom = edit_delivery_from.getText().toString();
                String deliveryTo = edit_delivery_to.getText().toString();

                String cargoName = edit_cargo_name.getText().toString();
                String cargoSize = edit_cargo_size.getText().toString();
                String cargoWeight = edit_cargo_weight.getText().toString();


                addOrder newOrder = new addOrder(cargoName, deliveryFrom, deliveryTo, id, cargoSize, cargoWeight);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("ORDERS").child(cargoName).setValue(newOrder);


                Snackbar.make(root, "Заказ успешно создан!", Snackbar.LENGTH_LONG).show();

                // after creating new account we clearing all fields

                edit_delivery_to.setText("");
                edit_delivery_from.setText("");
                edit_cargo_name.setText("");
                edit_cargo_weight.setText("");
                edit_cargo_size.setText("");


            }
        });

    }

}