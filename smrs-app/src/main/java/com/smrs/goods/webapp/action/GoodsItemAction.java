package com.smrs.goods.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jof.framework.util.Pager;
import com.smrs.enums.StatusEnum;
import com.smrs.goods.model.GoodsCategoryModel;
import com.smrs.goods.model.GoodsItemModel;
import com.smrs.goods.model.SupplierModel;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;



public class GoodsItemAction extends BaseGoodsAction{
	private static final long serialVersionUID = 1L;
	protected GoodsItemModel goodsItem = new GoodsItemModel();
	//protected 
	protected List<GoodsItemModel> allGoodsItemList = new ArrayList<GoodsItemModel>();

	protected List<GoodsCategoryModel> allGoodsCategoryList = new ArrayList<GoodsCategoryModel>();
	
	protected Pager<GoodsItemModel> pager = new Pager<GoodsItemModel>(); 
	protected String titleName="商品款式基本";

	


	public List<GoodsCategoryModel> getAllGoodsCategoryList() {
		return allGoodsCategoryList;
	}

	public String addItem(){
		
		if(StringUtils.isEmpty(this.actionCommand)){
			allGoodsItemList=goodsItemService.getAllActive();
			allGoodsCategoryList = this.goodsCategoryService.getAllActive();
			supplierList = supplierService.getAllActive();
			return SUCCESS;
		}
		
		supplierList = supplierService.getAllActive();
		goodsItem.setStatus(StatusEnum.ACTIVE.getStatus());
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, goodsItem);
		this.goodsItemService.addModel(goodsItem);
		allGoodsCategoryList = this.goodsCategoryService.getAllActive();
		this.addActionMessage("创建成功");
		return SUCCESS;
	}
	
	
	public String searchItem(){
		allGoodsItemList=goodsItemService.getAllActive();
		pager=goodsItemService.getByNameLikePager(goodsItem.getName(),pager);
		return SUCCESS;
	}

	
	public String updateItem(){
		supplierList = supplierService.getAllActive();
		if(StringUtils.isEmpty(this.actionCommand)){
			allGoodsItemList=goodsItemService.getAllActive();
			allGoodsCategoryList = this.goodsCategoryService.getAllActive();
			goodsItem = goodsItemService.getByPK(goodsItem.getId());	
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, goodsItem);
			if(goodsItem.getStatus()==null){
				goodsItem.setStatus(StatusEnum.ACTIVE.getStatus());
			}
			goodsItemService.updateModel(goodsItem);
			this.addActionMessage("修改成功");			
			allGoodsCategoryList = this.goodsCategoryService.getAllActive();
		}
		return SUCCESS;
	}
	
	public String deleteItem(){
		goodsItemService.deleteModel(goodsItem);
		return "search";
	}
	

	public Pager<GoodsItemModel> getPager() {
		return pager;
	}


	public void setPager(Pager<GoodsItemModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public List<GoodsItemModel> getAllGoodsItemList() {
		return allGoodsItemList;
	}


	public void setAllGoodsItemList(
			List<GoodsItemModel> allGoodsItemList) {
		this.allGoodsItemList = allGoodsItemList;
	}


	public GoodsItemModel getGoodsItem() {
		return goodsItem;
	}


	public void setGoodsItem(GoodsItemModel goodsItem) {
		this.goodsItem = goodsItem;
	}
}
