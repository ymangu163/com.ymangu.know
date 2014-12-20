package com.ymangu.know.voice;

import java.io.IOException;
import java.util.Properties;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.PageActivity;
import com.ymangu.know.R;
import com.ymangu.know.service.InstallAppService;
import com.ymangu.know.utils.ActivityUtil;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.utils.DialogUtil;
import com.ymangu.know.utils.MyToast;
import com.ymangu.know.utils.PropertiesUtil;
import com.ymangu.know.utils.SavePrivateData;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;


public class SettingPreferenceActivity extends PreferenceActivity implements
		OnPreferenceChangeListener {

	private static final String TAG = "SettingPreferenceActivity";

	private Context context;

	private String voiceRecognition;// 语音识别引擎
	private String voiceSpeech;// 语音播报引擎
	private String voiceRole;// 语音播报引擎
	private String myName;// 我的称呼
	private String allowAnim;

	private Preference loginPref;
	private Preference tingtingPref;

	private String loginPrefKey = "setting_login";
	private static final String SNDA = "snda";
	private ProgressDialog progressDialog;
	
	String token;
	private Properties properties;
	
	private ListPreference voiceSpeechRolePref;
	private ListPreference voiceSpeechPref;
	
//	private boolean isSndaVoiceSpeech = false;
	private static final int XUNFEI = 0;
	private static final int TINGTING = 1;
	
	@SuppressWarnings("deprecation")
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Preference 的管理者,从xml中导入数据
		PreferenceManager preferenceManager = getPreferenceManager();
		//设置 SharedPreferences
		preferenceManager.setSharedPreferencesName(Constants.SHARE_PREFERENCES_NAME);
		preferenceManager.setSharedPreferencesMode(MODE_PRIVATE);

		// 得到 /xml 下的文件
		addPreferencesFromResource(R.xml.setting);     

		context = this;
		progressDialog = new ProgressDialog(this);

		voiceRecognition = getString(R.string.choose_voice_recognition);   //语音识别引擎
		voiceSpeech = getString(R.string.choose_voice_speech);  // 语音播报引擎
		voiceRole = getString(R.string.choose_voice_role);   //语音角色
		
		myName = getString(R.string.my_name);
		allowAnim = getString(R.string.allow_anim);

		Preference voiceRecognitionPref = findPreference(voiceRecognition);
		voiceRecognitionPref.setOnPreferenceChangeListener(this);
		setSummary(voiceRecognitionPref,R.array.voice_recognition_entries_list_preference,voiceRecognition);

		voiceSpeechPref = (ListPreference) findPreference(voiceSpeech);
		voiceSpeechPref.setOnPreferenceChangeListener(this);
		setSummary(voiceSpeechPref,R.array.voice_speech_entries_list_preference, voiceSpeech);
		
		voiceSpeechRolePref = (ListPreference)findPreference(voiceRole);
		voiceSpeechRolePref.setOnPreferenceChangeListener(this);
//		setSummary2(voiceSpeechRolePref,R.array.preference_entries_tts_role,voiceRole);
		voiceSpeechRolePref.setSummary(voiceSpeechRolePref.getEntry());
		
		new SavePrivateData(context);
		Preference animPref = findPreference(allowAnim);
		animPref.setOnPreferenceChangeListener(this);
		setSummary(animPref,R.array.allow_anim_entries_list_preference, allowAnim);
		
		Preference mynamePref = findPreference(myName);
		SharedPreferences sp = mynamePref.getSharedPreferences();
		mynamePref.setSummary(sp.getString(myName, ""));
		mynamePref.setOnPreferenceChangeListener(this);
		
		loginPref = findPreference(getString(R.string.plz_login_key));
		
		tingtingPref = findPreference(getString(R.string.set_tingting));
		
		initHide();
		
		token = SavePrivateData.loadStringKey(SavePrivateData.ACCESS_TOKEN, "");
		LogUtils.d("token:" + token);
		if (token != null && !"".equals(token)) {
			loginPref.setTitle(R.string.logged_out);
			loginPref.setSummary(R.string.more_function);
		}
		
		try {
			properties = PropertiesUtil.getServerInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	};

	private void initHide() {
		int voiceSpeech2 =SavePrivateData.loadIntKey(SavePrivateData.CHOOSE_VOICE_SPEECH, 0);
		if (voiceSpeech2 == 0) {
			setXunfeiHiddenTingtingNotHidden(false);
		} else {
			setXunfeiHiddenTingtingNotHidden(true);
		}
	}
	
	/**
	 * true tingting hidden,false xunfei hidden
	 * @param flag
	 */
	private void setXunfeiHiddenTingtingNotHidden(boolean flag) {
		voiceSpeechRolePref.setSelectable(!flag);
		voiceSpeechRolePref.setEnabled(!flag);
		
		tingtingPref.setSelectable(flag);
		tingtingPref.setEnabled(flag);
	}

	@Override
	protected void onPause() {
		LogUtils.i("onPause");

		super.onPause();
	}

	@Override
	protected void onResume() {
		LogUtils.i("onResume");
		
		super.onPause();
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String key = preference.getKey();
		if (key.equals(voiceRecognition)) {
			setSummary2(preference,R.array.voice_recognition_entries_list_preference, newValue);
		} else if (key.equals(voiceSpeech)) {
			 boolean checkInstalledTT = InstallAppService.checkInstalledTT(this);
			 if (Integer.valueOf((String) newValue) == 1 && !checkInstalledTT) {
				 DialogUtil.showDialog(context, 
	            			context.getString(R.string.download_tingting_center), 
	            			context.getString(R.string.download_tingting_alert_msg), 
	            			context.getString(R.string.dialog_confirm), 
	            			context.getString(R.string.dialog_next_ask), 
	            			confirmDownloadTT, 
							null);
				 return false;
			 }
			 
			 if (Integer.valueOf((String) newValue) == 1 && checkInstalledTT) {
//				 new SndaVoiceSpeech(this.context);    
			 }
			 setSummary2(preference,R.array.voice_speech_entries_list_preference, newValue);
		} else if (key.equals(myName)) {
			preference.setSummary(String.valueOf(newValue));
		} else if (key.equals(allowAnim)) {
			setSummary2(preference,R.array.allow_anim_entries_list_preference, newValue);
		} else if (key.equals(voiceRole)) {
			setSummary2(preference,R.array.preference_entries_tts_role,newValue);
		}
		return true;
	}

	private void setSummary2(Preference preference, int arrays, Object newValue) {
		SharedPreferences sp = preference.getSharedPreferences();
		String role = sp.getString("choose_voice_role", "xiaoyan");
		saveCurrentVoiceSpeech(role);
		LogUtils.d("role:" + role);
		if (preference instanceof ListPreference) {
			ListPreference listPreference = (ListPreference) preference;
			CharSequence[] entries = listPreference.getEntries();
			int index = listPreference.findIndexOfValue((String) newValue);
			listPreference.setSummary(entries[index]);
		}
		String key = preference.getKey();
		
		if (key.equals(voiceSpeech)) {
			String voice = sp.getString("choose_voice_speech","0");
			int type = Integer.parseInt(voice);
			if (type == XUNFEI) {//xunfei voice speech
				
				if (preference instanceof ListPreference) {
					ListPreference listPreference = (ListPreference) preference;
					CharSequence[] entries = listPreference.getEntries();
					int index = listPreference.findIndexOfValue((String) newValue);
					
					CharSequence summary = listPreference.getSummary();
					LogUtils.d("getSummary() ----- >" + summary);
					LogUtils.d("entries[index] ----- >" + entries[index]);
					LogUtils.d( "compare summary ----- >" + "讯飞联网播报");
					
					if (entries[index].equals(getString(R.string.compare_xunfei))){
						return;
					}
				}
				voiceSpeechRolePref.setSelectable(false);
				voiceSpeechRolePref.setEnabled(false);
				
				tingtingPref.setSelectable(true);
				tingtingPref.setEnabled(true);
				
				
				
				SavePrivateData.saveStringKey(SavePrivateData.CURRENT_VOICE_SPEECH, SNDA);
			} else if (type == TINGTING){//snda voice speech
				
				if (preference instanceof ListPreference) {
					ListPreference listPreference = (ListPreference) preference;
					CharSequence[] entries = listPreference.getEntries();
					int index = listPreference.findIndexOfValue((String) newValue);
					
					CharSequence summary = listPreference.getSummary();
					LogUtils.d( "getSummary() ----- >" + summary);
					LogUtils.d("entries[index] ----- >" + entries[index]);
					LogUtils.d( "compare summary ----- >" + "听听离线播报(需要插件省流量)");
					
					if (entries[index].equals(getString(R.string.compare_tingting))){
						return;
					}
				}
				voiceSpeechRolePref.setSelectable(true);
				voiceSpeechRolePref.setEnabled(true);
				
				tingtingPref.setSelectable(false);
				tingtingPref.setEnabled(false);
				
				SavePrivateData.saveStringKey(SavePrivateData.CURRENT_VOICE_SPEECH, SNDA);
			}
			LogUtils.d("voice:" + voice);
		}
		
		role = sp.getString("choose_voice_role", "xiaoyan");
		LogUtils.d( "role:" + role);
		
	}

	/**
	 * save current voice speech to sharepreference
	 * @param role
	 */
	private void saveCurrentVoiceSpeech(String role) {
		CharSequence[] entries = voiceSpeechRolePref.getEntries();
		int indexOfValue = findIndexOfValue(R.array.preference_values_tts_role, role);
		if (indexOfValue != -1) {
			String value = entries[indexOfValue].toString();
			SavePrivateData.saveStringKey(SavePrivateData.CURRENT_VOICE_SPEECH_ROLE, value);
		}
	}
	
	/**
	 * return index from array
	 * @param array
	 * @param value
	 * @return
	 */
	private int findIndexOfValue(int array,String value) {
		String[] valueRole = getResources().getStringArray(array);
		for (int i = 0; i < valueRole.length; i ++) {
			if (valueRole[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	@Deprecated
	private void setSummary(Preference preference, int arrays, Object newValue) {
		SharedPreferences sp = preference.getSharedPreferences();
		String value = sp.getString(String.valueOf(newValue), "");
		String[] stringArray = getResources().getStringArray(arrays);
		String summary = null;
		if (value.equals("") || null == value) {
			summary = stringArray[Integer.valueOf((String) newValue)];
		} else {
			summary = stringArray[Integer.parseInt(value)];
		}
		LogUtils.e( "String.valueOf(newValue) summary " + summary);
		preference.setSummary(summary);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,Preference preference) {
		String key = preference.getKey();
		LogUtils.d("key is :" + key);
		
		if (key.equals(getString(R.string.set_tingting))) {
			Intent intent = new Intent();
			intent.setClassName("com.snda.tts.service", "com.snda.tts.config.TtsConfig");
			startActivity(intent);
			return false;
		}

		if (loginPrefKey.equals(preference.getKey())) {
			if (token != null && !"".equals(token)) {
				new Logout().execute();
			} else {
				DialogUtil.showDialog(context, 
						R.string.plz_login, 
						R.string.plz_login_2, 
						R.string.dialog_confirm, 
						R.string.dialog_next_ask, 
						confirmListener, 
						null);
			}
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	private DialogInterface.OnClickListener confirmListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			login();

		}
	};
	private DialogInterface.OnClickListener confirmDownloadTT = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			ActivityUtil.searchAppByPkgName(context, Constants.SNDA_TINGTING_PACKAGE_NAME);
			
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == Constants.LOGIN_REQUEST_CODE && resultCode == RESULT_OK) {
			loginResult(data);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@SuppressWarnings("deprecation")
	private void loginResult(Intent data) {
		Bundle bundle = data.getExtras();
		
		String resCode = bundle.getString("res_code");
		int code = Integer.parseInt(resCode);
		if (code == 0) {
			SavePrivateData.saveStringKey(SavePrivateData.ACCESS_TOKEN,bundle.getString("access_token"));
			MyToast.showL(context,
					code == 0 ? R.string.login_authorization_success : R.string.login_authorization_fail);
			Preference pref = findPreference(getString(R.string.plz_login_key));
			pref.setTitle(R.string.already_logined);
			pref.setSummary(R.string.more_function);
		} else {
			MyToast.showL(context,getString(R.string.login_authorization_fail));
		}
		StringBuilder sb = new StringBuilder();
		sb.append("res_code=" + bundle.getString("res_code") + "\n"); // 标准返回码。返回0表示成功
		sb.append("res_message=" + bundle.getString("res_message") + "\n"); // 返回码描述信息
		sb.append("access_token=" + bundle.getString("access_token") + "\n"); // 获取到的AT访问令牌
		sb.append("expires_in=" + bundle.getString("expires_in") + "\n"); // AT访问令牌的有效期（以秒为单位）
		sb.append("scope=" + bundle.getString("scope") + "\n"); // 权限列表，保留字段，默认为空，表示所有权限
		sb.append("state=" + bundle.getString("state") + "\n"); // 与请求参数中state的值一致
		sb.append("p_user_id=" + bundle.getString("p_user_id") + "\n"); // 天翼帐号的唯一标志，标识当前授权的用户
		LogUtils.d(bundle.toString());
		token = SavePrivateData.loadStringKey(SavePrivateData.ACCESS_TOKEN, "");
		LogUtils.e("save token is : " + token);
	}

	private void login() {
		
//		Intent intent = new Intent(context, AuthView.class);
		Intent intent = new Intent(context, PageActivity.class);   //AuthView 没有
		intent.putExtra("app_id", properties.getProperty(Constants.APP_189_ID)); // 应用在EMP平台上的唯一标识，在应用注册时分配
		intent.putExtra("app_secret", properties.getProperty(Constants.APP_189_SECRET)); // EMP颁发给应用的密钥信息
		intent.putExtra("display", "touch"); // 登录和授权页面的展现样式
		intent.putExtra("redirect_uri", "http://www.itcast-tech.com/"); // 必须与应用安全设置中的设置回调地址一致.用于验证请求的目的,防止hack响应的行为.
		startActivityForResult(intent, Constants.LOGIN_REQUEST_CODE);
	}
	
	class Logout extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage(getString(R.string.logged_out_ing));
			progressDialog.show();
		}


		@Override
		protected Void doInBackground(Void... params) {
			SystemClock.sleep(1500);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			loginPref.setTitle(R.string.plz_login);
			loginPref.setSummary(R.string.plz_login_more);
			SavePrivateData.deleteKey(SavePrivateData.ACCESS_TOKEN);
			token = SavePrivateData.loadStringKey(SavePrivateData.ACCESS_TOKEN, "");
			MyToast.showL(context, R.string.logged_success);
			progressDialog.dismiss();
			
		}
		
	}

}
