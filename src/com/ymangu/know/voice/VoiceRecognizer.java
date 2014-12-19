package com.ymangu.know.voice;

import java.util.ArrayList;

import android.content.Context;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.utils.Constants;

/**
 * . 语音识别，用的带UI的库。不带UI，意思是可以自定义UI.
 *   讯飞语音，①环境：把Msc.jar 和 armeabi 拷到工程libs目录.
 **/
public class VoiceRecognizer implements RecognizerDialogListener {
	private RecognizerDialog dialog;   //识别的对话框
	private StringBuilder sb = new StringBuilder();  //线程不安全，StringBuffer 线程安全
	
	public VoiceRecognizer(Context context) {
		// 根据讯飞 doc 的第4节 创建RecognizerDialog进行设置
		dialog = new RecognizerDialog(context, "appid="+ Constants.XUNFEI_APPID +"");
		dialog.setListener(this); //设置监听
	}
	
	//开始识别
	public void start() {
		//设置识别的类型，sms=文本转写，语音转文字
		String engine = "sms";
		//asr_ptt 是否带标点符号
		String params = "asr_ptt = 0";
		//参数3：是否进行语法识别
		dialog.setEngine(engine, params, null);     //4.1.2 节，设置识别参数
		
		//设置语音识别时的采样率
		dialog.setSampleRate(RATE.rate16k);
		//将识别对话框显示出来
		dialog.show();
		
	}
	
	@Override
	public void onEnd(SpeechError error) {
		//处理识别结果
		if (error == null) {//没有发生错误，直接拿到识别的结果了。
			LogUtils.d( "识别结果：" + sb.toString());
			//发送广播，在MainActivity去接收这个语音识别的结果
			
		}else{ //发生错误的时候
			int errorCode = error.getErrorCode();
			String desc = error.getErrorDesc();
			LogUtils.d(  "识别出错，错误码为：" + errorCode + " 错误原因为：" + desc);
		}
		/**
		 * . 将 StringBuilder 清空的两种方法
		 **/
//		sb.delete(0, sb.length());
		sb.setLength(0);
		
		
	}
	/**
	 * 每次识别结果得到的时候回调该方法，该方法会被多次调用
	 */
	@Override
	public void onResults(ArrayList<RecognizerResult> results, boolean isLast) {
		for(RecognizerResult result:results){
			String text=result.text;
			sb.append(text);			
		}
	}
	
}
