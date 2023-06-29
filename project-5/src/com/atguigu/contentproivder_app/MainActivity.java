package com.atguigu.contentproivder_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
private EditText et_main_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_main_number = (EditText) findViewById(R.id.et_main_number);
	}
	
	//选择联系人
	public void showContacts(View view){
		startActivityForResult(new Intent(MainActivity.this, ContactListActivity.class), 1);
	}
	
	//带回调的方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			//取出携带的电话号码
			String number = data.getStringExtra("number");
			//设置到输入框中
			et_main_number.setText(number);
		}
	}
}
