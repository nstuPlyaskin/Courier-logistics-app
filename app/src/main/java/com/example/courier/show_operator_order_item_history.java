package com.example.courier;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class show_operator_order_item_history extends AppCompatActivity {

    private TextView tvOrderID, tvFinishTime, tvDeliveryFrom, tvDeliveryTo, tvCargoSize, tvCargoWeight, tvShippingBy;
    private Button btnOperatorSendHistoryOrderToEmail;
    private DatabaseReference dbCouriers;
    private String tableCOMPLETED_ORDERS = "COMPLETED ORDERS";
    private ConstraintLayout root;
    private String currentDate = new SimpleDateFormat("от: dd.MM.yyyy время: HH:mm").format(Calendar.getInstance().getTime());

    FirebaseFirestore dbFireStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_operator_order_item_history);

        init();
        getIntentMain();

        btnOperatorSendHistoryOrderToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo there we must send order from HISTORY DB to email

                Map<String, Object> order = new HashMap<>();

                order.put("Название заказа", tvOrderID.getText().toString());
                order.put("Адрес взятия заказа", tvDeliveryFrom.getText().toString());
                order.put("Адрес приёма заказа", tvDeliveryTo.getText().toString());
                order.put("Размер груза", tvCargoSize.getText().toString());
                order.put("Вес груза", tvCargoWeight.getText().toString());
                order.put("Кто доставил", tvShippingBy.getText().toString());
                order.put("Время завершения выполнения заказа", tvFinishTime.getText().toString());

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"EMAIL TO SEND"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Выполненный заказ: " +order.get("Название заказа") + " " + currentDate);
                intent.putExtra(Intent.EXTRA_TEXT,
                        "Название заказа: " + order.get("Название заказа").toString() + "\n" +
                                "Адрес взятия заказа: " + order.get("Адрес взятия заказа").toString() + "\n" +
                                "Адрес приёма заказа: " + order.get("Адрес приёма заказа").toString() + "\n" +
                                "Размер груза: " + order.get("Размер груза").toString() + "\n" +
                                "Название заказа: " + order.get("Название заказа").toString() + "\n" +
                                "Вес груза: " + order.get("Вес груза").toString() + "\n" +
                                "Кто доставил: " + order.get("Кто доставил").toString() + "\n" +
                                "Время завершения выполнения заказа: " + order.get("Время завершения выполнения заказа").toString());

                                intent.setType("message/rfc822");

                                if (intent.resolveActivity(getPackageManager()) != null){
                                    startActivity(intent);
                                } else {
                                    Snackbar.make(root, "Ошибка: невозможно запустить почтовое приложение на вашем устройстве!", Snackbar.LENGTH_LONG).show();
                                }
            }
        });

    }
    private void init() {
        tvOrderID = findViewById(R.id.tvCargoNameOperatorHistoryOrder);
        tvDeliveryFrom = findViewById(R.id.tvDeliveryFromOperatorHistoryOrder);
        tvDeliveryTo = findViewById(R.id.tvDeliveryToOperatorHistoryOrder);
        tvCargoSize = findViewById(R.id.tvCargoSizeOperatorHistoryOrder);
        tvCargoWeight = findViewById(R.id.tvCargoWeightOperatorHistoryOrder);
        tvShippingBy = findViewById(R.id.tvShippingByOperatorHistoryOrder);
        tvFinishTime = findViewById(R.id.tvFinishTimeOperatorHistoryOrder);

        root = findViewById(R.id.rootShowOperatorItemHistory);

        btnOperatorSendHistoryOrderToEmail = findViewById(R.id.btnOperatorSendHistoryOrderToEmail);

        dbCouriers = FirebaseDatabase.getInstance().getReference(tableCOMPLETED_ORDERS);
    }

    private void getIntentMain() {
        Intent i = getIntent();
        if (i != null) {
            tvOrderID.setText(i.getStringExtra("history_order_id"));
            tvDeliveryFrom.setText(i.getStringExtra("history_order_delivery_from"));
            tvDeliveryTo.setText(i.getStringExtra("history_order_delivery_to"));
            tvCargoSize.setText(i.getStringExtra("history_order_cargo_size"));
            tvCargoWeight.setText(i.getStringExtra("history_order_cargo_weight"));
            tvShippingBy.setText(i.getStringExtra("history_order_shipping_by"));
            tvFinishTime.setText(i.getStringExtra("history_order_finish_time"));

        }
    }

}