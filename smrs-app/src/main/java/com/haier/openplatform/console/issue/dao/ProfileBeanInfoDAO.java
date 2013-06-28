package com.haier.openplatform.console.issue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.openplatform.console.issue.domain.ProfileBeanInfo;
import com.haier.openplatform.dao.CommonDAO;

/**
 * @author WangXuzheng
 *
 */
public interface ProfileBeanInfoDAO extends CommonDAO<ProfileBeanInfo, Long> {
	/**
	 * 查询某个profileBeanInfo下的直接子节点元素
	 * @param parentId
	 * @return
	 */
	public List<ProfileBeanInfo> getChildProfileBeanInfoList(@Param("parentId")long parentId,@Param("tableSuffix")String tableSuffix);
	/**
	 * @param profileBeanInfo
	 * @param tableName
	 */
	public void save(@Param("profileBeanInfo")ProfileBeanInfo profileBeanInfo,@Param("tableSuffix")String tableSuffix);
}
