package com.example.xingli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        homeButton= (Button) findViewById(R.id.button);
        //注册事件
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建Intent 对象
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                //启动Activity
                startActivity(intent);
            }
        });
    }

}
