/**
 * @author koteswara.g
 */
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
import com.infotree.qliktest.admin.entity.referencedata.ModuleConstraints;

@Repository
public class ModuleConstraintsDaoImpl extends
		AbstractQTAdminDao<ModuleConstraints> implements ModuleConstraintsDao {
	private static final Logger LOG = LoggerFactory.getLogger(ModuleConstraintsDaoImpl.class);
	@Override
	public ModuleConstraints save(ModuleConstraints mc){
		return super.save(mc);
	}

	@Override
	public int getCountOfPackages(Integer constraintId) {
		int count=0;
		try {
			Criteria crit = createBaseCriteria();
			crit.setProjection(Projections.property("moduleConstraintComp.moduleId"));
			crit.add(Restrictions.eq("moduleConstraintComp.constraintId", constraintId));
			count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<ModuleConstraints> findByConstraintId(Integer constraintId) {
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("moduleConstraintComp.constraintId", constraintId));
		List<ModuleConstraints> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public int getCountOfConstraints(Integer moduleId) {
		int count=0;
		try {
			Criteria crit = createBaseCriteria();
			crit.setProjection(Projections.property("moduleConstraintComp.constraintId"));
			crit.add(Restrictions.eq("moduleConstraintComp.moduleId", moduleId));
			count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<ModuleConstraints> findByModuleId(Integer moduleId) {
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("moduleConstraintComp.moduleId", moduleId));
		List<ModuleConstraints> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public int deleteByModuleId(Integer moduleId) {
		try {
			Session session = getSession();
			List<ModuleConstraints> list = findByModuleId(moduleId);
			if(list != null){
				Iterator<ModuleConstraints> iterator = list.iterator();
				while(iterator.hasNext()){
					ModuleConstraints md = iterator.next();
					session.delete(md);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
}
