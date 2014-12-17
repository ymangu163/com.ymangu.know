package com.ymangu.know;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.know.adapter.ViewPagerAdapter;
/**
 * 让ViewPager滑动activity
 * 一般情况下，ViewPager滑动view或者是Fragment
 * 滑动的activity的生命周期方法只有onCreate会被调用，onResume ... 不会被调用
 */
// PageActivity 包函 MainActivity和 IAskActivity.
public class PageActivity extends Activity {
	@ViewInject(R.id.pager)
	private 	android.support.v4.view.ViewPager viewPager;
	private LocalActivityManager lam;
	private SlidingMenu sm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_layout);
		ViewUtils.inject(this);
		
		//将滑动的Activity转成view,ViewPager只能添加View		
		 lam = new LocalActivityManager(this, true);
		 lam.dispatchCreate(savedInstanceState);  //这个方法必须被调用不能会崩掉
		initActivity();
		
		//实例化一个slidingmenu
		sm = new SlidingMenu(this);
		sm.setMode(SlidingMenu.LEFT);   //slidingmenu出现的屏幕左右方向
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);  //设置滑出slidingmenu的地方
		sm.setShadowDrawable(R.drawable.shadow);//  设置主页面和slidingmenu页面之间的阴影图片
		sm.setShadowWidthRes(R.dimen.shadow_width);//设置阴影的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);//设置主页面在slidingmenu上显示的宽度
//		sm.setBehindWidth(400);//直接设置slidingmenu的宽度
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//将当前的slidingmenu作为activity的一部分显示
		
		sm.setMenu(R.layout.navigation_layout);//设置slidingmenu显示的layout
		
	}
	//将滑动的Activity转成view
	@SuppressWarnings("deprecation")
	private void initActivity() {
		//只所以 new ArrayList 而不是 new List 是因为List是一个接口，不能new，ArrayList才是实现子类
		List<View> viewList=new ArrayList<View>();
		
		//将MainActivity转换成 View
		Intent intent=new Intent(this,MainActivity.class);
		View mainView = lam.startActivity("MainActivity", intent).getDecorView();
		viewList.add(mainView);
		
		//"IAskActivity"为 tag,也可称为id
		Intent intent2=new Intent(this,IAskActivity.class);
		View iaskView = lam.startActivity("IAskActivity", intent2).getDecorView();		
		viewList.add(iaskView);
		
		//将viewList 放到 ViewPager的适配器中去显示
		ViewPagerAdapter adapter=new ViewPagerAdapter(viewList);
		viewPager.setAdapter(adapter); 
		
		
	}
	
	
	

}
