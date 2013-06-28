package com.jof.framework.dao.hibernate.support;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author WangXuzheng
 *
 */
public class SqlTypeConvertorFactory {
	@SuppressWarnings("rawtypes")
	private static final Map<Key,SqlTypeConvertor> MAP = new HashMap<Key,SqlTypeConvertor>();
	private static final SqlTypeConvertor<Object,Object> DEFAULT_CONVERTOR = new DefaultConvertor();
	static{
		MAP.put(new Key(BigDecimal.class,Long.class), new BigDicemalToLongConvertor());
		MAP.put(new Key(BigDecimal.class,Integer.class), new BigDicemalToIntegerConvertor());
		MAP.put(new Key(BigDecimal.class,Float.class), new BigDicemalToFloatConvertor());
		MAP.put(new Key(BigDecimal.class,Double.class), new BigDicemalToDoubleConvertor());
		MAP.put(new Key(BigDecimal.class,Byte.class), new BigDicemalToByteConvertor());
		MAP.put(new Key(BigDecimal.class,String.class), new BigDicemalToStringConvertor());
	}
	
	@SuppressWarnings("unchecked")
	public static Object convertSqlType(Object in,Class<?> outType){
		if(in == null){
			return null;
		}
		SqlTypeConvertor<Object,Object> convertor = MAP.get(new Key(in.getClass(),outType));
		if(convertor == null){
			return DEFAULT_CONVERTOR.resolve(in);
		}
		return convertor.resolve(in);
	}
	private static class DefaultConvertor implements SqlTypeConvertor<Object, Object>{
		@Override
		public Object resolve(Object in) {
			return in;
		}
	}
	private static class BigDicemalToLongConvertor implements SqlTypeConvertor<BigDecimal, Long>{
		@Override
		public Long resolve(BigDecimal in) {
			return in == null ? null : in.longValue();
		}
	}
	private static class BigDicemalToIntegerConvertor implements SqlTypeConvertor<BigDecimal, Integer>{
		@Override
		public Integer resolve(BigDecimal in) {
			return in == null ? null : in.intValue();
		}
	}
	private static class BigDicemalToFloatConvertor implements SqlTypeConvertor<BigDecimal, Float>{
		@Override
		public Float resolve(BigDecimal in) {
			return in == null ? null : in.floatValue();
		}
	}
	private static class BigDicemalToDoubleConvertor implements SqlTypeConvertor<BigDecimal, Double>{
		@Override
		public Double resolve(BigDecimal in) {
			return in == null ? null : in.doubleValue();
		}
	}
	private static class BigDicemalToStringConvertor implements SqlTypeConvertor<BigDecimal, String>{
		@Override
		public String resolve(BigDecimal in) {
			return in == null ? null : in.toString();
		}
	}
	private static class BigDicemalToByteConvertor implements SqlTypeConvertor<BigDecimal, Byte>{
		@Override
		public Byte resolve(BigDecimal in) {
			return in == null ? null : in.byteValue();
		}
	}
	
	private static class Key{
		@SuppressWarnings("unused")
		private Class<?> inType;
		@SuppressWarnings("unused")
		private Class<?> outType;
		public Key(Class<?> inType, Class<?> outType) {
			super();
			this.inType = inType;
			this.outType = outType;
		}
		
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}
	}
}
