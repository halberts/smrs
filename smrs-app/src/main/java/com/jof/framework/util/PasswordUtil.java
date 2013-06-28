package com.jof.framework.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 密码加密类
 * @author WangXuzheng
 *
 */
public final class PasswordUtil {
	private static final String SYMBOL = "\\~\\!\\@\\#\\$\\%\\^&\\*()_\\+\\{\\}\\|\\:\\\\\"\\<\\>\\?\\-\\=\\[\\]\\;\\'\\,\\.\\/\\'";
	/**
	 * 特殊字符正则表达式
	 */
	private static final String SYMBOL_REG = ".*[" + SYMBOL +"]+.*";
	/**
	 * 大写字母至少出现一次正则表达式
	 */
	private static final String UPPER_CASE_CHARS = ".*[A-Z]+.*";
	/**
	 * 小写字母至少出现一次正则表达式
	 */
	private static final String LOWER_CASE_CHARS = ".*[a-z]+.*";
	/**
	 * 数字正则表达式
	 */
	private static final String NUMBERS = ".*[0-9]+.*";
	/**
	 * 密码校验正则表达式数组
	 */
	private static final String[] PASSWORD_REGEX = new String[] { SYMBOL_REG, UPPER_CASE_CHARS, LOWER_CASE_CHARS, NUMBERS };
	/**
	 * 最小密码长度
	 */
	private static final int MIN_PASSWORD_LENGTH = 8;

	private PasswordUtil() {
	}

	/**
	 * 密码进行加密
	 * @param password
	 * @return
	 */
	public static String encrypt(String password) {
				String temp=DigestUtils.md5Hex(password);
				return temp;
	}

	/**
	 * 判断是否合法的密码.
	 * 帐号密码至少8位，须符合大小写字母、数字、特殊字符四选三的要求
	 * @param password
	 * @return
	 */
	public static boolean isValidPassword(String password) {
		if (StringUtils.length(password) < MIN_PASSWORD_LENGTH) {
			return false;
		}
		//密码必须是特定字符
		if(!password.matches("(["+SYMBOL+"]|[a-zA-Z0-9])+")){
			return false;
		}
		Set<String> matchedRegs = new HashSet<String>();
		for (String reg : PASSWORD_REGEX) {
			if (password.matches(reg)) {
				matchedRegs.add(reg);
			}
		}
		return matchedRegs.size() >= 3;
	}
}
