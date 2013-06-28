package com.smrs.basicdata.webapp.action;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.security.LoginContextHolder;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.ChannelModel;
import com.smrs.basicdata.vo.ChannelSearchModel;
import com.smrs.enums.StatusEnum;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;



public class ChannelAction extends BaseBasicDataAction{
	private static final long serialVersionUID = 1L;
	private ChannelModel channel = new ChannelModel();
	protected Pager<ChannelModel> pager = new Pager<ChannelModel>(); 
	protected String titleName="渠道";
	
	





	public String addChannel(){
		if(channel!=null && StringUtils.isEmpty(channel.getName())){
			return SUCCESS;
		}
		channel.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, channel);
		this.channelService.addModel(channel);
		this.addActionMessage("创建成功");
		return SUCCESS;
	}
	
	
	public String searchChannel(){
		ChannelSearchModel channelSearchModel = new ChannelSearchModel();
		channelSearchModel.setChannel(channel);		
		channelSearchModel.setPager(pager);		
		pager=channelService.getByNameLikePager(channelSearchModel);
		return SUCCESS;
	}
	public String addChannelInit(){

		return SUCCESS;
	}
	
	public String updateChannel(){
		
		if(StringUtils.isEmpty(this.actionCommand)){
			channel = channelService.getByPK(channel.getId());	
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, channel);
			channelService.updateModel(channel);
			this.addActionMessage("修改成功");
		}
		return SUCCESS;
	}
	
	public String deleteChannel(){
		channelService.deleteModel(channel);
		return "search";
	}
	
	
	public ChannelModel getChannel() {
		return channel;
	}

	public void setChannel(ChannelModel channel) {
		this.channel = channel;
	}
	
	public Pager<ChannelModel> getPager() {
		return pager;
	}


	public void setPager(Pager<ChannelModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
