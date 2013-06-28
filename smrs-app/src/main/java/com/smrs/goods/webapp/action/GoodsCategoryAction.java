package com.smrs.goods.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import com.jof.framework.util.Pager;
import com.smrs.basicdata.webapp.action.KeyValueModel;
import com.smrs.enums.StatusEnum;
import com.smrs.goods.enums.CategoryLevelEnum;
import com.smrs.goods.model.GoodsAttributeModel;
import com.smrs.goods.model.GoodsCategoryModel;
import com.smrs.security.model.UserModel;
import com.smrs.util.AppUtil;
import com.smrs.util.DictConstants;



public class GoodsCategoryAction extends BaseGoodsAction{
	
	private static final long serialVersionUID = 1L;
	protected final String rootCategory="根类目";
	protected GoodsCategoryModel goodsCategory = new GoodsCategoryModel();
	//protected 
	protected List<GoodsCategoryModel> allGoodsCategoryList = new ArrayList<GoodsCategoryModel>();
	
	protected List<GoodsAttributeModel> allGoodsAttributeList = new  ArrayList<GoodsAttributeModel>();
	protected List<GoodsAttributeModel> selectedGoodsAttributeList = new  ArrayList<GoodsAttributeModel>();
	protected List<GoodsAttributeModel> heritageGoodsAttributeList  =new  ArrayList<GoodsAttributeModel>();
	protected List<GoodsAttributeModel> availableGoodsAttributeList = new ArrayList<GoodsAttributeModel>();

	protected Pager<GoodsCategoryModel> pager = new Pager<GoodsCategoryModel>(); 
	protected String titleName="类目";
	protected Long parentId;
	protected Long queryLevel;
	protected String parentCategoryId;
	
	protected String []selectedAttribute;

	public List<GoodsAttributeModel> getHeritageGoodsAttributeList() {
		return heritageGoodsAttributeList;
	}

	public List<GoodsAttributeModel> getAvailableGoodsAttributeList() {
		return availableGoodsAttributeList;
	}

