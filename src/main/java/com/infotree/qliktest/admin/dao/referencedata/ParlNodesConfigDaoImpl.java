package com.infotree.qliktest.admin.dao.referencedata;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.configs.ParallelNodes;

@Repository
public class ParlNodesConfigDaoImpl extends AbstractQTAdminDao<ParallelNodes> implements
		ParlNodesConfigDao {
	private static final Logger LOG = LoggerFactory.getLogger(ParlNodesConfigDaoImpl.class);
	
	@Override
	public ParallelNodes save(ParallelNodes t) {
		// TODO Auto-generated method stub
		return super.save(t);
	}

	@Override
	public String delete(ParallelNodes t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParallelNodes> list() {
		// TODO Auto-generated method stub
		return super.list();
	}

	@Override
	public ParallelNodes findByConfigName(String name) {
		ParallelNodes pn=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("nodeurl", name));
			pn = (ParallelNodes)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return pn;
	}
	
	@Override
	public List<ParallelNodes> getParallelNodesByCreated(String id){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("createdBy", id));
			List<ParallelNodes> list = findMany(crit);
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
	public String getParallelNodesById(String id){
		String name;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", id));
			List<ParallelNodes> prlConfigList = findMany(crit);
			name = "";
			Iterator<ParallelNodes> iterator=prlConfigList.iterator();
			while(iterator.hasNext()){
				ParallelNodes spy =iterator.next();
				name= spy.getNodeurl();
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return name;
	}
	@Override
	public ParallelNodes getById(Serializable id) {
		return super.getById(id);
	}

	@Override
	public ParallelNodes findByNameAndIdNot(String name, Integer id) {
		
		ParallelNodes pn;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("nodeurl", name));
			crit.add(Restrictions.ne("id", id));
			pn = (ParallelNodes)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return pn;
	}

	@Override
	public int updateParallelNodesConfiguration(ParallelNodes spy) {
		try {
			Session session=getSession();
			ParallelNodes objspy = getById(spy.getId());
			objspy.setNodeurl(spy.getNodeurl());
			objspy.setIpAddress(spy.getIpAddress());
			objspy.setPlatform(spy.getPlatform());
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
	public List<ParallelNodes> findByOrder() {
		Criteria crit = createBaseCriteria();
		crit.addOrder(Order.desc("id"));
		List<ParallelNodes> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
}
