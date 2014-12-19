package com.ymangu.know;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.know.utils.Constants;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		ViewUtils.inject(this);
	
		initView();
		
		
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
		case R.id.sendbtn_or_to_iask:
			
			break;
		default:
			break;	
		}
		
	}
	

}
