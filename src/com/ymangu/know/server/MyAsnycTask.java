package com.ymangu.know.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.R;
import com.ymangu.know.bean.Answer;
import com.ymangu.know.ui.UpdateView;

public class MyAsnycTask {
	private Context context;
	private UpdateView updateView;
	
	/**
	 * .  将 AsyncTask 写到单独类中，增强可读性
	 *  注意：AsyncTask 内部有线程池，128 个线程同时工作，工作效率高
	 *   缺点：有可能对资源的消耗大
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
	
	class QueryAnswerAsyncTask extends   AsyncTask<String, Integer, Answer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected Answer doInBackground(String... params) {
			String str = params[0];
			Answer answer = getAnswer(str);
			
			return answer;
		}
		@Override
		protected void onPostExecute(Answer result) {
			super.onPostExecute(result);
			if(result!=null){
				int type = result.getAnswerType();
				
				if(type==Answer.JOKE_ANSWER){
					updateView.updatSheView(result.getJokeAnswer());
					
					
				}else if(type==Answer.MM_ANSWER){
					String[] mmArr = context.getResources().getStringArray(R.array.mm);
					Random r = new Random();
					int index = r.nextInt(mmArr.length);
					updateView.updateMMView(mmArr[index],result.getMmAnswer());
				}
				
			}
			
			
			
			
		}
		
		
	}
	
	public Answer getAnswer(String str) {
		if(str.contains("笑话")){
			//得到 array 中的数据
			String[] jokeArr = context.getResources().getStringArray(R.array.joke);
			Random r = new Random();   //随机数对象
			int index = r.nextInt(jokeArr.length);  //得到随机 int 值
			Answer answer = new Answer();
			
			String joke = jokeArr[index];
			answer.setAnswerType(Answer.JOKE_ANSWER);
			answer.setJokeAnswer(joke);
			return answer;		
			
		}else if(str.contains("美女")||str.contains("妹子")){
			int resId = randomMM();
			Answer answer = new Answer();
			answer.setAnswerType(Answer.MM_ANSWER);
			answer.setMmAnswer(resId);
			return answer;
		}
		
		
		return null;
	}
	
	private int randomMM() {
		List<Integer> mmList = new ArrayList<Integer>();
		mmList.add(R.drawable.mm0);
		mmList.add(R.drawable.mm1);
		mmList.add(R.drawable.mm2);
		mmList.add(R.drawable.mm3);
		mmList.add(R.drawable.mm4);
		mmList.add(R.drawable.mm5);
		mmList.add(R.drawable.mm6);
		mmList.add(R.drawable.mm7);
		mmList.add(R.drawable.mm8);
		mmList.add(R.drawable.mm9);
		mmList.add(R.drawable.mm10);
		mmList.add(R.drawable.mm11);
		mmList.add(R.drawable.mm12);
		mmList.add(R.drawable.mm13);
		mmList.add(R.drawable.mm14);
		mmList.add(R.drawable.mm15);
		mmList.add(R.drawable.mm16);
		mmList.add(R.drawable.mm17);
		mmList.add(R.drawable.mm18);
		mmList.add(R.drawable.mm19);
		mmList.add(R.drawable.mm20);
		mmList.add(R.drawable.mm21);
		mmList.add(R.drawable.mm22);
		mmList.add(R.drawable.mm23);
		
		Random r = new Random();
		int index = r.nextInt(mmList.size());
		int resId = mmList.get(index);
		return resId;
	}
	
	
	
	
	
	/**
	 * . 这是更新 ProgressDialog 的例子，留在这里参考
	 **/
	class  QueryAsyncTask extends   AsyncTask<String, Integer, Object> {

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
