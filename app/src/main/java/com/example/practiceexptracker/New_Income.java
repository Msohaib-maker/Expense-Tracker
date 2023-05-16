package com.example.practiceexptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.practiceexptracker.databinding.ActivityNewIncomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class New_Income extends AppCompatActivity {

    ActivityNewIncomeBinding binding;
    DataSaver db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewIncomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getSupportActionBar().hide();
        db = new DataSaver(this);
        binding.doneid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = binding.amountid.getText().toString().trim();
                String type = "Income";
                String MyNote = binding.myNoteid.getText().toString();
                String date = new SimpleDateFormat("dd_MM_yyyy")
                        .format(Calendar.getInstance().getTime());
                db.AddData(amount,MyNote ,type,date);
                Intent i = new Intent(New_Income.this,MainActivity.class);
                try {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    // error catch
                }
            }
        });

        binding.cancelid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(New_Income.this,MainActivity.class);
                try {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    // error catch
                }
            }
        });
    }
}