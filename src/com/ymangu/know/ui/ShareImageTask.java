package com.ymangu.know.ui;

import java.io.File;
import java.io.FileNotFoundException;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.R;
import com.ymangu.know.recognizer.VoiceSpeechHelper;
import com.ymangu.know.utils.ActivityUtil;
import com.ymangu.know.utils.SavePrivateData;
import com.ymangu.know.utils.Screenshot;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

public class ShareImageTask {

	ProgressDialog dialog;
	private Context context;
//	private File file;
	private static final String TAG = "ShareImageTask";
	private VoiceSpeechHelper voiceHelper;
	private View view;
	
	public ShareImageTask(Context context,VoiceSpeechHelper voiceHelper,View view) {
		this.context = context;
		this.voiceHelper = voiceHelper;
		this.view = view;
	}
	
	public void execute() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			voiceHelper.say(context.getString(R.string.sdcard_error));
			return;
		}
		new ShareImageAsyncTask().execute();
	}
	
	class ShareImageAsyncTask extends AsyncTask<Void, Void, File> {
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(context);
			dialog.setMessage(context.getString(R.string.screenshoting_share));
			dialog.setCancelable(false);
			dialog.show();
			
		}

		@Override
		protected File doInBackground(Void... params) {
			
			File file = null;
			try {
				file = Screenshot.getScreenshot(context,view);
			} catch (FileNotFoundException e) {
				LogUtils.e( "ShareImageAsyncTask", e);
			}
			return file;
		}
		@Override
		protected void onPostExecute(File result) {
			super.onPostExecute(result);
			dialog.dismiss();
			String name = SavePrivateData.getMyName(context);
			if (voiceHelper != null) {
				voiceHelper.say(name + context.getString(R.string.plz_choose_sahre_type));
			}
			ActivityUtil.shareImage(context, result);
		}

	}

}
