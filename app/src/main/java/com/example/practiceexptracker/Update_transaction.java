package com.example.practiceexptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.practiceexptracker.databinding.ActivityUpdateTransactionBinding;

public class Update_transaction extends AppCompatActivity {

    String id,pos;
    String note,amount;
    DataSaver Update_db;
    ActivityUpdateTransactionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        Update_db = new DataSaver(this);
        if(bundle != null)
        {
            id = bundle.getString("key");
            pos = bundle.getString("pos");
            amount = bundle.getString("amount");
            note = bundle.getString("TheNote");
            //Toast.makeText(this, "pos : " + pos, Toast.LENGTH_SHORT).show();
        }

        if(amount != null && !amount.isEmpty())
        {
            binding.amountid.setText(amount);
        }

        if(note!=null && !note.isEmpty())
        {
            binding.myNoteid.setText(note);
        }

        binding.deleteid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Id = Integer.parseInt(id);
                int NPos = Integer.parseInt(pos);
                Update_db.deleteTransaction(Id);
                Intent i = new Intent(Update_transaction.this,MainActivity.class);
                i.putExtra("ItemPos",String.valueOf(NPos));
                startActivity(i);
            }
        });

        binding.Updateid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Id = Integer.parseInt(id);
                String newAmount = binding.amountid.getText().toString();
                String newNote = binding.myNoteid.getText().toString();

                if(newAmount.isEmpty() || newNote.isEmpty())
                {
                    return;
                }
                Update_db.updateTrans(newAmount,newNote,Id);
                Intent i = new Intent(Update_transaction.this,MainActivity.class);
                startActivity(i);
            }
        });


    }


}