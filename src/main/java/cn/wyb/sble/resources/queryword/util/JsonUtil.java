package cn.wyb.sble.resources.queryword.util;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import cn.wyb.sble.resources.queryword.constant.QueryWordConstant;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


/************************************************
 * Copyright (c)  by goldensoft
 * All right reserved.
 * Create Date: 2009-8-15
 * Create Author: liurong
 * File Name:  josn工具
 * Last version:  1.0
 * Function:这里写注释
 * Last Update Date:
 * Change Log:
**************************************************/ 
 
public class JsonUtil {
	private static JsonUtil allJsonUtil;
	private static JsonUtil notNullJsonUtil;
	private static JsonUtil notDefJsonUtil;
	private static JsonUtil notEmpJsonUtil;
    public static JsonUtil getAllJsonUtil() {
		return allJsonUtil;
	}
	public static void setAllJsonUtil(JsonUtil allJsonUtil) {
		JsonUtil.allJsonUtil = allJsonUtil;
	}
	public static JsonUtil getNotNullJsonUtil() {
		return notNullJsonUtil;
	}
	public static void setNotNullJsonUtil(JsonUtil notNullJsonUtil) {
		JsonUtil.notNullJsonUtil = notNullJsonUtil;
	}
	public static JsonUtil getNotDefJsonUtil() {
		return notDefJsonUtil;
	}
	public static void setNotDefJsonUtil(JsonUtil notDefJsonUtil) {
		JsonUtil.notDefJsonUtil = notDefJsonUtil;
	}
	public static JsonUtil getNotEmpJsonUtil() {
		return notEmpJsonUtil;
	}
	public static void setNotEmpJsonUtil(JsonUtil notEmpJsonUtil) {
		JsonUtil.notEmpJsonUtil = notEmpJsonUtil;
	}
	private ObjectMapper mapper;
	
