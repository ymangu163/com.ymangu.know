package com.ymangu.know;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ymangu.know.adapter.ViewPagerAdapter;
import com.ymangu.know.recognizer.VoiceSpeechHelper;
import com.ymangu.know.ui.ShareImageTask;
import com.ymangu.know.utils.ActivityUtil;
import com.ymangu.know.utils.AppHelper;
import com.ymangu.know.utils.BroadcastHelper;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.utils.DialogUtil;
/**
 * 让ViewPager滑动activity
 * 一般情况下，ViewPager滑动view或者是Fragment
 * 滑动的activity的生命周期方法只有onCreate会被调用，onResume ... 不会被调用
 */
import com.ymangu.know.utils.DensityUtil;
import com.ymangu.know.voice.SettingPreferenceActivity;

/**
 * . Activity 与Fragment 方式使用 SlidingMenu的不同
① 得到SlidingMenu对象的区别：
    	Activity: 获得 SlidingMenu对象是 new SlidingMenu()
    	Fragment: Activity 继承自SlidingFragmentActivity,通过 getSlidingMenu得到 SlidingMenu
 ② 设置SlidingMenu使用的布局的区别：
 	Activity:  sm.setMenu(layout  res  id)
 	Fragment:  setBehindContentView(R.layout.menu_frame);  FramLayout 用来装载Fragment 
     继承自 SlidingFragmentActivity 使用的布局文件是一个全屏的FramLayout,
 	 slidingMenu 的主页面的内容也是Framement来显示；
 **/

// PageActivity 包函 MainActivity和 IAskActivity.
public class PageActivity extends Activity implements OnClickListener {
	@ViewInject(R.id.pager)
	private 	ViewPager viewPager;
	@ViewInject(R.id.more_btn)
	private Button moreBtn;  //“：”的按钮
	@ViewInject(R.id.sub_menu)
	private LinearLayout subMenu;  //subMenu
	@ViewInject(R.id.setting_layout)
	private LinearLayout settingLayout;
	
