package com.example.courier;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courier.bd.addOrder;
import com.example.courier.orders.activity_operator_add_order;
import com.google.android.material.snackbar.Snackbar;


public class OperatorDashboardFragment extends Fragment {

    TextView tvCreateNewOrder, tvOperatorOrderList, tvOperatorAcceptedOrderList, tvOperatorHistoryOrderList;


    ConstraintLayout root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_operator_dashboard, container, false);


        tvCreateNewOrder = view.findViewById(R.id.tvCreateNewOrder);
        tvOperatorOrderList = view.findViewById(R.id.tvOperatorOrderList);
        tvOperatorAcceptedOrderList = view.findViewById(R.id.tvOperatorAcceptedOrderList);
        tvOperatorHistoryOrderList = view.findViewById(R.id.tvOperatorHistoryOrderList);

        root = view.findViewById(R.id.root_element_dashboard);

        tvCreateNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), activity_operator_add_order.class);
                startActivity(intent);

            }
        });

        tvOperatorOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_operator_read_opders.class);
                startActivity(intent);

            }
        });

        tvOperatorAcceptedOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), operator_show_accepted_orders.class);
                startActivity(intent);

            }
        });

        tvOperatorHistoryOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), show_operator_order_history.class);
                startActivity(intent);

            }
        });



        return view;
    }
}