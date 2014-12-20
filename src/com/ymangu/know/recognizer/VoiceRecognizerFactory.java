package com.ymangu.know.recognizer;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.utils.SavePrivateData;

import android.app.Activity;
import android.content.Context;

public class VoiceRecognizerFactory {

	private static final String TAG =  "VoiceRecognitionFactory";
	private Activity activity;
	private SndaVoiceRecognizer sndaVoiceRecognition;
	private IFlytekVoiceRecognizer iFlytekVoiceRecognition;
	private GoogleVoiceRecognizer googleVoiceRecognition;
	private YZSVoiceRecognizer yzsVoiceRecognizer;
	
	/**
	 * 改为单例
	 * @param context
	 */
	public VoiceRecognizerFactory(Context context) {
		this.activity = (Activity) context;
		this.iFlytekVoiceRecognition = new IFlytekVoiceRecognizer(this.activity);
        this.sndaVoiceRecognition = new SndaVoiceRecognizer(this.activity);
        this.googleVoiceRecognition = new GoogleVoiceRecognizer(this.activity);
        this.yzsVoiceRecognizer = new YZSVoiceRecognizer(this.activity);
	}
	
	/**
	 * . 使用哪个语音识别
	 **/
	public void startRecognition() {
		int voice = SavePrivateData.loadIntKey(SavePrivateData.CHOOSE_VOICE_RECOGNITION, 0);
		 switch (voice) {
         case Constants.IFLYTEK_VOICE_RECOGNITION:
        	 if (iFlytekVoiceRecognition == null) {
        		 iFlytekVoiceRecognition = new IFlytekVoiceRecognizer(this.activity);
        	 }
             iFlytekVoiceRecognition.showIatDialog();
             break;
         case Constants.SNDA_VOICE_RECOGNITION:
        	 if (sndaVoiceRecognition == null) {
        		 sndaVoiceRecognition = new SndaVoiceRecognizer(this.activity);
        	 }
//        	 sndaVoiceRecognition.showSndaVoiceRecognition();
             break;
         case Constants.GOOGLE_VOICE_RECOGNITION:
        	 if (googleVoiceRecognition == null) {
        		 googleVoiceRecognition = new GoogleVoiceRecognizer(this.activity);
        	 }
        	 googleVoiceRecognition.showgoogleVoiceRecognition();
             break;
         case Constants.YZS_VOICE_RECOGNITION:
        	 if (yzsVoiceRecognizer == null) {
        		 yzsVoiceRecognizer = new YZSVoiceRecognizer(this.activity);
        	 }
//        	 yzsVoiceRecognizer.showYZSRecognizer();
        	 break;
         default:
             break;
     }
     LogUtils.d( "语音引擎为:" + voice);
	}
	
	public void setOnResume() {
		if (sndaVoiceRecognition != null) {
//			sndaVoiceRecognition.setOnResume();
		}
	}
	
}
