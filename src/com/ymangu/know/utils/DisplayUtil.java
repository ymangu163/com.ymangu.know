package com.ymangu.know.utils;

import java.lang.reflect.Field;

import com.lidroid.xutils.util.LogUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class DisplayUtil {
	
	public static final String TAG = "DisplayUtil";

	/**
	 * return system bar height
	 * @param context
	 * @return
	 */
	public static int getStatuBarHeight(Context context) {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");  
			Object obj;
			obj = c.newInstance();
			Field field = c.getField("status_bar_height");  
	        int width = Integer.parseInt(field.get(obj).toString());  
	        int height = context.getResources().getDimensionPixelSize(width);
	        return height;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e("getStatuBarHeight", e);
		}
		return 0;
	}
	
	public static int getStatuBarHeight2(Context context) {
		Rect frame = new Rect();  
		((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
        int statusBarHeight = frame.top;  
        return statusBarHeight;
	}
	/**
	 * get screen height of this cellphone
	 * @param context
	 * @return
	 */
	public static int getMobileHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
	    int height = dm.heightPixels;  //得到高度
	    return height;
	}
	/**
	 * get screen width of this cellphone
	 * @param context
	 * @return
	 */
	public static int getMobileWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;    //得到宽度
	    return width;
		
	}
	
}
