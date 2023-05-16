package com.example.practiceexptracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class UltAdapter extends RecyclerView.Adapter<UltAdapter.MyViewHolder> {

    List<TransactionModel> myViewsList;
    Context context;

    public UltAdapter(List<TransactionModel> list,Context context)
    {
        this.context = context;
        this.myViewsList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UltAdapter.MyViewHolder holder, int position) {
        TransactionModel MyModel = myViewsList.get(position);
        Log.d("checkTag -> ", MyModel.getType());
        holder.amount.setText(MyModel.getAmount());
        if(Objects.equals(MyModel.getType(), "Income"))
        {
            holder.amount.setTextColor(Color.rgb(97,179,59));
            holder.priority.setBackgroundResource(R.drawable.mark);
        }
        else
        {
            holder.amount.setTextColor(Color.RED);
            holder.priority.setBackgroundResource(R.drawable.redmark);
        }
        final int pos = holder.getAdapterPosition();
        holder.date.setText(MyModel.getDate());
        holder.note.setText(MyModel.getNote());
        holder.Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Realpos = pos;
                Intent i = new Intent(context,Update_transaction.class);
                i.putExtra("key",myViewsList.get(pos).getId());
                i.putExtra("pos",String.valueOf(Realpos));
                i.putExtra("amount", myViewsList.get(pos).getAmount());
                i.putExtra("TheNote",myViewsList.get(pos).getNote());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myViewsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView note,amount,date;
        View priority;
        ImageView Btn;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            note = itemView.findViewById(R.id.MyTextid);
            amount = itemView.findViewById(R.id.amountid);
            date = itemView.findViewById(R.id.dateid);
            priority = itemView.findViewById(R.id.diffId);
            Btn = itemView.findViewById(R.id.newbtn2id);
        }

    }

}
