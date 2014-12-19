package com.ymangu.know.utils;

import android.content.Context;
import android.content.Intent;

/**
 * . 用于发送广播的工具类
 **/
public class BroadcastHelper {
	/**
	 * 发送语音识别结果的广播
	 * @param context
	 * @param String -- 发送带一个String类型的参数的广播
	 */
	public static void sendVoiceBroadCast(Context context,String content) {
		Intent intent = new Intent();
		 intent.setAction(Constants.VOICE_RECOGNITION_RESULT_ACTION);
		 intent.addCategory(Intent.CATEGORY_DEFAULT);
		 intent.putExtra(Constants.VOICE_RECOGNITION_RESULT_KEY, content); //把String 传递过去 
		   context.sendBroadcast(intent);
	}

	/**
	 * 发送String 类型的值的广播
	 * @param context
	 * @param action
	 * @param key
	 * @param value
	 */
	public static void sendBroadCast(Context context,String action,String key,String value) {
		Intent intent = new Intent();
		 intent.setAction(action);
		 intent.addCategory(Intent.CATEGORY_DEFAULT);
		 intent.putExtra(key, value);	//把key,value传递过去
		 context.sendBroadcast(intent);
	}
	
	//发送 int参数的广播
	public static void sendBroadCast(Context context,String action,String key,int value) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra(key, value);
		context.sendBroadcast(intent);
	}
	
	
	
}
