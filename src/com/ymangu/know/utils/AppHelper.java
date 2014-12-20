package com.ymangu.know.utils;


/**
 * .  去下载新版本的apk，并安装
 **/

import com.ymangu.know.R;
import com.ymangu.know.bean.UpdateInfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;

public class AppHelper {
	
	private static ProgressDialog dialog;

	private static final String TAG = "AppHelper";
	/**
	 * 返回版本 的String
	 * @param context
	 * @return
	 */
	public static String getAppVersion(Context context) {
		 try {
			// 获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			String version = packInfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		 return null;
	}
	/**得到版本 NO
	 * get android os version no
	 * @return
	 */
	public static float getAndroidVersion() {
		return Float.valueOf(android.os.Build.VERSION.RELEASE);
	}
	/**
	 * get device model
	 * @return
	 */
	public String getDeviceModel () {
		return android.os.Build.MODEL;
	}
	/** 得到SDK 版本
	 * get android os sdk version  2.2 = 8,2.3 = 9,4.2.1 = 17
	 * @return sdk version
	 */
	public static int getSDKVersion(){
		return android.os.Build.VERSION.SDK_INT;
	}
	
	/**
	 * .  安装 apk
	 **/
	public void installApk(Context context,Uri uri) {
        if (uri.toString().endsWith(".apk")) {
             Intent intent = new Intent();
             intent.setAction(Intent.ACTION_VIEW);
//                 intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
             intent.setDataAndType(uri,"application/vnd.android.package-archive");
             context.startActivity(intent);
        }
	}
	
	// 是否已更新
	public static void hasUpdate(final Context context,boolean showPreDialog) {
//		UmengUpdateAgent.setUpdateOnlyWifi(false);
//		UmengUpdateAgent.setUpdateAutoPopup(false);
		new AsyncUpdateCheck(context,showPreDialog).execute();
	}
	private static class AsyncUpdateCheck extends AsyncTask<Void, Void, UpdateInfo> {
		private Context context;
		private Boolean showPreDialog;
		private AsyncUpdateCheck(Context context,Boolean showPreDialog) {
			this.context = context;
			this.showPreDialog = showPreDialog;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (showPreDialog) {
				dialog = new ProgressDialog(context);
				dialog.setMessage(context.getString(R.string.check_update));
				dialog.setCancelable(false);
				dialog.show();
			}
		}
		
		@Override
		protected UpdateInfo doInBackground(Void... params) {
			
//			UmengUpdateAgent.update(context);
			final UpdateInfo info = new UpdateInfo();
//	        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//
//				@Override
//				public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
//					info.setUpdateStatus(updateStatus);
//					info.setResponse(updateInfo);
//				}
//	        });
	        SystemClock.sleep(2000);
			return info;
		}
		
		@Override
		protected void onPostExecute(UpdateInfo result) {
			super.onPostExecute(result);
			if (dialog != null) {
				dialog.dismiss();
			}
			if (result != null && !"".equals(result)) {
				int status = result.getUpdateStatus();
//				UpdateResponse response = result.getResponse();
//				String target_size = response.target_size;
//				String size = response.size;
//				MyLog.d(TAG, "size:" + size + "target_size:" + target_size);
				switch (status) {
				case 0:// has update
//					UmengUpdateAgent.showUpdateDialog(context, result.getResponse());
					break;
				case 1:// has no update
					if (showPreDialog) {
						MyToast.showL(context, "没有更新");
					}
					break;
				case 2:// none wifi
					MyToast.showL(context, "没有wifi连接， 只在wifi下更新");
					break;
				case 3:// time out
					MyToast.showL(context, "连接超时");
					break;

				default:
					break;
				}
			}
//			UmengUpdateAgent.setOnDownloadListener(new UmengDownloadListener(){
//	            @Override
//	            public void OnDownloadEnd(int result) {
//	            	MyToast.showS(context, "download result : " + result);
//	            }           
//	        });
		}
	}
}
