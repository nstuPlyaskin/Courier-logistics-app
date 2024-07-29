package com.example.courier;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.courier.courierActivity.courier_current_orders;
import com.example.courier.courierFragments.CourierChatFragment;
import com.example.courier.courierFragments.CourierDashboardFragment;
import com.example.courier.courierFragments.CourierMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class CourierHomePage extends AppCompatActivity {

    BottomNavigationView bottom_navigation_courier;
    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_home_page);

        bottom_navigation_courier = findViewById(R.id.bottom_navigation_courier);
        root = findViewById(R.id.courier_container);
        getSupportFragmentManager().beginTransaction().replace(R.id.courier_container, new CourierDashboardFragment()).commit();

        bottom_navigation_courier.setSelectedItemId(R.id.nav_courier_dashboard);

        Bundle arguments = getIntent().getExtras();
        String accountUsername = arguments.get("account_username").toString();


        bottom_navigation_courier.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){

                    case R.id.nav_courier_dashboard:
                        fragment = new CourierDashboardFragment();
                        break;


                    case R.id.nav_courier_chat:
                        fragment = new CourierChatFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.courier_container, fragment).commit();

                return true;
            }
        });



    }
}