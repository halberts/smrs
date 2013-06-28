package com.haier.openplatform.console.issue.quartz;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.support.JdbcUtils;

import com.haier.openplatform.console.issue.util.TableSuffixGenerator;
import com.haier.openplatform.util.SpringApplicationContextHolder;

/**
 * 自动创建profile_bean_info表和profile_info表
 * @author WangXuzheng
 *
 */
public class AutoCreateProfileInfoTableJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DataSource dataSource = SpringApplicationContextHolder.getBean("dataSource");
		Connection connection = null;
		ResultSet profileInfoResultSet = null;
		ResultSet profileInfoBeanResultSet = null;
		Statement profileInfoStatement = null;
		Statement profileBeanInfoStatement = null;
		String nextMonthTableSuffix = TableSuffixGenerator.generateNextMonthDateSuffix();
		try {
			//判断表是否已经被创建成功了
			connection = dataSource.getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			
			//profile_info表
			String profileInfoTableName = "PROFILE_INFO"+nextMonthTableSuffix;
			profileInfoResultSet = databaseMetaData.getTables(null, null,profileInfoTableName ,new String[] {"TABLE"});
			if(!profileInfoResultSet.next()){
				String profileInfoTableSchema = ((String)SpringApplicationContextHolder.getBean("profileInfoSchema")).replaceAll("\r|\n", " ");
				String sql = String.format(profileInfoTableSchema, nextMonthTableSuffix,nextMonthTableSuffix);
				profileInfoStatement = connection.createStatement();
				profileInfoStatement.execute(sql);
			}
			
			//profile_bean_info表
			String profileBeanInfoTableName = "PROFILE_BEAN_INFO"+nextMonthTableSuffix;
			profileInfoBeanResultSet = databaseMetaData.getTables(null, null,profileBeanInfoTableName ,new String[] {"TABLE"});
			if(!profileInfoBeanResultSet.next()){
				String profileBeanInfoTableSchema = ((String)SpringApplicationContextHolder.getBean("profileBeanInfoSchema")).replaceAll("\r|\n", " ");
				String sql = String.format(profileBeanInfoTableSchema, nextMonthTableSuffix,nextMonthTableSuffix);
				profileBeanInfoStatement = connection.createStatement();
				profileBeanInfoStatement.execute(sql);
			}
		} catch (Exception e) {
			throw new JobExecutionException(e, true);
		}finally{
			JdbcUtils.closeResultSet(profileInfoResultSet);
			JdbcUtils.closeResultSet(profileInfoBeanResultSet);
			JdbcUtils.closeStatement(profileInfoStatement);
			JdbcUtils.closeStatement(profileBeanInfoStatement);
			JdbcUtils.closeConnection(connection);
		}
	}
}
