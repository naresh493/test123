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
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class UserTenantDaoImpl extends AbstractQTAdminDao<UserTenant> implements UserTenantDao {
	private static final Logger LOG = LoggerFactory.getLogger(UserTenantDaoImpl.class);

	public UserTenant getById(String userId) {
		return super.getById(userId);
	}

	public UserTenant findByIds(Integer userId, Integer tenantId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("userTenantComp.userId", userId);
			Criterion rhs = Restrictions.eq("userTenantComp.tenantId", tenantId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			UserTenant ut = findOne(crit);
			if(ut != null){
				return ut;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public int deleteByUserId(Integer userId) {
		try {
			Session session = getSession();
			UserTenant ut = findByUserId(userId);
			if(ut != null){
				session.delete(ut);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	
	public int deleteByTenantId(Integer tenantId) {
		try {
			Session session = getSession();
			UserTenant uts = findByTenantId(tenantId);
			if(uts != null){
					session.delete(uts);
				}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	
	public int deleteByUserIdAndTenantId(Integer userId, Integer tenantId) {
		try {
			Session session = getSession();
			UserTenant ut = findByIds(userId, tenantId);
			if(ut != null){
				session.delete(ut);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	public int insertNewUserTenantEntity(UserTenant userTenant) {
		try {
			Session session = getSession();
			session.save(userTenant);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	public UserTenant findByUserId(Integer userId) {
		UserTenant ut=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userTenantComp.userId", userId));
			ut = (UserTenant)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return ut;
	}

	public UserTenant findByTenantId(Integer tenantId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userTenantComp.tenantId", tenantId));
			//UserTenant ut = (UserTenant)crit.uniqueResult();
			List<UserTenant> userTenantList = findMany(crit);
			if(userTenantList != null)
			return userTenantList.get(0);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			
		}
		return null;
	}

	public List<UserTenant> getByUserId(Integer userId) {
		return null;
	}

	public List<UserTenant> getByTenantId(Integer tenantId) {
		return null;
	}

	public List<UserTenant> getByIds(Integer userId, Integer tenantId) {
		return null;
	}

	@Override
	public UserTenant findByTenantIdAndCreatedBy(Integer tenantId,
			Integer createdBy) {
		// TODO Auto-generated method stub
		UserTenant ut = null;
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("userTenantComp.tenantId", tenantId);
			Criterion rhs = Restrictions.eq("createdBy", createdBy+"");
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			ut = (UserTenant)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return ut;
	}
}