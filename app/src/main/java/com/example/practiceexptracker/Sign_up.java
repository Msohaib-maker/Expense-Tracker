package com.example.practiceexptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.practiceexptracker.databinding.ActivitySignInBinding;
import com.example.practiceexptracker.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_up extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();

        binding.Tosigninid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new
                        Intent(Sign_up.this,Sign_in.class);
                try {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    // error catch
                }
            }
        });

        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.Emailid.getText().toString();
                String password = binding.Passwordid.getText().toString();
                UserRegister(email,password);
            }
        });

    }

    public void UserRegister(String email,String password)
    {
        if(email.isEmpty())
        {
            binding.Emailid.setError("this field is required");
            binding.Emailid.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.Emailid.setError("Email pattern does not match");
            binding.Emailid.requestFocus();
            return;
        }
        if(password.length() < 6)
        {
            binding.Passwordid.setError("length should be 6 at min");
            binding.Passwordid.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Sign_up.this, "Registered", Toast.LENGTH_SHORT).show();
                        Intent i = new
                                Intent(Sign_up.this,MainActivity.class);
                        try {
                            startActivity(i);
                        }
                        catch (Exception e)
                        {
                            // error catch
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Sign_up.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


}