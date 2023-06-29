package com.example.xingli;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DictActivity extends AppCompatActivity {
    private Button insertButton, updateButton, searchButton, deleteButton;
    private Button btn_xl1;
    private EditText name, age;
    private TextView show, showAge;
    final DatabaseHelper dbHelper = new DatabaseHelper(DictActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        btn_xl1 = findViewById(R.id.btn_xl);
        btn_xl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建Intent 对象
                Intent intent = new Intent(DictActivity.this, Classification.class);
                //启动Activity
                startActivity(intent);
            }
        });

        insertButton = findViewById(R.id.btn_insert);
        updateButton = findViewById(R.id.btn_update);
        searchButton = findViewById(R.id.btn_search);
        deleteButton = findViewById(R.id.btn_delete);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        show = findViewById(R.id.tv_show);
        showAge = findViewById(R.id.tv_showAge);


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        myShow();


        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name.getText().toString());
                values.put("age", age.getText().toString());
                long id = db.insert("information", null, values);
                Log.d("myDeBug", "insert");

                myShow();


                db.close();
                name.setText(null);
                age.setText(null);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("age", age.getText().toString());
                db.update("information", values, "name=?", new String[]{name.getText().toString()});

                myShow();


                db.close();
                Log.d("myDebug", "update");
                name.setText(null);
                age.setText(null);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String name1 = name.getText().toString();
                show.setText(null);
                if (name1.equals("")) {
//                    show.setText("姓名");
//                    showAge.setText("单号");
//                    Cursor cursor = db.rawQuery("select * from information",null);
//
//                    while (cursor.moveToNext()) {
//                        String newName = cursor.getString(cursor.getColumnIndex("name"));
//                        int newAge = cursor.getInt(cursor.getColumnIndex("age"));
//                        show.setText(show.getText() + "\n" + newName);
//                        showAge.setText(showAge.getText()+"\n" + newAge);
//                    }

                    myShow();

                    db.close();
                } else {
                    show.setText("姓名");
                    showAge.setText("单号");
                    Cursor cursor = db.rawQuery("select * from information where name = ? ", new String[]{name1});

                    while (cursor.moveToNext()) {
                        String newName = cursor.getString(cursor.getColumnIndex("name"));
                        int newAge = cursor.getInt(cursor.getColumnIndex("age"));
//                        show.setText(show.getText() + "\n" + newName + "\t" + newAge);
                        show.setText(show.getText() + "\n" + newName);
                        showAge.setText(showAge.getText() + "\n" + newAge);
                    }

                    cursor.close();
                    db.close();
                    name.setText(null);
                    age.setText(null);
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("information", "name=?", new String[]{name.getText().toString()});


                myShow();


                db.close();
                Log.d("myDeBug", "DeleteSuccess");
                name.setText(null);
                age.setText(null);
            }
        });

    }

    public void myShow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        show.setText("姓名");
        showAge.setText("单号");
        Cursor cursor = db.rawQuery("select * from information", null);

        while (cursor.moveToNext()) {
            String newName = cursor.getString(cursor.getColumnIndex("name"));
            int newAge = cursor.getInt(cursor.getColumnIndex("age"));
            show.setText(show.getText() + "\n" + newName);
            showAge.setText(showAge.getText() + "\n" + newAge);
        }
        cursor.close();
    }
}
