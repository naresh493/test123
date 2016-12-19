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
import com.infotree.qliktest.admin.entity.configs.ObjectSpy;

@Repository
public class ObjectSpyConfigDaoImpl extends AbstractQTAdminDao<ObjectSpy> implements
		ObjectSpyConfigDao {
	private static final Logger LOG = LoggerFactory.getLogger(ObjectSpyConfigDaoImpl.class);
	
	@Override
	public ObjectSpy save(ObjectSpy t) {
		return super.save(t);
	}

	@Override
	public String delete(ObjectSpy t) {
		return null;
	}

	@Override
	public List<ObjectSpy> list() {
		return super.list();
	}

	@Override
	public ObjectSpy findByConfigName(String name) {
		ObjectSpy objspy=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("configurationname", name));
			objspy = (ObjectSpy)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return objspy;
		
		
	}
	@Override
	public List<ObjectSpy> getObjectspyByCreated(String id){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("createdBy", id));
			List<ObjectSpy> list = findMany(crit);
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
	public String getObjectspyById(String id){
		String name=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", id));
			List<ObjectSpy> objspyList = findMany(crit);
			name = "";
			
			Iterator<ObjectSpy> iterator=objspyList.iterator();
			while(iterator.hasNext()){
				ObjectSpy spy =iterator.next();
				name= spy.getConfigurationname();
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return name;
	}
	@Override
	public ObjectSpy getById(Serializable id) {
		return super.getById(id);
	}

	@Override
	public ObjectSpy findByNameAndIdNot(String name, Integer id) {
			
			ObjectSpy objspy=null;
			try {
				Criteria crit = createBaseCriteria();
				Criterion lhs = Restrictions.eq("configurationname", name);
				Criterion rhs = Restrictions.ne("id", id);
				LogicalExpression exp = Restrictions.and(lhs, rhs);
				crit.add(exp);
				objspy = (ObjectSpy)crit.uniqueResult();
			} catch (HibernateException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
			return objspy;
		
	}

	@Override
	public int updateObjectspyConfiguration(ObjectSpy spy) {
		
		try {
			Session session=getSession();
			ObjectSpy objspy = getById(spy.getId());
			objspy.setConfigurationname(spy.getConfigurationname());
			objspy.setProjectId(spy.getProjectId());
			objspy.setBrowser(spy.getBrowser());
			objspy.setUrl(spy.getUrl());
			objspy.setElementwaittime(spy.getElementwaittime());
			objspy.setId(spy.getId());
			if(spy.getVersion() != null) {
				objspy.setVersion(spy.getVersion());
			}
			objspy.setPortNumber(spy.getPortNumber());
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
	public List<ObjectSpy> findByOrder() {
		Criteria crit = createBaseCriteria();
		crit.addOrder(Order.desc("id"));
		List<ObjectSpy> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
}
