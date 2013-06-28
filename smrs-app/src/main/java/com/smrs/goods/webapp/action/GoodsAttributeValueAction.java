package com.smrs.goods.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.enums.StatusEnum;
import com.smrs.goods.model.GoodsAttributeModel;
import com.smrs.goods.model.GoodsAttributeValueModel;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;



public class GoodsAttributeValueAction extends BaseGoodsAction{
	private static final long serialVersionUID = 1L;
	protected GoodsAttributeValueModel goodsAttributeValue = new GoodsAttributeValueModel();



	protected List<GoodsAttributeModel> goodsAttributeList = new ArrayList<GoodsAttributeModel>();


	protected Pager<GoodsAttributeValueModel> pager = new Pager<GoodsAttributeValueModel>(); 
	protected String titleName="商品属性值";
	

	public String addGoodsAttributeValue(){
		goodsAttributeList=goodsAttributeService.getAllActive();
		
		if(StringUtils.isEmpty(this.actionCommand)){
			return SUCCESS;
		}
		
		if(goodsAttributeValue==null || StringUtils.isEmpty(goodsAttributeValue.getName())){
			goodsAttributeValue = new GoodsAttributeValueModel();
			return SUCCESS;
		}
		
		
		goodsAttributeValue.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, goodsAttributeValue);
		this.goodsAttributeValueService.addModel(goodsAttributeValue);
		
		this.addActionMessage("创建成功");
		return SUCCESS;
	}
	
	
	public String searchGoodsAttributeValue(){
		//goodsAttributeValueSearchModel GoodsAttributeSearchModel = new GoodsAttributeSearchModel();
		//GoodsAttributeSearchModel.setGoodsAttribute(goodsAttributeValue);		
		//GoodsAttributeSearchModel.setPager(pager);		
		goodsAttributeList=goodsAttributeService.getAllActive();
		pager=goodsAttributeValueService.getByNameLikePager(goodsAttributeValue.getName(),pager);
		return SUCCESS;
	}

	
	public String updateGoodsAttributeValue(){
		goodsAttributeList=goodsAttributeService.getAllActive();
		
		if(StringUtils.isEmpty(this.actionCommand)){
			goodsAttributeValue = goodsAttributeValueService.getByPK(goodsAttributeValue.getId());	
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, goodsAttributeValue);
			goodsAttributeValueService.updateModel(goodsAttributeValue);
			this.addActionMessage("修改成功");
		}
		return SUCCESS;
	}
	
	public String deleteGoodsAttributeValue(){
		goodsAttributeValueService.deleteModel(goodsAttributeValue);
		return "search";
	}
	

	public Pager<GoodsAttributeValueModel> getPager() {
		return pager;
	}


	public void setPager(Pager<GoodsAttributeValueModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
	public List<GoodsAttributeModel> getGoodsAttributeList() {
		return goodsAttributeList;
	}


	public void setGoodsAttributeList(List<GoodsAttributeModel> goodsAttributeList) {
		this.goodsAttributeList = goodsAttributeList;
	}

	public GoodsAttributeValueModel getGoodsAttributeValue() {
		return goodsAttributeValue;
	}


	public void setGoodsAttributeValue(GoodsAttributeValueModel goodsAttributeValue) {
		this.goodsAttributeValue = goodsAttributeValue;
	}
}
