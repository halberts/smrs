package com.smrs.basicdata.vo;

import com.jof.framework.util.SearchModel;
import com.smrs.basicdata.model.ChannelModel;
import com.smrs.basicdata.model.RegionModel;

public class RegionSearchModel extends SearchModel<RegionModel>{
	
	private static final long serialVersionUID = 1438306561743499006L;
	private RegionModel region = new RegionModel();
	public RegionModel getRegion() {
		return region;
	}
	public void setRegion(RegionModel region) {
		this.region = region;
	}
	

}
