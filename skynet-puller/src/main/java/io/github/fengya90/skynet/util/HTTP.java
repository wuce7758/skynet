package io.github.fengya90.skynet.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HTTP {
	
	public static byte[] doGet(String url, int timeoutSocket,
			int timeoutConnection, HashMap<String, String> head) throws Exception {
		CloseableHttpResponse httpReponse = null;
		CloseableHttpClient httpClient = null;
		byte[] ret = null;
		try {
			httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeoutSocket)
					.setConnectTimeout(timeoutConnection).build();
			HttpGet httpget = new HttpGet(url);
			if (head != null) {
				Iterator<Entry<String, String>> iter = head.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					String key = entry.getKey().toString();
					String val = entry.getValue().toString();
					httpget.setHeader(key, val);
				}
			}
			httpget.setConfig(requestConfig);
			httpReponse = httpClient.execute(httpget);
			if (isHttpOk(httpReponse)) {
				HttpEntity entity = httpReponse.getEntity();
				ret = EntityUtils.toByteArray(entity);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			httpReponse.close();
			httpClient.close();
		}
		return ret;
	}

	private static boolean isHttpOk(CloseableHttpResponse httpReponse) {
		String line = httpReponse.getStatusLine().toString();
		char c = line.split(" ")[1].charAt(0);
		if (c == '2') {
			return true;
		}
		return false;
	}
}
