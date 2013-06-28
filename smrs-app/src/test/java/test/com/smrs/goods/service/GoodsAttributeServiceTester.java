package test.com.smrs.goods.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.haier.openplatform.test.dbunit.annotation.IUnitDataSet;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.StoreModel;
import com.smrs.basicdata.vo.StoreSearchModel;
import com.smrs.goods.model.GoodsAttributeModel;

@IUnitDataSet
public class GoodsAttributeServiceTester extends BaseGoodsTestCase{
	
	@Test
	public void testAddGoodsAttribute(){
		GoodsAttributeModel model = new GoodsAttributeModel();
		Assert.assertNotNull(model);
		//System.out.println("city="+model.getCity());
		//model = goodsAttributeService.getByPK(99999901l);
		model.setName("name01");
		model.setCode("code01");
		goodsAttributeService.addModel(model);
		Assert.assertFalse(model.getId()==null);
		System.out.println(model.getId());
		/*
		List list = storeService.getAll();
		for(int index=0;index<list.size();index++){
			model = (StoreModel)list.get(index);
			System.out.println("city="+model.getCity());
		}
		*/
		//System.out.println("city="+model.getCity());
	}
	
	
	/*
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
	*/
}
