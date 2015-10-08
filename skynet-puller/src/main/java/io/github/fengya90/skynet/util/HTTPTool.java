package io.github.fengya90.skynet.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HTTPTool {
	public static byte[] doGet(String url, int timeoutSocket,
							   int timeoutConnection,Map head)
			throws Exception {
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
				Iterator<Entry<String, String>> iter = head.entrySet()
						.iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					String key = entry.getKey().toString();
					String val = entry.getValue().toString();
					httpget.setHeader(key, val);
				}
			}

			httpget.setConfig(requestConfig);
			httpReponse = httpClient.execute(httpget);
			if (httpReponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpReponse.getEntity();
				ret = EntityUtils.toByteArray(entity);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (httpReponse != null) {
				httpReponse.close();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		}
		return ret;
	}


	public static byte[] doPost(String url, int timeoutSocket,
								 int timeoutConnection,Map head, byte[] content) throws Exception {
		byte[] ret = null;
		CloseableHttpResponse httpReponse = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();

		try{
			httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeoutSocket)
					.setConnectTimeout(timeoutConnection).build();
			HttpPost httpPost = new HttpPost(url);
			if (head != null) {
				Iterator<Entry<String, String>> iter = head.entrySet()
						.iterator();
				while (iter.hasNext()) {
					Entry<String, String> entry = iter.next();
					String key = entry.getKey().toString();
					String val = entry.getValue().toString();
					httpPost.setHeader(key, val);
				}
			}
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new ByteArrayEntity(content));
			httpReponse = httpClient.execute(httpPost);
			if (httpReponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpReponse.getEntity();
				ret = EntityUtils.toByteArray(entity);
			}
		}catch (Exception e) {
			throw e;
		}finally {
			if (httpReponse != null) {
				httpReponse.close();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		}

		return ret;
	}
}

