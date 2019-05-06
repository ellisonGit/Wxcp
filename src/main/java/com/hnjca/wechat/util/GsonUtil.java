package com.hnjca.wechat.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author duYongzhi
 * @date 2016-06-15
 * @company
 */
public class GsonUtil {

	private static Gson gson;

	public static Gson getGson() {
		if (GsonUtil.gson == null)
			GsonUtil.gson = new GsonBuilder()
					.excludeFieldsWithoutExposeAnnotation().setDateFormat(
							"yyyy-MM-dd HH:mm:ss").create();
		return GsonUtil.gson;
	}

	public static void outJson(HttpServletResponse response, String json) {
		response.setHeader("Cache-Control",
				"no-store, max-age=0, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(json);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void outListToJson(HttpServletResponse response, List<?> list) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dataList", list);
		GsonUtil.outJson(response, GsonUtil.getGson().toJson(dataMap));
	}

	public static void outListToJson(HttpServletResponse response, int count, List<?> list) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("count", count);
		dataMap.put("dataList", list);
		GsonUtil.outJson(response, GsonUtil.getGson().toJson(dataMap));
	}

	public static void outObjectToJson(HttpServletResponse response, Object object) {
		GsonUtil.outJson(response, GsonUtil.getGson().toJson(object));
	}

	public static String objectToJson(Object object) {
		return getGson().toJson(object);
	}

	@SuppressWarnings("unchecked")
	public static Object jsonToObject(String jsonStr, Class _class) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, _class);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList jsonToArrayList(String jsonStr, Type type) {
		Gson gson = new Gson();
		ArrayList list = gson.fromJson(jsonStr, type);
		return list;
	}

	/**
	 * JsonElement
	 * @param element
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList elementToArrayList(JsonElement element, Type type) {
		Gson gson = new Gson();
		ArrayList list = gson.fromJson(element, type);
		return list;
	}

}
