package com.project.rushabh.restaurant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.rushabh.restaurant.R;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    TextInputEditText emailLoginText, passwordLoginText;

    Button loginBtn;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        emailLoginText = findViewById(R.id.textInputEditText_login_email);
        passwordLoginText = findViewById(R.id.textInputEditText_login_password);

        loginBtn = findViewById(R.id.btn_login);

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_pref_file_name), MODE_PRIVATE);
    }

    public void onLoginClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String email = emailLoginText.getText().toString();
                String password = passwordLoginText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (passwordLoginText.getText().length() < 8) {
                                        passwordLoginText.setError("Less than 8");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "auth failed", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    editor = sharedPreferences.edit();
                                    editor.putBoolean(getString(R.string.spk_is_logged_in), true).apply();
                                    Toast.makeText(LoginActivity.this, "signed in", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                break;
            case R.id.text_to_sign_up:
                startActivity(new Intent(getIntent())); //Register activity
                finish();
        }
    }
}
