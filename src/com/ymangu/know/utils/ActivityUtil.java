package com.ymangu.know.utils;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.R;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ActivityUtil {

	private static final String TAG = "ActivityUtil";
	
	/**
	 * 延迟去往新的Activity
	 * @param context
	 * @param cls
	 * @param delay
	 */
	public static void delayToActivity(final Context context,final Class<?> cls,long delay) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				context.startActivity(new Intent(context, cls));
			}
		}, delay);
	}
	/**
	 * 跳转到另一个Activity，不携带数据，不设置flag
	 * @param context
	 * @param cls
	 */
	public static void goToActivity(Context context,Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(intent);
	}
	/**
	 * 跳到另一个Activiy，使用动画
	 * @param context   -- 上下文
	 * @param cls	--- 跳到哪个Activity
	 * @param enterAnim  -- 进入时动画
	 * @param exitAnim  -- 退出时动画
	 *  bundle  --  传递的数据
	 */
	public static void goToActivity(Context context,Class<?> cls,int enterAnim,int exitAnim,Bundle bundle) {
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		activity.overridePendingTransition(enterAnim,exitAnim);
	}
	/**
	 * to new activity,use animation from right to left
	 * 使用从左到右的动画
	 * @param context
	 * @param cls
	 */
	public static void goToActivityFromLeft2Right(Context context,Class<?> cls) {
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,R.anim.out_to_right);
	}
	/** 使用从左到右的动画，并携带数据
	 * to new activity,use animation from right to left carry data
	 * @param context
	 * @param cls
	 */
	public static void goToActivityFromLeft2Right(Context context,Class<?> cls,Bundle bundle) {
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,R.anim.out_to_right);
	}
	/**
	 * to new activity,use animation from left to right
	 *  从右到左
	 * @param context
	 * @param cls
	 */
	public static void goToActivityFromRight2Left(Context context,Class<?> cls) {
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_left,R.anim.out_to_left);
	}
	/**
	 * to new activity,use animation from left to right carry data
	 * @param context
	 * @param cls
	 */
	public static void goToActivityFromRight2Left(Context context,Class<?> cls,Bundle bundle) {
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_left,R.anim.out_to_left);
	}
	/**
	 * to new activity,use animation from bottom to top carry data
	 * 从下到上
	 * @param context
	 * @param cls
	 * @param bundle
	 */
	public static void goToActivityFromBottom2Top(Context context,Class<?> cls,Bundle bundle) {
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_bottom,R.anim.out_to_top);
	}
	/**
	 * to new activity,use animation from bottom to top
	 * @param context
	 * @param cls
	 * @param bundle
	 */
	public static void goToActivityFromBottom2Top(Context context,Class<?> cls) {
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_bottom,R.anim.out_to_top);
	}
	/**
	 * to new activity,use animation form top to bottom
	 * 从上到下
	 * @param context
	 * @param cls
	 */
	public static void goToActivityFromTop2Bottom(Context context,Class<?> cls) {
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_top,R.anim.out_to_bottom);
	}
	
	
	/**
	 * 跳转到另一个Activity，携带数据
	 * @param context
	 * @param cls
	 */
	public static void goToActivity(Context context,Class<?> cls,Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	/**
	 * 跳到应用市场，搜索"听听中心.apk"
	 * @param context
	 */
	public static void searchTingting(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q=pub:听听中心"));
        context.startActivity(intent);
	}
	/**
	 * 根据应用名字去应用市场查询该应用
	 * @param context
	 * @param appName
	 */
	public static void searchTingting(Context context,String appName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://search?q=pub:"+ appName +""));
		context.startActivity(intent);
	}
	/**
	 * 根据应用的包名，去应用市场搜索该应用
	 * com.google.android.voicesearch google语音
	 * com.snda.tts.service 听听中心
	 * @param context
	 * @param appPckName
	 */
	public static void searchAppByPkgName(Context context,String appPckName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://details?id=" + appPckName));
		context.startActivity(intent);
	}
	/**
	 * .跳到拨电话的Activity
	 **/
	public static void call(Context context,String phoneNumber) {
		Intent myIntentDial = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));  
		context.startActivity(myIntentDial); 
	}
	
	/**
	 * . 一键分享图片
	 **/
	public static void shareImage(final Context context,File file) {
		if (file != null) {
			Intent intent=new Intent(Intent.ACTION_SEND);  
			intent.setType("image/*");
			Uri u = Uri.fromFile(file);
			intent.putExtra(Intent.EXTRA_STREAM, u);
			context.startActivity(Intent.createChooser(intent, "分享"));
		}
	}
	
	/**
	 * . 设置网络 
	 **/
	public static void openSettingNet(Context context) {
        Intent intent=null;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if(android.os.Build.VERSION.SDK_INT>10){
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            LogUtils.d("api level 10");
        }else{
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
            LogUtils.d("api level less 10");
        }
        context.startActivity(intent);
    }
	
	/**
	 * . 打开浏览器
	 **/
	public static void openBroswer(Context context,String url) {
		Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  
//        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");  
        context.startActivity(it); 
	}
}
