package com.ymangu.know.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lidroid.xutils.util.LogUtils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.View;


// 截屏
public class Screenshot {

	private static final String TAG = "Screenshot";
	/**
	 * Screenshot and save image to the sdcard 
	 * 
	 * @param context
	 * @return image file
	 * @throws FileNotFoundException
	 */
	public static File getScreenshot(Context context,View view) throws FileNotFoundException {
		File file = null;
		try {
			if (view == null) {
				return null;
			}
//		View view = ((Activity)context).getWindow().getDecorView();
//		View mainScreen = ((Activity)context).findViewById(layoutResId);
			int height = DisplayUtil.getMobileHeight(context);
			int width = DisplayUtil.getMobileWidth(context);
			Bitmap bitmap = Bitmap.createBitmap( width, height, Config.ARGB_8888 );
			Canvas canvas = new Canvas(bitmap);
			
			view.draw(canvas);
			long time = System.currentTimeMillis();
			
			file = new File(Constants.IMAGE_APP_PATH+ "/" + time + ".png");
			
			FileOutputStream f = null;
			f = new FileOutputStream(file);
			boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, f);
			if(b){
				LogUtils.d( "screentshot success");
			}
			if (file.exists()) {
				LogUtils.d( "image file exists");
			} else {
				LogUtils.d( "image file not exists");
			}
		} catch (Exception e) {
			LogUtils.e("getScreenshot", e);
		}
		return file;
	}
	
}
