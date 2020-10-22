package com.cc.manage.security;


import java.security.MessageDigest;

/**
 * @author ：ZL
 * @time ：Created in 2020/4/14 11:04
 * @description：
 */

public class PasswordHelper {
	
	/**
	 * 根据用户名与密码做md5单向hash加密，以username作为盐加入到password加密过程中
	 * @param loginName 登录名
	 * @param password 用户密码明文
	 * @return md5(username+password)
	 */
	public  String encryptPassword(String loginName, String password) {
		String inStr = loginName + password;
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++){
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	public static void main(String[] args) {
		String npwd = new PasswordHelper().encryptPassword("admin", "123456");
		System.out.println(npwd);
	}

}