    public ObjectMapper getMapper() {
        return mapper;
    }
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    public JsonUtil(Include include){
        mapper = new ObjectMapper(); 
        mapper.setSerializationInclusion(include); 
       //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性 
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
        //禁止使用int代表Enum的order()來反序列化Enum,非常危險 
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true); 
        setDateFormat(QueryWordConstant.DATE_TIME_FORMAT);
         
    }
    /**
     * 创建输出全部属性
     * @return
     */
    public static JsonUtil buildNormalBinder(){
    	synchronized (JsonUtil.class) {
			if(JsonUtil.allJsonUtil ==null){
				JsonUtil.allJsonUtil=new JsonUtil(Include.ALWAYS);
			}
		}
        return JsonUtil.allJsonUtil;
    }
    /**
     * 创建只输出非空属性的
     * @return
     */
    public static JsonUtil buildNonNullBinder(){
        synchronized (JsonUtil.class) {
			if(JsonUtil.notNullJsonUtil ==null){
				JsonUtil.notNullJsonUtil=new JsonUtil(Include.NON_NULL);
			}
		}
        return JsonUtil.notNullJsonUtil;
    }
    /**
     * 创建只输出初始值被改变的属性
     * @return
     */
    public static JsonUtil buildNonDefaultBinder(){
        synchronized (JsonUtil.class) {
			if(JsonUtil.notDefJsonUtil ==null){
				JsonUtil.notDefJsonUtil=new JsonUtil(Include.NON_DEFAULT);
			}
		}
        return JsonUtil.notDefJsonUtil;
    }
    /**
     * 创建只输出初始值被改变的属性
     * @return
     */
    public static JsonUtil buildNonEmptyBinder(){
        synchronized (JsonUtil.class) {
			if(JsonUtil.notEmpJsonUtil ==null){
				JsonUtil.notEmpJsonUtil=new JsonUtil(Include.NON_EMPTY);
			}
		}
        return JsonUtil.notEmpJsonUtil;
    }

    /**
     * 把json字符串转成对象
     * @param json
     * @param clazz
     * @return
     */
    public <T> T getJsonToObject(String json,Class<T> clazz){
        T object=null;
        if(!StringUtils.isEmpty(json)) {
            try {
                object=getMapper().readValue(json, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把JSON转成list
     * @param json [{"...":"...","...":"...","...":"..."}] 
     * @param clazz map
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    public Object getJsonToList(String json,Class clazz){
        Object object=null;
        if(!StringUtils.isEmpty(json)) {
           try {
                object=getMapper().readValue(json,TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    
    /**
     * 把JSON转成list中是MAP《String，clazz》
     * @param json
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    public Object getJsonToListByMap(String json,Class clazz){
        Object object=null;
        if(!StringUtils.isEmpty(json)) {
           try {
                object=getMapper().readValue(json,TypeFactory.defaultInstance().constructArrayType(TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, clazz)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    
    @SuppressWarnings({ "rawtypes" })
    public Object[] getJsonToArray(String json,Class clazz){
    	Object[] object=null;
        if(!StringUtils.isEmpty(json)) {
           try {
                object=getMapper().readValue(json,TypeFactory.defaultInstance().constructArrayType(clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    public byte[] getJsonTobyteArray(String json){
    	byte[] object=null;
        if(!StringUtils.isEmpty(json)) {
           try {
                object=getMapper().readValue(json,TypeFactory.defaultInstance().constructArrayType(byte.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把JSON转成Map
     * @param json
     * @param keyclazz
     * @param valueclazz
     * @return
     */
    @SuppressWarnings({ "rawtypes"})
    public Object getJsonToMap(String json,Class keyclazz,Class valueclazz){
        Object object=null;
        if(!StringUtils.isEmpty(json)) {
            try {
                object=getMapper().readValue(json,TypeFactory.defaultInstance().constructParametricType(HashMap.class, keyclazz,valueclazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把JSON转成Map<> value是MAP类型(KEY string )
     * @param json
     * @param keyclazz
     * @param valueclazz MAP中的value CLASS
     * @return
     */
    @SuppressWarnings("rawtypes")
	public Object getJsonToMapByMap(String json,Class keyclazz,Class valueclazz){
        Object object=null;
        if(!StringUtils.isEmpty(json)) {
            try {
                object=getMapper().readValue(json,TypeFactory.defaultInstance().constructMapType(HashMap.class, TypeFactory.defaultInstance().uncheckedSimpleType(keyclazz), TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class,valueclazz)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把JSON转成Map<> value是LIST类型
     * @param json
     * @param keyclazz
     * @param valueclazz LIST中的CLASS
     * @return
     */
    @SuppressWarnings("rawtypes")
	public Object getJsonToMapByList(String json,Class keyclazz,Class valueclazz){
        Object object=null;
        if(!StringUtils.isEmpty(json)) {
            try {
                object=getMapper().readValue(json,TypeFactory.defaultInstance().constructMapType(HashMap.class, TypeFactory.defaultInstance().uncheckedSimpleType(keyclazz), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, valueclazz)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * 把json格式数据装成map
     * @param str
     * @return
     */
    public static Map<String,String> getJsonToMap(String str){
        Map<String,String> map = new HashMap<String, String>();
        if(!StringUtils.isEmpty(str)){
            String[] s=str.split(",");
            if(s.length>0){
                for (int i = 0; i < s.length; i++) {
                    String con=s[i];
                    int s1=con.indexOf(":");
                    if(s1>0){
                        map.put(con.substring(0,s1).trim().replace("\"", ""), con.substring(s1+1).replace("\"", ""));
                    }else{
                        map.put(con.trim().replace("\"", ""), "");
                    }
                }
            }
        }
        return map;
    }

    /**
     * 把map转成combo数据格式的json格式
     * @return String (json)
     */
    public String getMapToJson(Map<String,String> map) {
        List<String[]> list =new ArrayList<String[]>();
        if (null != map && !map.isEmpty()) {
            for (String key : map.keySet()) {
                String[] strS = new String[2];
                strS[0] = key;
                strS[1] = map.get(key);
                list.add(strS);
            }
        }
        return jsonObject(list);
    }

    /**
     * 把对象转成json格式
     * @param obj 需要转的对象
     * @return String
     */
    @SuppressWarnings("rawtypes")
	public String jsonObject(List list) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen;
        try {
            gen = new JsonFactory().createGenerator(sw);
            getMapper().writeValue(gen, list);
            gen.close();
        } catch (Exception e) {
            
        }
        return sw.toString();
    }
    
    /**
     * 把JSON转成Object
     * @param json
     * @param keyclazz
     * @param valueclazz
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    public Object getJsonToObject(String json,Class objclazz,Class ...pclazz){
        Object object=null;
        if(!StringUtils.isEmpty(json)) {
            try {
                object=getMapper().readValue(json,TypeFactory.defaultInstance().constructParametricType(objclazz, pclazz));
            } catch (Exception e) {
            }
        }
        return object;
    }
    
    /**
     * 把JSON转成Object
     * @param <T>
     * @param json
     * @param keyclazz
     * @param valueclazz
     * @return
     */
    public <T> Object getJsonToObject(String json,TypeReference<T> typeReference){
        Object object=null;
        if(!StringUtils.isEmpty(json)) {
            try {
                object=getMapper().readValue(json, typeReference);
            } catch (Exception e) {
            }
        }
        return object;
    }
    /**
     * 把对象转成字符串
     * @param object
     * @return
     */
    public String toJson(Object object){
        String json=null;
        try {
            json=getMapper().writeValueAsString(object);
        }  catch (Exception e) {
        	e.printStackTrace();
        }
        return json;
    }
    /**
     * 设置日期格式
     * @param pattern
     */
    public void setDateFormat(String pattern){
        if(!StringUtils.isEmpty(pattern)){
            DateFormat df=new SimpleDateFormat(pattern);
            getMapper().setDateFormat(df);
        }
    }
    
    @SuppressWarnings("unchecked")
	public static Object getResultObject(String json) {
    	Map<String, Object> map = (Map<String, Object>) buildNormalBinder().getJsonToMap(json, String.class, Object.class);
    	if (map != null ) {
    		return map.get("result");
    	}else {
    		return json;
    	}
    }
    
    public static void main(String[] args){
    }
}