package com.smrs.basicdata.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jof.framework.dao.hibernate.BaseDAOHibernateImpl;
import com.smrs.basicdata.dao.DictAreaDao;
import com.smrs.basicdata.enums.DictTypeEnum;
import com.smrs.basicdata.model.DictAreaModel;
import com.smrs.service.BaseService;

@Component(value="dictAreaService")
public class DictAreaService extends  BaseService<DictAreaModel, String>{
	private Logger logger = LoggerFactory.getLogger(DictAreaService.class);
	
	@Autowired
	private DictAreaDao dictAreaDao;
	
	public  List<DictAreaModel> getByDictType(DictTypeEnum dictType,String parentId) {
		
		List<DictAreaModel> list = dictAreaDao.getByDictType(dictType,parentId);
		logger.info("dictType="+ dictType +" result=" +list);
		return list;
	}
	
	@Override
	public BaseDAOHibernateImpl<DictAreaModel, String> getPerformDao() {
		
		return dictAreaDao;
	}
	
}
