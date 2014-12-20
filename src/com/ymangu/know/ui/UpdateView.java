package com.ymangu.know.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ymangu.know.R;
import com.ymangu.know.utils.DateUtil;
import com.ymangu.know.voice.VoicePlayer;

/**
 * .    <!-- 
            	当想让一个scrollview的子控件全屏也就是fillparent的时候，
            	设置android:layout_height="fill_parent"不起作用，
            	需要加上fillviewport属性，但是当子控件的高度等于scrollview的高度时，
            	这个标签就没有任何意义了            	
            	scrollbars：不显示滑动条
             -->
   *          ②  ScrollView 只能有一个子View,但是这个子View可以有多个子View
 **/


public class UpdateView {
	private Context context;
	private ScrollView scrollView;
	private LinearLayout baseLayout;
	private VoicePlayer player;
	
	public UpdateView(Context context,ScrollView scrollView, VoicePlayer player) {

		this.context = context;
		this.scrollView = scrollView;
		this.baseLayout = (LinearLayout) scrollView.findViewById(R.id.base_layout);
		this.player = player;
	
	}
	//	更新我说的话,在MainActivity上显示
	public void updateMyView(String result) {
		// InflateView是自定义的用于 把 xml 转换成 view的工具类
		View myView = InflateView.inflateMyView(context);
		TextView date = (TextView) myView.findViewById(R.id.messagedetail_row_date); //时间戳
		TextView myTv = (TextView) myView.findViewById(R.id.messagedetail_row_text);
		
		date.setText(DateUtil.getCurrentTime());
		myTv.setText(result);
		
		baseLayout.addView(myView);  //将我的场景更新到scrollview里的baselayout上
		scrollToBottom();  //不断增加View时，不断更新SrollView
	}
	
	private void scrollToBottom() {
		scrollView.post(new Runnable() {

			@Override
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			}
			
		});
	}
	
	//更新它的对话
	public void updatSheView(String jokeAnswer) {

		View sheView = InflateView.inflateSheView(context);
		TextView date = (TextView) sheView.findViewById(R.id.messagedetail_row_date);
		TextView sheTv = (TextView) sheView.findViewById(R.id.messagedetail_row_text);
		sheTv.setText(jokeAnswer);
		date.setText(DateUtil.getCurrentTime());
		this.player.play(jokeAnswer);  //调用 语音播放
		baseLayout.addView(sheView);
		
		scrollToBottom();
		
		
	}
	
	//更新MM图片
	public void updateMMView(String string, int mmAnswer) {
		View mmView = InflateView.inflateMMView(context);
		TextView tv = (TextView) mmView.findViewById(R.id.messagedetail_row_text);
		ImageView mm = (ImageView) mmView.findViewById(R.id.image_mm);
		
		tv.setText(string);
		mm.setImageResource(mmAnswer);
		this.player.play(string);
		baseLayout.addView(mmView);
		
		scrollToBottom();
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	

}
