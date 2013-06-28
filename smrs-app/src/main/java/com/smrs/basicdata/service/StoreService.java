package com.smrs.basicdata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jof.framework.util.PageInfo;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.dao.StoreDao;
import com.smrs.basicdata.enums.StoreTypeEnum;
import com.smrs.basicdata.model.StoreModel;
import com.smrs.basicdata.vo.StoreSearchModel;
import com.smrs.service.BaseService;
@Service(value="warehouseService")
public class StoreService extends BaseService<StoreModel,Long>{
	@Autowired
	private StoreDao storeDao;
	
	public List<StoreModel> getAll(){
		List<StoreModel> list = storeDao.getAll();
		return list;
	}
	
	public List<StoreModel> findByName(String name){
		List<StoreModel> list = storeDao.findByWarehouseName(name);
		return list;
	}
	
	public List<StoreModel> getRegionStoreList(){
		List<StoreModel> list = storeDao.getStoreByStoreType(StoreTypeEnum.region);
		return list;
	}
	
	public List<StoreSearchModel> findByNameLike(String name,PageInfo pageInfo){
		List<StoreSearchModel> listVO  = new ArrayList<StoreSearchModel>();
		List<StoreModel> list = storeDao.findByStoreModelNameLike(name,pageInfo);
		if(CollectionUtils.isNotEmpty(list)){
			for(int index=0;index<list.size();index++){
				StoreModel oneModel = list.get(index);
				StoreSearchModel vo = new StoreSearchModel();
				BeanUtils.copyProperties(oneModel, vo);
				/*StoreModel parentModel = (StoreModel)warehouseDao.get(StoreModel.class, new Integer(oneModel.getParentId()));
				
				if(parentModel!=null){
					WarehouseVO parentVO = new WarehouseVO();
					parentVO.setName(parentModel.getName());
					//parentVO.setDescription(parentModel.getDescription());
					parentVO.setId(parentModel.getId());
					vo.setParentModel(parentVO);					
				}
				String levelName = DictionaryUtil.getInstance().getLevelName(vo.getLevel()+"");
				vo.setLevelName(levelName);
				listVO.add(vo);
				*/
			}
		}
		return listVO;
	}

	
	public List<StoreModel> getMajorStoreList(){
		List<StoreModel> model = storeDao.getMajorStore();
		return model;
	}

	public List<StoreModel> getChilden(Long id){
		List<StoreModel> model = storeDao.getChilden(id);
		return model;
	}
	
	public StoreModel getMajorStore(){
		List<StoreModel> list = storeDao.getMajorStore();
		StoreModel model =list.get(0);		
		return model;
	}
	
	public StoreDao getPerformDao(){
		return storeDao;
	}
	
	public Pager<StoreModel> getByNameLikePager(final StoreSearchModel model){
		String name = model.getStore().getName();
		model.getStore();
		StoreModel store = model.getStore();
		HashMap<String,Integer> nameValues = new HashMap<String,Integer>();
		if(store.getStoreType()!=0){
			nameValues.put("storeType",store.getStoreType());
		}
		
		Pager<StoreModel> pager = storeDao.getByNameLikePager(name,model.getPager(),nameValues); 
		
		return pager;
		//return null;
	}
	
	public Pager<StoreModel> getByNameLikeAndTypePager(final StoreSearchModel model,StoreTypeEnum storeType){
		String name = model.getStore().getName();
		Pager<StoreModel> pager = storeDao.getByNameLikeAndTypePager(name,model.getPager(),storeType); 
		return pager;		
	}
	
} 
