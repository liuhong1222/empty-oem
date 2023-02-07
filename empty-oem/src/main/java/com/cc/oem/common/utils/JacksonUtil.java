/**
 *
 */
package com.cc.oem.common.utils;

import com.cc.oem.modules.job.entity.task.StatisticsDate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author SongBang
 * jackson utils
 */
public class JacksonUtil {

	private static final Logger logger     = LoggerFactory.getLogger(JacksonUtil.class);

	//    public static ObjectMapper objectMapper;
	private static Gson         gson;
	private static JsonParser jsonParser = new JsonParser();

	/**
	 * object to string
	 * @param obj object
	 * @return String
	 * @throws Exception exception
	 */
	public static String obj2Str(Object obj) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	/**
	 * convert json string to object
	 * @param jsonStr
	 * @param clazz class
	 * @return
	 * @throws Exception
	 */
	public static <T> T str2Obj(String jsonStr,Class<T> clazz)throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, clazz);
	}


	/**
	 * convert json string to object
	 * @param jsonStr
	 * @param type TypeReference
	 * @return
	 * @throws Exception
	 */
	public static <T> T str2Obj(String jsonStr, TypeReference<T> type) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, type);
	}

	/**
	 * 把Object转换为json字符串
	 */
	public static String toJson(Object object) {
		return exec(() -> gson.toJson(object));
	}


	/**
	 * 把Object转换为json字符串
	 */
	public static String toJson(Integer[] ints) {
		return exec(() -> gson.toJson(ints));
	}

	/**
	 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 */
	public static <T> T readValue(String jsonStr, Class<T> valueType) {
		return exec(() -> gson.fromJson(jsonStr, valueType));
	}


    @Autowired
	public void setGson(final Gson gson) {
		JacksonUtil.gson = gson;
	}

	private static <T> T exec(Function<T> function) {
		try {
			return function.apply();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@FunctionalInterface
	private interface Function<T> {
		T apply();
	}
}
