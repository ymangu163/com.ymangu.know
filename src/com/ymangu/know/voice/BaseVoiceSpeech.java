package com.ymangu.know.voice;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.utils.SavePrivateData;

import android.content.Context;


public class BaseVoiceSpeech{

	private static final String TAG = "BaseVoiceSpeech";
	private Context context;
	private static final int IFLYTEK_VOICE_SPEECH = 0;
	private static final int SNDA_VOICE_SPEECH = 1;
	
	private IFlytekSpeech flytekSpeech = null;
	
//	private SndaVoiceSpeech sndaVoiceSpeech;
	
	public BaseVoiceSpeech(Context context) {
		this.context = context;
		if(flytekSpeech == null) {
			flytekSpeech = new IFlytekSpeech(this.context);
		}
	}
	/**
	 * choose voice speech engine by sharepreference 
	 * @param content
	 */
	public void chooseVoiceSpeech(String content) {
		int voiceSpeech = SavePrivateData.loadIntKey(SavePrivateData.CHOOSE_VOICE_SPEECH, 0);
		boolean onOff = SavePrivateData.loadBooleanKey(SavePrivateData.VOICE_ON_OFF, true);
		//if close voice,return
		if (!onOff) {
			LogUtils.d("voice close");
			return;
		} else {
			LogUtils.d("voice normal");
		}
		switch (voiceSpeech) {
		case IFLYTEK_VOICE_SPEECH:
			flytekSpeech.synthetizeInSilence(content);
			LogUtils.d( "voice sppech is " + "iflytek");
			break;
		case SNDA_VOICE_SPEECH:
//			if (sndaVoiceSpeech == null) {
//				sndaVoiceSpeech = new SndaVoiceSpeech(this.context);
//			}
//			sndaVoiceSpeech.say(content);
			LogUtils.d("voice sppech is " + "sndo");
			break;
		default://default use iflytek voice engine,if not find correct voice speech
        	flytekSpeech.synthetizeInSilence(content);
			break;
		}
	}
	
	/**
	 * stop  all say
	 */
	public void stopSay() {
		if (flytekSpeech != null){
			flytekSpeech.canclePlay();
		}
//		if (sndaVoiceSpeech != null) {
//			sndaVoiceSpeech.stopSay();
//		}
	}
	
	public void unregisterSndaTTSReceiver() {
//		if (sndaVoiceSpeech != null) 
//			sndaVoiceSpeech.unregisterReceiver();
	}
	
	public void unbindSndaService() {
//		if (sndaVoiceSpeech != null) 
//			sndaVoiceSpeech.unbindSndaService();
	}
	
}
