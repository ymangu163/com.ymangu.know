package com.ymangu.know.utils;

import android.content.Context;

import java.util.Random;

import com.ymangu.know.R;

public class RandomData {

	/**
	 * 从arrays中随机获取一个文本回答
	 * @param context
	 * @param resId
	 * @return
	 */
	public static String getRandomText(Context context,int resId) {
		String[] arrays = context.getResources().getStringArray(resId);

		Random r = new Random();
		int i = r.nextInt(arrays.length);
		String content = arrays[i];
		return content;
	}
	public static String getRandomText(Context context,int resId,String name) {
		String[] arrays = context.getResources().getStringArray(resId);
		
		Random r = new Random();
		int i = r.nextInt(arrays.length);
		String content = arrays[i];
		content = String.format(content, name);
		return content;
	}
	/**
	 * 随机返回一个图片id
	 * @return
	 */
	public static int getRandomImageId() {
		int imgSize = 10;//当前一共有5张图片供随机选择
		int resId = R.drawable.tag_bg;
		Random ra =new Random();
		int id = R.drawable.mm8;
		resId = resId + ra.nextInt(imgSize);
		return resId;
	}
	
}
