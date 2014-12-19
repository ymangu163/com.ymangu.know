package com.ymangu.know.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.MainActivity;
import com.ymangu.know.ui.UpdateView;

public class MyAsnycTask {
	private Context context;
	private UpdateView updateView;
	
	/**
	 * .  将 AsyncTask 写到单独类中，增强可读性
	 **/
	public MyAsnycTask(Context context,UpdateView updateView){
		this.context=context;
		this.updateView=updateView;	
	}
	
	//在服务器端搜索请求答案
	public void queryAnswer(String text){
		new QueryAnswerAsyncTask().execute(text);
	}
	
	/**
	 * .① params  入口参数
	 *  ② Progress:更新进度条时进度单位类型，一般是int --- Integer
	 *  ③ Result :最终拿到的结果的类型，比如说Person
	 *  
	 *  注意：Result参数类型和 doInBackground的返回类型及 onPostExecute的参数类型必须保持一致。
	 **/
	class  QueryAnswerAsyncTask extends   AsyncTask<String, Integer, Object> {

		private ProgressDialog progressDialog;

		@Override
		protected Object doInBackground(String... params) {
			String str = params[0];
			for (int i = 0; i < 10;  i ++) {
				SystemClock.sleep(1000);
				publishProgress((i + 1) * 10);
				LogUtils.d( "sleep 了 " + i + " 秒");
			}
			return str + "_玩命加载成功";
			
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMax(100);
			progressDialog.setTitle("提示");
			progressDialog.setMessage("当前正在玩命加载中,请稍后 ... ");
			progressDialog.setIcon(android.R.drawable.ic_menu_info_details);
			progressDialog.setCancelable(false);
			progressDialog.show();
			
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			Toast.makeText(context, (String) result , 1).show();
			progressDialog.cancel();
			progressDialog.dismiss();
			
			
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			int position = values[0];
			progressDialog.setProgress(position);
			
			
		}
		
	}
	
	
	
	
	
	

}
