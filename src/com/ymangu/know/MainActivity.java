package com.ymangu.know;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity {
	@ViewInject(R.id.mainText)
	private TextView  mainTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_layout);
		ViewUtils.inject(this);
		//得到类名
		mainTextView.setText(MainActivity.class.getSimpleName());
		
		
		
	}
	

}
