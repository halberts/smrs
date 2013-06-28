package com.haier.openplatform.console.component.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.openplatform.console.component.dao.ComponentDAO;
import com.haier.openplatform.console.component.module.Model;
import com.haier.openplatform.console.component.module.ModelSearchModel;
import com.haier.openplatform.console.component.module.RealModel;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;
import com.haier.openplatform.util.Pager;

public class ComponentDAOImpl extends BaseDAOHibernateImpl<Model,Long> implements ComponentDAO{
	public Pager<Model> getComponents(ModelSearchModel modelSearchModel){ 
		Map<String, String> values = new HashMap<String, String>();
		List<Model> list = this.findByNamedQuery("component.getComponents", values);  
		modelSearchModel.getPager().setRecords(list);
		return modelSearchModel.getPager();
	}
	public List<RealModel> getRealModel(Long modelId){
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("modelId", modelId);
		List<RealModel> list = this.findByNamedQuery("component.getRealModel", values);  
		return list;
	}
}
