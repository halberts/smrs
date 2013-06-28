package test.com.smrs.basicdata.service;

import javax.annotation.Resource;

import org.junit.Ignore;

import test.com.smrs.BaseTestCase;

import com.smrs.basicdata.service.StoreService;

/**
 * @author WangXuzheng
 *
 */
@Ignore
public class BaseBasicTestCase extends BaseTestCase {
	//@Resource
	//protected DepartmentService departmentService;
	@Resource 
	protected StoreService storeService;
}
