package com.infotree.qliktest.admin.dao.referencedata;

import java.io.Serializable;
import java.util.Iterator;
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
import com.infotree.qliktest.admin.entity.configs.BugTracking;
@Repository
public class BugTrackingConfigDaoImpl extends AbstractQTAdminDao<BugTracking>
			implements BugTrackingConfigDao {
	
	private static final Logger LOG = 
			LoggerFactory.getLogger(BugTrackingConfigDaoImpl.class);
	@Override
	public BugTracking save(BugTracking t) {
		return super.save(t);
	}

	@Override
	public String delete(BugTracking t) {
		return null;
	}

	@Override
	public List<BugTracking> list() {
		return super.list();
	}

	@Override
	public BugTracking findByConfigName(String name) {
		
			BugTracking bugTracking=null;
			try {
				Criteria crit = createBaseCriteria();
				crit.add(Restrictions.eq("configurationname", name));
				bugTracking = (BugTracking)crit.uniqueResult();
			} catch (HibernateException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
			return bugTracking;
	}
	
	@Override
	public List<BugTracking> getBugTrackingByCreated(String id){
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("created_by", id));
		List<BugTracking> configList = findMany(crit);
		if(configList != null){
			return configList;
		}else{
			return null;
		}
		
		
	}
	
	@Override
	public String getBugTrackingById(String id){
		String name=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", id));
			List<BugTracking> configList = findMany(crit);
			name = "";
			Iterator<BugTracking> iterator=configList.iterator();
			while(iterator.hasNext()){
				BugTracking spy =iterator.next();
				name= spy.getConfigurationname();
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return name;
	}
	@Override
	public BugTracking getById(Serializable id) {
		return super.getById(id);
	}

	@Override
	public BugTracking findByNameAndIdNot(String name, Integer id) {
		// TODO Auto-generated method stub
		BugTracking bugTracking=null;
		try {
			Criteria crit = createBaseCriteria();
			Criterion namecrit = Restrictions.eq("configurationname", name);
			Criterion idcrit = Restrictions.ne("id", id);
			LogicalExpression exp = Restrictions.and(namecrit, idcrit);
			crit.add(exp);
			bugTracking = (BugTracking)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return bugTracking;
	}

	@Override
	public int updateBugTrackingConfiguration(BugTracking spy) {

		try {
			Session session=getSession();
			
			BugTracking objspy = getById(spy.getId());
			objspy.setConfigurationname(spy.getConfigurationname());
			objspy.setUrl(spy.getUrl());
			objspy.setId(spy.getId());
			objspy.setModifiedDate(spy.getModifiedDate());
			objspy.setModifiedBy(spy.getModifiedBy());
			session.save(objspy);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		 return 1;
	}

	@Override
	public List<BugTracking> findByOrder() {
		Criteria crit = createBaseCriteria();	
		crit.addOrder(Order.desc("id"));
		List<BugTracking> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
}
