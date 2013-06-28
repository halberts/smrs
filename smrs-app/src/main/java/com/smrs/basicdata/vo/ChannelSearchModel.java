package com.smrs.basicdata.vo;

import com.jof.framework.util.SearchModel;
import com.smrs.basicdata.model.ChannelModel;
import com.smrs.security.model.GroupModel;

public class ChannelSearchModel extends SearchModel<ChannelModel>{
	
	private static final long serialVersionUID = 1438306561743499006L;
	private ChannelModel channel = new ChannelModel();
	public ChannelModel getChannel() {
		return channel;
	}
	public void setChannel(ChannelModel channel) {
		this.channel = channel;
	}

}
