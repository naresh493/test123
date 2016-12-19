/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

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
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class ProjectTenantDaoImpl extends AbstractQTAdminDao<ProjectTenant> implements ProjectTenantDao {

	private static final Logger LOG = LoggerFactory.getLogger(ProjectTenantDaoImpl.class);
	
	public ProjectTenant getById(String projectId) {
		return super.getById(projectId);
	}

	public ProjectTenant findByProjectId(Integer projectId) {
		try {
			Session session = getSession();
			Criteria crit = session.createCriteria(ProjectTenant.class);
			crit.add(Restrictions.eq("projectTenantComp.projectId",projectId));
			ProjectTenant pt = findOne(crit);
			if(pt != null){
				return pt;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	public ProjectTenant findByTenantId(Integer tenantId) {
		ProjectTenant pt=null;
		try {
			Session session = getSession();
			Criteria crit = session.createCriteria(ProjectTenant.class);
			crit.add(Restrictions.eq("projectTenantComp.tenantId",tenantId));
			pt = (ProjectTenant)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return pt;
		
	}

	public ProjectTenant findByIds(Integer projectId, Integer tenantId) {
		ProjectTenant pt=null;
		try {
			Criterion lhs = Restrictions.eq("projectTenantComp.projectId", projectId);
			Criterion rhs = Restrictions.eq("projectTenantComp.tenantId", tenantId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			pt = (ProjectTenant)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return pt;
		
	}

	public List<ProjectTenant> getByProjectId(Integer projectId) {
		return null;
	}

	public List<ProjectTenant> getByTenantId(Integer tenantId) {
		try {
			Session session = getSession();
			Criteria crit = session.createCriteria(ProjectTenant.class);
			crit.add(Restrictions.eq("projectTenantComp.tenantId",tenantId));
			List<ProjectTenant> list = findMany(crit);
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

	public List<ProjectTenant> getByIds(Integer projectId, Integer tenantId) {
		return null;
	}
	
	public String delete(ProjectTenant t) {
		return super.delete(t);
	}

	public int deleteByProjectId(Integer projectId) {
		try {
			ProjectTenant pt = findByProjectId(projectId);
			Session session = getSession();
			if(pt != null){
				session.delete(pt);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	public int insertNewProjectTenantEntity(ProjectTenant entity) {
		try {
			Session session = getSession();
			session.save(entity);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return 1;
	}

	

	@Override
	public Integer getCountByTenantId(Integer tenantId) {
		int count=0;
		try {
			Criteria crit = createBaseCriteria();
			crit.setProjection(Projections.max("projectTenantComp.projectId"));
			crit.add(Restrictions.eq("projectTenantComp.tenantId", tenantId));
			count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return count;
		
	}

	

	@Override
	public int deleteByTenantId(Integer tenantId) {
		try {
			ProjectTenant list = findByTenantId(tenantId);
			Session session = getSession();
			if(list != null){
				session.delete(list);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	
	
}
