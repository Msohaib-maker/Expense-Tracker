package com.example.practiceexptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.practiceexptracker.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_in extends AppCompatActivity {

    ActivitySignInBinding binding;
    FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        myAuth = FirebaseAuth.getInstance();

        if(myAuth != null)
        {
            Intent i = new
                    Intent(Sign_in.this,MainActivity.class);
            try {
                startActivity(i);
            }
            catch (Exception e)
            {
                // error catch
            }
        }
        binding.Tosignupid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new
                        Intent(Sign_in.this,Sign_up.class);
                try {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    // error catch
                }
            }
        });

        binding.lgnbtnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signInEmailid.getText().toString();
                String password = binding.signinPasswordid.getText().toString();
                UserLogin(email,password);
            }
        });
    }

    public void UserLogin(String email,String password)
    {
        if(email.isEmpty())
        {
            binding.signInEmailid.setError("this field is required");
            binding.signInEmailid.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.signInEmailid.setError("Email pattern does not match");
            binding.signInEmailid.requestFocus();
            return;
        }
        if(password.length() < 6)
        {
            binding.signinPasswordid.setError("length should be 6 at min");
            binding.signinPasswordid.requestFocus();
            return;
        }

        myAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Sign_in.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Sign_in.this,MainActivity.class);
                        try {
                            startActivity(intent);
                        }
                        catch (Exception e)
                        {
                            // error catch
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Sign_in.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}