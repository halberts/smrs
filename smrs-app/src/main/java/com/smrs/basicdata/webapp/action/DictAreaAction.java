package com.smrs.basicdata.webapp.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jof.framework.util.Pager;
import com.smrs.basicdata.enums.DictTypeEnum;
import com.smrs.basicdata.model.DictAreaModel;
import com.smrs.basicdata.service.DictAreaService;

public class DictAreaAction extends BaseBasicDataAction{
	

	private static final long serialVersionUID = 1L;

	protected String titleName="省市";
	
	@Autowired
	private DictAreaService dictAreaService;
	
	private Pager<DictAreaModel> pager = new Pager<DictAreaModel>();
	
	public Pager<DictAreaModel> getPager() {
		return pager;
	}

	public void setPager(Pager<DictAreaModel> pager) {
		this.pager = pager;
	}

	public DictAreaService getDictAreaService() {
		return dictAreaService;
	}

	public void setDictAreaService(DictAreaService dictAreaService) {
		this.dictAreaService = dictAreaService;
	}

	private String provinceId;
	private String cityId;
	
	@Override
	public String getTitleName() {		
		return titleName;
	}
	
	public String queryProvinceDictArea(){
		List<DictAreaModel> areaList = dictAreaService.getByDictType(DictTypeEnum.AREA_PROVINCE,null);
        
          	List<KeyValueModel> listTemp = new ArrayList<KeyValueModel>();
        	if(areaList!=null){
	        	for(DictAreaModel area:areaList){
	        		KeyValueModel model = new KeyValueModel();
	        		model.setId(area.getDictId());
	        		model.setName(area.getName());
	        		listTemp.add(model);
	        	}
	         writeOutJson(listTemp);
	        }        	
        return null;
	}
	
	
	public String queryCityDictArea(){
		List<DictAreaModel> areaList = dictAreaService.getByDictType(DictTypeEnum.AREA_CITY,provinceId);
        
        	List<KeyValueModel> listTemp = new ArrayList<KeyValueModel>();
        	if(areaList!=null){
	        	for(DictAreaModel area:areaList){
	        		KeyValueModel model = new KeyValueModel();
	        		model.setId(area.getDictId());
	        		model.setName(area.getName());
	        		listTemp.add(model);
	        	}
	        	writeOutJson(listTemp);
	        }
 		return null;
	}
	
	public String queryZoneDictArea(){
		List<DictAreaModel> areaList = dictAreaService.getByDictType(DictTypeEnum.AREA_ZONE,cityId);
       	List<KeyValueModel> listTemp = new ArrayList<KeyValueModel>();
        if(areaList!=null){
	        	for(DictAreaModel area:areaList){
	        		KeyValueModel model = new KeyValueModel();
	        		model.setId(area.getDictId());
	        		model.setName(area.getName());
	        		listTemp.add(model);
	        	}
	        writeOutJson(listTemp);
	    }        	
		return null;
	}
	
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
}
