package cn.wyb.sble.resources.queryword.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * @author wangyongbing
 *
 */
public class JsonUtils {

	
	private static Gson getGson(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson g = gsonBuilder.create();
		return g;
	}
	
	/**
	 * ��jsonת��Ϊmap��value��object
	 * 
	 * @param data json����
	 * @return
	 */
	public static JsonObject  getMapFromJsonVO(String data){
		return getGson().fromJson(data, JsonObject.class);
	}
	
	/**
	 * ��jsonת��Ϊmap��value��String
	 * 
	 * @param data json����
	 * @return
	 */
	public static Map<String,String> getMapFromJsonVS(String data){
		return getGson().fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
	}
	
	/**
	 * ��json����ת��Ϊjava��
	 * @param data
	 * @param clazz
	 * @return
	 */
	public static <T> T getPogo(String data,Class<T> clazz){
		return getGson().fromJson(data,clazz);
	}
}
