package com.infotree.qliktest.admin.dao.referencedata;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.RoleModule;

@Repository
public class RoleModuleDaoImpl extends AbstractQTAdminDao<RoleModule> implements
		RoleModuleDao {

	private static final Logger LOG = LoggerFactory.getLogger(RoleModuleDaoImpl.class);
	@Override
	public RoleModule save(RoleModule t) {
		// TODO Auto-generated method stub
		return super.save(t);
	}

	@Override
	public String delete(RoleModule t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleModule> list() {
		// TODO Auto-generated method stub
		return super.list();
	}

	@Override
	public List<RoleModule> findByModuleId(Integer moduleId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("roleModuleComp.moduleId", moduleId));
			List<RoleModule> roleList = findMany(crit);
			if(roleList == null || roleList.size() == 0){
				return null;
			}else{
				return roleList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public int getRoleCountByModuleId(Integer moduleId) {
		int count=0;
		try {
			Criteria crit = createBaseCriteria();
			crit.setProjection(Projections.max("roleModuleComp.roleid"));
			crit.add(Restrictions.eq("roleModuleComp.moduleId", moduleId));
			count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return count;
	}

	
	@Override
	public int deleteByModuleId(Integer moduleId) {
		try {
			List<RoleModule> list = findByModuleId(moduleId);
			Session session = getSession();
			if(list != null){
				Iterator<RoleModule> iterator = list.iterator();
				while(iterator.hasNext()){
					RoleModule roleModule = iterator.next();
					session.delete(roleModule);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

}
