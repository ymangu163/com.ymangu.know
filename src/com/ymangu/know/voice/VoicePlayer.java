package com.ymangu.know.voice;
/**
 * . 语音播报的接口
 **/
public interface VoicePlayer {

	void play(String text);  //不带UI的请问播报
	
	void playUI(String text); //带UI的请问播报
	
	void pause();
	
	void resume();
	
	void cancel();
	
	void destory();
}
