package com.eg.fireman.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getRandomString() {
		return getRandomString(64, false);
	}

	public static String getRandomString(int length, boolean readable) {
		// 没有易读性的字典
		String dictionaryNoRead = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		// 具有易读性的字典
		String dictionaryCanRead = "abdefghjqrtyABDEFGHJQRTY23456789";
		String dictionary;
		if (readable = true) {
			dictionary = dictionaryCanRead;
		} else {
			dictionary = dictionaryNoRead;
		}
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int nextInt = random.nextInt(dictionary.length());
			stringBuilder.append(dictionary.charAt(nextInt));
		}
		return stringBuilder.toString();
	}
}
