package com.ymangu.know;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * .   //1.拿到当前acitivity的layout的view
        //2.绑定透明度变化的动画，并添加动画监听事件
        //3.动画执行完毕后，进入主页面
 **/
public class WelcomeActivity extends Activity {
	@ViewInject(R.id.welcome_layout)
	private View relativeView;	//① 使用ViewUtils方法找到控件，控件名字可以自定义
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {;
	
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.welcome);
		
		ViewUtils.inject(this);	//② 注入view和事件
		AlphaAnimation anim=new AlphaAnimation(0.1f, 1.0f); //透明度动画
		anim.setDuration(3000);
		relativeView.startAnimation(anim);  //为View 添加动画
		anim.setAnimationListener(am);  //设置动画的监听事件
		
	}
	
	private  AnimationListener am=new AnimationListener(){

		@Override
		public void onAnimationStart(Animation animation) {
		}
		
		//动画执行结束的时候去主页面
		@Override
		public void onAnimationEnd(Animation animation) {
			
			
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
		
	};
	
	
	
	
	
	
	
	
	
	
}
