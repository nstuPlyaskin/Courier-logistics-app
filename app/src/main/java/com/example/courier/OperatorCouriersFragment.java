package com.example.courier;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.courier.bd.readUser;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class OperatorCouriersFragment extends Fragment {

    TextView tvCreateNewCourier;
    TextView tvCourierList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_operator_couriers, container, false);


        tvCreateNewCourier = view.findViewById(R.id.tvCreateNewCourier);
        tvCourierList = view.findViewById(R.id.tvCourierList);


        tvCreateNewCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), activity_operator_register_courier.class);
                startActivity(intent);
            }
        });

        tvCourierList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), readUser.class);
                startActivity(intent);
            }
        });
        return view;
    }

}