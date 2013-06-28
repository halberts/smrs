package com.jof.framework.dao.hibernate;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.haier.openplatform.SysException;
import com.jof.framework.dao.BaseDAO;
import com.jof.framework.dao.hibernate.support.ColumnToPropertyResultTransformer;
import com.jof.framework.dao.hibernate.support.DynamicHibernateStatementBuilder;
import com.jof.framework.dao.hibernate.support.DynamicQuery;
import com.jof.framework.dao.hibernate.support.QueryType;
import com.jof.framework.dao.hibernate.support.Return;
import com.jof.framework.dao.hibernate.support.ReturnProperty;
import com.jof.framework.dao.hibernate.support.StatementTemplate;
import com.jof.framework.enums.StatusEnum;
import com.jof.framework.util.Pager;
import com.jof.framework.util.Reflections;




/**
 * Hibernate 实现的DAO层
 * @param <T> DAO操作的对象类型
 * @param <ID> 主键类型
 * @author jonathan
 *
 */
public class SimpleHibernate3SupportDao<T,ID extends Serializable>  implements BaseDAO<T, ID>,InitializingBean{
	private static final Logger LOGER = LoggerFactory.getLogger(SimpleHibernate3SupportDao.class);
	protected SessionFactory sessionFactory;
	protected Class<T> entityClass;
	/**
	 * 模板缓存
	 */
	private HibernateTemplate hibernateTemplate = new HibernateTemplate();
	
