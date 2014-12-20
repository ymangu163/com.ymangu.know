package com.ymangu.know.voice;

import java.io.IOException;
import java.util.Properties;

import android.content.Context;


import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SynthesizerPlayer;
import com.iflytek.speech.SynthesizerPlayerListener;
import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.R;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.utils.PropertiesUtil;
import com.ymangu.know.utils.SavePrivateData;

public class IFlytekSpeech  implements SynthesizerPlayerListener{

	private static final String TAG = "IFlytekSpeech";
	private SynthesizerPlayer mSynthesizerPlayer;
	private Context context;
	private String appid;
	
	public IFlytekSpeech(Context context) {
		this.context = context;
		try {
			Properties p = PropertiesUtil.getServerInstance();
			appid = p.getProperty(Constants.IFLYTED_APPID);
		} catch (IOException e) {
		}
	}
	
	/**
	 * stop iflytek voice speech
	 */
	protected void canclePlay() {
		if (null != mSynthesizerPlayer) {
			mSynthesizerPlayer.cancel();
		}
	}
	/**
	 * start voice synthetize
	 * @param content
	 * 		say content
	 */
	protected void synthetizeInSilence(String content) {
        if (null == mSynthesizerPlayer) {
            // 创建合成对象.
            mSynthesizerPlayer = SynthesizerPlayer.createSynthesizerPlayer(
            		context, "appid=" + appid);
        }

        // 设置合成发音人.
		String voiceSpeech = SavePrivateData.loadStringKey(context.getString(R.string.choose_voice_role), "vinn");
        LogUtils.i("current voice speech role is : " + voiceSpeech);
        
        mSynthesizerPlayer.setVoiceName(voiceSpeech);

        // 设置发音人语速
        int speed = SavePrivateData.loadIntKey(
        		context.getString(R.string.preference_key_tts_speed), 50);
        mSynthesizerPlayer.setSpeed(speed);

        // 设置音量.
        int volume = SavePrivateData.loadIntKey(
        		context.getString(R.string.preference_key_tts_volume), 50);
        mSynthesizerPlayer.setVolume(volume);

        // 设置背景音.
        String music = SavePrivateData.loadStringKey(
        		context.getString(R.string.preference_key_tts_music),
        		context.getString(R.string.preference_default_tts_music));
        mSynthesizerPlayer.setBackgroundSound(music);
        
        // 进行语音合成.
        mSynthesizerPlayer.playText(content, null, this);
    }
	
	@Override
	public void onBufferPercent(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnd(SpeechError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayBegin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayPaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayPercent(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayResumed() {
		// TODO Auto-generated method stub
		
	}

	
	
}
