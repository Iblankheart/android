package com.atguigu.contentproivder_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListActivity extends ListActivity {
	private ContactAdapter adapter;
	private List<Map<String, String>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_contact_list);
		adapter = new ContactAdapter();
		// 使用ContentResolver对象查询
		ContentResolver resolver = getContentResolver();
		Cursor cursor = resolver.query(Phone.CONTENT_URI, new String[] {
				Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, null);
		list = new ArrayList<Map<String, String>>();
		while (cursor.moveToNext()) {
			String name = cursor.getString(0);
			String number = cursor.getString(1);
			Map<String, String> map = new HashMap<String, String>();
			map.put("NAME", name);
			map.put("NUMBER", number);
			list.add(map);
		}
		cursor.close();
		// 使用ListActivty内部的listView
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		//得到当前行的号码
		String number = list.get(position).get("NUMBER");
		Intent intent = new Intent();
		intent.putExtra("number", number);
		//待结果的返回
		setResult(RESULT_OK, intent);
		//移除当前界面
		finish();
	}

	class ContactAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(ContactListActivity.this, R.layout.item_contact, null);
				holder.tv_item_name = (TextView) convertView.findViewById(R.id.tv_item_name);
				holder.tv_item_number = (TextView) convertView.findViewById(R.id.tv_item_number);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			Map<String, String> map = list.get(position);
			holder.tv_item_name.setText(map.get("NAME"));
			holder.tv_item_number.setText(map.get("NUMBER"));
			return convertView;
		}
		
		class ViewHolder{
			public TextView tv_item_name;
			public TextView tv_item_number;
		}
	}
}
