package com.jof.framework.webapp.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

/**
 * 默认的日期格式转化器，使用yyyy-MM-dd格式显示日期
 * @author WangXuzheng
 * @see java.text.SimpleDateFormat
 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(Map, String[], Class)
 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(Map, Object)
 */
public class DefaultDateConverter extends StrutsTypeConverter {
	private SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map ctx, String[] value, Class arg2) {
		if (StringUtils.isBlank(value[0])) {
			return null;
		}

		try {
			return defaultDateFormat.parse(value[0]);
		} catch (ParseException pe) {
			throw new TypeConversionException(pe.getMessage(), pe);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map ctx, Object data) {
		return defaultDateFormat.format((Date) data);
	}
}
