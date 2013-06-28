package com.smrs.goods.webapp.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.enums.StatusEnum;
import com.smrs.goods.model.GoodsAttributeModel;
import com.smrs.goods.model.GoodsItemModel;
import com.smrs.goods.model.GoodsSkuModel;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;



public class GoodsSkuAction extends BaseGoodsAction{
	private static final long serialVersionUID = 1L;
	protected GoodsSkuModel goodsSku = new GoodsSkuModel();
	protected static final String SESSION_KEY_GOODS_ITEM="SESSION_KEY_GOODS_ITEM";
	//protected 
	protected List<GoodsSkuModel> allGoodsSkuList = new ArrayList<GoodsSkuModel>();

	protected List<GoodsItemModel> allGoodsItemList = new ArrayList<GoodsItemModel>();
	protected List<GoodsAttributeModel> allGoodsAttributeList = new ArrayList<GoodsAttributeModel>();
	protected String [] selectedAttributes;

	public List<GoodsAttributeModel> getAllGoodsAttributeList() {
		return allGoodsAttributeList;
	}


	public void setAllGoodsAttributeList(
			List<GoodsAttributeModel> allGoodsAttributeList) {
		this.allGoodsAttributeList = allGoodsAttributeList;
	}

	public String[] getSelectedAttributes() {
		return selectedAttributes;
	}


	public void setSelectedAttributes(String[] selectedAttributes) {
		this.selectedAttributes = selectedAttributes;
	}


	public List<GoodsItemModel> getAllGoodsItemList() {
		return allGoodsItemList;
	}
	
	private Long itemId;
	
	public Long getItemId() {
		return itemId;
	}


	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	protected Pager<GoodsSkuModel> pager = new Pager<GoodsSkuModel>(); 
	protected String titleName="产品信息";
	
	private HashSet<GoodsAttributeModel> getAllAttributeFromItem(GoodsItemModel itemModel){
		HashSet<GoodsAttributeModel> goodsAttributeList = new HashSet<GoodsAttributeModel>();
		Set<GoodsAttributeModel> tempSet;
		tempSet = goodsCategoryService.getAllContainAttributesByCategoryId(itemModel.getCategoryId1());
		goodsAttributeList.addAll(tempSet);			
		tempSet = goodsCategoryService.getAllContainAttributesByCategoryId(itemModel.getCategoryId2());
		goodsAttributeList.addAll(tempSet);
		tempSet = goodsCategoryService.getAllContainAttributesByCategoryId(itemModel.getCategoryId3());
		goodsAttributeList.addAll(tempSet);
		tempSet = goodsCategoryService.getAllContainAttributesByCategoryId(itemModel.getCategoryId4());
		goodsAttributeList.addAll(tempSet);
		return goodsAttributeList;
	}
	
	public String addSku(){
		supplierList = supplierService.getAllActive();
		if(StringUtils.isEmpty(this.actionCommand)){
			//allGoodsSkuList=goodsSkuService.getAllActive();
			GoodsItemModel model = goodsItemService.getByPK(itemId);
			allGoodsItemList = new ArrayList<GoodsItemModel>();
			allGoodsItemList.add(model);
			goodsSku = new GoodsSkuModel();
			goodsSku.setItemId(itemId);
			goodsSku.setItemCode(model.getCode());
			goodsSku.setBatchFlag(model.getBatchFlag());
			goodsSku.setMfgSkuCode(model.getMfgSkuCode());
			goodsSku.setSalesUnit(model.getSalesUnit());
			goodsSku.setStorageType(model.getStorageType());
			goodsSku.setValidFlag(model.getValidFlag());
			goodsSku.setSupplierId(model.getSupplierId());
			this.getSession().setAttribute(SESSION_KEY_GOODS_ITEM, model);
			allGoodsAttributeList.addAll(getAllAttributeFromItem(model));
			return SUCCESS;
		}
		goodsSku.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, goodsSku);
		this.goodsSkuService.addModel(goodsSku);		
		
		GoodsItemModel model = goodsItemService.getByPK(goodsSku.getItemId());
		allGoodsAttributeList.addAll(getAllAttributeFromItem(model));
		
		allGoodsItemList = new ArrayList<GoodsItemModel>();
		allGoodsItemList.add(model);
		
		this.addActionMessage("创建成功");
		return SUCCESS;
	}
	
	
	public String searchSku(){
	
		allGoodsSkuList=goodsSkuService.getAllActive();
		pager=goodsSkuService.getByNameLikePager(goodsSku.getName(),pager);
		return SUCCESS;
	}

	
	public String updateSku(){
		supplierList = supplierService.getAllActive();

		if(StringUtils.isEmpty(this.actionCommand)){
			allGoodsSkuList=goodsSkuService.getAllActive();
			allGoodsItemList = this.goodsItemService.getAllActive();
			goodsSku = goodsSkuService.getByPK(goodsSku.getId());	
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, goodsSku);

			goodsSkuService.updateModel(goodsSku);
			this.addActionMessage("修改成功");			
			allGoodsItemList = this.goodsItemService.getAllActive();
		}
		
		GoodsItemModel model = goodsItemService.getByPK(goodsSku.getItemId());
		allGoodsAttributeList.addAll(getAllAttributeFromItem(model));
		
		allGoodsItemList = new ArrayList<GoodsItemModel>();
		allGoodsItemList.add(model);

		return SUCCESS;
	}
	
	public String deleteSku(){
		goodsSkuService.deleteModel(goodsSku);
		return "search";
	}
	

	public Pager<GoodsSkuModel> getPager() {
		return pager;
	}


	public void setPager(Pager<GoodsSkuModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public List<GoodsSkuModel> getAllGoodsSkuList() {
		return allGoodsSkuList;
	}


	public void setAllGoodsSkuList(
			List<GoodsSkuModel> allGoodsSkuList) {
		this.allGoodsSkuList = allGoodsSkuList;
	}


	public GoodsSkuModel getGoodsSku() {
		return goodsSku;
	}


	public void setGoodsSku(GoodsSkuModel goodsSku) {
		this.goodsSku = goodsSku;
	}
}
