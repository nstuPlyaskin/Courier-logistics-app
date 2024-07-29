package com.example.courier.courierFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.courier.R;
import com.example.courier.message.activity_chat;

public class CourierChatFragment extends Fragment {

    TextView discriptionChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courier_chat, container, false);

        discriptionChat = view.findViewById(R.id.discriptionChat);

        discriptionChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_chat.class);
                startActivity(intent);
            }
        });



        return view;
    }
}