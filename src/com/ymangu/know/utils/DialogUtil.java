package com.ymangu.know.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ymangu.know.MainActivity;
import com.ymangu.know.R;
import com.ymangu.know.bean.Result;
import com.ymangu.know.server.Config;
import com.ymangu.know.server.MyHttpClient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;


public class DialogUtil {
	
	
	private static LoginSuccessListener loginSuccessCallback;
	public static final String TAG = "DialogUtil";
	/**
	 * 显示一个对话框 
	 * @param context 
	 * @param titleMsg 对话框标题
	 * @param alertMessage 对话框提示信息
	 * @param confirmMsg 确定按钮显示内容
	 * @param cancelMsg 取消按钮显示内容
	 * @param confirmListener 确定点击事件处理
	 * @param cancelListener 取消点击事件处理 
	 */
	public static void showDialog(Context context,String titleMsg,String alertMessage,
			String confirmMsg,String cancelMsg,DialogInterface.OnClickListener confirmListener,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(android.R.drawable.ic_menu_info_details);
		dialog.setTitle(titleMsg);
		dialog.setMessage(alertMessage);
		dialog.setPositiveButton(confirmMsg, confirmListener);
		dialog.setNegativeButton(cancelMsg, cancelListener);
		dialog.show();
	}
	/**
	 * 显示一个对话框 ,传入的是String 在R中的id
	 * @param context 
	 * @param titleResId 对话框标题
	 * @param alertMessageResId 对话框提示信息
	 * @param confirmMsgResId  确定按钮显示内容
	 * @param cancelMsgResId 取消按钮显示内容
	 * @param confirmListener 确定点击事件处理
	 * @param cancelListener 取消点击事件处理
	 */
	public static void showDialog(Context context,int titleResId,int alertMessageResId,
			int confirmMsgResId,int cancelMsgResId,DialogInterface.OnClickListener confirmListener,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(android.R.drawable.ic_menu_info_details);
		dialog.setTitle(titleResId);
		dialog.setMessage(alertMessageResId);
		dialog.setPositiveButton(confirmMsgResId, confirmListener);
		dialog.setNegativeButton(cancelMsgResId, cancelListener);
		dialog.create();
		dialog.show();
	}
	
	public static void showUpdateDetails(Context context) {
		boolean showUpdate = SavePrivateData.loadBooleanKey(SavePrivateData.SHOW_UPDATE_LIST, false);
		boolean first = SavePrivateData.loadBooleanKey(SavePrivateData.FIRST_BOOT, true);
        if (first || !showUpdate) {
        	SavePrivateData.saveBooleanKey(SavePrivateData.FIRST_BOOT, false);
        	
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setIcon(android.R.drawable.ic_input_add);
			dialog.setTitle(R.string.update_log);
			ListView listView = new ListView(context);
			listView.setCacheColorHint(Color.parseColor("#00000000"));
			listView.setSelector(android.R.color.transparent);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					context, 
					R.layout.dialog_listview_item,
					R.id.item_text,
					getData());
			listView.setAdapter(adapter);
			dialog.setView(listView);
			dialog.setPositiveButton(R.string.iknow, null);
			dialog.create();
			dialog.show();
			SavePrivateData.saveBooleanKey(SavePrivateData.SHOW_UPDATE_LIST, true);
        }
	}
	
	public static void switchProvince(Context context,String message,DialogInterface.OnClickListener listener) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(android.R.drawable.ic_menu_info_details);
		dialog.setTitle(android.R.string.dialog_alert_title);
		dialog.setMessage(message);
		dialog.setPositiveButton("确定", listener);
		dialog.setNegativeButton("取消", null);
		dialog.setCancelable(false);
		dialog.create();
		dialog.show();
	}
	
	/**
	 * 修改已经问过的问题
	 * @param context
	 * @param updateView
	 */
	public static void updateAskDialog(final Context context,String content) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(android.R.drawable.ic_menu_info_details);
		dialog.setTitle(context.getString(R.string.update_question));
		final EditText et = new EditText(context);
		et.setText(content);
		et.selectAll();
		dialog.setView(et);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				updateView.updateMyView(et.getText().toString());
