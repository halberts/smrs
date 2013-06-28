package test.com.smrs.basicdata.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.haier.openplatform.test.dbunit.annotation.IUnitDataSet;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.StoreModel;
import com.smrs.basicdata.vo.StoreSearchModel;

@IUnitDataSet
public class StoreServiceTester extends BaseBasicTestCase{
	
	//@Test
	public void testGetMajorStore(){
		StoreModel model = this.storeService.getMajorStore();
		Assert.assertNotNull(model);
		//System.out.println("city="+model.getCity());
		model = storeService.getByPK(99999901l);
		List list = storeService.getAll();
		for(int index=0;index<list.size();index++){
			model = (StoreModel)list.get(index);
			System.out.println("city="+model.getCity());
		}
		//System.out.println("city="+model.getCity());
	}
	
	@Test
	public void testGetByNameLikePager(){
		StoreSearchModel model = new StoreSearchModel();
		Pager pager = new Pager();
		StoreModel storeModel = new StoreModel();
		storeModel.setStoreType(1);
		model.setPager(pager);
		model.setStore(storeModel);
		storeService.getByNameLikePager(model);
	}
}
