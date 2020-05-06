package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<adapter_item> list;
    EditText user;
    EditText pass;
    Button save;
    Button load;
    Button update;
    Button clear;
    DBhelper db;
    int pos;
    adapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        save = findViewById(R.id.save);
        load = findViewById(R.id.load);
        update = findViewById(R.id.update);
        clear = findViewById(R.id.clear);
        lv = findViewById(R.id.lv);
        list = new ArrayList<adapter_item>();
        db = new DBhelper(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.insertData(user.getText().toString(), pass.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(DBhelper.COL_1, user.getText().toString());
                values.put(DBhelper.COL_2, pass.getText().toString());
                boolean isUpdate = db.update(values, list.get(pos).getId());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();

                onLoad(v);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.clearDatabase();
                onLoad(v);
            }
        });

    }

    public void onLoad(View view) {
        list.clear();
        Cursor cursor = db.showData();
        if(cursor.moveToFirst())
        {
            do {
                adapter_item item = new adapter_item(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                list.add(item);
            } while (cursor.moveToNext());
        }
        ListView lv = (ListView) findViewById(R.id.lv);
        adp = new adapter();
        lv.setAdapter(adp);
    }

    class adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public adapter_item getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View item_view = getLayoutInflater().inflate(R.layout.item, parent, false);
            TextView us = item_view.findViewById(R.id.us);
            TextView pas = item_view.findViewById(R.id.pas);
            Button up = item_view.findViewById(R.id.up);
            Button del = item_view.findViewById(R.id.del);
            us.setText(list.get(position).getUser());
            pas.setText(list.get(position).getPass());
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int deletedRows = db.delete(list.get(position).getId());
                    if (deletedRows > 0)
                        Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                }
            });

            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "ENTER USERNAME and PASSWORD\nCLICK ON UPDATE NOW", Toast.LENGTH_LONG).show();
                    pos = position;
                }
            });
            return item_view;
        }
    }
}