//				MainActivity.customeAsyncTask.execute(et.getText().toString());
			
				InputMethodUtil.hiddenInputMethod(context, et);
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				InputMethodUtil.hiddenInputMethod(context, et);
			}
		});
		dialog.setCancelable(true);
		dialog.create();
		dialog.show();
		InputMethodUtil.showInputMethodDelay(context,500);
	}
	
	private static List<String> getData(){
		
        List<String> data = new ArrayList<String>();
        data.add("1.新增消息场景");
        data.add("2.新增优惠活动场景");
        data.add("3.套餐场景增加系列筛选功能和价格排序功能");
        data.add("4.其他用户体验优化");
        data.add("5.bug fixs");
        
        return data;
    }
	private static Context context;
	static EditText username;
	static EditText password;
	
	
	/**
	 * . 用户登录对话框
	 **/
	public static void showLoginDialog(final Context context) {
		DialogUtil.context = context;
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setIcon(android.R.drawable.ic_menu_info_details);
		dialog.setTitle(context.getString(R.string.login));
//		username = new EditText(context);
//		username.setSingleLine(true);
//		password = new EditText(context);
//		password.setSingleLine(true);
//		CheckBox cb = new CheckBox(context);
//		cb.setText("记住密码");
//		password.setId(111111);
//		username.setNextFocusDownId(111111);
//		//set password edit text can not diaplay
//		password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//		username.setHint(context.getString(R.string.plz_input_username));
//		password.setHint(context.getString(R.string.plz_input_password));
//		LinearLayout layout = new LinearLayout(context);
//		layout.setOrientation(LinearLayout.VERTICAL);
//		layout.addView(username);
//		layout.addView(password);
//		layout.addView(cb);
//		dialog.setView(layout);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.login_layout_dialog, null);
		username = (EditText) view.findViewById(R.id.username);
		password = (EditText) view.findViewById(R.id.password);
		CheckBox rememberPsw = (CheckBox) view.findViewById(R.id.remember_psw);
		rememberPsw.setOnCheckedChangeListener(checkboxListener);
		dialog.setView(view);
		
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				InputMethodUtil.hiddenInputMethod(context, username);
				String loginUrl = Config.IAsk.getLoginUrl(username.getText().toString(), username.getText().toString());
				new LoginTask().execute(loginUrl);
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				InputMethodUtil.hiddenInputMethod(context, username);
			}
		});
		dialog.setCancelable(true);
		dialog.create();
		dialog.show();
		InputMethodUtil.showInputMethodDelay(context,500);
	}
	
	private static OnCheckedChangeListener  checkboxListener = new OnCheckedChangeListener () {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			if (isChecked) {
				SavePrivateData.saveBooleanKey(SavePrivateData.REMEMBER_PSW, true);
			} else {
				SavePrivateData.saveBooleanKey(SavePrivateData.REMEMBER_PSW, false);
			}
		}
		
	};
	private static ProgressDialog dialog;
	static class LoginTask extends AsyncTask<String, Void, Result> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(context);
			dialog.setMessage(context.getString(R.string.logining_plz_wait));
			dialog.setCancelable(false);
			dialog.show();
		}
		
		@Override
		protected Result doInBackground(String... params) {
			Result result = new Result();
			try {
				String loginUrl = params[0];
				String json = MyHttpClient.get(loginUrl);
				if (json == null || "".equals(json)) {
					result.setResult(false);
					Error error = new Error(context.getString(R.string.net_unavailable));
					result.setError(error);
					return result;
				}
				JSONObject jo = new JSONObject(json);
//				MyLog.d(TAG, "login response json:" + jo);
				String status = jo.getString("status");
				if (status.equals(context.getString(R.string.login_success))) {
					long id = jo.getLong("id");
					SavePrivateData.saveBooleanKey(SavePrivateData.IS_LOGIN_SUCCESS, true);
					SavePrivateData.saveLongKey(SavePrivateData.IASK_USER_ID, id);
					boolean isRemberPsw = SavePrivateData.loadBooleanKey(SavePrivateData.REMEMBER_PSW, true);
					if (isRemberPsw) {
						SavePrivateData.saveStringKey(SavePrivateData.IASK_USER_NAME, username.getText().toString());
						SavePrivateData.saveStringKey(SavePrivateData.IASK_PASSWORD, password.getText().toString());
					}
					result.setResult(true);
				} else {
					SavePrivateData.saveBooleanKey(SavePrivateData.IS_LOGIN_SUCCESS, false);
					result.setResult(false);
					Error error = new Error(status);
					result.setError(error);
				}
				
			} catch (IOException e) {
				SavePrivateData.saveBooleanKey(SavePrivateData.IS_LOGIN_SUCCESS, false);
				result.setResult(false);
				Error error = new Error(context.getString(R.string.net_not_good));
				result.setError(error);
				e.printStackTrace();
			} catch (JSONException e) {
				SavePrivateData.saveBooleanKey(SavePrivateData.IS_LOGIN_SUCCESS, false);
				result.setResult(false);
				Error error = new Error(context.getString(R.string.login_fail_reson2));
				result.setError(error);
				e.printStackTrace();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(Result result) {
			super.onPostExecute(result);
			dialog.dismiss();
			Boolean success = result.getResult();
			if (success) {
				MyToast.showL(context,context.getString(R.string.login_success));
				if (loginSuccessCallback != null) {
					loginSuccessCallback.callback();
				}
			} else {
				Error error = result.getError();
				MyToast.showL(context,error.getMessage());
			}
		}
		
	}
	
	public static void setLoginSuccessListener(LoginSuccessListener listener) {
		DialogUtil.loginSuccessCallback = listener	;
	}
	
	public interface LoginSuccessListener {
		public void callback();
	}
	
}
