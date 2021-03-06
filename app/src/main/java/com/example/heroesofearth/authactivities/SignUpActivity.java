package com.example.heroesofearth.authactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heroesofearth.Auth;
import com.example.heroesofearth.R;
import com.example.heroesofearth.Score;
import com.example.heroesofearth.pojos.User;
import com.example.heroesofearth.repository.Repo;
import com.example.heroesofearth.welcomeactivities.CompleteRegistrationActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private TextView textViewLoginButton;

    private View.OnClickListener registerButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (name.length() > 0 || email.length() > 0 || password.length() > 0) {

                if (password.equals(confirmPassword)) {
                    User user = new User(email, password, name);

                    boolean isSuccessful = Auth.signUp(user);

                    if (isSuccessful) {
                        Auth.login(email, password);
                        Score.resetChallengeCompletions(Repo.dailyChallengesList);

                        startActivity(new Intent(SignUpActivity.this,
                                CompleteRegistrationActivity.class));

                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Sign Up failed. The user already exists!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this,
                            "Passwords didn't matched!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignUpActivity.this,
                        "Please enter the required information!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener loginButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        registerButton = findViewById(R.id.buttonRegister);
        textViewLoginButton = findViewById(R.id.textViewLoginButton);

        nameEditText.setText("Demo User 2");
        emailEditText.setText("demo-email-2@domain");
        passwordEditText.setText("demo-pass");
        confirmPasswordEditText.setText("demo-pass");

        registerButton.setOnClickListener(registerButtonOnClickListener);
        textViewLoginButton.setOnClickListener(loginButtonOnClickListener);
    }
}