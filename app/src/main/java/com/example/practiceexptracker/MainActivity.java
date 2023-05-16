package com.example.practiceexptracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.practiceexptracker.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    UltAdapter myAdapter;
    List<TransactionModel> list;
    FirebaseAuth AuthUI;
    RecyclerView rv;
    DataSaver myData;
    String N_pos;
    int totalIncome =0;
    int totalExpense = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        toolbarTop.inflateMenu(R.menu.menu_items);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);

        toolbarTop.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.SettingsId)
                {

                }
                else if(item.getItemId() == R.id.LogoutId)
                {
                    FirebaseAuth.getInstance().signOut();
                }
                return false;
            }
        });

        Bundle data = getIntent().getExtras();

        myData = new DataSaver(this);
        list = RetrieveData();
        if(data != null)
        {
            N_pos = data.getString("ItemPos");
        }
        

        //list = new ArrayList<>();
        rv = binding.recyclerView;
        rv.setLayoutManager(new LinearLayoutManager(this));
        TransactionModel t = new TransactionModel("1","2000","lunch","22/2/22","income");
        //list.add(t);
        //list.add(t);

        myAdapter = new UltAdapter(list,this);
        rv.setAdapter(myAdapter);


        binding.incomebtnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,New_Income.class);
                try {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    // code
                }
            }
        });

        binding.expensebtnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,New_Expense.class);
                try {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    // code
                }
            }
        });
    }

    public List<TransactionModel> RetrieveData()
    {
        List<TransactionModel> newlist = new ArrayList<>();
        newlist = myData.ReadAllData();

        for(int i=0;i<newlist.size();i++)
        {
            TransactionModel Tm = newlist.get(i);
            int amount = 0;
            try
            {
                amount = Integer.parseInt(Tm.getAmount());
            }
            catch (Exception e)
            {
                // code
                amount = 0;
            }


            if (Objects.equals(Tm.getType(), "Income")) {
                    totalIncome += amount;
            } else {
                    totalExpense += amount;
            }


        }
        binding.IncomeAmountid.setText(String.valueOf(totalIncome));
        binding.ExpensesAmountid.setText(String.valueOf(totalExpense));
        binding.BalanceAmountid.setText(String.valueOf(totalIncome - totalExpense));

        return  newlist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items,menu);
        return true;
    }
}