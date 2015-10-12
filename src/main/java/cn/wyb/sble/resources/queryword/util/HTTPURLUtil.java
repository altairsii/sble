package cn.wyb.sble.resources.queryword.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.wyb.sble.resources.queryword.constant.QueryWordConstant;

public class HTTPURLUtil {

	Logger logger = LoggerFactory.getLogger(getClass());

	private static class DefaultTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	}


	/**
	 * ִ��HTTP POST����
	 * 
	 * @param url
	 *            �����ַ
	 * @param params
	 *            �������
	 * @return ��Ӧ�ַ���
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params,
			int connectTimeout, int readTimeout) throws IOException {
		return doPost(url, params, QueryWordConstant.CHARSET_UTF8, connectTimeout, readTimeout);
	}

	/**
	 * ִ��HTTP POST����
	 * 
	 * @param url
	 *            �����ַ
	 * @param params
	 *            �������
	 * @param charset
	 *            �ַ�������UTF-8, GBK, GB2312
	 * @return ��Ӧ�ַ���
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params,
			String charset, int connectTimeout, int readTimeout)
			throws IOException {
		String ctype = "application/x-www-form-urlencoded;charset=" + charset;
		String query = buildQuery(params, charset);
		byte[] content = {};
		if (query != null) {
			content = query.getBytes(charset);
		}
		return doPost(url, ctype, content, connectTimeout, readTimeout);
	}

	/**
	 * ִ��HTTP POST����
	 * 
	 * @param url
	 *            �����ַ
	 * @param ctype
	 *            ��������
	 * @param content
	 *            �����ֽ�����
	 * @return ��Ӧ�ַ���
	 * @throws IOException
	 */
	public static String doPost(String url, String ctype, byte[] content,
			int connectTimeout, int readTimeout) throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try {
				conn = getConnection(new URL(url), QueryWordConstant.POST, ctype);
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
				throw e;
			}
			try {
				out = conn.getOutputStream();
				out.write(content);
				rsp = getResponseAsString(conn);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
				throw e;
			}

		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	/**
	 * ִ�д��ļ��ϴ���HTTP POST����
	 * 
	 * @param url
	 *            �����ַ
	 * @param textParams
	 *            �ı��������
	 * @param fileParams
	 *            �ļ��������
	 * @return ��Ӧ�ַ���
	 * @throws IOException
	 */
//	public static String doPost(String url, Map<String, String> params,
//			Map<String, FileItem> fileParams, int connectTimeout,
//			int readTimeout) throws IOException {
//		if (fileParams == null || fileParams.isEmpty()) {
//			return doPost(url, params, QueryWordConstant.CHARSET_UTF8, connectTimeout,
//					readTimeout);
//		} else {
//			return doPost(url, params, fileParams, QueryWordConstant.CHARSET_UTF8,
//					connectTimeout, readTimeout);
//		}
//	}

	/**
	 * ִ�д��ļ��ϴ���HTTP POST����
	 * 
	 * @param url
	 *            �����ַ
	 * @param textParams
	 *            �ı��������
	 * @param fileParams
	 *            �ļ��������
	 * @param charset
	 *            �ַ�������UTF-8, GBK, GB2312
	 * @return ��Ӧ�ַ���
	 * @throws IOException
	 */
