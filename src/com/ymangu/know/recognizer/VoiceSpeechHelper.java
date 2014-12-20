package com.ymangu.know.recognizer;

import com.ymangu.know.ui.UpdateView;
import com.ymangu.know.utils.RandomData;
import com.ymangu.know.voice.BaseVoiceSpeech;

import android.content.Context;

/**
 * voice speech helper class
 * @author xiejc
 *
 */
public class VoiceSpeechHelper {

	private Context context;
	private BaseVoiceSpeech voiceSpeech;
	
	
	public VoiceSpeechHelper(Context context,BaseVoiceSpeech voiceSpeech) {
		this.context = context;
		this.voiceSpeech = voiceSpeech;
	}
	/**
     * 讲笑话 唐诗等，来自arrays文件
     */
	public void randomSay(int resId,UpdateView updateView) {
		String content = RandomData.getRandomText(context, resId);
        voiceSpeech.chooseVoiceSpeech(content);
        updateView.updatSheView(content);
	}
	
	public void randomNameSay(int resId,String name,UpdateView updateView) {
    	String content = RandomData.getRandomText(context, resId,name);
    	voiceSpeech.chooseVoiceSpeech(content);
    	updateView.updatSheView(content);
    }
	
	public void sayAndUpdateView(String content,UpdateView updateView) {
        voiceSpeech.chooseVoiceSpeech(content);
        updateView.updatSheView(content);
    }
	public void say(String content) {
		voiceSpeech.chooseVoiceSpeech(content);
	}
	
	public void stopSay() {
    	if (voiceSpeech != null) {
    		voiceSpeech.stopSay();
    	}
    }
	
	public void unSndaRegister() {
		voiceSpeech.unregisterSndaTTSReceiver();
        voiceSpeech.unbindSndaService();
	}
	
	
	 
}
