package com.ymangu.know;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.know.utils.BroadcastHelper;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.voice.VoiceRecognizer;

public class MainActivity extends Activity implements OnClickListener {
	@ViewInject(R.id.switch_text_voice_layout)
	private Button switch_text_voice_layout;
	@ViewInject(R.id.voice_start)
	private Button voice_start;
	@ViewInject(R.id.back_2_navigation_or_to_voice)
	private Button back_2_navigation_or_to_voice;
	@ViewInject(R.id.sendbtn_or_to_iask)
	private Button sendbtn_or_to_iask;
	@ViewInject(R.id.text_start)
	private EditText text_start;
	@ViewInject(R.id.warning_layout)
	private View netLayout;
	@ViewInject(R.id.scolllayout)
	private ScrollView srollView;
	
	private boolean voiceState = true;
	private ConnectivityManager cm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main);
		ViewUtils.inject(this);    //注入View
	
		initView();  //初始化View
		checkNet();  // 一上来就检测一下网络
		registerBroadcast(); //注册监听网络状态的广播
		
		//生成语音识别对象
		voiceRecognizer = new VoiceRecognizer(this);  
		
		
	}
	
	//注册监听网络状态改变的广播
	private void registerBroadcast() {
		//注册监听网络状态改变的广播
		IntentFilter netFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		netFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(netReceive, netFilter);
		
		//注册activity销毁的广播
		IntentFilter destoryFilter = new IntentFilter(Constants.ACTIVITY_DESTORY_ACTION);
		destoryFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(destoryReceive, destoryFilter);
		
		
	}
	
	/**
	 * 相当于activity的destory方法
	 */
	private BroadcastReceiver destoryReceive = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.ACTIVITY_DESTORY_ACTION)) {
				
				unregisterReceiver(netReceive);//销毁广播
//				unregisterReceiver(navigationReceive);//销毁广播				
				unregisterReceiver(this);//自杀
				
			}
		}
	};
	
	
	//定义监听网络状态的广播接收者
	private BroadcastReceiver netReceive = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				//获得系统的连接管理器，网络管理器
				//获得激活的网络信息
				NetworkInfo info = cm.getActiveNetworkInfo();
			
//				boolean available = info.isAvailable();	 //当前网络是否有效			
//				boolean connected = info.isConnected();	//当前网络是否已连接				
//				String netType = info.getTypeName();//当前网络的类型 "WIFI" 
				if (info != null && info.isAvailable() && info.isConnected()) {
					//网络是可用的
					netLayout.setVisibility(View.GONE);					
				}else{
					//当前网络不可用
					netLayout.setVisibility(View.VISIBLE);				
					LogUtils.d("网络不可用");
				}
			}
			
		}
	};
	private VoiceRecognizer voiceRecognizer;
	
	/**
	 * .1. 被动方式(不推荐)：后台运行一个线程，每隔几秒监测一下当前网络情况
	 * 2. 监听一个系统广播(推荐)：android中当网络状态发生改变时，系统会发送一个网络状态改变的广播。
	 **/
	private void checkNet() {
		//系统网络连接服务管理器
		 cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 //记得添加权限，不然会出错
		 NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null && info.isAvailable() && info.isConnected()) {
				//网络可用
				netLayout.setVisibility(View.GONE);
			} else {
				//网络不可用
				netLayout.setVisibility(View.VISIBLE);
			}
	}
	/**
	 * . 这是被动方式，只用学习写在这里
	 **/
	private void checkNetOld() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = cm.getActiveNetworkInfo();
				//监测网络是否可用，如果不可用，再通过handler发送主线程去显示提示
				
			}
		};
		timer.schedule(task, 1000, 3000);		
	}
	
	
	
	private void initView() {
		switch_text_voice_layout.setOnClickListener(this);
		back_2_navigation_or_to_voice.setOnClickListener(this);
		sendbtn_or_to_iask.setOnClickListener(this);
		voice_start.setOnClickListener(this);		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_text_voice_layout:
			//语音模式变为文本模式
			/**
			 * 1.将语音输入的按钮隐藏
			 * 2.将back_2_navigation_or_to_voice 显示的图标更换
			 * 3.将自己隐藏
			 * 4.将sendbtn_or_to_iask按钮显示的图标更换
			 * 5.将text_start显示
			 */
			voice_start.setVisibility(View.GONE);
			back_2_navigation_or_to_voice.setBackgroundResource(R.drawable.voice_btn_bg);
			switch_text_voice_layout.setVisibility(View.GONE);
			sendbtn_or_to_iask.setBackgroundResource(R.drawable.send_btn_bg2);
			text_start.setVisibility(View.VISIBLE);
			voiceState = false;
			break;
		case R.id.back_2_navigation_or_to_voice:
			//从语音到文本模式转换
			/**
			 * 1.将back_2_navigation_or_to_voice显示的图标恢复成默认的
			 * 2.将text_start隐藏
			 * 3.将voice_start显示
			 * 4.将sendbtn_or_to_iask显示的图标更换
			 * 5.将switch_text_voice_layout显示
			 */
			
			/**
			 * .MainActivity 与PageActivity之间的通信 -- 通过广播来实现。
				要实现的效果：点击MainActivity中的按钮，让SlidingMenu滑出。
			 **/
			if (voiceState) {
				//打开slidingmenu
				//向pageActivity发送打开slidingmenu的广播
				Toast.makeText(this, "打开slidingmenu", 0).show();
				Intent intent = new Intent(Constants.SLIDING_MENU_ACTION);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				sendBroadcast(intent);  //发送广播
				
			} else {
				//
				back_2_navigation_or_to_voice.setBackgroundResource(R.drawable.introduction_btn_bg);
				text_start.setVisibility(View.GONE);
				voice_start.setVisibility(View.VISIBLE);
				sendbtn_or_to_iask.setBackgroundResource(R.drawable.iask_bottom_btn_bg);
				switch_text_voice_layout.setVisibility(View.VISIBLE);
				voiceState = true;  //文本模式
			}
			break;
		case R.id.sendbtn_or_to_iask:  //跳到爱问页面
			if (voiceState) {
				Toast.makeText(this, "viewpager滑动到爱问页面", 0).show();
				//向pageActivity发送打开爱问页面的广播
				BroadcastHelper.sendBroadCast(getApplicationContext(), Constants.WHERE_PAGE_ACTION, null, null);
			} else {
				//更新场景1
				String question = text_start.getText().toString();
				if (!TextUtils.isEmpty(question)) {
//					asyncTask.queryAnswer(question);
				}
			}		
			
			
			
			break;
			
		case R.id.voice_start:
			voiceRecognizer.start();//开始语音识别
			break;
		default:
			break;	
		}
		
	}
	
	/**
	 * .因为MainActivity被转换成了View，加入到ViewPager中。
	 * MainActivity的生命周期发生了改变，只有onCreate会被执行，所以写在onDestroy()中注销广播的
	 * 方法不会被执行，怎么办呢？
	 *  MainActivity 与PageActivity的生命周期是一样的，可以在PageActivity的onDestroy() 中发送一个
	 *  广播给MainActivity，接收到广播后，写一个相当于onDestroy()的方法 注销广播。 * 
	 * 
	 **/
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
