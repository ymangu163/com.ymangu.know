package com.ymangu.know.recognizer;

import java.util.ArrayList;

import com.ymangu.know.utils.PreferencesKey;
import com.ymangu.know.utils.SavePrivateData;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


/**
 * .  这里是云之声的语音识别
 **/
public class SndaVoiceRecognizer extends BaseVoiceRecognizer  {

	private static final String TAG = "SndaVoiceRecognition";
//	private SndaAsrDialog dialog;     // 云之声的库中的对话框
	private String ip;
	private short port;

	private Activity activity;
	
	private String sndaAppID;
	private String sndaUser;
	private String AppIdUser;

	public SndaVoiceRecognizer(Activity activity) {
		this.activity = activity;
		this.sndaAppID = getSndaAppID();
		this.sndaUser = getSndaUser();
		this.AppIdUser = "appid="+this.sndaAppID+",user="+this.sndaUser+"";
	}

	
//	protected void showSndaVoiceRecognition() {
//		long start_time = System.currentTimeMillis();
//		dialog = new SndaAsrDialog(this.activity, "text",this.AppIdUser);// 填写申请得到的appid和user后进行编译测试
//		dialog.setListener(this);
//		dialog.isDebug(true);
//		dialog.setAsrParams(getParams());
//		if (!TextUtils.isEmpty(ip))
//			dialog.setAsrClientInfo(ip, port);// 设置服务器信息
//		// dialog.setStyle(Integer.valueOf(mSharedPreferences.getString(
//		// PreferencesKey.KEY_CONTROL_STYLE, "2")));
//		// dialog.setStyle(1);
//		long end_time = System.currentTimeMillis();
//
//		long interval = end_time - start_time;
//
//		Log.w("DEBUG", "time to set dialog =" + interval);
//		dialog.show();
//	}

//	@Override
//	public void onAsrTimeOut(int arg0, String msg) {
//		Log.d(TAG, "超时信息为： " + msg);
//	}
//
//	@Override
//	public void onEnd(SndaError error) {
//		if (error != null) {
//			Log.d(TAG, "结束信息为： " + error.getErrMsg());
//			Log.d(TAG, "结束代码为： " + error.getErrCode());
//		}
//		
//	}

//	@Override
//	public void onResults(ArrayList<SndaResult> results, boolean isLast) {
//		StringBuilder builder = new StringBuilder();
//		for (SndaResult recognizerResult : results) {
//			builder.append(recognizerResult.getText());
//		}
//		Intent intent = new Intent();
//        intent.setAction(Constants.VOICE_RECOGNITION_RESULT_ACTION);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.putExtra(Constants.VOICE_RECOGNITION_RESULT, builder.toString());
//        this.activity.sendBroadcast(intent);
//	}

	/**
	 * When Activity onResume Called
	 */
//	protected void setOnResume() {
//		getClientInfo();
//		int style = Integer.valueOf(SavePrivateData.loadStringKey(
//				PreferencesKey.KEY_CONTROL_STYLE, "2"));
//		SndaLoadResources.preloadImageResources(style, this.activity);
//		SndaLoadResources.preloadMediaResources(this.activity);
//		SndaLoadResources.preloadLibraryResources(this.activity);
//	}

	public String getParams() {
		String str = "";
		if (!SavePrivateData.loadBooleanKey(PreferencesKey.KEY_SAVE_SPEECH,
				false))
			str = str + "," + "Speech_saving=no";
		else
			str = str + "," + "Speech_saving=yes";
		str = str
				+ ",Speech_format="
				+ SavePrivateData.loadStringKey(
						PreferencesKey.KEY_SPEECH_FORMAT, "pcm");
		str = str
				+ ",Speech_filepath="
				+ SavePrivateData.loadStringKey(PreferencesKey.KEY_SPEECH_PATH,
						"/sdcard/snda_speech");
		str = str
				+ ",Sampling_rate="
				+ SavePrivateData.loadStringKey(PreferencesKey.KEY_SPEECH_RATE,
						"16000");
		if (!SavePrivateData.loadBooleanKey(PreferencesKey.KEY_VAD_SETTING,
				false))
			str = str + ",Speech_detection=" + "0";
		else
			str = str + ",Speech_detection=" + "1";
		str = str
				+ ",Speech_detection_timeout="
				+ SavePrivateData.loadStringKey(PreferencesKey.KEY_VAD_TIMEOUT,
						"2000");
		str = str
				+ ",Max_speech_length="
				+ SavePrivateData.loadStringKey(
						PreferencesKey.KEY_SPEECH_TIMEOUT, "120");
		str = str
				+ ",ResultFormat="
				+ SavePrivateData.loadStringKey(
						PreferencesKey.KEY_RESULT_FORMART, "0");
		return str;
	}

	private void getClientInfo() {
		String info = SavePrivateData.loadStringKey(
				PreferencesKey.KEY_CLIENT_INFO, "");
		if (!TextUtils.isEmpty(info)) {
			String[] data = info.split(":");
			for (int i = 0; i < data.length; i++) {
				System.out.println("data[i] =  " + data[i]);
			}
			ip = data[0];
			int num = Integer.valueOf(data[1]);
			port = (short) num;
		}
	}

}
