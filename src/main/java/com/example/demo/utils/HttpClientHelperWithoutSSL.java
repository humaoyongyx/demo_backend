package com.example.demo.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientHelperWithoutSSL  {

	/** HttpClient */
	private static CloseableHttpClient httpClient;
	/** 连接超时时间(ms) */
	private static int connectTimeout = 5000;
	/** socket响应超时时间(ms) */
	private static int socketTimeout = 20000;
	/** 每个host最多可以有多少连接 */
	private static int maxConnectionsPerHost = 50;
	/** 所有Host总共连接数 */
	private static int maxTotalConnections = 1000;

	private static PoolingHttpClientConnectionManager connManager;

	private static RequestConfig requestConfig;

	private static List<Header> defaultHeaders;

	static {
		init();
	}

	@SuppressWarnings("deprecation")
	private static void init() {
		try {
			requestConfig = RequestConfig.custom()
					.setConnectTimeout(connectTimeout)
					.setSocketTimeout(socketTimeout).build();
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.setDefaultRequestConfig(requestConfig).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用GET方式请求指定URI并以字符串形式获取响应内容，响应的文本内容根据Content-Type头来解码，如果没有此Header则默认使用ISO
	 * -8859-1来解码
	 * 
	 * @param url
	 * @param params
	 * @return 响应内容
	 */
	public String getUriContentUsingGetImpl(String url,
			Map<String, Object> params) {
		return getUriRequestContent(url, true, params, null, null);
	}

	public String getUriContentUsingGetImpl(String url,
			Map<String, Object> params, List<Header> headerList) {
		return getUriRequestContent(url, true, params, headerList, null);
	}

	public String getUriContentUsingPostImpl(String url,
			Map<String, Object> params, String charset) {
		return getUriRequestContent(url, false, params, null, charset);
	}

	public String getUriContentUsingPostImpl(String url,
			Map<String, Object> params, List<Header> headerList, String charset) {
		return getUriRequestContent(url, false, params, headerList, charset);
	}

	public static String getUriContentUsingGet(String url,
			Map<String, Object> params) {
		return getUriRequestContent(url, true, params, null, null);
	}

	public static String getUriContentUsingGet(String url,
			Map<String, Object> params, List<Header> headerList) {
		return getUriRequestContent(url, true, params, headerList, null);
	}

	public static String getUriContentUsingPost(String url,
			Map<String, Object> params, String charset) {
		return getUriRequestContent(url, false, params, null, charset);
	}

	public static String getUriContentUsingPost(String url,
			Map<String, Object> params, List<Header> headerList, String charset) {
		return getUriRequestContent(url, false, params, headerList, charset);
	}



	public static String getUriContentUsingJsonPost(String url,Map<String, Object> params,String charset){
		return getUriContentUsingJsonPost(url,params,null,charset);
	}

	public static String getUriContentUsingJsonPost(String url,Map<String, Object> params,Header[] headers,String charset){
		HttpPost httpPost = new HttpPost(url);
		StringEntity request = new StringEntity(JSON.toJSONString(params),charset);//解决中文乱码问题
		request.setContentEncoding(charset);
		request.setContentType("application/json");

		httpPost.setEntity(request);
		httpPost.setHeaders(headers);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			} else {
				return charset == null ? EntityUtils.toString(entity)
						: EntityUtils.toString(entity, charset);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 以GET或POST方法请求指定URI并以字符串形式获取响应内容，响应的文本内容根据Content-Type头来解码，
	 * 如果没有此Header则默认使用ISO-8859-1来解码
	 * 
	 * @param url
	 *            请求地址
	 * @param useGet
	 *            是否使用GET方式
	 * @param params
	 *            请求参数
	 * @param headers
	 * @param charset
	 *            POST时指定的参数编码，如果为空则默认使用ISO-8859-1编码参数
	 * @return 响应内容，字符串的解码字符集首先从响应头Content-Type头获取，如果没有此Header则默认使用ISO-8859-1来解码
	 */
	public static String getUriRequestContent(String url, boolean useGet,
			Map<String, Object> params, List<Header> headers, String charset) {
		HttpUriRequest request = null;
		try {
			// GET request
			if (useGet) {
				if (params != null && params.size() > 0) {
					URIBuilder builder = new URIBuilder(url);
					for (Map.Entry<String, Object> param : params.entrySet()) {
						Object value = param.getValue();
						// 值为null时忽略参数
						if (value == null) {
							continue;
						}
						builder.addParameter(param.getKey(), value.toString());
					}
					request = new HttpGet(builder.build());
				} else {
					request = new HttpGet(url);
				}
			}
			// POST request
			else {
				request = new HttpPost(url);
				HttpPost post = (HttpPost) request;
				if (params != null && params.size() > 0) {
					List<NameValuePair> nvList = new ArrayList<NameValuePair>();
					for (Map.Entry<String, Object> param : params.entrySet()) {
						Object value = param.getValue();
						if (value == null) {
							continue;
						}
						nvList.add(new BasicNameValuePair(param.getKey(), value
								.toString()));
					}
					UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
							nvList, charset);
					post.setEntity(formEntity);
				}
			}
			// add header
			if (headers != null && headers.size() > 0) {
				for (Header header : headers) {
					request.addHeader(header);
				}
			}

			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			} else {
				return charset == null ? EntityUtils.toString(entity)
						: EntityUtils.toString(entity, charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			((HttpRequestBase) request).releaseConnection();
		}
	}

	/**
	 * 请求指定URL地址并调用提供的响应回调方法来处理响应
	 * 
	 * @param url
	 *            请求地址
	 * @param isGet
	 *            是否使用GET方式
	 * @param params
	 *            请求参数
	 * @param charset
	 *            POST时指定的参数编码，如果为空则默认使用ISO-8859-1编码参数
	 */
	public static <T> T requestUri(String url, boolean isGet,
			Map<String, Object> params, String charset,
			ResponseHandler<? extends T> responseHandler) {
		HttpUriRequest request = null;
		try {
			// GET request
			if (isGet) {
				if (params != null && params.size() > 0) {
					URIBuilder builder = new URIBuilder(url);
					for (Map.Entry<String, Object> param : params.entrySet()) {
						Object value = param.getValue();
						if (value == null) {
							continue;
						}
						builder.addParameter(param.getKey(), value.toString());
					}
					request = new HttpGet(builder.build());
				} else {
					request = new HttpGet(url);
				}
			}
			// POST request
			else {
				request = new HttpPost(url);
				HttpPost post = (HttpPost) request;
				if (params != null && params.size() > 0) {
					List<NameValuePair> nvList = new ArrayList<NameValuePair>();
					for (Map.Entry<String, Object> param : params.entrySet()) {
						Object value = param.getValue();
						if (value == null) {
							continue;
						}
						nvList.add(new BasicNameValuePair(param.getKey(), value
								.toString()));
					}
					UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
							nvList, charset);
					post.setEntity(formEntity);
				}
			}
			return httpClient.execute(request, responseHandler);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			((HttpRequestBase) request).releaseConnection();
		}
	}

	public static String execute(HttpUriRequest request) {
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			} else {
				return EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T execute(HttpUriRequest request,
			ResponseHandler<? extends T> responseHandler) {
		try {
			if (responseHandler == null) {
				throw new NullPointerException(
						"response handler can not be null");
			}
			return httpClient.execute(request, responseHandler);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void config(int connectTimeout, int socketTimeout) {
		try {
			httpClient.close();
			HttpClientHelperWithoutSSL.socketTimeout = socketTimeout;
			HttpClientHelperWithoutSSL.connectTimeout = connectTimeout;
			init();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
