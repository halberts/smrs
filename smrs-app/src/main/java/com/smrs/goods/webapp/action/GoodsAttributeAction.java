package com.smrs.goods.webapp.action;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.enums.StatusEnum;
import com.smrs.goods.model.GoodsAttributeModel;
import com.smrs.goods.service.GoodsAttributeService;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;



public class GoodsAttributeAction extends BaseGoodsAction{
	private static final long serialVersionUID = 1L;
	private GoodsAttributeModel goodsAttribute = new GoodsAttributeModel();
	protected Pager<GoodsAttributeModel> pager = new Pager<GoodsAttributeModel>(); 
	protected String titleName="商品属性";
	
	





	public String addGoodsAttribute(){
		if(goodsAttribute!=null && StringUtils.isEmpty(goodsAttribute.getName())){
			return SUCCESS;
		}
		goodsAttribute.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, goodsAttribute);
		this.goodsAttributeService.addModel(goodsAttribute);
		this.addActionMessage("创建成功");
		return SUCCESS;
	}
	
	
	public String searchGoodsAttribute(){
		//goodsAttributeSearchModel GoodsAttributeSearchModel = new GoodsAttributeSearchModel();
		//GoodsAttributeSearchModel.setGoodsAttribute(goodsAttribute);		
		//GoodsAttributeSearchModel.setPager(pager);		
		
		pager=goodsAttributeService.getByNameLikePager(goodsAttribute.getName(),pager);
		return SUCCESS;
	}
	public String addGoodsAttributeInit(){

		return SUCCESS;
	}
	
	public String updateGoodsAttribute(){
		
		if(StringUtils.isEmpty(this.actionCommand)){
			goodsAttribute = goodsAttributeService.getByPK(goodsAttribute.getId());	
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, goodsAttribute);
			goodsAttributeService.updateModel(goodsAttribute);
			this.addActionMessage("修改成功");
		}
		return SUCCESS;
	}
	
	public String deleteGoodsAttribute(){
		goodsAttributeService.deleteModel(goodsAttribute);
		return "search";
	}
	
	
	public GoodsAttributeModel getGoodsAttribute() {
		return goodsAttribute;
	}

	public void setGoodsAttribute(GoodsAttributeModel GoodsAttribute) {
		this.goodsAttribute = GoodsAttribute;
	}
	
	public Pager<GoodsAttributeModel> getPager() {
		return pager;
	}


	public void setPager(Pager<GoodsAttributeModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
