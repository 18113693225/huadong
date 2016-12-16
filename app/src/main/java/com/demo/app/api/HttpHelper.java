package com.demo.app.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpHelper {

	public HttpHelper() {
	}

	/**
	 * 
	 * @param mContext
	 * @return �����Ƿ�����������
	 */
	public static boolean CheckedNet(Context mContext) {
		// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
		try {
			ConnectivityManager connectivity = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// ��ȡ�������ӹ���Ķ���
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// �жϵ�ǰ�����Ƿ��Ѿ�����
					if (info.isAvailable()) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * HttpPost
	 * 
	 * @param url
	 *          ����·��
	 * @param mPairs
	 * @param header
	 * @param timeOut
	 * @param encode
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public String getDataByPost(String url, List<NameValuePair> mPairs,
			ArrayList<HashMap<String, String>> header, int timeOut,
			String encode) throws ClientProtocolException, IOException,
			Exception {
		HttpClient mClient = new DefaultHttpClient();
		HttpPost mHttpPost = new HttpPost(url);

		if (header != null) {
			for (HashMap<String, String> map : header) {
				mHttpPost.setHeader(map.get("Name").toString(), map
						.get("Value").toString());
			}
		}

		if (mPairs != null) {
			mHttpPost.setEntity(new UrlEncodedFormEntity(mPairs, "utf8"));
		}

		mHttpPost.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut / 2);
		mHttpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				timeOut / 2);

		HttpResponse mResponse = mClient.execute(mHttpPost);

		int state = mResponse.getStatusLine().getStatusCode();
		HttpEntity mEntity = mResponse.getEntity();
		if (state == 200 && mEntity != null) {
			String result = EntityUtils.toString(mEntity, "utf8");
			mClient.getConnectionManager().shutdown();
			mHttpPost.abort();
			Log.i("kill",result);
			return result;
		}

		mClient.getConnectionManager().shutdown();
		mHttpPost.abort();
		return "";
	}

	/**
	 * HttpPost
	 * 
	 * @param url
	 * @param mPairs
	 * @param header
	 * @param timeOut
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public String getDataByPost(String url, List<NameValuePair> mPairs,
			ArrayList<HashMap<String, String>> header, int timeOut)
			throws ClientProtocolException, IOException, Exception {
		return getDataByPost(url, mPairs, header, timeOut, HTTP.UTF_8);
	}

	/**
	 * HttpPost
	 * 
	 * @param url
	 * @param mPairs
	 * @param header
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public String getDataByPost(String url, List<NameValuePair> mPairs,
			ArrayList<HashMap<String, String>> header)
			throws ClientProtocolException, IOException, Exception {
		return getDataByPost(url, mPairs, header, 60000, HTTP.UTF_8);
	}

	/**
	 * HttpPost
	 * 
	 * @param url
	 * @param mPairs
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public String getDataByPost(String url, List<NameValuePair> mPairs)
			throws ClientProtocolException, IOException, Exception {
		return getDataByPost(url, mPairs, null, 60000, HTTP.UTF_8);
	}

	/**
	 * HttpPost
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public String getDataByPost(String url) throws ClientProtocolException,
			IOException, Exception {
		return getDataByPost(url, null, null, 60000, HTTP.UTF_8);
	}

	/**
	 * HttpPost����
	 * 
	 * @param url
	 * @param mPairs
	 * @param header
	 * @param timeOut
	 * @param encode
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public InputStream getStreamByPost(String url, List<NameValuePair> mPairs,
			ArrayList<HashMap<String, String>> header, int timeOut,
			String encode) throws ClientProtocolException, IOException,
			Exception {
		HttpClient mClient = new DefaultHttpClient();
		HttpPost mHttpPost = new HttpPost(url);

		if (header != null) {
			for (HashMap<String, String> map : header) {
				mHttpPost.setHeader(map.get("Name").toString(), map
						.get("Value").toString());
			}
		}

		if (mPairs != null) {
			mHttpPost.setEntity(new UrlEncodedFormEntity(mPairs, encode));
		}

		mHttpPost.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut / 2);
		mHttpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				timeOut / 2);

		HttpResponse mResponse = mClient.execute(mHttpPost);

		int state = mResponse.getStatusLine().getStatusCode();
		HttpEntity mEntity = mResponse.getEntity();
		if (state == 200 && mEntity != null) {
			InputStream mInputStream = mEntity.getContent();
			mClient.getConnectionManager().shutdown();
			mHttpPost.abort();
			return mInputStream;
		}

		mClient.getConnectionManager().shutdown();
		mHttpPost.abort();
		return null;
	}

	/**
	 * HttpPost����
	 * 
	 * @param url
	 * @param mPairs
	 * @param header
	 * @param timeOut
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public InputStream getStreamByPost(String url, List<NameValuePair> mPairs,
			ArrayList<HashMap<String, String>> header, int timeOut)
			throws ClientProtocolException, IOException, Exception {
		return getStreamByPost(url, mPairs, header, timeOut, HTTP.UTF_8);
	}

	/**
	 * HttpPost����
	 * 
	 * @param url
	 * @param mPairs
	 * @param header
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public InputStream getStreamByPost(String url, List<NameValuePair> mPairs,
			ArrayList<HashMap<String, String>> header)
			throws ClientProtocolException, IOException, Exception {
		return getStreamByPost(url, mPairs, header, 60000, HTTP.UTF_8);
	}

	/**
	 * HttpPost����
	 * 
	 * @param url
	 * @param mPairs
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public InputStream getStreamByPost(String url, List<NameValuePair> mPairs)
			throws ClientProtocolException, IOException, Exception {
		return getStreamByPost(url, mPairs, null, 60000, HTTP.UTF_8);
	}

	/**
	 * HttpPost����
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public InputStream getStreamByPost(String url)
			throws ClientProtocolException, IOException, Exception {
		return getStreamByPost(url, null, null, 60000, HTTP.UTF_8);
	}

	/**
	 * HttpGet
	 * 
	 * @param url
	 * @param header
	 * @param timeOut
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public String getDataByGet(String url,
			ArrayList<HashMap<String, String>> header, int timeOut)
			throws ClientProtocolException, IOException, Exception {
		HttpClient mClient = new DefaultHttpClient();
		HttpGet mHttpGet = new HttpGet(url);

		if (header != null) {
			for (HashMap<String, String> map : header) {
				mHttpGet.setHeader(map.get("Name").toString(), map.get("Value")
						.toString());
			}
		}

		mHttpGet.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut / 2);
		mHttpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				timeOut / 2);

		HttpResponse mResponse = mClient.execute(mHttpGet);

		int state = mResponse.getStatusLine().getStatusCode();
		HttpEntity mEntity = mResponse.getEntity();
		if (state == 200 && mEntity != null) {
			String result = EntityUtils.toString(mEntity);
			mClient.getConnectionManager().shutdown();
			mHttpGet.abort();
			return result;
		}

		mClient.getConnectionManager().shutdown();
		mHttpGet.abort();
		return "";
	}

	/**
	 * HttpGet Header
	 * 
	 * @param url
	 * @param header
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public String getDataByGet(String url,
			ArrayList<HashMap<String, String>> header)
			throws ClientProtocolException, IOException, Exception {
		return getDataByGet(url, header, 60000);
	}

	/**
	 * HttpGet
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws Exception
	 */
	public String getDataByGet(String url) throws ClientProtocolException,
			IOException, Exception {
		return getDataByGet(url, null, 60000);
	}

	/*
	 * Http���� Header ����
	 */
	public InputStream getInputStreamByGet(String url,
			ArrayList<HashMap<String, String>> header, int timeOut)
			throws ClientProtocolException, IOException, Exception {
		HttpClient mClient = new DefaultHttpClient();
		HttpGet mHttpGet = new HttpGet(url);

		if (header != null) {
			for (HashMap<String, String> map : header) {
				mHttpGet.setHeader(map.get("Name").toString(), map.get("Value")
						.toString());
			}
		}

		mHttpGet.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut / 2);
		mHttpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				timeOut / 2);

		HttpResponse mResponse = mClient.execute(mHttpGet);

		int state = mResponse.getStatusLine().getStatusCode();
		HttpEntity mEntity = mResponse.getEntity();
		if (state == 200 && mEntity != null) {
			InputStream result = mEntity.getContent();
			mClient.getConnectionManager().shutdown();
			mHttpGet.abort();
			return result;
		}

		mClient.getConnectionManager().shutdown();
		mHttpGet.abort();
		return null;
	}

	/*
	 * Http���� �в���
	 */
	public InputStream getInputStremByGet(String url,
			ArrayList<HashMap<String, String>> header)
			throws ClientProtocolException, IOException, Exception {
		return getInputStreamByGet(url, header, 60000);
	}

	/*
	 * Http���� �޲���
	 */
	public InputStream getInputStremByGet(String url)
			throws ClientProtocolException, IOException, Exception {
		return getInputStreamByGet(url, null, 60000);
	}

	/**
	 * ����WebService ��Soap��
	 * 
	 * @param URL
	 * @param action
	 * @param values
	 * @return �����������ַ���
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String getDataBySoap(String URL, final String action,
			final List<NameValuePair> values) throws ClientProtocolException,
			IOException {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(URL);
			post.setHeader("Content-Type", "text/xml; charset=utf-8");
			post.setHeader("SOAPAction", "http://tempuri.org/" + action);

			ContentProducer mContentProducer = new ContentProducer() {

				public void writeTo(OutputStream outstream) throws IOException {
					Writer writer = new OutputStreamWriter(outstream, "UTF-8");
					writer.write(getHttpBody(action, values));
					writer.flush();
				}
			};
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 30000);
			post.setEntity(new EntityTemplate(mContentProducer));
			HttpResponse response = httpClient.execute(post);

			if (200 == response.getStatusLine().getStatusCode()
					&& response.getEntity() != null) {
				String reString = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				reString = reString.substring(reString.indexOf('&'),
						reString.lastIndexOf(';') + 1);
				reString = reString.replace("&lt;", "<");
				reString = reString.replace("&gt;", ">");
				return reString;
			}
			post.abort();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	private String getHttpBody(String action, List<NameValuePair> values) {
		StringBuilder mBuilder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		mBuilder.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		mBuilder.append("<soap:Body>");
		mBuilder.append(" <" + action + " xmlns=\"http://tempuri.org/\">");
		if (values != null) {
			for (NameValuePair map : values) {
				try {
					String nameString = "";
					String valueString = "";
					nameString = map.getName();
					valueString = map.getValue();
					mBuilder.append("<" + nameString + ">" + valueString + "</"
							+ nameString + ">");
				} catch (Exception e) {
				}
			}
		}
		mBuilder.append("</" + action + ">");
		mBuilder.append("</soap:Body>");
		mBuilder.append("</soap:Envelope>");
		return mBuilder.toString();
	}
}
