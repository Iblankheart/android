package com.example.a3

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.a3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn).setOnClickListener {

            Log.d(TAG,"${Thread.currentThread().name} main thread .")

            Thread{
                val castIntent = Intent("cn.edu.swu.cs.android.lesson6.MY_CAST")
                castIntent.setPackage(packageName)
                castIntent.putExtra("name","swu")
                sendBroadcast(castIntent)

                Log.d(TAG,"${Thread.currentThread().name} send cast.")
            }.start()


        }

        // 动态注册广播接收器
        registerReceiver(MyReceiver(), IntentFilter("cn.edu.swu.cs.android.lesson6.MY_CAST"))
    }
}