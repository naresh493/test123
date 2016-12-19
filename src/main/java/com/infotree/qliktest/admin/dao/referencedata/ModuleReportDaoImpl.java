/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.ModuleReportsAssign;

@Repository
public class ModuleReportDaoImpl  extends AbstractQTAdminDao<ModuleReportsAssign> implements ModuleReportDao {

	private static final Logger LOG = LoggerFactory.getLogger(ModuleReportDaoImpl.class);
	@Override
	public List<ModuleReportsAssign> findByIds(Integer moduleId,
			Integer reportId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("moduleReportComp.moduleId", moduleId);
			Criterion rhs = Restrictions.eq("moduleReportComp.reportId", reportId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			List<ModuleReportsAssign> list = findMany(crit);
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
	public List<ModuleReportsAssign> getByReportId(Integer reportId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("moduleReportComp.reportId", reportId));
			List<ModuleReportsAssign> list = findMany(crit);
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
	public List<ModuleReportsAssign> getByModuleId(Integer moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(Module.class);
			subCrit.add(Restrictions.eq("id", moduleId));
			subCrit.setProjection(Projections.property("id"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("moduleReportComp.moduleId").in(subCrit));
			List<ModuleReportsAssign> list = findMany(crit);
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
	public ModuleReportsAssign save(ModuleReportsAssign entity) {
		// TODO Auto-generated method stub
		return super.save(entity);
		
		
	}

	@Override
	public int insertNewModuleReportEntity(
			ModuleReportsAssign moduleReportsAssign) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public int getReportCountByModuleId(Integer moduleId) {
		int count=0;
		try {
			Criteria crit = createBaseCriteria();
			crit.setProjection(Projections.max("moduleReportComp.reportId"));
			crit.add(Restrictions.eq("moduleReportComp.moduleId", moduleId));
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
			Session session = getSession();
			List<ModuleReportsAssign> moduleReport = getByModuleId(moduleId);
			if(moduleReport != null){
				Iterator<ModuleReportsAssign> iterator = moduleReport.iterator();
				while(iterator.hasNext()){
					ModuleReportsAssign mp = iterator.next();
					session.delete(mp);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

}
