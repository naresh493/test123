package com.infotree.qliktest.admin.dao.referencedata;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.configs.PerformanceTesting;


@Repository
public class PerformanceConfigDaoImpl extends
		AbstractQTAdminDao<PerformanceTesting> implements PerformanceConfigDao {
	private static final Logger LOG = LoggerFactory.getLogger(PerformanceConfigDaoImpl.class);
	
	
	@Override
	public PerformanceTesting save(PerformanceTesting t) {
		return super.save(t);
	}

	@Override
	public String delete(PerformanceTesting t) {
		return null;
	}

	@Override
	public List<PerformanceTesting> list() {
		return super.list();
	}
	
	@Override
	public PerformanceTesting getById(Serializable id) {
		return super.getById(id);
	}

	@Override
	public PerformanceTesting findByName(String name) {
		PerformanceTesting pn=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("name", name));
			pn = (PerformanceTesting)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return pn;
	}

	@Override
	public PerformanceTesting findByNameAndIdNotIn(String name, Integer id) {
		PerformanceTesting pn=null;
		try {
			Criteria crit = createBaseCriteria();
			Criterion namecrit = Restrictions.eq("name", name);
			Criterion idcrit = Restrictions.ne("id", id);
			LogicalExpression exp = Restrictions.and(namecrit, idcrit);
			crit.add(exp);
			pn = (PerformanceTesting)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return pn;
	}

	@Override
	public int updatePerformanceConfig(PerformanceTesting performance) {
		try {
			Session session=getSession();
			PerformanceTesting objspy = getById(performance.getId());
			objspy.setName(performance.getName());
			objspy.setPort(performance.getPort());
			objspy.setProjid(performance.getProjid());
			objspy.setUrl(performance.getUrl());
			objspy.setWaitTime(performance.getWaitTime());
			objspy.setId(performance.getId());
			objspy.setModifiedDate(performance.getModifiedDate());
			objspy.setModifiedBy(performance.getModifiedBy());
			 session.save(objspy);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		 return 1;
		
	}

	@Override
	public List<PerformanceTesting> getByProjectId(Integer projectId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("projid", projectId));
			List<PerformanceTesting> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
			
		}
		
	}
	
	@Override
	public List<PerformanceTesting> getByOrder() {
		Criteria crit = createBaseCriteria();
		crit.addOrder(Order.desc("createdDate"));
		List<PerformanceTesting> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

}
