/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

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
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;


/**
 * Data access object for the Role entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class TenantDaoImpl extends AbstractQTAdminDao<Tenant> implements TenantDao {

	private static final Logger LOG = LoggerFactory.getLogger(TenantDaoImpl.class);
	
	/**
	 * Gets the list of roles
	 * @return a list of roles
	 */
	public List<Tenant> getTenantList() {
		return super.list();
	}

	public Tenant save(Tenant t) {
		return super.save(t);
	}

	public String delete(Tenant t) {
		return null;
	}

	public Tenant getById(String userId) {
		return super.getById(userId);
	}

	public Tenant findByName(String tenantName) {
		Tenant tenant=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("name", tenantName));
			tenant = (Tenant)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return tenant;
	}
	
	public void save(List<Tenant> tenantList) {	
		
		try {
			Session session = null;
			for (Tenant tenant : tenantList) {
				session = getSession();
				session.beginTransaction();
				try {
					session.save(tenant);
				}catch(Exception e){
					LOG.error("Error in saving Tenant " + e.getMessage());
				}
				session.flush();
				session.clear();
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}

	public List<Tenant> getActiveTenants() {
		try {
			Criteria crit = createBaseCriteria();
			Criterion isDisabled = Restrictions.eq("disabled",(byte)0);
			crit.add(isDisabled);
			List<Tenant> list = findMany(crit);
			if(list == null || list.size() == 0){
				return null;
			}else{
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public int updateTenant(Tenant t) {
		try {
			Session session = getSession();
			Tenant tenant = getById(t.getId());
			tenant.setName(t.getName());
			tenant.setModifiedBy(t.getModifiedBy());
			tenant.setModifiedDate(t.getModifiedDate());
			session.save(tenant);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Tenant findByTenantId(Integer id) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", id));
			Tenant t = (Tenant)crit.uniqueResult();
			if(t == null){
				return null;
			}else{
				return t;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Tenant findByNameAndNotId(String name, Integer id) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion rhs = Restrictions.ne("id", id);
			Criterion lhs = Restrictions.eq("name", name);
			
			LogicalExpression exp = Restrictions.and(rhs, lhs);
			
			crit.add(exp);
			Tenant t = (Tenant)crit.uniqueResult();
			if(t == null){
				return null;
			}else{
				return t;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Tenant findByTenantIdAndCreatedBy(Integer tenantId, Integer createdBy) {
		Tenant t = null;
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("createdBy", createdBy+"");
			Criterion rhs = Restrictions.eq("id", tenantId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			t = (Tenant)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return t;
	}
	
	@Override
	public Tenant findByProject(Integer projectId) {
		Tenant t=null;
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
			subCrit.add(Restrictions.eq("projectTenantComp.projectId", projectId));
			subCrit.setProjection(Projections.property("projectTenantComp.tenantId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").eq(subCrit));
			t = (Tenant)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return t;
	}
}
