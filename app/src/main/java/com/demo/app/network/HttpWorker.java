package com.demo.app.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;


import com.demo.app.util.Constents;


public class HttpWorker {
	final int CONNECTION_TIMEOUT = 5000, SOCKET_TIMEOUT = 10000;

	private HttpParams httpParameters;
	
	private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";


	public HttpWorker() {
		httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.

		HttpConnectionParams.setConnectionTimeout(httpParameters,
				CONNECTION_TIMEOUT);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.

		HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIMEOUT);

	}

	public String get(String uri) throws ClientProtocolException, IOException,
			NetworkUnaviableException {
////		check();
		return uncheck_get(uri);
	}

	public String uncheck_get(String uri) throws ClientProtocolException,
			IOException, NetworkUnaviableException {

		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		HttpGet request = new HttpGet(Constents.REQUEST_URL+uri);
		HttpResponse resp = client.execute(request);
		String result = getHttpResponse(resp);
		return result;
	}

	public String post(String uri, String json) throws ClientProtocolException,
			IOException, NetworkUnaviableException {
		// 将JSON进行UTF-8编码,以便传输中文
        //json = URLEncoder.encode(json, HTTP.UTF_8);
        
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);

		HttpPost httpPost = new HttpPost(Constents.REQUEST_URL+uri);
		json = URLEncoder.encode(json, "utf-8");
		// Log.d(SI.LOGTAG, "HttpPost:" + uri);
		StringEntity se = new StringEntity(json);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
		httpPost.setEntity(se);

		HttpResponse resp = client.execute(httpPost);
		String result = getHttpResponse(resp);
		
		result = URLDecoder.decode(result, "utf-8");
		// Log.d(SI.LOGTAG, "post:" + post);
		// Log.d(SI.LOGTAG, "back:" + result);
		return result;
	}
	
	public String put(String uri, String json) throws ClientProtocolException,
		IOException, NetworkUnaviableException {
		String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
        
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);

		HttpPut httpPut = new HttpPut(Constents.REQUEST_URL+uri);
		// Log.d(SI.LOGTAG, "HttpPost:" + uri);
		StringEntity se = new StringEntity(encoderJson);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPut.setEntity(se);
		
		HttpResponse resp = client.execute(httpPut);
		String result = getHttpResponse(resp);
		// Log.d(SI.LOGTAG, "post:" + post);
		// Log.d(SI.LOGTAG, "back:" + result);
		return result;
	}
	
	public String delete(String uri, String post) throws ClientProtocolException,
		IOException, NetworkUnaviableException {
		//check();
		
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
		
		HttpDelete httpDelete = new HttpDelete(Constents.REQUEST_URL+uri);
		// Log.d(SI.LOGTAG, "HttpPost:" + uri);
		//List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("postdata", post));
		//httpDelete.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		
		HttpResponse resp = client.execute(httpDelete);
		String result = getHttpResponse(resp);
		// Log.d(SI.LOGTAG, "post:" + post);
		// Log.d(SI.LOGTAG, "back:" + result);
		return result;
	}

	private String getHttpResponse(HttpResponse resp)
			throws IllegalStateException, IOException {

		HttpEntity entity = resp.getEntity();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				entity.getContent()));
		StringBuffer sb = new StringBuffer();
		String result = br.readLine();

		while (result != null) {
			sb.append(result);
			result = br.readLine();
		}
		result = sb.toString();
		return result;
	}
}
