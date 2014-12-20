package com.ymangu.know.fragment;

import com.ymangu.know.R;
import com.ymangu.know.recognizer.VoiceRecognizerFactory;
import com.ymangu.know.server.Config;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.utils.DialogUtil;
import com.ymangu.know.utils.MyToast;
import com.ymangu.know.utils.SavePrivateData;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class IAskFragment extends BaseFragment implements OnClickListener {

	private static AutoCompleteTextView iaskTextCompleted;
	private VoiceRecognizerFactory voiceRecognizerFactory;
	@Override
	protected View initView(LayoutInflater inflater) {
		rootView  = inflater.inflate(R.layout.iask_ask_fragment,null);

		return rootView;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		//自动完成TextView
		iaskTextCompleted = (AutoCompleteTextView) rootView.findViewById(R.id.iask_edit_text);
		 iaskTextCompleted.setOnEditorActionListener(iaskEditorActionListener);  //编辑事件
		
		 //声音的按钮
		Button voiceStart= (Button) rootView.findViewById(R.id.voice_btn);
		  voiceStart.setOnClickListener(this);
		  //生成语音识别对象
		  voiceRecognizerFactory = new VoiceRecognizerFactory(ctx);
	
		
	}
	
	//AutoCompleteTextView 的 OnEditorActionListener -- 事件捕捉处理
	 private OnEditorActionListener iaskEditorActionListener = new OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// 假如是Enter 键
			if (actionId == EditorInfo.IME_ACTION_SEND) {
				String question = iaskTextCompleted.getText().toString();  //把控件上的文字 获取到
				//导入登录用户及密码
				boolean success = SavePrivateData.loadBooleanKey(SavePrivateData.IS_LOGIN_SUCCESS, false);
				if (success) {  //如果登录成功
					String url = Config.IAsk.submitAsk(question);   //获取服务器的URL地址
//					new SubmitQuestion().execute(url);    //把问题提交到服务器
					
				}else{
					DialogUtil.showLoginDialog(ctx);
				}
			}
			
			
			return false;
		}
	 };

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.voice_btn:
			/**
			 * . 声音识别成功后，发送广播，在Activity中接收广播，再把结果传给 Fragment，更新UI
			 **/
			voiceRecognizerFactory.startRecognition();
			break;
		case R.id.iask_edit_text:
			//设置软键盘 的状态
			Constants.Data.SOFT_INPUT_STATU = true;
			break;
		case R.id.submit_ask_btnX:  //提交按钮
			String content = iaskTextCompleted.getText().toString();
			if (content != null && !"".equals(content)) {
				 //提交到服务器
				
			} else {
				MyToast.showL(ctx, ctx.getString(R.string.plz_input_content_submit));
			}
			
			break;
		default:
			break;
		
		}
		
		
		
		
	}

	public static void updateCompleted(String result) {
		if (result != null && !"".equals(result)) {
			iaskTextCompleted.setText(result);
    		
	}
		
	}
	
	
}
