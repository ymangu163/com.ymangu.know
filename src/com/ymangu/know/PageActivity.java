package com.ymangu.know;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_layout);
		ViewUtils.inject(this);
		
		//将滑动的Activity转成view,ViewPager只能添加View		
		 lam = new LocalActivityManager(this, true);
		 lam.dispatchCreate(savedInstanceState);  //这个方法必须被调用不能会崩掉
		initActivity();
		
		
		
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
