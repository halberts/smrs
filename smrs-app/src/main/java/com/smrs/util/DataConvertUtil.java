package com.smrs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.util.Calendar;
import java.util.Date;

/**
 * @author mk convert clob to string
 */
public class DataConvertUtil {

	/**
	 * Clob数据转为String
	 * 
	 * @param clob
	 * @return
	 */
	public static String doClobToString(Clob clob) {
		if (null == clob) {
			return null;
		}

		Reader reader = null;
		StringBuffer clobContent = new StringBuffer();
		BufferedReader bfReader = null;
		try {
			reader = clob.getCharacterStream();

			// 得到流
			bfReader = new BufferedReader(reader);
			String lineContent = bfReader.readLine();
			while (null != lineContent) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				clobContent.append(lineContent);
				clobContent.append("\r\n");
				lineContent = bfReader.readLine();
			}
		} catch (Exception ex) {
			throw new RuntimeException("failed to read clob...", ex);
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
				if (null != bfReader) {
					bfReader.close();
				}
			} catch (IOException e) {
				/**
				 * deliberately swallow
				 */
			}
		}

		return clobContent.toString();
	}
	
	

}
