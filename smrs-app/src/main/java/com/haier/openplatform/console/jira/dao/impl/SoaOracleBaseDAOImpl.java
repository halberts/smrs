package com.haier.openplatform.console.jira.dao.impl;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.haier.openplatform.console.jira.dao.SoaOracleBaseDAO;
import com.haier.openplatform.dao.hibernate.BaseDAOHibernateImpl;

/**
 * @author ben
 */
public class SoaOracleBaseDAOImpl extends BaseDAOHibernateImpl<Object, Long> implements SoaOracleBaseDAO {
	@Override
	public int getKqDay(Map<String, String> pram) {
		// TODO Auto-generated method stub
		String sql = "select count(1) kqday  from soa.i_kq_kqresult where" +
				" (to_date(xiabantime,'HH24:mi')-to_date(shangbantime,'HH24:mi'))*24>8 and " +
				"  empsn in ('"+pram.get("empsn").replaceAll(",", "','")+"') ";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		return Integer.parseInt(map.get("KQDAY").toString());
	}
	
	@Override
	public int getQjDay(Map<String, String> pram) {
		// TODO Auto-generated method stub
		String sql = "select count(1) qjday from soa.i_aws_wbkqyc where ZTBG='请假' and ygbh in ('"+pram.get("ygbh").replaceAll(",", "','")+"') ";
		Query q = createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map = (Map<String, Object>) q.list().get(0);
		return Integer.parseInt(map.get("QJDAY").toString());
	}
}

