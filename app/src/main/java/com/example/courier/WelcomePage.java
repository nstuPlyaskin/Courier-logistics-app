package com.example.courier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage extends AppCompatActivity {

    private Button btnCourier;
    private Button btnOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        btnOperator = findViewById(R.id.btnSoftSelectOperator);                                     // initialization OPERATOR button
        btnCourier = findViewById(R.id.btnSoftSelectCourier);                                       // initialization COURIER button

        btnOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, activity_operator_login.class);
                startActivity(intent);
            }                                                       // open operator app
        });

        btnCourier.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(WelcomePage.this, activity_courier_login.class);
               startActivity(intent);
           }                                                        // open courier app
       });

    }
}


