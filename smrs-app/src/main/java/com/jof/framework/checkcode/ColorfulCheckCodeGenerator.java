package com.jof.framework.checkcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生产彩色图片验证码
 * @author WangXuzheng
 *
 */
public class ColorfulCheckCodeGenerator extends AbstractCheckCodeGenerator {
	/**
	 * 验证码字体
	 */
	private String fontName = null;
	/**
	 * 干扰线的条数
	 */
	private int lines = 155;
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	@Override
	public BufferedImage generateCheckCode(String checkCode) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		Random random = new Random();
		// 设定图像背景色
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);
		Font font = new Font(this.fontName, Font.BOLD, this.height-4);
		graphics.setFont(font);
		// 画边框
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, width - 1, height - 1);
		// 随机产生155条干扰线
		graphics.setColor(getRandColor(160, 200));
		for (int i = 0; i < this.lines; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			graphics.drawLine(x, y, x + xl, y + yl);
		}
		for (int i = 0; i < checkCode.length(); i++) {
			String strRand = String.valueOf(checkCode.charAt(i));
			graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			graphics.drawString(strRand, i*(this.width/checkCode.length())+2, this.height-4);

		}
		graphics.dispose();
		return bufferedImage;
	}

	private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		int resultFc = fc;
		int resultBc = bc;
		if (fc > 255) {
			resultFc = 255;
		}
		if (bc > 255) {
			resultBc = 255;
		}
		int r = fc + random.nextInt(resultBc - resultFc);
		int g = fc + random.nextInt(resultBc - resultFc);
		int b = fc + random.nextInt(resultBc - resultFc);
		return new Color(r, g, b);
	}
}
