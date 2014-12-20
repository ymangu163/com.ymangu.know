package com.ymangu.know.recognizer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;


import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.R;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.utils.SavePrivateData;

/**
 * .  讯飞的语音识别
 **/
public class IFlytekVoiceRecognizer extends BaseVoiceRecognizer implements RecognizerDialogListener{
	private static final String TAG =  "IFlytekVoiceRecognizer";
	private Activity activity;
	private RecognizerDialog iatDialog;
	
	public IFlytekVoiceRecognizer(Activity activity) {
		this.activity = activity;
		iatDialog = new RecognizerDialog(activity, "appid=" + getIflytekAppID());
        iatDialog.setListener(this);
	}
	
	/**
     * 显示转写对话框.
     *
     * @param
     */
	 public void showIatDialog() {
	        // 获取引擎参数
	        String engine = SavePrivateData.loadStringKey(
	        		this.activity.getString(R.string.preference_key_iat_engine),
	        		this.activity.getString(R.string.preference_default_iat_engine));

	        // 获取area参数，POI搜索时需要传入.
	        String area = null;
	        if ("poi".equals(engine)) {
	            final String defaultProvince = this.activity.getString(R.string.preference_default_poi_province);
	            String province = SavePrivateData.loadStringKey(
	            		this.activity.getString(R.string.preference_key_poi_province),
	                    defaultProvince);
	            final String defaultCity = this.activity.getString(R.string.preference_default_poi_city);
	            String city = SavePrivateData.loadStringKey(
	            		this.activity.getString(R.string.preference_key_poi_city), defaultCity);

	            if (!defaultProvince.equals(province)) {
	                area = "search_area=" + province;
	                if (!defaultCity.equals(city)) {
	                    area += city;
	                }
	            }
	        }

	        if (TextUtils.isEmpty(area))
	            area = "";
	        else
	            area += ",";
	        // 设置转写Dialog的引擎和poi参数.
	        iatDialog.setEngine(engine, area, null);

	        // 设置采样率参数，由于绝大部分手机只支持8K和16K，所以设置11K和22K采样率将无法启动录音.
	        String rate = SavePrivateData.loadStringKey(
	        		this.activity.getString(R.string.preference_key_iat_rate),
	        		this.activity.getString(R.string.preference_default_iat_rate));
	        if (rate.equals("rate8k"))
	            iatDialog.setSampleRate(RATE.rate8k);
	        else if (rate.equals("rate11k"))
	            iatDialog.setSampleRate(RATE.rate11k);
	        else if (rate.equals("rate16k"))
	            iatDialog.setSampleRate(RATE.rate16k);
	        else if (rate.equals("rate22k"))
	            iatDialog.setSampleRate(RATE.rate22k);
	        // mResultText.setText(null);
	        // 弹出转写Dialog.
	        iatDialog.show();
	    }
	
	/**
     * RecognizerDialogListener的"返回识别结果"回调接口. 通常服务端会多次返回结果调用此接口，结果需要进行累加.
     *
     * @param results
     * @param isLast
     */
	 StringBuilder builder = new StringBuilder();
    @Override
    public void onResults(ArrayList<RecognizerResult> results, boolean isLast) {
        if (isLast) {
        	for (RecognizerResult recognizerResult : results) {
                builder.append(recognizerResult.text);
                LogUtils.d("识别的话为islast：" + builder.toString());
            }
        } else {
        	for (RecognizerResult recognizerResult : results) {
//        		builder.append(recognizerResult.text);
        		LogUtils.d("识别的话为is normallast：" + builder.append(recognizerResult.text).toString());
        	}
        }
    }
    
    @Override
	public void onEnd(SpeechError arg0) {
    	String content = builder.toString();
        Intent intent = new Intent();
        intent.setAction(Constants.VOICE_RECOGNITION_RESULT_ACTION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(Constants.VOICE_RECOGNITION_RESULT, content);
        this.activity.sendBroadcast(intent);     //识别完之后发送广播，把结果广播出去
        builder.setLength(0);
	}
    
}
