package com.example.courier;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.courier.message.activity_chat;


public class OperatorMapFragment extends Fragment {

    TextView discriptionChatOper;
    String isOperator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operator_map, container, false);

        discriptionChatOper = view.findViewById(R.id.discriptionChatOper);

        discriptionChatOper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOperator = "yes";
                Intent intent = new Intent(getActivity(), activity_chat.class);
                intent.putExtra("isOperator", isOperator);
                startActivity(intent);
            }
        });



        return view;
    }
}