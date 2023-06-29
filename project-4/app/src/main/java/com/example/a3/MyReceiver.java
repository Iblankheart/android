package com.example.a3;

class MyReceiver : BroadcastReceiver() {
        val TAG  = "MyReceiver"
        override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d(TAG,"${Thread.currentThread().name} 收到了广播...")
        }
}