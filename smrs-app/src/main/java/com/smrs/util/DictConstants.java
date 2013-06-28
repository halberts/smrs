package com.smrs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smrs.basicdata.enums.StoreLevelEnum;
import com.smrs.enums.GenderTypeEnum;
import com.smrs.enums.SortIndexEnum;
import com.smrs.enums.StatusEnum;
import com.smrs.goods.enums.CategoryLevelEnum;
import com.smrs.goods.enums.PhysicalTypeEnum;
import com.smrs.goods.enums.StorageTypeEnum;
import com.smrs.logtrace.enums.LogTraceTypeEnum;
import com.smrs.relationship.enums.CustomerLevelEnum;
import com.smrs.relationship.enums.CustomerTypeEnum;
import com.smrs.security.enums.ClerkTypeEnum;
import com.smrs.security.enums.UserTypeEnum;

public class DictConstants {
	public static final String SEPARATE_KEY="-";
	
	private static  DictConstants mInstance = null;
	private ArrayList<GenderTypeEnum> genderTypeList = null;
	private ArrayList<ClerkTypeEnum> clerkTypeList=null;
	private ArrayList<UserTypeEnum> userTypeList;
	protected Map<Character, String> statusMap;
	protected ArrayList<StatusEnum> statusList;
	protected ArrayList<SortIndexEnum> sortIndexList;

	protected ArrayList<StoreLevelEnum> storeLevelList;

	protected ArrayList<CategoryLevelEnum> categoryLevelList;
	protected ArrayList<LogTraceTypeEnum> logTraceTypeList = new ArrayList<LogTraceTypeEnum>();
	protected List<StorageTypeEnum> storageTypelList;
	protected List<PhysicalTypeEnum> physicalTypeList;
	protected List<CustomerTypeEnum> customerTypeList;
	
	protected List<CustomerLevelEnum> customerLevelList;
	
	
	public static DictConstants getInstance(){
		if(mInstance==null){
			mInstance = new DictConstants();
			mInstance.initialize();
		}
		return mInstance;
	}
	
	
	
	



	public List<CustomerLevelEnum> getCustomerLevelList() {
		return customerLevelList;
	}







	public void initialize(){
		//buildWarehouseLevel();
		//buildActiveFlagList();
		
		genderTypeList =new  ArrayList<GenderTypeEnum>();
		genderTypeList.add(GenderTypeEnum.MALE);
		genderTypeList.add(GenderTypeEnum.FEMALE);
		
		clerkTypeList = new ArrayList<ClerkTypeEnum>();
		clerkTypeList.add(ClerkTypeEnum.REGULAR);
		clerkTypeList.add(ClerkTypeEnum.FLOATING);

		userTypeList = new ArrayList<UserTypeEnum>();
		userTypeList.add(UserTypeEnum.NORMAL);
		userTypeList.add(UserTypeEnum.MANAGER);
		
		
		statusMap = new HashMap<Character,String>();
		statusMap.put(StatusEnum.ACTIVE.getId(),StatusEnum.ACTIVE.getName());
		statusMap.put(StatusEnum.INACTIVE.getId(),StatusEnum.INACTIVE.getName());
		statusList = new ArrayList<StatusEnum>();
		statusList.add(StatusEnum.ACTIVE);
		statusList.add(StatusEnum.INACTIVE);
		
		sortIndexList = new ArrayList<SortIndexEnum>();
		sortIndexList.add(SortIndexEnum.sort01);
		sortIndexList.add(SortIndexEnum.sort02);
		sortIndexList.add(SortIndexEnum.sort03);
		sortIndexList.add(SortIndexEnum.sort04);
		sortIndexList.add(SortIndexEnum.sort05);
		sortIndexList.add(SortIndexEnum.sort06);
		sortIndexList.add(SortIndexEnum.sort07);
		sortIndexList.add(SortIndexEnum.sort08);
		sortIndexList.add(SortIndexEnum.sort09);
		
		
		storeLevelList =  new ArrayList<StoreLevelEnum>();
		storeLevelList.add(StoreLevelEnum.A);
		storeLevelList.add(StoreLevelEnum.B);
		storeLevelList.add(StoreLevelEnum.C);
		storeLevelList.add(StoreLevelEnum.D);
		storeLevelList.add(StoreLevelEnum.E);
		
		
		 categoryLevelList = new ArrayList<CategoryLevelEnum>();
		 categoryLevelList.add(CategoryLevelEnum.levelOne);
		 categoryLevelList.add(CategoryLevelEnum.levelTwo);
		 categoryLevelList.add(CategoryLevelEnum.levelThree);
		 categoryLevelList.add(CategoryLevelEnum.levelFour);
		 
		 storageTypelList = new ArrayList<StorageTypeEnum>();
		 storageTypelList.add(StorageTypeEnum.normal);
		 storageTypelList.add(StorageTypeEnum.freeze);
		 
		 physicalTypeList = new ArrayList<PhysicalTypeEnum>();
		 physicalTypeList.add(PhysicalTypeEnum.solid);
		 physicalTypeList.add(PhysicalTypeEnum.liquid);
		 physicalTypeList.add(PhysicalTypeEnum.gas);
		 
		 customerTypeList = new ArrayList<CustomerTypeEnum>();
		 customerTypeList.add(CustomerTypeEnum.normal);
		 customerTypeList.add(CustomerTypeEnum.wholesale);
		 customerTypeList.add(CustomerTypeEnum.agent);
		
		 customerLevelList = new ArrayList<CustomerLevelEnum>();
		 customerLevelList.add(CustomerLevelEnum.A);
		 customerLevelList.add(CustomerLevelEnum.B);
		 customerLevelList.add(CustomerLevelEnum.C);
		 customerLevelList.add(CustomerLevelEnum.D);
		 customerLevelList.add(CustomerLevelEnum.E);
		 
		 logTraceTypeList = new ArrayList<LogTraceTypeEnum>();
		 logTraceTypeList.add(LogTraceTypeEnum.ALL);
		 logTraceTypeList.add(LogTraceTypeEnum.LOGIN);
		 logTraceTypeList.add(LogTraceTypeEnum.SMS);
		 logTraceTypeList.add(LogTraceTypeEnum.GOODS);
		 
	}
	
	
	public ArrayList<LogTraceTypeEnum> getLogTraceTypeList() {
		return logTraceTypeList;
	}

	public List<CustomerTypeEnum> getCustomerTypeList() {
		return customerTypeList;
	}
	
	
	public List<PhysicalTypeEnum> getPhysicalTypeList() {
		return physicalTypeList;
	}

	public List<StorageTypeEnum> getStorageTypelList() {
		return storageTypelList;
	}

	public ArrayList<CategoryLevelEnum> getCategoryLevelList() {
		return categoryLevelList;
	}
	
	public ArrayList<SortIndexEnum> getSortIndexList() {
		return sortIndexList;
	}

	public Map<Character, String> getStatusMap() {
		return statusMap;
	}

	public ArrayList<StatusEnum> getStatusList() {
		return statusList;
	}
	
	public List<UserTypeEnum> getUserTypeList() {
		return userTypeList;
	}

	public List<ClerkTypeEnum> getClerkTypeList() {		
		return clerkTypeList;
	}
	public List<GenderTypeEnum> getGenderTypeList() {
		return genderTypeList;
	}
	
	public ArrayList<StoreLevelEnum> getStoreLevelList() {
		return storeLevelList;
	}

}
