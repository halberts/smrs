package com.jof.framework.checkcode;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 生成字母数字混合模式的字符串
 * @author WangXuzheng
 *
 */
public class AlphaNumericCheckCodeTextProvider extends AbstractCheckCodeTextProvider {
	@Override
	public String getText() {
		return RandomStringUtils.randomAlphanumeric(this.getSize());
	}
}
