package com.example.xingli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Classification extends AppCompatActivity {
    private Button Button1;
    private Button Button2;
    private Button Button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        Button1 = findViewById(R.id.button1);
        Button2 = findViewById(R.id.button2);

        Button6 = findViewById(R.id.button6);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建Intent 对象
                Intent intent = new Intent(Classification.this, DictActivity.class);
                //启动Activity
                startActivity(intent);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建Intent 对象
                Intent intent = new Intent(Classification.this, CompanyActivity.class);
                //启动Activity
                startActivity(intent);
            }
        });


        Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建Intent 对象
                Intent intent = new Intent(Classification.this,LoginActivity.class);
                //启动Activity
                startActivity(intent);
            }
        });
    }
}
