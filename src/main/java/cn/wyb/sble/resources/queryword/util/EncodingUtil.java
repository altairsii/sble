package cn.wyb.sble.resources.queryword.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class EncodingUtil {

	/**
	 * ��Ĭ�ϵ�utf-8����string
	 * @param str
	 * @return
	 */
	public static String decodeUrlEncodedString(String str,String encoding){
		try {
		    // ��ֹXSS����
			return str == null ? null : SafeUtil.safeString(URLDecoder.decode(str, encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
