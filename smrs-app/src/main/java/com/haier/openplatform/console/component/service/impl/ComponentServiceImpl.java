package com.haier.openplatform.console.component.service.impl;

import java.util.List;

import com.haier.openplatform.console.component.dao.ComponentDAO;
import com.haier.openplatform.console.component.module.Model;
import com.haier.openplatform.console.component.module.ModelSearchModel;
import com.haier.openplatform.console.component.module.RealModel;
import com.haier.openplatform.console.component.service.ComponentService;
import com.haier.openplatform.util.Pager;

public class ComponentServiceImpl implements ComponentService{
	private ComponentDAO componentDAO;
	public ComponentDAO getComponentDAO() {
		return componentDAO;
	}
	public void setComponentDAO(ComponentDAO componentDAO) {
		this.componentDAO = componentDAO;
	}
	public Pager<Model> getComponents(ModelSearchModel modelSearchModel){
		Pager<Model> pagerModel =  componentDAO.getComponents(modelSearchModel);
		List<Model> listModel = pagerModel.getRecords();
		for(int i=0;i<listModel.size();i++){
			List<RealModel> listRealModel = componentDAO.getRealModel(listModel.get(i).getId());
			listModel.get(i).setListModel(listRealModel);
		}
		/*pagerModel.getRecords().clear();
		pagerModel.getRecords().addAll(listModel);*/
		return pagerModel;
	} 
}
