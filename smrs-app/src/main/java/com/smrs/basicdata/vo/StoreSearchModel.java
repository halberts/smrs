package com.smrs.basicdata.vo;

import com.jof.framework.util.SearchModel;
import com.smrs.basicdata.model.StoreModel;

public class StoreSearchModel  extends SearchModel<StoreModel>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StoreModel store;

	public StoreModel getStore() {
		return store;
	}

	public void setStore(StoreModel store) {
		this.store = store;
	}

}
