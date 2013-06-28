package com.jof.framework.checkcode.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.openplatform.util.Servlets;
import com.haier.openplatform.webapp.action.BaseAppAction;
import com.jof.framework.checkcode.AbstractCheckCodeGenerator;
import com.jof.framework.checkcode.AbstractCheckCodeTextProvider;

/**
 * 用于生成验证码的action类
 * @author jonathan
 *
 */
public class CheckCodeAction extends BaseAppAction {
	/**
	 * 用于放在session中用来存储验证码的key值
	 */
	public static final String CHECKCODE_KEY = "checkCode";
	private static final long serialVersionUID = -2533697262719059413L;
	private InputStream inputStream;
	@Autowired
	private AbstractCheckCodeGenerator colorfulCheckCodeGenerator;
	@Autowired
	private AbstractCheckCodeTextProvider alphaNumericCheckCodeTextProvider;
	
	private String checkCodeKey = CHECKCODE_KEY;
	
	public void setColorfulCheckCodeGenerator(AbstractCheckCodeGenerator checkCodeGenerator) {
		this.colorfulCheckCodeGenerator = checkCodeGenerator;
	}
	public void setAlphaNumericCheckCodeTextProvider(AbstractCheckCodeTextProvider checkCodeTextProvider) {
		this.alphaNumericCheckCodeTextProvider = checkCodeTextProvider;
	}
	public void setCheckCodeKey(String checkCodeKey) {
		this.checkCodeKey = checkCodeKey;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	@Override
	public String execute() throws Exception {
		HttpServletResponse response = getResponse();
		Servlets.setDisableCacheHeader(response);
		response.setContentType("image/jpeg");
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();  
		ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);  
		final String checkCode = alphaNumericCheckCodeTextProvider.getText();
		ImageIO.write(colorfulCheckCodeGenerator.generateCheckCode(checkCode), "jpeg", imageOut);
		imageOut.close();  
		this.inputStream = new ByteArrayInputStream(output.toByteArray());
		getSession().setAttribute(checkCodeKey, checkCode);
		return SUCCESS;
	}
}
