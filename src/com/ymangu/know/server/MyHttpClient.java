package com.ymangu.know.server;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MyHttpClient {

    private static HttpClient customerHttpClient;
	
	private MyHttpClient(){};
	
	public static synchronized HttpClient getHttpClient() {
		if (null== customerHttpClient) {
            HttpParams params =new BasicHttpParams();
            // 设置一些基本参数
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params,HTTP.UTF_8);
            HttpProtocolParams.setUseExpectContinue(params, true);
//            HttpProtocolParams
//                    .setUserAgent(
//                            params,
//                            "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
//                                    +"AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            // 超时设置
            /* 从连接池中取连接的超时时间 */
            ConnManagerParams.setTimeout(params, 1000);
            /* 连接超时 */
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            /* 请求超时 */
            HttpConnectionParams.setSoTimeout(params, 5000);
            
            // 设置HttpClient支持HTTP和HTTPS两种模式
            SchemeRegistry schReg =new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

            // 使用线程安全的连接管理来创建HttpClient
            ClientConnectionManager conMgr =new ThreadSafeClientConnManager(params, schReg);
            customerHttpClient =new DefaultHttpClient(conMgr, params);
        }
        return customerHttpClient;
    }
	
	/**
	 * use get method request http access
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public static String get(String url) throws IOException,IllegalArgumentException {
		HttpEntity httpEntity = getHttpEntity(url);
		if (httpEntity != null) {
			String json = EntityUtils.toString(httpEntity,HTTP.UTF_8);
			return json;
		}
		return null;
	}
	public static InputStream getInputStream(String url) throws IOException {
		HttpEntity httpEntity = getHttpEntity(url);
		if (httpEntity != null) {
			InputStream is = httpEntity.getContent();
			return is;
		}
		
		return null;
	}
	/**
	 * Get httpClient httpEntity from assign url
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static HttpEntity getHttpEntity(String url) throws IOException {
		HttpClient httpClient = getHttpClient();

		url = url.replaceAll(" ", "%20");
		HttpGet request = new HttpGet(url);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = null;
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
			return entity;
		}
		return entity;
	}
	
}
