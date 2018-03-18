package com.zy.utils;

import java.util.Random;

public class Constant {

	public static final int TOKEN_LEN = 0x10;

	/**
	 * 更具mac获取token
	 * 
	 * @param Mac
	 * @return
	 */
	public static String productToken(String Mac) {

		return Mac + getRandomCharAndNumr(TOKEN_LEN);
	}

	/**
	 * 获取随机字母数字组合
	 * 
	 * @param length
	 *            字符串长�?
	 * @return
	 */
	public static String getRandomCharAndNumr(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean b = random.nextBoolean();
			if (b) { // 字符�?
				// int choice = random.nextBoolean() ? 65 : 97;
				// 取得65大写字母还是97小写字母
				str += (char) (65 + random.nextInt(26));// 取得大写字母
			} else { // 数字
				str += String.valueOf(random.nextInt(10));
			}
		}
		return str;
	}
}
