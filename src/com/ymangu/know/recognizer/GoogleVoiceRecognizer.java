package com.ymangu.know.recognizer;


import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.R;
import com.ymangu.know.utils.ActivityUtil;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.utils.DialogUtil;
import com.ymangu.know.utils.MyToast;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;

/**
 * .  google 语音识别
 **/
public class GoogleVoiceRecognizer {
	
	private Activity activity;
	private static final String TAG = "GoogleVoiceRecognition";
	
	public GoogleVoiceRecognizer(Context context) {
		this.activity = (Activity) context;
	}
	protected void showgoogleVoiceRecognition() {
		try {
		    //通过Intent传递语音识别的模式，开启语音
		    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		    //语言模式和自由模式的语音识别
		    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		    //提示语音开始
		    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "开始语音");
		    //显示几个匹配结果
		    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
//                    intent.putExtra(RecognizerIntent.EXTRA_WEB_SEARCH_ONLY, true);
		    //开始语音识别
		    this.activity.startActivityForResult(intent, Constants.VOICE_RECOGNITION_REQUEST_CODE);
		} catch (Exception e) {
		    // TODO: handle exception
		    e.printStackTrace();
		    LogUtils.e( "showgoogleVoiceRecognition", e);
		    //Note:用户没有安装voice search.apk，提示用户下载安装后再设置
		    MyToast.showL(this.activity, "您本机尚未安装VoiceSearch，请下载后再使用");
		    ActivityUtil.searchAppByPkgName(this.activity, Constants.VIDEO_SEARCH_PACKAGE_NAME);
		}
	}
	
	public void shoeDownloadDialot() {
		DialogUtil.showDialog(this.activity, 
				this.activity.getString(R.string.download_google_voice_search), 
				this.activity.getString(R.string.download_google_voice_search_alert_msg), 
				this.activity.getString(R.string.dialog_confirm), 
				this.activity.getString(R.string.dialog_next_ask), 
				confirmListener, 
				null);
	}
	
private DialogInterface.OnClickListener confirmListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			 ActivityUtil.searchAppByPkgName(activity, Constants.VIDEO_SEARCH_PACKAGE_NAME);
		}
	};
	
	
}
