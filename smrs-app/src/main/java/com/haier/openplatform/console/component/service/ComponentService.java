package com.haier.openplatform.console.component.service;

import com.haier.openplatform.console.component.module.Model;
import com.haier.openplatform.console.component.module.ModelSearchModel;
import com.haier.openplatform.util.Pager;

public interface ComponentService {
	public Pager<Model> getComponents(ModelSearchModel modelSearchModel);
}
