package com.demo.app.api;

import java.io.IOException;



import android.os.AsyncTask;

import org.apache.http.client.ClientProtocolException;

public class HttpData extends AsyncTask<String, Void, String>{
	private String url;
	private HttpGatDataListener listener;

	public HttpData(String url, HttpGatDataListener listener) {
		this.url=url;
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		HttpHelper httpHelper = new HttpHelper();
		try {
			return httpHelper.getDataByGet(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		listener.getDataUrl(result);
		super.onPostExecute(result);
	}

}
