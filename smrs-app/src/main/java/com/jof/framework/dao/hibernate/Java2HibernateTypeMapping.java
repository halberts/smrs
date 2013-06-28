package com.jof.framework.dao.hibernate;
import static org.hibernate.type.StandardBasicTypes.BIG_DECIMAL;
import static org.hibernate.type.StandardBasicTypes.BIG_INTEGER;
import static org.hibernate.type.StandardBasicTypes.BLOB;
import static org.hibernate.type.StandardBasicTypes.BOOLEAN;
import static org.hibernate.type.StandardBasicTypes.BYTE;
import static org.hibernate.type.StandardBasicTypes.CALENDAR;
import static org.hibernate.type.StandardBasicTypes.CHARACTER;
import static org.hibernate.type.StandardBasicTypes.CLOB;
import static org.hibernate.type.StandardBasicTypes.DATE;
import static org.hibernate.type.StandardBasicTypes.DOUBLE;
import static org.hibernate.type.StandardBasicTypes.FLOAT;
import static org.hibernate.type.StandardBasicTypes.INTEGER;
import static org.hibernate.type.StandardBasicTypes.LONG;
import static org.hibernate.type.StandardBasicTypes.SHORT;
import static org.hibernate.type.StandardBasicTypes.STRING;
import static org.hibernate.type.StandardBasicTypes.TIMESTAMP;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.Type;

/**
 * Hibernate工具类，用来存储java类型与hibernate类型之间的映射关系
 * @author WangXuzheng
 * @see org.hibernate.type.StandardBasicTypes
 */
public class Java2HibernateTypeMapping {
	private static final Map<String,JavaHibernateType> TYPE_MAP = new HashMap<String, JavaHibernateType>();
	static{
		registerHibernateType("int",INTEGER,Integer.class);
		registerHibernateType("java.lang.Integer", INTEGER,Integer.class);
		registerHibernateType("string", STRING,String.class);
		registerHibernateType("java.lang.String", STRING,String.class);
		registerHibernateType("long", LONG,Long.class);
		registerHibernateType("java.lang.Long", LONG,Long.class);
//		registerHibernateType("date", DATE,Date.class);
//		registerHibernateType("java.util.Date", DATE,Date.class);
		registerHibernateType("date", TIMESTAMP,Date.class);
		registerHibernateType("java.util.Date", TIMESTAMP,Date.class);
		registerHibernateType("sql_date", DATE, java.sql.Date.class);
		registerHibernateType("java.sql.Date", DATE, java.sql.Date.class);
		registerHibernateType("time_stamp", TIMESTAMP, Date.class);
		registerHibernateType("java.sql.TimeStamp", TIMESTAMP, Date.class);
		registerHibernateType("big_integer", BIG_INTEGER,BigInteger.class);
		registerHibernateType("java.math.BigInteger", BIG_INTEGER,BigInteger.class);
		registerHibernateType("char", CHARACTER,Character.class);
		registerHibernateType("java.lang.Character", CHARACTER,Character.class);
		registerHibernateType("byte", BYTE,Byte.class);
		registerHibernateType("java.lang.Byte", BYTE,Byte.class);
		registerHibernateType("float", FLOAT,Float.class);
		registerHibernateType("java.lang.Float", FLOAT,Float.class);
		registerHibernateType("double", DOUBLE,Double.class);
		registerHibernateType("java.lang.Double", DOUBLE,Double.class);
		registerHibernateType("boolean", BOOLEAN,Boolean.class);
		registerHibernateType("java.lang.Boolean", BOOLEAN,Boolean.class);
		registerHibernateType("big_decimal", BIG_DECIMAL,BigDecimal.class);
		registerHibernateType("java.math.BigDecimal", BIG_DECIMAL,BigDecimal.class);
		registerHibernateType("short", SHORT,Short.class);
		registerHibernateType("java.lang.Short", SHORT,Short.class);
		registerHibernateType("java.sql.Clob", CLOB, Clob.class);
		registerHibernateType("clob", CLOB,  Clob.class);
		registerHibernateType("blob", BLOB, Blob.class);
		registerHibernateType("alendar", CALENDAR, Calendar.class);
		registerHibernateType("java.util.Calendar", CALENDAR, Calendar.class);
	};
	
	/**
	 * 注册java类型与hibernate类型之间的映射关系
	 * @param javaType
	 * @param hibernateType
	 * @see org.hibernate.type.StandardBasicTypes
	 */
	public static void registerHibernateType(String javaType,Type hibernateType,Class<?> javaTypeClass){
		TYPE_MAP.put(javaType, new JavaHibernateType(hibernateType, javaTypeClass));
	}
	/**
	 * 获取映射集合
	 * @return
	 */
	public static Map<String, JavaHibernateType> getMapping(){
		return TYPE_MAP;
	}
}
