package com.ys.module.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private final static Pattern phone = Pattern.compile("^-?[0-9]+");
	/**
	 * 判断是否为空
	 */
	public static boolean isEmpty(CharSequence input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	/**
	 * s是否为手机号码
	 */
	public static boolean isPhone(CharSequence phoneNum) {
		if (isEmpty(phoneNum))
			return false;
		return phone.matcher(phoneNum).matches();
	}
	/**
	 *是否为邮箱
	 */
	public static boolean isEmail(CharSequence email) {
		if (isEmpty(email))
			return false;
		return emailer.matcher(email).matches();
	}
	/**
	 *是否为纯数字
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	/**
	 * String 转int类型
	 * @param str 字符串
	 * @param defValue 默认值
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static String getID(String idNumber) {
		if (idNumber == null) {
			return "";
		}
		if (idNumber.length() == 15) {
			String temp1 = idNumber.substring(0, 8);
			String temp2 = idNumber.substring(12, idNumber.length());
			return temp1 + "****" + temp2;
		}
		if (idNumber.length() == 18) {
			String temp1 = idNumber.substring(0, 10);
			String temp2 = idNumber.substring(14, idNumber.length());
			return temp1 + "****" + temp2;
		}
		return "";
	}
	/**
	 *
	 */
	public static String nullTanst(String value){
		if(StringUtils.isEmpty(value)){
			return "";
		}
		return value;
	}
	public static String nullTanst(Integer value){
		if(null==value){
			return "";
		}
		return Integer.valueOf(value).intValue()+"";
	}
	public static String nullTanst(Long value){
		if(null==value){
			return "";
		}
		return Long.valueOf(value).intValue()+"";
	}
}