//	public static String doPost(String url, Map<String, String> params,
//			Map<String, FileItem> fileParams, String charset,
//			int connectTimeout, int readTimeout) throws IOException {
//		if (fileParams == null || fileParams.isEmpty()) {
//			return doPost(url, params, charset, connectTimeout, readTimeout);
//		}
//
//		String boundary = System.currentTimeMillis() + ""; // ����ָ���
//		HttpURLConnection conn = null;
//		OutputStream out = null;
//		String rsp = null;
//		try {
//			try {
//				String ctype = "multipart/form-data;boundary=" + boundary
//						+ ";charset=" + charset;
//				conn = getConnection(new URL(url), QueryWordConstant.POST, ctype);
//				conn.setConnectTimeout(connectTimeout);
//				conn.setReadTimeout(readTimeout);
//			} catch (IOException e) {
//				Map<String, String> map = getParamsFromUrl(url);
//				throw e;
//			}
//
//			try {
//				out = conn.getOutputStream();
//
//				byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n")
//						.getBytes(charset);
//
//				// ��װ�ı��������
//				Set<Entry<String, String>> textEntrySet = params.entrySet();
//				for (Entry<String, String> textEntry : textEntrySet) {
//					byte[] textBytes = getTextEntry(textEntry.getKey(),
//							textEntry.getValue(), charset);
//					out.write(entryBoundaryBytes);
//					out.write(textBytes);
//				}
//
//				// ��װ�ļ��������
//				Set<Entry<String, FileItem>> fileEntrySet = fileParams
//						.entrySet();
//				for (Entry<String, FileItem> fileEntry : fileEntrySet) {
//					FileItem fileItem = fileEntry.getValue();
//					byte[] fileBytes = getFileEntry(fileEntry.getKey(),
//							fileItem.getFileName(), fileItem.getMimeType(),
//							charset);
//					out.write(entryBoundaryBytes);
//					out.write(fileBytes);
//					out.write(fileItem.getContent());
//				}
//
//				// �������������־
//				byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n")
//						.getBytes(charset);
//				out.write(endBoundaryBytes);
//				rsp = getResponseAsString(conn);
//			} catch (IOException e) {
//				Map<String, String> map = getParamsFromUrl(url);
//				VfinanceLogger.logCommError(e, conn, map.get("app_key"),
//						map.get("method"), params);
//				throw e;
//			}
//
//		} finally {
//			if (out != null) {
//				out.close();
//			}
//			if (conn != null) {
//				conn.disconnect();
//			}
//		}
//
//		return rsp;
//	}

	private static byte[] getTextEntry(String fieldName, String fieldValue,
			String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private static byte[] getFileEntry(String fieldName, String fileName,
			String mimeType, String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}

	/**
	 * ִ��HTTP GET����
	 * 
	 * @param url
	 *            �����ַ
	 * @param params
	 *            �������
	 * @return ��Ӧ�ַ���
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params)
			throws IOException {
		return doGet(url, params, QueryWordConstant.CHARSET_UTF8);
	}

	/**
	 * ִ��HTTP GET����
	 * 
	 * @param url
	 *            �����ַ
	 * @param params
	 *            �������
	 * @param charset
	 *            �ַ�������UTF-8, GBK, GB2312
	 * @return ��Ӧ�ַ���
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params,
			String charset) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;

		try {
			String ctype = "application/x-www-form-urlencoded;charset="
					+ charset;
			String query = buildQuery(params, charset);
			try {
				conn = getConnection(buildGetUrl(url, query), QueryWordConstant.GET, ctype);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
				throw e;
			}

			try {
				rsp = getResponseAsString(conn);
			} catch (IOException e) {
				Map<String, String> map = getParamsFromUrl(url);
				throw e;
			}

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	private static HttpURLConnection getConnection(URL url, String method,
			String ctype) throws IOException {
		HttpURLConnection conn = null;
		if ("https".equals(url.getProtocol())) {
			SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0],
						new TrustManager[] { new DefaultTrustManager() },
						new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url
					.openConnection();
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return false;// Ĭ����֤��ͨ��������֤��У�顣
				}
			});
			conn = connHttps;
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}

		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "aop-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);
		return conn;
	}

	private static URL buildGetUrl(String strUrl, String query)
			throws IOException {
		URL url = new URL(strUrl);
		if (StringUtils.isEmpty(query)) {
			return url;
		}

		if (StringUtils.isEmpty(url.getQuery())) {
			if (strUrl.endsWith("?")) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "?" + query;
			}
		} else {
			if (strUrl.endsWith("&")) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "&" + query;
			}
		}

		return new URL(strUrl);
	}

	public static String buildQuery(Map<String, String> params, String charset)
			throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			// ���Բ����������ֵΪ�յĲ���
			if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=")
						.append(URLEncoder.encode(value, charset));
			}
		}

		return query.toString();
	}

	protected static String getResponseAsString(HttpURLConnection conn)
			throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsString(conn.getInputStream(), charset);
		} else {
			String msg = getStreamAsString(es, charset);
			if (StringUtils.isEmpty(msg)) {
				throw new IOException(conn.getResponseCode() + ":"
						+ conn.getResponseMessage());
			} else {
				throw new IOException(msg);
			}
		}
	}

	private static String getStreamAsString(InputStream stream, String charset)
			throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	private static String getResponseCharset(String ctype) {
		String charset = QueryWordConstant.CHARSET_UTF8;

		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

	/**
	 * ʹ��Ĭ�ϵ�UTF-8�ַ����������������ֵ��
	 * 
	 * @param value
	 *            ����ֵ
	 * @return �������Ĳ���ֵ
	 */
	public static String decode(String value) {
		return decode(value, QueryWordConstant.CHARSET_UTF8);
	}

	/**
	 * ʹ��Ĭ�ϵ�UTF-8�ַ��������������ֵ��
	 * 
	 * @param value
	 *            ����ֵ
	 * @return �����Ĳ���ֵ
	 */
	public static String encode(String value) {
		return encode(value, QueryWordConstant.CHARSET_UTF8);
	}

	/**
	 * ʹ��ָ�����ַ����������������ֵ��
	 * 
	 * @param value
	 *            ����ֵ
	 * @param charset
	 *            �ַ���
	 * @return �������Ĳ���ֵ
	 */
	public static String decode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLDecoder.decode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * ʹ��ָ�����ַ��������������ֵ��
	 * 
	 * @param value
	 *            ����ֵ
	 * @param charset
	 *            �ַ���
	 * @return �����Ĳ���ֵ
	 */
	public static String encode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLEncoder.encode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	private static Map<String, String> getParamsFromUrl(String url) {
		Map<String, String> map = null;
		if (url != null && url.indexOf('?') != -1) {
			map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
		}
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return map;
	}

	/**
	 * ��URL����ȡ���еĲ�����
	 * 
	 * @param query
	 *            URL��ַ
	 * @return ����ӳ��
	 */
	public static Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split("&");
		if (pairs != null && pairs.length > 0) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if (param != null && param.length == 2) {
					result.put(param[0], param[1]);
				}
			}
		}

		return result;
	}

}