	protected static VoiceSpeechHelper voiceHelper;
	private LocalActivityManager lam;
	private SlidingMenu sm;
	private boolean animState = true;  //记住动画的状态
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager_layout);
		ViewUtils.inject(this);
		
		viewPager.setOnPageChangeListener(onPageChangeListener);
		
		initViews();
		
		//将滑动的Activity转成view,ViewPager只能添加View		
		 lam = new LocalActivityManager(this, true);
		 lam.dispatchCreate(savedInstanceState);  //这个方法必须被调用不能会崩掉
		initActivity();
		
		initSlidingMenu();
		
		//加载动画
		topDownAnim = AnimationUtils.loadAnimation(this, R.anim.top_dowm);
		 topUpAnim = AnimationUtils.loadAnimation(this, R.anim.top_up);
		//设置动画执行后的监听处理
		topDownAnim.setAnimationListener(topDownAnimListener);
		topUpAnim.setAnimationListener(topUpAnimListener);
		 
		 //接收来自MainActivity的广播
		registerBroadcast();
		
	}
	
	 //接收来自MainActivity的广播
	private void registerBroadcast() {
		//注册slidingMenu滑出的广播
		IntentFilter smIntentFilter = new IntentFilter(Constants.SLIDING_MENU_ACTION);
		smIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(smReceive, smIntentFilter);
		
		//注册去往爱问页的广播
		IntentFilter pageFilter = new IntentFilter(Constants.WHERE_PAGE_ACTION);
		pageFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(pageReceive, pageFilter);
	}
	
	/**
	 * 去往爱问的广播
	 */
	private BroadcastReceiver pageReceive = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.WHERE_PAGE_ACTION)) {
				viewPager.setCurrentItem(1);   //viewPager跳转
			}
		}
		
	};
	
	// 定义一个广播接收者
	private BroadcastReceiver smReceive = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Constants.SLIDING_MENU_ACTION)) {
				sm.toggle();   //把开滑动菜单
			}
		}
		
	};
	//初始化View
	private void initViews() {
		moreBtn.setOnClickListener(this);
		settingLayout.setOnClickListener(this);   //添加点击事件
		
		//subMenu 的4个按钮的点击监听
		findViewById(R.id.setting_layout).setOnClickListener(this);
    	findViewById(R.id.login_layout).setOnClickListener(this);
    	findViewById(R.id.help_layout).setOnClickListener(this);
    	findViewById(R.id.update_layout).setOnClickListener(this);
    	findViewById(R.id.share_icon_layout).setOnClickListener(this);
		
		
	}

	private ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			//目的：让viewpager第一页的时候滑出slidingmenu，第二页滑动的时候回到第一页
			switch (position) {
			case 0:
				sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//只有在首页时才允许滑出slidingmenu
				break;
			case 1:
				sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//
				break;
			default:
				sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//在viewpager的其他页的时候，不允许滑出slidingmenu
				break;					
			}
			
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};
	
	private Animation topDownAnim;
	private Animation topUpAnim;
	//向下动画的监听事件
	private AnimationListener topDownAnimListener = new AnimationListener(){
		@Override
		public void onAnimationStart(Animation animation) {
		}
		@Override
		public void onAnimationEnd(Animation animation) {
			/**
			 * 1.让当前动画固定住，不要回到原来的位置
			 * 2.为了响应点击事件，需要重新设置submenu的位置和宽高
			 */
			animation.setFillAfter(true);//动画执行后停住
			//先设置submenu的宽高, dip2px 在代码中设置dp的一个工具
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getApplicationContext(),70));
			
			//让其在titlebar的下边，因为titlebar是50dp，向下移动50dp，正好就显示出了submenu
			params.setMargins(0, DensityUtil.dip2px(getApplicationContext(), 50), 0, 0);
			subMenu.clearAnimation();			//清掉submenu上的动画
			subMenu.setLayoutParams(params);		//将参数设置给submenu	
			
		}
		@Override
		public void onAnimationRepeat(Animation animation) {
		}		
	};
	// 向上动画的监听
	private AnimationListener topUpAnimListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			animation.setFillAfter(true);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getApplicationContext(),70));
			params.setMargins(0, 0, 0, 0);
			subMenu.clearAnimation();//清掉submenu上的动画
			subMenu.setLayoutParams(params);//将参数设置给submenu
			subMenu.setVisibility(View.GONE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}
	};
	
	
	private void initSlidingMenu() {
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
		
		new NavigationHandler(this,sm);//实现导航页的动画需要的代码量比较大，所以写在单独的类中。
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_btn:
			//触发动画，打开动画或者关闭动画
			if (animState) { 	//true 为 执行向下动画
				showSubMenu();  
				
			}else{//false 为 执行向上动画
				hideSubMenu();   //隐藏子菜单
			}
					
			break;
		case R.id.setting_layout:   
			Toast.makeText(getApplicationContext(), "点击了setting 按钮", 0).show();
			
			hideSubMenu();   //隐藏子菜单			
			ActivityUtil.goToActivity(this, SettingPreferenceActivity.class);
			break;			
			
		case R.id.login_layout:	
			hideSubMenu();   
			 DialogUtil.showLoginDialog(this);
			break;
		case R.id.help_layout:
			hideSubMenu();   
			 sm.toggle();  //显示滑动菜单
			break;
			
			// 更新 apk
		case R.id.update_layout:  
			hideSubMenu();   
			AppHelper.hasUpdate(this,true);   //去下载新版本的apk，并安装
			break;
		
			//一键分享截图
		case R.id.share_icon_layout:	
			View view = getWindow().getDecorView(); 
			new ShareImageTask(this,voiceHelper,view).execute();   //分享
			hideSubMenu();
			
			break;
		default:
			break;
		
		}
	}
	
	//显示子菜单
	private void showSubMenu() {  
		animState = false;
		subMenu.setVisibility(View.VISIBLE);
		subMenu.startAnimation(topDownAnim);  //绑定View 开启动画
	}

	private void hideSubMenu() {  //false 为 执行向上动画
		animState = true;
		subMenu.startAnimation(topUpAnim);
	}
	
	// 在onDestroy()中 记得要销毁广播，不然后报错
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//给 MainActivity 发送一个广播，通过执行了 onDestroy()
		BroadcastHelper.sendBroadCast(getApplicationContext(), Constants.ACTIVITY_DESTORY_ACTION, null, null);
		
		unregisterReceiver(smReceive);
		smReceive = null;
	}
	

}
