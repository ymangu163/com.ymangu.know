package com.ymangu.know.service;

import java.util.List;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.utils.Constants;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class InstallAppService {

	private static final String TAG = "InstallAppService";
	
	/**
	 *  check phone whether installed 盛大听听
	 * @param context
	 * @return return true if already installed,otherwise return false
	 * @throws NameNotFoundException
	 */
	public static boolean checkInstalledTT(Context context) {
		boolean isInstall = false;
		ApplicationInfo appInfo;
		try {
			appInfo = context.getPackageManager().getApplicationInfo(Constants.SNDA_TINGTING_PACKAGE_NAME, 0);
			if (appInfo == null) {
				isInstall = false;
			} else {
				isInstall = true;
			}
		} catch (NameNotFoundException e) {
			isInstall = false;
			LogUtils.d( "没有安装盛大听听");
		}
		return isInstall;
	}
	
	public static List<String> getAllAppPackageName(Context context) {
		List<ApplicationInfo> apps = context.getPackageManager().getInstalledApplications(0);
		for (int i = 0; i < apps.size(); i ++) {
			ApplicationInfo appInfo = apps.get(i);
			if (appInfo.flags == 0) {
				System.out.println(appInfo.packageName);
				LogUtils.d("包名：" + appInfo.packageName);
				LogUtils.d( "类名：" + appInfo.className);
				LogUtils.d( "名称：" + appInfo.name);
			}
		}
		return null;
	}
	
}
