package com.ymangu.know.recognizer;

import java.util.ArrayList;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.utils.Constants;

import android.app.Activity;
import android.content.Intent;

/**
 * .  盛大语音识别
 **/

public class YZSVoiceRecognizer extends BaseVoiceRecognizer{
//	public class YZSVoiceRecognizer extends BaseVoiceRecognizer implements RecognizerDialogListener{

	private static final String TAG =  "YZSVoiceRecognizer";
	private Activity activity;
//	private RecognizerDialog recognizerDialog;
	
	public YZSVoiceRecognizer(Activity activity) {
		
		this.activity = activity;
		
	}
	
//	public void showYZSRecognizer() {
//		recognizerDialog = new RecognizerDialog(activity, getYZSAppID());
//		recognizerDialog.setListener(this);
//		recognizerDialog.setCancelable(false);
//		recognizerDialog.show();
//	}
//	
	StringBuilder builder = new StringBuilder();
//	@Override
//	public void onResults(ArrayList<RecognizerResult> results, boolean isLast) {
//		
//    	for (RecognizerResult recognizerResult : results) {
//            builder.append(recognizerResult.text);
//            MyLog.d(TAG, "recognizer text is : " + builder.toString());
//        }
//		
//	}
//	@Override
//	public void onEnd(SpeechError error) {
//		String content = builder.toString();
//		if (error  == null) {//请求成功，返回null
//			if(!"".equals(content)){ 
//				sendBoradcast(content);
//			} else {
//				MyToast.showL(this.activity, "没有听到声音");
//			}
//		} else {//请求失败
//			MyToast.showL(this.activity, error.errorMsg);
//			return;
//		}
//	}

	private void sendBoradcast(String content) {
//		String content = builder.toString();
        Intent intent = new Intent();
        intent.setAction(Constants.VOICE_RECOGNITION_RESULT_ACTION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(Constants.VOICE_RECOGNITION_RESULT, content);
        this.activity.sendBroadcast(intent);
        //Clear StringBuilder
        builder.delete(0, builder.length());
        LogUtils.d( "识别的话为islast：" + builder.toString());
	}

}
