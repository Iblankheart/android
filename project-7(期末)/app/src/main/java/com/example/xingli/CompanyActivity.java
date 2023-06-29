package com.example.xingli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CompanyActivity extends AppCompatActivity {
    private Button btn_xl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        btn_xl1 = findViewById(R.id.btn_xl);
        btn_xl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建Intent 对象
                Intent intent = new Intent(CompanyActivity.this, Classification.class);
                //启动Activity
                startActivity(intent);
            }
        });
    }
}
