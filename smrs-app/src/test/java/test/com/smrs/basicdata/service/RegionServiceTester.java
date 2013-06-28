package test.com.smrs.basicdata.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.haier.openplatform.test.dbunit.annotation.IUnitDataSet;
import com.jof.framework.util.Pager;
import com.smrs.basicdata.model.RegionModel;
import com.smrs.basicdata.service.RegionService;
import com.smrs.basicdata.vo.RegionSearchModel;

@IUnitDataSet
public class RegionServiceTester  extends BaseBasicTestCase{
	@Autowired	
	RegionService regionService ;
	@Test
	public void testGetByNameLikePager(){
		Pager<RegionModel> pager = new Pager<RegionModel>();
		RegionSearchModel searchModel = new RegionSearchModel();
		RegionModel region = new RegionModel();
		region.setName("a");
		searchModel.setRegion(region);
		searchModel.setPager(pager);
		Pager<RegionModel> tempPager=regionService.getByNameLikePager(searchModel);
		
	}
}
