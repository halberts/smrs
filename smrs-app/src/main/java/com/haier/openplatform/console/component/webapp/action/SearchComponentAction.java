package com.haier.openplatform.console.component.webapp.action;

import com.haier.openplatform.console.component.module.Model;
import com.haier.openplatform.console.component.module.ModelSearchModel;
import com.haier.openplatform.util.Pager;

public class SearchComponentAction extends BaseComponentAction{
	private static final long serialVersionUID = 3769834185920879219L;
	private Pager<Model> pager = new Pager<Model>(); 
	private Model model = new Model(); 
	public Pager<Model> getPager() {
		return pager;
	}
	public void setPager(Pager<Model> pager) {
		this.pager = pager;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	@Override
	public String execute() throws Exception {
		ModelSearchModel modelSearchModel = new ModelSearchModel();
		model.setId(1L);
		modelSearchModel.setModel(model);
		modelSearchModel.setPager(pager);
		//this.pager = groupService.getModel(modelSearchModel); 
		this.pager = super.getComponentService().getComponents(modelSearchModel);
		return SUCCESS;
	}
}
