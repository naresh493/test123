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
import com.infotree.qliktest.admin.entity.configs.EnterpriseSpy;

@Repository
public class EnterpriseSpyConfigDaoImpl extends AbstractQTAdminDao<EnterpriseSpy> implements
EnterpriseSpyConfigDao {
	private static final Logger LOG = LoggerFactory.getLogger(EnterpriseSpyConfigDaoImpl.class);
	
	@Override
	public EnterpriseSpy save(EnterpriseSpy t) {
		return super.save(t);
	}

	@Override
	public String delete(EnterpriseSpy t) {
		return null;
	}

	@Override
	public List<EnterpriseSpy> list() {
		return super.list();
	}

	@Override
	public EnterpriseSpy findByConfigName(String name) {
		EnterpriseSpy spy=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("configurationname", name));
			spy = (EnterpriseSpy)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return spy;
		
	}
	@Override
	public List<EnterpriseSpy> getEnterpriseSpyByCreated(String id){
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("createdBy", id));
		List<EnterpriseSpy> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	public String getEnterpriseSpyById(String id){
		String name=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("createdBy", id));
			List<EnterpriseSpy> entpList = findMany(crit);
			name = "";
			Iterator<EnterpriseSpy> iterator=entpList.iterator();
			while(iterator.hasNext()){
				EnterpriseSpy spy =iterator.next();
				name= spy.getConfigurationname();
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return name;
	}
	@Override
	public EnterpriseSpy getById(Serializable id) {
		return super.getById(id);
	}

	@Override
	public EnterpriseSpy findByNameAndIdNot(String name, Integer id) {
		EnterpriseSpy spy=null;
		try {
			Criteria crit = createBaseCriteria();
			Criterion namecrit = Restrictions.eq("configurationname", name);
			Criterion idcrit = Restrictions.ne("id", id);
			LogicalExpression exp = Restrictions.and(namecrit, idcrit);
			crit.add(exp);
			spy = (EnterpriseSpy)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return spy;
	}

	@Override
	public int updateEnterpriseSpyConfiguration(EnterpriseSpy spy) {
		try {
			Session session=getSession();
			EnterpriseSpy objspy = getById(spy.getId());
			objspy.setConfigurationname(spy.getConfigurationname());
			objspy.setProjectid(spy.getProjectid());
			objspy.setPath(spy.getPath());
			objspy.setElementwaittime(spy.getElementwaittime());
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
	public List<EnterpriseSpy> findByConfigType(String type) {
		// TODO Auto-generated method stub
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("typeEntpc", type));
		List<EnterpriseSpy> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	public List<EnterpriseSpy> findByOrder() {
		Criteria crit = createBaseCriteria();
		crit.addOrder(Order.desc("id"));
		List<EnterpriseSpy> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

}
