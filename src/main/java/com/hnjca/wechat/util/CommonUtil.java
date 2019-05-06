package com.hnjca.wechat.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author ellison
 *
 */

public class CommonUtil {
	private static Properties props = new Properties();

	static {
		try {
			//play框架下要用这种方式加载
			props.load(CommonUtil.class.getClassLoader().getResourceAsStream("WConfig.properties"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {

		return props.getProperty(key);
	}


	/**
	 * 
	 * <P>标题：获取一个uuid</p>
	 * <P>公司：</P>
	 * @author
	 * @data
	 * @return
	 */
	public static String   getUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		return uuid;
	}
}
