package com.ymangu.know.server;

import com.ymangu.know.utils.SavePrivateData;

import android.content.Context;

/**
 * . 配置访问服务器的 URL地址
 **/

public class Config {

//	public static final String BASE_URL = "http://210.51.165.2:6000";
	public static final String BASE_URL = "http://210.51.165.3:5000";
//	public static final String BASE_URL = "http://192.168.0.99:8081";
	//北京
	public static final String BEIJING_BASE_URL = BASE_URL;
	public static final String BEIJING_Q_SERVER = BASE_URL + "/api/search.json?";
	
	public static final String PACKAGE_SERIES = BASE_URL + "/api/search!pack.do";
	
	public static final String PACKAGE_DETAIL_BY_ID = BASE_URL + "/api/search!getBrandId.do?id=";
	
	public static final String FUZZY_URL = BASE_URL + "/api/search!fuzzy.do?q=";
	
	public static final String MOBILE_BRAND = BASE_URL + "/api/search!mobileBrand.do?spname=";
	
	public static final String MOBILE_BRAND_BY_NAME = BASE_URL + "/api/search!mobileList.do?brand=";
	
	public static final String ALREADY_ANSWER_COUNT = BASE_URL + "/api/search!totalQuestion.do";
	
	
	/**
	 *积木套餐url
	 */
	public static final String JM_URL = "http://www.189.cn/diy/";
	
	/**
	 * 
	 * @param catalog Mobile or Pack
	 * @param startIndex start index
	 * @param endIndex end index
	 * @return
	 */
	public static final String getMobilePackPage(String catalog,int startIndex,int endIndex) {
		return BASE_URL + "/api/search!searchCatalog.do?c=" + catalog + "&start=" + startIndex + "&end=" + endIndex + "";
	}
	
	/**
	 * get assign mobile brand by name
	 * @param name
	 * @return
	 */
	public static String getMobileBrandByName(String name){
		return MOBILE_BRAND_BY_NAME + name;
	}
	
	public static String getFuzzyUrl(String content) {
		String url = FUZZY_URL + content;
		return url;
	}
	/**
	 * switch district
	 * @param context
	 * @return
	 */
	public static String getCurrentBaseUrl(Context context) {
		String city = SavePrivateData.loadStringKey(SavePrivateData.CURRENT_CITY, "beijing");
		String serverPath = null;
		if (city.equals("beijing")) {
			serverPath = Config.BEIJING_BASE_URL;
		} else if (city.equals("anhui")) {
			
		}
		return serverPath;
	}
	
	public static String getPackageUrl(String id) {
		return PACKAGE_DETAIL_BY_ID + id;
	}
	
	public static final class IAsk {
		
		public static final String SEARCH_SUGGEST = BASE_URL + "/api/search!iaskSugest.do?key=";
		
		public static final String IASK_TYPE_HOT = BASE_URL + "/api/search!iaskList.do?tab=hot";
		public static final String IASK_TYPE_NEW = BASE_URL + "/api/search!iaskList.do?tab=new";
		public static final String IASK_TYPE_HIGHT = BASE_URL + "/api/search!iaskList.do?tab=hight";
		public static final String IASK_GET_USER_INFO = BASE_URL + "/api/search!userInfo.do";
		
		/**
		 * 我的提问
		 */
		public static final String IASK_MY_ASK = BASE_URL + "/api/search!questionList.do?tab=1";
		/**
		 * 我的回答
		 */
		public static final String IASK_MY_ANSWER = BASE_URL + "/api/search!questionList.do?tab=2";
		/**
		 * get ask by id
		 * @param id
		 * @return
		 */
		public static final String getAskById(String id) {
			return BASE_URL + "/api/search!iaskDetail.do?id=" + id;
		}
		
		public static final String getSuggestUrl(String keyword) {
			return SEARCH_SUGGEST + keyword;
		}
		/**
		 * get login url
		 */
		public static final String getLoginUrl(String username,String password) {
			return BASE_URL + "/api/search!iaskLogin.do?username=" + username + "&password=" + password + "&rememberMe=yes";
		}
		/**
		 * new create new iask question
		 * @param question
		 * @return
		 */
		public static final String submitAsk(String question) {
			return BASE_URL + "/api/search!iaskQuestionSave.do?title="+ question +"";
		}
		/**
		 * answer assign question by id
		 * @param id
		 * @param answer
		 * @return
		 */
		public static final String submitAnswer(String id,String answer) {
			return BASE_URL + "/api/search!iaskAnswerSave.do?detail="+ answer +"&qid=" + id;
		}
		
		/**
		 * get integral hightest iask url 
		 * @param page
		 * @return
		 */
		public static final String getIAskHightPage(int page) {
			return BASE_URL + "/api/search!iaskList.do?tab=hight&page=" + page;
		}
		/**
		 * get newest iask url
		 * @param page
		 * @return
		 */
		public static final String getIAskNewPage(int page) {
			return BASE_URL + "/api/search!iaskList.do?tab=new&page=" + page;
		}
		/**
		 * get hottest iask url
		 * @param page
		 * @return
		 */
		public static final String getIAskHotPage(int page) {
			return BASE_URL + "/api/search!iaskList.do?tab=hot&page=" + page;
		}
		
	}
	
}
