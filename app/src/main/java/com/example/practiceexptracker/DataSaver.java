package com.example.practiceexptracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataSaver extends SQLiteOpenHelper {

    private static final String DbName = "tansacdb";
    private static final int version = 1;
    private static final String TableName = "TransTable";
    private static final String ID = "id";
    private static final String Amount = "amount";
    private static final String Note = "note";
    private static final String type = "type";
    private static final String date = "date";

    public DataSaver(@Nullable Context context) {
        super(context, DbName, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String theQuery = "CREATE TABLE " + TableName +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Amount + " TEXT, " + Note +
                " TEXT, " + type + " TEXT, " +  date + " TEXT)";
        db.execSQL(theQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(sqLiteDatabase);
    }

    public void AddData(String amount, String note,String Type,String Date)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Amount,amount);
        values.put(Note,note);
        values.put(type,Type);
        values.put(date,Date);

        db.insert(TableName,null,values);
        db.close();

    }

    public ArrayList<TransactionModel> ReadAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TableName,null);
        ArrayList<TransactionModel> list = new ArrayList<>();

        if(cur.moveToFirst())
        {
            do
            {
                int index = cur.getColumnIndexOrThrow(ID);
                String id = cur.getString(index);
                index = cur.getColumnIndexOrThrow(Amount);
                String amount = cur.getString(index);
                index = cur.getColumnIndexOrThrow(Note);
                String note = cur.getString(index);
                index = cur.getColumnIndexOrThrow(type);
                String mytype = cur.getString(index);
                index = cur.getColumnIndexOrThrow(date);
                String Date = cur.getString(index);
                TransactionModel transactionModel =
                        new TransactionModel(id,amount,note,Date,mytype);
                list.add(transactionModel);
            }
            while(cur.moveToNext());
        }

        cur.close();
        return list;
    }

    public void deleteTransaction(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TableName, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateTrans(String amount,String note,int id) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(Amount, amount);
        values.put(Note, note);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TableName, values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
