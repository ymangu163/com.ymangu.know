package com.ymangu.know.recognizer;

import java.io.IOException;

import com.lidroid.xutils.util.LogUtils;
import com.ymangu.know.utils.Constants;
import com.ymangu.know.utils.PropertiesUtil;

/**
 * .  各种语音库的 appId
 **/
public class BaseVoiceRecognizer {

	private static final String TAG = "BaseVoiceRecognition";
	
    
    protected String getIflytekAppID() {
		String appid = null;
		try {
			appid = (String) PropertiesUtil.getServerInstance().get(Constants.IFLYTED_APPID);
		} catch (IOException e) {
			LogUtils.e( "getIflytekAppID", e);
		}
		return appid;
	}
    
    protected String getSndaAppID() {
    	String appid = null;
		try {
			appid = (String) PropertiesUtil.getServerInstance().get(Constants.SNDA_APPID);
		} catch (IOException e) {
			LogUtils.e( "getSndaAppID", e);
		}
		return appid;
	}
    protected String getSndaUser() {
    	String user = null;
    	try {
    		user = (String) PropertiesUtil.getServerInstance().get(Constants.SNDA_USER);
    	} catch (IOException e) {
    		LogUtils.e("getSndaUser", e);
    	}
    	return user;
    }
    protected String getYZSAppID() {
    	String appid = null;
    	try {
    		appid = (String) PropertiesUtil.getServerInstance().get(Constants.YUNZHISHENG_APPID);
    	} catch (IOException e) {
    		LogUtils.e("getYZSAppID", e);
    	}
    	return appid;
    }
	
}
