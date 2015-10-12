
package cn.wyb.sble.resources.queryword.util;

public class SafeUtil {

    /**
     * ·ÀÖ¹²ÎÊý×¢Èë£¬XSS¹¥»÷
     * @param value
     * @return
     */
    public static String safeString(String value) {
        if (value != null && value.length() > 0) {
            value = replaceAll(value, "<", "&lt;");
            value = replaceAll(value, ">", "&gt;");
        }
        return value;
    }

    private static String replaceAll(String str, String target, String replacement) {
        if (str.indexOf(target) >= 0) {
            str = str.replaceAll(target, replacement);
        }
        return str;
    }

    public static String safeEncodeString(String value) {
        if (value != null && value.indexOf("%3") > 0) {
            value = value.replaceAll("%3[c|C]", "&lt;");
            value = value.replaceAll("%3[e|E]", "&gt;");
        }
        return value;
    }
}
