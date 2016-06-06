package com.bamboo.util;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class HttpHelper {

	public static final String TAG = HttpHelper.class.getName();

	private static AsyncHttpClient httpClient;

	public static void cancelAllRequest() {
		if (null != httpClient) {
			httpClient.cancelAllRequests(true);
		}
	}

	public static final synchronized AsyncHttpClient getHttpClient() {
		if (null == httpClient) {
			httpClient = new AsyncHttpClient();
			httpClient.setTimeout(10000);
		}
		return httpClient;
	}

	public static AsyncHttpClient get(String url,
			final TextHttpResponseHandler callBack) {
		return get(url, null, callBack);
	}

	public static AsyncHttpClient post(String url,
			final TextHttpResponseHandler callBack) {
		return post(url, null, callBack);
	}

	public static AsyncHttpClient get(String url, RequestParams params,
			final TextHttpResponseHandler callBack) {
		Log.i(TAG, "GET:" + url + '?' + params + "\n");
		final AsyncHttpClient httpClientient = getHttpClient();
		httpClientient.get(url, params, callBack);
		return httpClientient;
	}

	public static AsyncHttpClient post(final String url, RequestParams params,
			final TextHttpResponseHandler callBack) {
		Log.i(TAG, "POST:" + url + '?' + params + "\n");
		final AsyncHttpClient httpClientient = getHttpClient();
		httpClientient.post(url, params, callBack);
		return httpClientient;
	}

}