	public String getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}



	public Long getQueryLevel() {
		return queryLevel;
	}

	public void setQueryLevel(Long queryLevel) {
		this.queryLevel = queryLevel;
	}

	public String[] getSelectedAttribute() {
		return selectedAttribute;
	}

	public void setSelectedAttribute(String[] selectedAttribute) {
		this.selectedAttribute = selectedAttribute;
	}

	private boolean checkIsExist(GoodsCategoryModel goodsCategory){
		List<GoodsCategoryModel> list = goodsCategoryService.getByName(goodsCategory.getName());
		if(CollectionUtils.isNotEmpty(list)){
			setPageValueList(goodsCategory);
			this.addActionError("名称"+goodsCategory.getName()+"已近存在");
			return true;
		}
		list = goodsCategoryService.getByCode(goodsCategory.getCode());
		if(CollectionUtils.isNotEmpty(list)){
			setPageValueList(goodsCategory);
			this.addActionError("编码"+goodsCategory.getCode()+"已近存在");
			return true;
		}
		return false;
	}
	
	private void setGoodsCategoryAttribute(){		
		if(selectedAttribute!=null){
			for(String lo:selectedAttribute){
				GoodsAttributeModel temp = new GoodsAttributeModel();
				temp.setId(Long.parseLong(lo));
				goodsCategory.getAttributes().add(temp);
			}
		}
	}
	
	public String addGoodsCategory(){
		
		allGoodsAttributeList = goodsAttributeService.getAllActive();
		availableGoodsAttributeList.addAll(allGoodsAttributeList);
		if(StringUtils.isEmpty(this.actionCommand)){
			setPageValueList(goodsCategory);
			return SUCCESS;
		}
		
		
		if(checkIsExist(goodsCategory)==true){
			return SUCCESS;
		}
		
		goodsCategory.setStatus(StatusEnum.ACTIVE.getStatus());
		
		UserModel userModel = this.getUserModelFromSession();
		AppUtil.setCreateUserInfo(userModel, goodsCategory);
		setGoodsCategoryAttribute();
		
		if(goodsCategory.getParent().getId().equals(0l)){
			goodsCategory.setParent(null);
		}
		
		if(goodsCategory.getParent()==null){
			goodsCategory.setLevel(CategoryLevelEnum.levelOne.getId());
		}else{
			GoodsCategoryModel	cateModel=goodsCategoryService.getByPK(goodsCategory.getParent().getId());
			Long newLevel = cateModel.getLevel()+1;
			if(newLevel>CategoryLevelEnum.getMaxLevel().getId()){
				this.addActionError("在四级类目下不能在添加新的子类目");
				goodsCategory.setLevel(cateModel.getLevel());
				return SUCCESS;
			}			
			goodsCategory.setLevel(cateModel.getLevel()+1);
		}
		
		this.goodsCategoryService.addModel(goodsCategory);
		
		//allGoodsCategoryList=goodsCategoryService.getAllActive();
		this.addActionMessage("创建成功");
		setPageValueList(goodsCategory);
		if(goodsCategory.getAttributes()!=null){
			for(GoodsAttributeModel one:goodsCategory.getAttributes()){
				GoodsAttributeModel temp= goodsAttributeService.getByPK(one.getId());
				selectedGoodsAttributeList.add(temp);	
			}
		}
		availableGoodsAttributeList.removeAll(selectedGoodsAttributeList);
		
		return SUCCESS;
	}
	

	private void setPageValueList(GoodsCategoryModel goodsCategory){
		GoodsCategoryModel temp= new GoodsCategoryModel();		
		if(goodsCategory.getParent()!=null){
			Long tempParentId= goodsCategory.getParent().getId();
			if(tempParentId!=null && !tempParentId.equals(0l)){			
				GoodsCategoryModel temp1=goodsCategoryService.getByPK(tempParentId);
				allGoodsCategoryList.add(temp1);			
				Set<GoodsAttributeModel> tempSet=goodsCategoryService.getAllContainAttributesByCategoryId(tempParentId);
				this.heritageGoodsAttributeList.addAll(tempSet);			
				GoodsCategoryModel parent= new GoodsCategoryModel();
				parent.setId(tempParentId);
				goodsCategory.setParent(parent);
				
				GoodsCategoryModel cateModel = goodsCategoryService.getByPK(tempParentId);
				Long newLevel = cateModel.getLevel()+1;
				if(newLevel>CategoryLevelEnum.getMaxLevel().getId()){
					this.addActionError("在四级类目下不能在添加新的子类目");
					goodsCategory.setLevel(cateModel.getLevel());
				}			
				goodsCategory.setLevel(newLevel);			
				availableGoodsAttributeList.removeAll(heritageGoodsAttributeList);
				
			}else{
				allGoodsCategoryList = new ArrayList<GoodsCategoryModel>();
				temp.setId(null);
				temp.setName(this.rootCategory);
				allGoodsCategoryList.add(temp);					
				goodsCategory.setLevel(CategoryLevelEnum.levelOne.getId());
			}
		}
		if(goodsCategory.getParent()==null){
			allGoodsCategoryList = new ArrayList<GoodsCategoryModel>();
			temp.setId(null);
			temp.setName(this.rootCategory);
			allGoodsCategoryList.add(temp);					
			goodsCategory.setLevel(CategoryLevelEnum.levelOne.getId());
		}
		
	}
	
	
	public String searchGoodsCategory(){
		allGoodsCategoryList=goodsCategoryService.getAllActive();
		pager=goodsCategoryService.getByNameLikePager(goodsCategory.getName(),pager);
		return SUCCESS;
	}

	
	public String updateGoodsCategory(){
		
		allGoodsAttributeList = goodsAttributeService.getAllActive();
		
		availableGoodsAttributeList.addAll(allGoodsAttributeList);
		if(StringUtils.isEmpty(this.actionCommand)){			
			goodsCategory = goodsCategoryService.getByPK(goodsCategory.getId());	
			Hibernate.initialize(goodsCategory);
			Set<GoodsAttributeModel> tempSet= goodsCategory.getAttributes();
			selectedGoodsAttributeList.addAll(tempSet);			
			availableGoodsAttributeList.removeAll(selectedGoodsAttributeList);			
			this.setPageValueList(goodsCategory);
		}else{
			UserModel userModel = this.getUserModelFromSession();
			AppUtil.setUpdateUserInfo(userModel, goodsCategory);
			if(goodsCategory.getParent()!=null){
				if(goodsCategory.getId().equals(goodsCategory.getParent().getId())){
					goodsCategory.setParent(null);
				}else if(goodsCategory.getParent().getId()==null){
					goodsCategory.setParent(null);
				}
			}
			setGoodsCategoryAttribute();
			goodsCategoryService.updateModel(goodsCategory);
			goodsCategory = goodsCategoryService.load(goodsCategory.getId());	
			if(goodsCategory.getAttributes()!=null){
				for(GoodsAttributeModel one:goodsCategory.getAttributes()){
					GoodsAttributeModel temp= goodsAttributeService.getByPK(one.getId());
					selectedGoodsAttributeList.add(temp);	
				}
			}
			//Set<GoodsAttributeModel> tempSet= goodsCategory.getAttributes();			
			Hibernate.initialize(goodsCategory.getAttributes());			
			availableGoodsAttributeList.removeAll(selectedGoodsAttributeList);
			this.addActionMessage("修改成功");
			this.setPageValueList(goodsCategory);			
		}
		return SUCCESS;
	}
	
	public String deleteGoodsCategory(){
		goodsCategoryService.deleteModel(goodsCategory);
		return "search";
	}
	
	
	public String queryGoodsCategory(){
		Long tempId=null;
		if(parentCategoryId!=null){
			String [] parentString = parentCategoryId.split(DictConstants.SEPARATE_KEY);
			tempId = Long.parseLong(parentString[0]);
		}
		List<GoodsCategoryModel> list = goodsCategoryService.getSubCategoryByParent(tempId, queryLevel);
		ArrayList<KeyValueModel> listModel = new ArrayList<KeyValueModel>();
		if(CollectionUtils.isNotEmpty(list)){
			for(GoodsCategoryModel one:list){
				KeyValueModel model = new KeyValueModel();
				model.setId(one.getIdName()+"");
				model.setName(one.getNameCode());
				listModel.add(model);
			}
			this.writeOutJson(listModel);
		}
		return null;
	}

	public Pager<GoodsCategoryModel> getPager() {
		return pager;
	}


	public void setPager(Pager<GoodsCategoryModel> pager) {
		this.pager = pager;
	}

	public String getTitleName() {
		return titleName;
	}


	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public List<GoodsCategoryModel> getAllGoodsCategoryList() {
		return allGoodsCategoryList;
	}


	public void setAllGoodsCategoryList(List<GoodsCategoryModel> allGoodsCategoryList) {
		this.allGoodsCategoryList = allGoodsCategoryList;
	}


	public GoodsCategoryModel getGoodsCategory() {
		return goodsCategory;
	}


	public void setGoodsCategory(GoodsCategoryModel goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	
	public List<GoodsAttributeModel> getAllGoodsAttributeList() {
		return allGoodsAttributeList;
	}


	public void setAllGoodsAttributeList(
			List<GoodsAttributeModel> allGoodsAttributeList) {
		this.allGoodsAttributeList = allGoodsAttributeList;
	}
	
	
	public List<GoodsAttributeModel> getSelectedGoodsAttributeList() {
		return selectedGoodsAttributeList;
	}


	public void setSelectedGoodsAttributeList(
			List<GoodsAttributeModel> selectedGoodsAttributeList) {
		this.selectedGoodsAttributeList = selectedGoodsAttributeList;
	}
	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
