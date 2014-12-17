package com.ymangu.know;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class IAskActivity extends Activity {
	@ViewInject(R.id.iaskText)
	private TextView iaskTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iask_layout);
		ViewUtils.inject(this);
		//得到类名
		iaskTextView.setText(IAskActivity.class.getSimpleName());
		
		
	}

}
