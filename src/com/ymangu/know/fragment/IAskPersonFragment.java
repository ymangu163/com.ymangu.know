package com.ymangu.know.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ymangu.know.R;
import com.ymangu.know.server.Config.IAsk;

public class IAskPersonFragment extends BaseFragment implements OnClickListener, OnItemClickListener {
	
	private RelativeLayout myAnswer;
	private RelativeLayout myAsk;
	private RelativeLayout myMessage;
	
	private ImageView iaskHightestRewardGreenBg;
	
	private TextView username;
	private TextView wisdomMoney;
	private TextView level;
	private TextView title;
	private TextView experience;
	private Button loginBtn;
	private ListView listView;
	private List<IAsk> list;
	
	private static ProgressBar progressBar;
	private static LinearLayout progressbarLayout;
	private static TextView progressbarTips;
	
	private boolean loginSuccess;
	
	@Override
	protected View initView(LayoutInflater inflater) {
		rootView = inflater.inflate(R.layout.iask_person_fragment, null);
		return rootView;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		 initViewsAndClickListener();  //初始化View
		
		
		
		
	}

	
	private void initViewsAndClickListener() {
		
		myAnswer = (RelativeLayout) rootView.findViewById(R.id.iask_my_answer_layout);
        myAsk = (RelativeLayout) rootView.findViewById(R.id.iask_my_ask_layout);
        myMessage = (RelativeLayout) rootView.findViewById(R.id.iask_my_message_layout);
        loginBtn = (Button) rootView.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        iaskHightestRewardGreenBg = (ImageView) rootView.findViewById(R.id.iask_highest_reward_green_bg);
		
        username = (TextView) rootView.findViewById(R.id.user_name);
        wisdomMoney = (TextView) rootView.findViewById(R.id.wisdom_money2);
        level = (TextView) rootView.findViewById(R.id.user_level);
        title = (TextView) rootView.findViewById(R.id.user_title);
        experience = (TextView) rootView.findViewById(R.id.user_experience);
		
        progressbarTips = (TextView) rootView.findViewById(R.id.circle_progressbar_tips);
        progressbarLayout = (LinearLayout) rootView.findViewById(R.id.progressbar_layout);
        progressBar = (ProgressBar) rootView.findViewById(R.id.circle_progressbar);
        listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
		
        myAnswer.setOnClickListener(this);
        myAsk.setOnClickListener(this);
        myMessage.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		
		
		
		
	}

	//ListView 的Item点击事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		
		
	}

}
