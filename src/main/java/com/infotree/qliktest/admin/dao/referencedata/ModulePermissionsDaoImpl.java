package com.infotree.qliktest.admin.dao.referencedata;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;
@Repository
public class ModulePermissionsDaoImpl extends AbstractQTAdminDao<ModulePermissions> implements
		ModulePermissionsDao {
	private static final Logger LOG = LoggerFactory.getLogger(ModulePermissionsDaoImpl.class);
	@Override
	public ModulePermissions getById(Serializable id) {
		return null;
	}

	@Override
	public ModulePermissions save(ModulePermissions t) {
		return super.save(t);
	}

	@Override
	public String delete(ModulePermissions t) {
		return null;
	}

	@Override
	public List<ModulePermissions> list() {
		return super.list();
	}

	@Override
	public ModulePermissions findByModuleIdAndPermissionId(Integer moduleId,
			Integer permissionId) {
		ModulePermissions mp=null;
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("modulePermissionsComp.moduleId", moduleId);
			Criterion rhs = Restrictions.eq("modulePermissionsComp.permissionId", permissionId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			
			crit.add(exp);
			mp = (ModulePermissions)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return mp;
	}

	@Override
	public List<ModulePermissions> findByModuleId(Integer moduleId) {
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("modulePermissionsComp.moduleId", moduleId));
		List<ModulePermissions> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
		
	}

	@Override
	public int getPermissionsCountByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		int count=0;
		try {
			Criteria crit = createBaseCriteria();
			crit.setProjection(Projections.property("modulePermissionsComp.permissionId"));
			crit.add(Restrictions.eq("modulePermissionsComp.moduleId", moduleId));
			count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return count;
		
	}

	@Override
	public List<ModulePermissions> findByPermissionsId(Integer permId) {
		
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("modulePermissionsComp.permissionId", permId));
		List<ModulePermissions> list = findMany(crit);
		if(list == null || list.size() == 0){
			return null;
		}else{
			return list;
		}
		
	}

	

	

	@Override
	public int deleteByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		List<ModulePermissions> list = findByModuleId(moduleId);
		Session session = getSession();
		if(list != null){
			Iterator<ModulePermissions> iterator = list.iterator();
			while(iterator.hasNext()){
				ModulePermissions mp = iterator.next();
				session.delete(mp);
			}
		}
		return 1;
	}

}
