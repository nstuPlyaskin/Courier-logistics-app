package com.example.courier.courierFragments;
// FRAGMENT COURIER DASHBOARD LAYOUT

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.courier.R;
import com.example.courier.activity_operator_register_courier;
import com.example.courier.bd.addOrder;
import com.example.courier.courierActivity.courier_current_orders;
import com.example.courier.courierActivity.courier_dasboard;
import com.example.courier.courierActivity.courier_show_accepted_orders;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CourierDashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courier_dashboard, container, false);

        TextView tvCourierOrderListDashboard, tvCourierCurrentOrderList, tvCourierCompletedOrderList;
        tvCourierOrderListDashboard = view.findViewById(R.id.tvCourierOrderListDashboard);
        tvCourierCurrentOrderList = view.findViewById(R.id.tvCourierAcceptedOrders);
        tvCourierCompletedOrderList = view.findViewById(R.id.tvCourierCompletedOrderList);


        tvCourierOrderListDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), courier_current_orders.class);
                startActivity(intent);
            }
        });


        tvCourierCurrentOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), courier_show_accepted_orders.class);
                startActivity(intent);
            }
        });

        tvCourierCompletedOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), activity_courier_order_history.class);
                startActivity(intent);
            }
        });


        return view;
    }
}