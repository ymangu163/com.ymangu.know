package com.ymangu.know;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.know.adapter.IAskFragmentAdapter;
import com.ymangu.know.fragment.IAskFragment;
import com.ymangu.know.utils.Constants;

public class IAskActivity extends FragmentActivity  implements  OnClickListener{
	@ViewInject(R.id.iask_main_view_pager)
	private ViewPager  viewPager;
	
	@ViewInject(R.id.iask_ask_text)
	private TextView iask_ask_text;
	@ViewInject(R.id.iask_answer_text)
	private TextView iask_answer_text;
	@ViewInject(R.id.iask_person_text)
	private TextView iask_person_text;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iask_layout_manager_fragment);
		ViewUtils.inject(this);
		
		//准备适配器，滑动三个fragment
		IAskFragmentAdapter fragmentAdapter = new IAskFragmentAdapter(getSupportFragmentManager());
		viewPager.setAdapter(fragmentAdapter);
		viewPager.setOnPageChangeListener(changeListener); //设置viewPager滑动监听事件
		
		registerBroadcast();
		
		//TextView 的点击监听
		iask_ask_text.setOnClickListener(this);
		iask_answer_text.setOnClickListener(this);
		iask_person_text.setOnClickListener(this);
		
	}
	//注册广播
	private void registerBroadcast() {
		   // 注册语音识别的结果接收广播 
		   IntentFilter intentFilterVoice = new IntentFilter(Constants.VOICE_RECOGNITION_RESULT_ACTION);
	        intentFilterVoice.addCategory(Intent.CATEGORY_DEFAULT);	      
	        this.registerReceiver(voiceRecognitionReceiver, intentFilterVoice);
		
		//注册activity销毁的广播
		IntentFilter destoryFilter = new IntentFilter(Constants.ACTIVITY_DESTORY_ACTION);
		destoryFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(destoryReceive, destoryFilter);
		
	}
	
	private BroadcastReceiver voiceRecognitionReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.VOICE_RECOGNITION_RESULT_ACTION)) {
				String result = intent.getStringExtra(Constants.VOICE_RECOGNITION_RESULT);
				IAskFragment.updateCompleted(result);
			}
		}
		
	};
	
	
	/**
	 * 相当于activity的destory方法
	 */
	private BroadcastReceiver destoryReceive = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.ACTIVITY_DESTORY_ACTION)) {
				
				unregisterReceiver(voiceRecognitionReceiver);
				unregisterReceiver(this);//自杀
				
			}
		}
	};
	
	
	
	
	private ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener () {

		@Override
		public void onPageScrollStateChanged(int arg0) {			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {			
		}
		// ViewPager 滑动时 改变3个TextView的状态
		@Override
		public void onPageSelected(int position) {			
			switch (position) {
			case 0:
				setIAskSelectedState();
				break;
			case 1:
				setIAskAnswerSelectedState();
				break;
			case 2:
				setIAskPersonSelectedState();
				break;
			default:
				break;
			}		
			
		}
		
		
	};

	/**
	 * . 点击3个TextView时，跳转到不同的Fragment
	 **/
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.iask_ask_text:
			viewPager.setCurrentItem(0,false);
			//我要答和个人中心为普通状态，自己为按下状态
			setIAskSelectedState();
			
			break;
		case R.id.iask_answer_text:
			viewPager.setCurrentItem(1,false);
			
			setIAskAnswerSelectedState();
			
			break;
		case R.id.iask_person_text:
			viewPager.setCurrentItem(2,false);
			
			setIAskPersonSelectedState();
			break;
		default:
			break;
		
		
		}
		
		
	}



	private void setIAskPersonSelectedState() {
		iask_person_text.setBackgroundResource(R.drawable.iask_bottom_tab_ask_pressed);
		iask_answer_text.setBackgroundResource(R.drawable.iask_bottom_tab_answer_bg);
		iask_ask_text.setBackgroundResource(R.drawable.iask_bottom_tab_answer_bg);
		
	}

	private void setIAskAnswerSelectedState() {
		iask_answer_text.setBackgroundResource(R.drawable.iask_bottom_tab_ask_pressed);
		iask_ask_text.setBackgroundResource(R.drawable.iask_bottom_tab_answer_bg);
		iask_person_text.setBackgroundResource(R.drawable.iask_bottom_tab_answer_bg);
		
	}
	private void setIAskSelectedState() {
		iask_ask_text.setBackgroundResource(R.drawable.iask_bottom_tab_ask_pressed);
		iask_answer_text.setBackgroundResource(R.drawable.iask_bottom_tab_answer_bg);
		iask_person_text.setBackgroundResource(R.drawable.iask_bottom_tab_answer_bg);
		
	}

}
