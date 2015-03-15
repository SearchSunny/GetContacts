package com.example.getcontacts.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.getcontacts.R;
import com.example.getcontacts.bean.Person;
import com.example.getcontacts.bean.SerializableMap;
/**
 * 获取手机联系人，并实现搜索功能
 * @author miaowei
 *
 */
public class MainActivity extends BaseActivity {

	private Button mBtn_getcontacts;
	
	private TextView mParticipantsNum; 
	private TextView mGet_person; 
	/**
	 * 此Map用于接收选中联系人的ID和状态
	 */
	private SerializableMap map;
	
	/**
	 * 记录已选择人数
	 */
	private HashMap<Object,Person> personMap;
	
	/**
	 * 参与人员姓名
	 */
	private ArrayList<String> personName;
	
	private StringBuilder strPersonName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = new SerializableMap();
		personMap = new HashMap<Object, Person>();
		personName = new ArrayList<String>();
		
		
		mParticipantsNum = (TextView) findViewById(R.id.participantsNum);
		mGet_person = (TextView) findViewById(R.id.get_person);
		mBtn_getcontacts = (Button) findViewById(R.id.btn_getcontacts);
		mBtn_getcontacts.setOnClickListener(onClickListener);
		
	}
	/**
	 * 获取事件
	 */
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.btn_getcontacts:
				Intent intent = new Intent();
				
				intent.setClass(MainActivity.this, ContactsListMultipleActivity.class);
				
				intent.putExtra("map", map);
				
				intent.putExtra("personMap", personMap);
			
				startActivityForResult(intent, 110);
				break;

			default:
				break;
			}
			
		}
		
	};
	
	
	/**
	 * 保存联系人
	 */
	private ArrayList<Person> persions;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/**
		 * 处理调用联系人的返回操作
		 */
		switch (requestCode) {
		case 110:
			System.out.println(resultCode);
			personName.clear();
			if (map.getMap() != null && map.getMap().size() > 0) {
				
				map.getMap().clear();
			}
			if (personMap != null && personMap.size() > 0) {
				
				personMap.clear();
			}
			strPersonName = new StringBuilder();
			
			Message msg = new Message();
			msg.what = 10;
			
			if (data != null && data.getExtras() != null) {
				persions = (ArrayList<Person>) data.getExtras().getSerializable("GET_CONTACT");
				map = (SerializableMap)data.getExtras().getSerializable("map");
				personMap = (HashMap<Object, Person>)data.getExtras().getSerializable("personMap");
				int size = persions.size();
				for (Person person : persions) {
					String phone = person.getPhone();
					String name = person.getName();
					strPersonName.append(name+"，");
					personName.add(name);
					Log.i("lock","name======"+name);
				}
				msg.arg1 = size+1;
				String name = strPersonName.replace(strPersonName.lastIndexOf("，"), strPersonName.length(), " ").toString();
				msg.obj = name;
				strPersonName = null;
				mHandler.sendMessage(msg);
			}else{
				
				msg.arg1 = 0;
				mHandler.sendMessage(msg);
			}

			break;
		}
	}
	
	/**
	 * 处理界面更新
	 */
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 10:
				mParticipantsNum.setText(String.valueOf(((int)msg.arg1 - 1)));
				String str = (String)msg.obj;
				mGet_person.setVisibility(View.VISIBLE);
				mGet_person.setText(str);
				break;
			default:
				break;
			}
			
			
		};
	};

}
