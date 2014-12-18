package com.ymangu.know.fragment;

import com.ymangu.know.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class IAskAnswerFragment extends BaseFragment {

	@Override
	protected View initView(LayoutInflater inflater) {
		View view  = inflater.inflate(R.layout.freg_text,null);
		TextView tv = (TextView) view.findViewById(R.id.text);
		tv.setText(IAskAnswerFragment.class.getSimpleName());//显示当前的fragment的名字
		return view;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {

	}

}
