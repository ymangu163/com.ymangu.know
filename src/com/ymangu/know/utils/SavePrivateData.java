package com.ymangu.know.utils;

import com.ymangu.know.R;

import android.content.Context;
import android.content.SharedPreferences;

public class SavePrivateData {

	public static final String KEY_PREFERENCES = Constants.SHARE_PREFERENCES_NAME;

	public static final String ACCESS_TOKEN = "access_token";
	public static final String MY_NAME = "my_name";
	public static final String LON = "longitude";
	public static final String LAT = "latitude";
	public static final String CHOOSE_VOICE_RECOGNITION = "choose_voice_recognition";
	public static final String CHOOSE_VOICE_SPEECH = "choose_voice_speech";
	public static final String FIRST_BOOT = "first_boot";
	public static final String CURRENT_VERSION = "current_version";
	public static final String CURRENT_CITY = "current_city";
	public static final String TITLE_TEXT = "title_text";
	public static final String PROVINCE_TEXT = "province_text";
	public static final String IS_ABORT_SMS_BROADCAST = "is_abort_sms_broadcast";
	public static final String ONE_MORE = "one_more";
	public static final String DOWNLOAD_ID = "download_id";
	public static final String DOWNLOAD_APK_PATH = "download_apk_path";
	public static final String CURRENT_VOICE_SPEECH_ROLE = "current_voice_speech_role";
	public static final String CURRENT_VOICE_SPEECH = "current_voice_speech";
	public static final String VOICE_ON_OFF = "voice_on_off";
	public static final String IS_LOGIN_SUCCESS = "is_login_success";
	public static final String IASK_USER_ID = "iask_user_id";
	public static final String IASK_USER_NAME = "iask_user_name";
	public static final String IASK_PASSWORD = "iask_password";
	public static final String REMEMBER_PSW = "remember_psw";
	
	/**
	 *	modify date when publisher new version
	 */
	public static final String SHOW_UPDATE_LIST = "show_update_list_20130318";


	protected static SharedPreferences settings;
	protected static SharedPreferences.Editor editor;
	
	private static Context context;

	@SuppressWarnings("static-access")
	public SavePrivateData(Context context) {
		settings = context.getSharedPreferences(KEY_PREFERENCES,Context.MODE_PRIVATE);
		editor = settings.edit();
		this.context = context;
	}

//	public static void init(Context context) {
//		if (settings == null) {
//			settings = PreferenceManager.getDefaultSharedPreferences(context);
//		}
//	}

	/**
	 * Load the value referred to the configuration given the key
	 * 
	 * @param key
	 *            the String formatted key representing the value to be loaded
	 * @return String String formatted vlaue related to the give key
	 */
	protected static String loadKey(String key) {
		if(settings!=null){
			return settings.getString(key, null);		
		}else{
			return "";
		}
		
	}
	/**
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	protected static String loadKey(String key,String defValue) {
		return settings.getString(key, defValue);
	}
	
	

	/**
	 * Save the loaded twin key-value using the android context package
	 * SharedPreferences.Editor instance
	 * 
	 * @param key
	 *            the key to be saved
	 * @param value
	 *            the value related to the key String formatted
	 */
	protected static void saveKey(String key, String value) {
		if(editor==null){
			return;			
		}
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * delete value by assign key
	 * @param key
	 */
	public static void  deleteKey(String key) {
		editor.remove(key);
		editor.commit();
	}

	/**
	 * Commit the changes
	 * 
	 * @return true if new values were correctly written into the persistent
	 *         storage
	 */
	public static boolean commit() {
		
		return editor.commit();
	}

	public static boolean loadBooleanKey(String key, boolean defaultValue) {
		String v = loadKey(key);
		boolean bv;
		if (v == null) {
			bv = defaultValue;
		} else {
			if (v.equals("TRUE")) {
				bv = true;
			} else {
				bv = false;
			}
		}
		return bv;
	}

	public static void saveBooleanKey(String key, boolean value) {
		String v;
		if (value) {
			v = "TRUE";
		} else {
			v = "FALSE";
		}
		saveKey(key, v);
	}

	public static int loadIntKey(String key, int defaultValue) {
		String v = loadKey(key);
		int iv;
		if (v == null) {
			iv = defaultValue;
		} else {
			try {
				iv = Integer.parseInt(v);
			} catch (Exception e) {
				iv = defaultValue;
			}
		}
		return iv;
	}

	public void saveIntKey(String key, int value) {
		String v = String.valueOf(value);
		saveKey(key, v);
	}
	public static double loadDoubleKey(String key, double defaultValue) {
		String v = loadKey(key);
		double iv;
		if (v == null) {
			iv = defaultValue;
		} else {
			try {
				iv = Double.parseDouble(v);
			} catch (Exception e) {
				iv = defaultValue;
			}
		}
		return iv;
	}
	public static void saveDoubleKey(String key, double value) {
		String v = String.valueOf(value);
		saveKey(key, v);
	}

	public static long loadLongKey(String key, long defaultValue) {
		String v = loadKey(key);
		long iv;
		if (v == null) {
			iv = defaultValue;
		} else {
			try {
				iv = Long.parseLong(v);
			} catch (Exception e) {
				iv = defaultValue;
			}
		}
		return iv;
	}

	public static void saveLongKey(String key, long value) {
		String v = String.valueOf(value);
		saveKey(key, v);
	}

	public static String loadStringKey(String key, String defaultValue) {
		String v = loadKey(key);
		if (v == null) {
			v = defaultValue;
		}
		return v;
	}

	public static void saveStringKey(String key, String value) {
		saveKey(key, value);
	}
	
	public static double getLat() {
		return SavePrivateData.loadDoubleKey(LAT, 0);
	}
	public static double getLon() {
		return SavePrivateData.loadDoubleKey(LON, 0);
	}
	public static String getMyName(Context ctx) {
		String name = SavePrivateData.loadStringKey(SavePrivateData.MY_NAME,  ctx.getString(R.string.master));
		return name;
	}
	@Deprecated
	public static double getOldAppVersion() {
		double version = SavePrivateData.loadDoubleKey(SavePrivateData.CURRENT_VERSION, 1.0);
		return version;
	}

}
