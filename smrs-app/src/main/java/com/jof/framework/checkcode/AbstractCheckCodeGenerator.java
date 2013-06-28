package com.jof.framework.checkcode;

import java.awt.image.BufferedImage;

/**
 * 生成验证码图片
 * @author jonathan
 *
 */
public abstract class AbstractCheckCodeGenerator {
	/**
	 * 验证码宽度
	 */
	protected int width = 45;
	/**
	 * 验证码高度
	 */
	protected int height = 20;
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * 生成验证码算法
	 * @param checkCode 验证码文案
	 * @return
	 */
	public abstract BufferedImage generateCheckCode(String checkCode);
}
