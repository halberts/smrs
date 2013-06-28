package com.jof.framework.checkcode;

/**
 * 验证码上的文案
 * @author WangXuzheng
 *
 */
public abstract class AbstractCheckCodeTextProvider {
	/**
	 * 生成的验证码的位数
	 */
	private int size = 4;
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}

	/**
	 * 生成的验证码文案
	 * @return
	 */
	public abstract String getText();
}
