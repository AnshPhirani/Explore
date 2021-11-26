package com.example.explore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailET, passEt, confirmPassET;
    private Button registerBtn, loginBtn;
    private CheckBox checkBox;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailET = findViewById(R.id.register_email_et);
        passEt = findViewById(R.id.register_password_et);
        confirmPassET = findViewById(R.id.register_confirm_password_et);
        registerBtn = findViewById(R.id.btn_register);
        loginBtn = findViewById(R.id.btn_signup_login);
        checkBox = findViewById(R.id.register_checkbox);
        progressBar = findViewById(R.id.progressbar_register);

        passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPassET.setTransformationMethod(PasswordTransformationMethod.getInstance());

        mAuth = FirebaseAuth.getInstance();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPassET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPassET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passEt.getText().toString();
                String confirmPass = confirmPassET.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(confirmPass)){
                    if(password.equals(confirmPass)){

                        progressBar.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    sendToMain();
                                }
                                else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error -  " +  error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void sendToMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            sendToMain();
        }

    }
}