package com.jof.framework.webapp.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;
/**
 * Struts2的日期处理函数
 * @author jonathan
 *
 */
public class DateConverter extends StrutsTypeConverter {
	//private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
    @SuppressWarnings("rawtypes")
	@Override
    public Object convertFromString(Map ctx, String[] value, Class arg2) {
        if (StringUtils.isBlank(value[0])) {
            return null;
        }

        try {
        	return df.get().parse(value[0]);
        } catch (ParseException pe) {
            throw new TypeConversionException(pe.getMessage(), pe);
        }
    }

    @SuppressWarnings("rawtypes")
	@Override
    public String convertToString(Map ctx, Object data) {
    	return df.get().format((Date)data);
    }
} 
