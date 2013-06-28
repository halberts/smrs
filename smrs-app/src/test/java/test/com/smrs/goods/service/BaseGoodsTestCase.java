package test.com.smrs.goods.service;

import javax.annotation.Resource;

import org.junit.Ignore;

import test.com.smrs.BaseTestCase;

import com.smrs.basicdata.service.StoreService;
import com.smrs.goods.service.GoodsAttributeService;

/**
 * @author WangXuzheng
 *
 */
@Ignore
public class BaseGoodsTestCase extends BaseTestCase {
	//@Resource
	//protected DepartmentService departmentService;
	//@Resource 
	//protected StoreService storeService;
	
	@Resource 
	protected GoodsAttributeService goodsAttributeService;
	
}
