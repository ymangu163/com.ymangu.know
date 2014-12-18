package com.ymangu.know;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.anim.ExpandAnimation;
import com.ymangu.know.domain.ViewBean;
/**
 * 前提：将所有的title和对应的layout封装ViewBean中，然后将所有ViewBean放到List中
 * 
 * 1.点击任意一个title的时候，将该title对应的隐藏的layout显示出来，并将该title置为选中状态
 * 2.在点击其他title的时候，需要判断之前有没有打开过动画
 * 3.如果打开过，需要将之前打开的动画关闭，并且将之前的按下效果恢复成默认
 * 
 */
public class NavigationHandler {
	private Activity activity;
	private List<ViewBean> viewList;
	private boolean animState=true;  //标记是否可以执行动画

	
	public NavigationHandler(Activity activity) {
		this.activity=activity;
		initViews();
	}
	
	private void initViews(){
		viewList = new ArrayList<ViewBean>();
		//1.将所有的viewTitle和viewLayout封装成ViewBean，并且用list保存
		ViewBean bean = new ViewBean();
		View view = activity.findViewById(R.id.navigation_root_view);  //id为根 LinearLayout
		
		//初始化营业厅
		RelativeLayout businessHallTitle = (RelativeLayout) view.findViewById(R.id.find_business_hall_title);
		final View businessHallLayout = view.findViewById(R.id.business_hall_layout);
		bean.setViewTitle(businessHallTitle);
		bean.setViewLayout(businessHallLayout);
		viewList.add(bean);
		
		//初始化国际漫游
				RelativeLayout internationalRoamingTitle = (RelativeLayout) view.findViewById(R.id.international_roaming_title);
				final View internationalRoamingLayout = view.findViewById(R.id.international_roaming_layout);
				bean = new ViewBean();
				bean.setViewTitle(internationalRoamingTitle);
				bean.setViewLayout(internationalRoamingLayout);
				viewList.add(bean);
		
				//初始化查找手机
				RelativeLayout cellPhoneTitle = (RelativeLayout) view.findViewById(R.id.find_cellphone_title);
				final View cellPhoneLayout = view.findViewById(R.id.find_cellphone_layout);
				bean = new ViewBean();
				bean.setViewTitle(cellPhoneTitle);
				bean.setViewLayout(cellPhoneLayout);		
		
				//初始化套餐
				RelativeLayout packageTitle = (RelativeLayout) view.findViewById(R.id.package_title);
				final View packageLayout = view.findViewById(R.id.package_layout);
				bean = new ViewBean();
				bean.setViewTitle(packageTitle);
				bean.setViewLayout(packageLayout);
				viewList.add(bean);
				
				//初始化实时话费
				RelativeLayout telephoneChargeTitle = (RelativeLayout) view.findViewById(R.id.telephone_charge_title);
				final View telephoneChargeLayout = view.findViewById(R.id.telephone_charge_layout);
				bean = new ViewBean();
				bean.setViewTitle(telephoneChargeTitle);
				bean.setViewLayout(telephoneChargeLayout);
				viewList.add(bean);
				
				//初始化拨打客服电话
				RelativeLayout customerServicePhoneTitle = (RelativeLayout) view.findViewById(R.id.call_customer_service_phone_title);
				final View customerServicePhoneLayout = view.findViewById(R.id.call_customer_service_phone_layout);
				bean = new ViewBean();
				bean.setViewTitle(customerServicePhoneTitle);
				bean.setViewLayout(customerServicePhoneLayout);
				viewList.add(bean);
				
				//初始化唐诗
				RelativeLayout tangTitle = (RelativeLayout) view.findViewById(R.id.listen_tang_poetry_title);
				final View tangPoetryLayout = view.findViewById(R.id.tang_poetry_layout);
				bean = new ViewBean();
				bean.setViewTitle(tangTitle);
				bean.setViewLayout(tangPoetryLayout);
				viewList.add(bean);
				
				//初始化笑话
				RelativeLayout jokeTitle = (RelativeLayout) view.findViewById(R.id.say_joke_title);
				final View jokeLayout = view.findViewById(R.id.jokey_layout);
				bean = new ViewBean();
				bean.setViewTitle(jokeTitle);
				bean.setViewLayout(jokeLayout);
				viewList.add(bean);
				
				//初始化美女
				RelativeLayout beautyTitle = (RelativeLayout) view.findViewById(R.id.see_beauty_title);
				final View beautyLayout = view.findViewById(R.id.beauty_layout);
				bean = new ViewBean();
				bean.setViewTitle(beautyTitle);
				bean.setViewLayout(beautyLayout);
				viewList.add(bean);
				
				//初始化应用
				RelativeLayout appTitle = (RelativeLayout) view.findViewById(R.id.find_app_title);
				final View appLayout = view.findViewById(R.id.app_layout);
				bean = new ViewBean();
				bean.setViewTitle(appTitle);
				bean.setViewLayout(appLayout);
				viewList.add(bean);
				
				// 把初始化好的List数据源传入				
				setAnimation(viewList); 
		
		
	}

	private void setAnimation(List<ViewBean> viewList2) {
		//给title设置点击事件，但是执行动画的是title对应的layout
		for(ViewBean bean:viewList){
			final View viewTitle = bean.getViewTitle();   //得到title，触发的开关
			final View viewLayout = bean.getViewLayout();  //得到layout,由它执行动画
			viewTitle.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//给title对应的layout执行动画的操作
					executeAnimation(viewTitle,viewLayout);
					
					
				}
			});
			
			
		}
		
		
		
		
		
		
		
	}
	
	
	/**
	 * 执行动画
	 * @param viewLayout
	 */
	protected void executeAnimation(View viewTitle, View viewLayout) {
		/**
		 * 执行动画的view如果是隐藏状态，自动展开，展开后将其设置为可见状态
		 * 执行动画的view如果是可见状态，自动隐藏，隐藏后将其设置为不可见状态。
		 */
		
		if (animState) { //表示当前可以执行动画
			ExpandAnimation anim = new ExpandAnimation(viewLayout, 300); //用开源项目的动画
			// true 已展开，false 已关闭
			boolean toggle = anim.toggle();  
			if (toggle) {//表示当前动画已经展开
				
				
				
				
			}else{//当前动画已经关闭
				
				
			}
			
			
			
			
			viewLayout.startAnimation(anim); //让viewLayout执行动画
			anim.setAnimationListener(animationListener); //动画监听
			
		}else{  //表示当前不可以执行动画
			LogUtils.d( "can't play animation");
			
		}

		
		
		
		
	}
	
	//动画监听事件
	private Animation.AnimationListener animationListener=new Animation.AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			
		}
	};
	
	
	
	

}
