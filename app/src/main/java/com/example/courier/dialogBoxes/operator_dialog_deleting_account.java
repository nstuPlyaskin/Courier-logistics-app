package com.example.courier.dialogBoxes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.courier.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class operator_dialog_deleting_account extends AppCompatActivity {

    private DatabaseReference dbCouriers;
    private Button btnDeleteUser;
    private EditText edit_Name;

    private EditText edit_Deleting_Account;

    private String COURIER_KEY = "COURIER";

    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_dialog_deleting_account);

        root = findViewById(R.id.rootDialogDeletingAccount);

        btnDeleteUser = findViewById(R.id.btnDialogDeleteUser);
        edit_Name = findViewById(R.id.edit_Deleting_Account);

        edit_Deleting_Account = findViewById(R.id.edit_Deleting_Account);

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_Name.getText().toString();

                DatabaseReference deletingUsername = FirebaseDatabase.getInstance().getReference("COURIER/"+name); // на выходе получаем путь до записи name в бд courier

                if(!name.isEmpty()){
                    deleteData(name);
                    return;
                }
            }
        });

    }

    private void deleteData(String name) {

        dbCouriers = FirebaseDatabase.getInstance().getReference("COURIER");
        String deletingTmp = edit_Deleting_Account.getText().toString();
        dbCouriers.child(deletingTmp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
           public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                       Snackbar.make(root,"User " + name + " was deleted", Snackbar.LENGTH_LONG).show();
                       edit_Name.setText("");
                  } else {
                      Snackbar.make(root,"Error", Snackbar.LENGTH_LONG).show();
                 }
            }
        });


    }
}