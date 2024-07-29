package com.example.courier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_operator_login extends AppCompatActivity {
    private EditText edit_Email, edit_Password, edit_Code;
    private Button btnLogin;
    private ConstraintLayout root;

    private String secureOperatorCode;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_login);

        secureOperatorCode = "oper6";

        edit_Email = findViewById(R.id.edit_Email_Operator_Login);
        edit_Password = findViewById(R.id.edit_Password_Operator_Login);
        edit_Code = findViewById(R.id.edit_Code_Operator_Login);

        btnLogin = findViewById(R.id.btnLogin);

        root = findViewById(R.id.ConstraintLayout_Operator_Login);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edit_Email.getText().toString().isEmpty()){
                    Snackbar.make(root,"Email can't be empty.", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (edit_Password.getText().toString().isEmpty()){
                    Snackbar.make(root,"Password can't be empty.", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (edit_Code.getText().toString().contains(secureOperatorCode)){
                    mAuth.signInWithEmailAndPassword(edit_Email.getText().toString(), edit_Password.getText().toString())

                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(activity_operator_login.this, OperatorHomePage.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Snackbar.make(root,"Wrong username or password", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                } else {
                    Snackbar.make(root,"You use wrong operator code.", Snackbar.LENGTH_LONG).show();
                    return;
                }


                }


        });
    }
}