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
	 * 将json转换为map，value是object
	 * 
	 * @param data json数据
	 * @return
	 */
	public static JsonObject  getMapFromJsonVO(String data){
		return getGson().fromJson(data, JsonObject.class);
	}
	
	/**
	 * 将json转换为map，value是String
	 * 
	 * @param data json数据
	 * @return
	 */
	public static Map<String,String> getMapFromJsonVS(String data){
		return getGson().fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
	}
	
	/**
	 * 将json对象转换为java类
	 * @param data
	 * @param clazz
	 * @return
	 */
	public static <T> T getPogo(String data,Class<T> clazz){
		return getGson().fromJson(data,clazz);
	}
}
