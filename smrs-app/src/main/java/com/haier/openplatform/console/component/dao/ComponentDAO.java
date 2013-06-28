package com.haier.openplatform.console.component.dao;
/**
 * 组件统计持久接口
 */
import java.util.List;

import com.haier.openplatform.console.component.module.Model;
import com.haier.openplatform.console.component.module.ModelSearchModel;
import com.haier.openplatform.console.component.module.RealModel;
import com.haier.openplatform.dao.BaseDAO;
import com.haier.openplatform.util.Pager;

public interface ComponentDAO extends BaseDAO<Model, Long>{
	/**
	 * 获取所有组件
	 */
	public Pager<Model> getComponents(ModelSearchModel modelSearchModel);
	/**
	 * 获取组件实际使用情况
	 */
	public List<RealModel> getRealModel(Long modelId);
}
