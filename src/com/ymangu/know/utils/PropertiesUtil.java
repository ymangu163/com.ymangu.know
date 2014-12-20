package com.ymangu.know.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 加载关键字映射
 * @author xiejc
 *
 */
public class PropertiesUtil {

	private static Properties properties = null;
	private static Properties properties_server = null;
	
	private PropertiesUtil(){
		
	};
	
	public static Properties getInstance() throws IOException {
		if (properties == null) {
			synchronized(PropertiesUtil.class) {
				if (properties == null) {
					properties =new Properties();
					properties.load(PropertiesUtil.class.getResourceAsStream("/assets/voice.properties"));
				}
			}
		}
		return properties;
	}
	/**
	 * return request server path properties
	 * @return
	 * @throws IOException
	 */
	public static Properties getServerInstance() throws IOException {
		if (properties_server == null) {
			synchronized (PropertiesUtil.class) {
				if (properties_server == null) {
					properties_server =new Properties();
					properties_server.load(PropertiesUtil.class.getResourceAsStream("/assets/config.properties"));
				}
			}
		}
		return properties_server;
	}
	
}