	public HibernateTemplate getHibernateTemplate() {
		
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	protected DynamicHibernateStatementBuilder dynamicStatementBuilder;
	/**
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernate3SupportDao() {
		this.entityClass = Reflections.getSuperClassGenricType(getClass());
	}

	/**
	 * 取得sessionFactory.
	 */
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候在子类重载本函数.
	 */
	
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	public void setDynamicStatementBuilder(DynamicHibernateStatementBuilder dynamicStatementBuilder) {
		this.dynamicStatementBuilder = dynamicStatementBuilder;
	}

	/**
	 * 取得当前Session.
	 */
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
  
	/**
	 * 保存新增或修改的对象.
	 */
	@Override
	public void save(final T entity) {
		Validate.notNull(entity, "entity不能为空");
		getSession().save(entity);
		LOGER.debug("save entity: {}", entity);
	}

	/**
	 * 删除对象.
	 * 
	 * @param entity 对象必须是session中的对象或含id属性的transient对象.
	 */
	@Override
	public void delete(final T entity) {
		if(entity == null){
			return;
		}
		getSession().delete(entity);
		LOGER.debug("delete entity: {}", entity);
	}

	/**
	 * 按id删除对象.
	 */
	@Override
	public void delete(final ID id) {
		Validate.notNull(id, "id不能为空");
		delete(get(id));
		LOGER.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/**
	 * 按id获取对象.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(final ID id) {
		Validate.notNull(id, "id不能为空");
		return (T) getSession().get(entityClass, id);
	}

	/**
	 * 按id列表获取对象列表.
	 */
	public List<T> get(final Collection<ID> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/**
	 *	获取全部对象.
	 */
	@Override
	public List<T> getAll() {
		return find();
	}

	/**
	 *	获取全部对象.
	 */
	@Override
	public List<T> getAllActive() {
		//Criterion criterion = Criterion.
		Criterion criterion = Restrictions.eq("status",StatusEnum.ACTIVE.getId());
		return find(criterion);
	}
	
	/**
	 *	获取全部对象, 支持按属性行序.
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/**
	 * 按属性查找对象列表, 匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findUniqueBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return ((T) createCriteria(criterion).uniqueResult());
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByHQL(final String hql, final Object... values) {
		return createHQLQuery(hql, values).list();
	}
	
	/**
	 * 按HQL查询对象列表，并将对象封装成指定的对象
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 * @deprecated
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByHQLRowMapper(RowMapper<X> rowMapper,final String hql, final Object... values) {
		Validate.notNull(rowMapper, "rowMapper不能为空！");
		List<Object[]> result = createHQLQuery(hql, values).list();
		return buildListResultFromRowMapper(rowMapper, result);
	}

	/**
	 * @param rowMapper
	 * @param result
	 * @deprecated
	 * @return
	 */
	protected <X> List<X> buildListResultFromRowMapper(RowMapper<X> rowMapper, List<Object[]> result) {
		List<X> rs = new ArrayList<X>(result.size());
		for(Object[] obj : result){
			rs.add(rowMapper.fromColumn(obj));
		}
		return rs;
	}
	
	
	/**
	 * 按SQL查询对象列表.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	/**
	 * @param rowMapper
	 * @param sql
	 * @param values
	 * @deprecated 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findBySQLRowMapper(RowMapper<X> rowMapper,final String sql, final Object... values) {
		Validate.notNull(rowMapper, "rowMapper不能为空！");
		List<Object[]> result = createSQLQuery(sql, values).list();
		return buildListResultFromRowMapper(rowMapper, result);
	}
	
	/**
	 * 按SQL查询对象列表,并将结果集转换成指定的对象列表
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findBySQL(final String sql, final Object... values) {
		return createSQLQuery(sql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByHQL(final String hql, final Map<String, ?> values) {
		return createHQLQuery(hql, values).list();
	}
	
	/**
	 * 按HQL查询对象列表,并将结果集封装成对象列表
	 * 
	 * @param values 命名参数,按名称绑定.
	 * @deprecated
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByHQLRowMapper(RowMapper<X> rowMapper,final String hql, final Map<String, ?> values) {
		Validate.notNull(rowMapper, "rowMapper不能为空！");
		List<Object[]> result = createHQLQuery(hql, values).list();
		return buildListResultFromRowMapper(rowMapper, result);
	}
	
	/**
	 * 按SQL查询对象列表.
	 * @param sql SQL查询语句
	 * @param values 命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findBySQL(final String sql, final Map<String, ?> values) {
		return createSQLQuery(sql, values).list();
	}
	
	/**
	 * 查询在xxx.hbm.xml中配置的查询语句,返回结果类型在xml中配置
	 * @param queryName 查询的名称
	 * @param parameters 参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByNamedQuery(final String queryName, final Map<String, ?> parameters) {
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get(queryName);
		String statement = processTemplate(statementTemplate,parameters);
		Return returnMapping = statementTemplate.getDynamicQuery().getReturnMapping();
		List<ReturnProperty> returnProperties = returnMapping.getReturnProperties();
		if(returnProperties.isEmpty()){
			if(statementTemplate.getQueryType() == QueryType.HQL){
				return this.findByHQL(statement);
			}else{
				return this.findBySQL(statement);
			}
		}else{
			SQLQuery sqlQuery = createSQLQuery(statement, parameters);
			DynamicQuery dynamicQuery = statementTemplate.getDynamicQuery();
			sqlQuery.setCacheable(dynamicQuery.isCacheable());
			if(dynamicQuery.isCacheable()){
				sqlQuery.setCacheRegion(dynamicQuery.getCacheRegion());
			}
			sqlQuery.setResultTransformer(new ColumnToPropertyResultTransformer(statementTemplate.getDynamicQuery().getReturnMapping()));
			Map<String, JavaHibernateType> typeMap = Java2HibernateTypeMapping.getMapping();
			for(ReturnProperty returnProperty : dynamicQuery.getReturnMapping().getReturnProperties()){
				sqlQuery.addScalar(returnProperty.getColumn().toUpperCase(),typeMap.get(returnProperty.getType()).getHibernateType());
			}
			return sqlQuery.list();
		}
	}
	
	/**
	 * 查询在xxx.hbm.xml中配置的查询语句
	 * @param rowMapper
	 * @param queryName 查询的名称
	 * @param parameters 参数
	 * @deprecated 使用{@link #findByNamedQuery(String, Map)}方法代替
	 * @return
	 */
	public <X> List<X> findByNamedQuery(RowMapper<X> rowMapper,final String queryName, final Map<String, ?> parameters) {
		StatementTemplate statementTemplate = dynamicStatementBuilder.getTemplateCache().get(queryName);
		String statement = processTemplate(statementTemplate,parameters);
		if(statementTemplate.getQueryType() == QueryType.HQL){
			return this.findByHQLRowMapper(rowMapper,statement);
		}else{
			return this.findBySQLRowMapper(rowMapper,statement);
		}
	}
	
	/**
	 * 按SQL查询对象列表,并将结果集封装成对象列表
	 * @param sql SQL查询语句
	 * @param values 命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <X> List<X> findBySQLRowMapper(RowMapper<X> rowMapper,final String sql, final Map<String, ?> values) {
		Validate.notNull(rowMapper, "rowMapper不能为空！");
		List<Object[]> result = createSQLQuery(sql, values).list();
		return buildListResultFromRowMapper(rowMapper, result);
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByHQL(final String hql, final Object... values) {
		return (X) createHQLQuery(hql, values).uniqueResult();
	}
	
	/**
	 * 按SQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueBySQL(final String sql, final Object... values) {
		return (X) createSQLQuery(sql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByHQL(final String hql, final Map<String, ?> values) {
		return (X) createHQLQuery(hql, values).uniqueResult();
	}
	
	/**
	 * 按HQL查询唯一对象.
	 * @param sql sql语句
	 * @param values 命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueBySQL(final String sql, final Map<String, ?> values) {
		return (X) createSQLQuery(sql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecuteHQL(final String hql, final Object... values) {
		return createHQLQuery(hql, values).executeUpdate();
	}
	
	/**
	 * 执行SQL进行批量修改/删除操作.
	 * 
	 * @param sql sql语句
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 更新记录数.
	 */
	public int batchExecuteSQL(final String sql, final Object... values) {
		return createSQLQuery(sql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecuteHQL(final String hql, final Map<String, ?> values) {
		return createHQLQuery(hql, values).executeUpdate();
	}
	
	/**
	 * 执行SQL进行批量修改/删除操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecuteSQL(final String sql, final Map<String, ?> values) {
		return createSQLQuery(sql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Query createHQLQuery(final String queryString, final Object... values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * @param sqlQueryString sql语句
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Query createSQLQuery(final String sqlQueryString, final Object... values) {
		Query query = getSession().createSQLQuery(sqlQueryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public Query createHQLQuery(final String queryString, final Map<String, ?> values) {
		Query query = getSession().createQuery(queryString);
		if (values != null && values.size()>0) {
			query.setProperties(values);
		}
		return query;
	}
	
	/**
	 * 根据查询SQL与参数列表创建Query对象.
	 * 与find()函数可进行更加灵活的操作.
	 * @param queryString SQL语句
	 * @param values 命名参数,按名称绑定.
	 */
	public SQLQuery createSQLQuery(final String queryString, final Map<String, ?> values) {
		SQLQuery query = getSession().createSQLQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 * 
	 * @param criterions 数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化.
	 * 如果传入entity, 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性.
	 * 如需初始化关联属性,需执行:
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 为Query添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer.
	 * 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}

	@Override
	public void update(T object) {
		getSession().update(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T load(ID id) {
		return (T) getSession().load(this.entityClass, id);
	}
	

	
	/**
	 * 将list转化为数组
	 * @param list
	 * @return
	 */
	protected Criterion[] list2Array(List<Criterion> list){
		if(list == null){
			return new Criterion[0];
		}
		Criterion[] result = new Criterion[list.size()];
		for(int i = 0; i < list.size(); i++){
			result[i] = list.get(i);
		}
		return result;
	}

	
	public void afterPropertiesSet() throws Exception {
		hibernateTemplate.setSessionFactory(sessionFactory);
		return;
	}
	
	protected String processTemplate(StatementTemplate statementTemplate,Map<String, ?> parameters){
		StringWriter stringWriter = new StringWriter();
		try {
			statementTemplate.getTemplate().process(parameters, stringWriter);
		} catch (Exception e) {
			LOGER.error("处理DAO查询参数模板时发生错误：{}",e.toString());
			throw new SysException(e);
		}
		return stringWriter.toString();
	}
	
	public Pager<T> getByNameLikePager(String name,final Pager<?> pageRequest){
		//String name = model.getRegion().getName();
		String hsql="";
		
		if(StringUtils.isNotEmpty(name)){
		   hsql = " from "+this.entityClass.getSimpleName()+ " where name like '%"+name+"%'"; 
		}else{
			hsql = " from "+this.entityClass.getSimpleName();
		}
		Pager<T> result = Pager.cloneFromPager(pageRequest);
		Long totalCount=countHQLUniqueResule(hsql,new Object[]{});
		result.setTotalRecords(totalCount);		
		Query query = this.getSession().createQuery(hsql);
		this.setPageParameterToQuery(query, pageRequest);
		result.setRecords(query.list());
		return result;		
	}
	
	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHQLUniqueResule(final String hql, final Map<String,?> mapNameValues) {
		/*String countHql = prepareCountHQLOrSQL(hql);*/
		String countHql = "select count(*) " + removeSelect(removeOrders(hql));
		try {
			Long count = findUniqueByHQL(countHql, mapNameValues);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}
	
	protected String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	protected String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from ");
		return new StringBuilder(hql.substring(beginPos)).append(" ").toString();
	}

	
	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHQLUniqueResule(final String hql, final Object[] values) {
		/*String countHql = prepareCountHQLOrSQL(hql);*/
		String countHql = "select count(*) " + removeSelect(removeOrders(hql));
		try {
			Long count = findUniqueByHQL(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}
	
	
	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final Pager<?> pageRequest) {
		q.setFirstResult(pageRequest.getFirstResult().intValue());
		q.setMaxResults(pageRequest.getPageSize().intValue());
		return q;
	}
}
