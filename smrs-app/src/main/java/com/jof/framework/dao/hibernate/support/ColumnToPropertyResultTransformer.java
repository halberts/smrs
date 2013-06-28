package com.jof.framework.dao.hibernate.support;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.lang.ClassUtils;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haier.openplatform.dao.hibernate.Java2HibernateTypeMapping;
import com.haier.openplatform.dao.hibernate.JavaHibernateType;

/**
 * 将hibernate的列属性自动映射为java的类属性名
 * @author WangXuzheng
 *
 */
public class ColumnToPropertyResultTransformer implements ResultTransformer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ColumnToPropertyResultTransformer.class);
	private static final long serialVersionUID = -4501332136735559350L;
	static{
		ConvertUtils.register(new DateConverter(null), Date.class);
		ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
	}
	private Return returnMapping;
	public ColumnToPropertyResultTransformer(Return returnMapping){
		this.returnMapping = returnMapping;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String,Object> resultMap = new HashMap<String,Object>(tuple.length);
		for ( int i=0; i<tuple.length; i++ ) {
			String alias = aliases[i];
			if ( alias!=null ) {
				resultMap.put(alias.toUpperCase(), tuple[i] );
			}
		}
		List<ReturnProperty> returnProperties = returnMapping.getReturnProperties();
		Object returnValue = org.springframework.beans.BeanUtils.instantiate(returnMapping.getClazz());
		Map<String, JavaHibernateType> typeMap = Java2HibernateTypeMapping.getMapping();
		for(ReturnProperty returnProperty : returnProperties){
			Object value = resultMap.get(returnProperty.getColumn().toUpperCase());
			Class<?> targetClass = null;
			try {
				targetClass = typeMap.get(returnProperty.getType())==null? ClassUtils.getClass(returnProperty.getType()):typeMap.get(returnProperty.getType()).getJavaTypeClass();
			} catch (ClassNotFoundException e) {
				LOGGER.error(e.toString());
				throw new RuntimeException(e);
			}
			Object convertedValue = SqlTypeConvertorFactory.convertSqlType(value, targetClass);
			try {
				instantiateNestedProperties(returnValue,returnProperty.getName());
				BeanUtils.setProperty(returnValue, returnProperty.getName(), convertedValue);
			} catch (Exception e) {
				LOGGER.error(e.toString());
				throw new RuntimeException(e);
			}
		}
		return returnValue;
	}

	private void instantiateNestedProperties(Object obj, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		String[] fieldNames = fieldName.split("\\.");
        if (fieldNames.length > 1) {
            StringBuffer nestedProperty = new StringBuffer();
            for (int i = 0; i < fieldNames.length - 1; i++) {
                String fn = fieldNames[i];
                if (i != 0) {
                    nestedProperty.append(".");
                }
                nestedProperty.append(fn);
                Object value = PropertyUtils.getProperty(obj, nestedProperty.toString());
                if (value == null) {
                    PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(obj, nestedProperty.toString());
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    Object newInstance = propertyType.newInstance();
                    PropertyUtils.setProperty(obj, nestedProperty.toString(), newInstance);
                }
            }
        }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return collection;
	}

}
