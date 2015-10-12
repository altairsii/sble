package cn.wyb.sble.resources.queryword.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class EncodingUtil {

	/**
	 * 以默认的utf-8解码string
	 * @param str
	 * @return
	 */
	public static String decodeUrlEncodedString(String str,String encoding){
		try {
		    // 防止XSS攻击
			return str == null ? null : SafeUtil.safeString(URLDecoder.decode(str, encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
