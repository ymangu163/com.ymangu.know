package com.ymangu.know.voice;

import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SynthesizerPlayer;
import com.iflytek.speech.SynthesizerPlayerListener;
import com.iflytek.ui.SynthesizerDialog;
import com.ymangu.know.utils.Constants;

import android.content.Context;
import android.widget.Toast;

/**
 * . 请问播报的实现类
 **/
public class VoicePlayerImpl implements VoicePlayer {
	
	private Context context;
	private SynthesizerDialog dialog;
	private SynthesizerPlayer player;
	
	public VoicePlayerImpl(Context context) {
		this.context = context;
		String appid = "appid="+ Constants.XUNFEI_APPID +"";
		//获得语音合成实例,然后设置参数
		dialog = new SynthesizerDialog(context, appid); 
		//无UI语音合成
		player = SynthesizerPlayer.createSynthesizerPlayer(context, appid);
		
	}

	@Override
	public void play(String text) {
		//设置播放的速度
		player.setSpeed(50);
		//设置播放的音量
		player.setVolume(50);
		//设置背景音
		player.setBackgroundSound(null);
		//设置播报的角色
		player.setVoiceName("vixm");
		// arg0: 要播报的text  ,arg1:参数；  arg2:listener  合成回调接口
		player.playText(text, null, synthesizerPlayerListener);

	}

	private SynthesizerPlayerListener synthesizerPlayerListener = new SynthesizerPlayerListener() {
		/**
		 * 当前缓冲的百分比
		 */
		@Override
		public void onBufferPercent(int arg0, int arg1, int arg2) {
			
		}
		/**
		 * 播报结束
		 */
		@Override
		public void onEnd(SpeechError arg0) {
			
		}
		/**
		 * 开始播报
		 */
		@Override
		public void onPlayBegin() {
			Toast.makeText(context, "开始播报了 ... ", 0).show();
		}
		/**
		 * 播报暂停
		 */
		@Override
		public void onPlayPaused() {
			
		}
		/**
		 * 当前播放的百分比
		 */
		@Override
		public void onPlayPercent(int arg0, int arg1, int arg2) {
			
		}
		/**
		 * 播报恢复
		 */
		@Override
		public void onPlayResumed() {
			
		}
		
	};
	
	
	
	@Override
	public void playUI(String text) {
		//设置播放的速度
	    dialog.setSpeed(50);
		//设置播放的音量
		dialog.setVolume(50);
		//设置背景音
		dialog.setBackgroundSound(null);
		//设置播报的角色
		dialog.setVoiceName("vixm");
		//arg0:要合成的文本字符串; arg1: 一些参数
		dialog.setText(text, null);
		dialog.show();//将当前对话框显示		
	}

	
	
	
	@Override
	public void pause() {
		dialog.pause();
		player.pause();
	}

	@Override
	public void resume() {
		dialog.resume();
		player.resume();
	}

	@Override
	public void cancel() {
		dialog.cancel();
		player.cancel();
	}

	@Override
	public void destory() {
		dialog.destory();
		player.destory();
	}
	
	
	
	

}
