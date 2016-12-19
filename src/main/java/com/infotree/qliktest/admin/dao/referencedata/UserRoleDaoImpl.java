package com.infotree.qliktest.admin.dao.referencedata;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class UserRoleDaoImpl extends AbstractQTAdminDao<UserRole> implements UserRoleDao {
	private static final Logger LOG = LoggerFactory.getLogger(UserRoleDaoImpl.class);

	public UserRole getById(String userId) {
		return super.getById(userId);
	}
	
	public List<UserRole> list(){
		return super.list();
	}
	
	public UserRole findByUserId(Integer userId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userRoleComp.userId", userId));
			
			List<UserRole> list = findMany(crit);
			if(list == null || list.size() == 0){
				return null;
			}else{
				return list.get(0);
			}
			/*
			UserRole ur = (UserRole)crit.uniqueResult();
			if(ur != null){
				return ur;
			}else{
				return null;
			}*/
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	public UserRole findByRoleId(Integer roleId) {
		return null;
	}

	public List<UserRole> findByIds(Integer userId, Integer roleId) {
		Criterion userCrt = Restrictions.eq("userRoleComp.userId", userId);
		Criterion roleCrt = Restrictions.eq("userRoleComp.roleId", roleId);
		LogicalExpression exp = Restrictions.and(userCrt, roleCrt);
		Criteria crit = createBaseCriteria();
		crit.add(exp);
		List<UserRole> list = findMany(crit);
		if(list == null || list.size() == 0){
			return null;
		}else{
			return list;
		}
	}

	public List<UserRole> getByUserId(Integer userId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userRoleComp.userId", userId));
			List<UserRole> list = findMany(crit);
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

	public List<UserRole> getByRoleId(Integer roleId) {
		return null;
	}

	public List<UserRole> getByIds(Integer userId, Integer roleId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("userRoleComp.userId", userId);
			Criterion rhs = Restrictions.eq("userRoleComp.roleId", roleId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			List<UserRole> list = findMany(crit);
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
	
	public String delete(UserRole t) {
		return super.delete(t);
	}

	public int deleteByUserId(Integer userId) {
		try {
			List<UserRole> list = getByUserId(userId);
			Session session = getSession();
			if(list != null){
				Iterator<UserRole> iterator = list.iterator();
				while(iterator.hasNext()){
					UserRole ur = iterator.next();
					session.delete(ur);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	public int insertNewUserRoleEntity(UserRole userRole) {
		try {
			Session session = getSession();
			session.beginTransaction();
			session.save(userRole);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public List<UserRole> getAllRecords() {
		try {
			Criteria crit = createBaseCriteria();
			List<UserRole> list = findMany(crit);
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

	@Override
	public int updateByUserId(UserRole userRole) {
		SQLQuery query=null;
		try {
			Session session = getSession();
			session.beginTransaction();
			query = session.createSQLQuery("update user_role set role_id=:roleId,modified_by=:modifiedby modified_date=:modifieddate where usesr_id=:userid");
			query.setInteger("roleId", userRole.getUserRoleComp().getRoleId());
			query.setString("modifiedby", userRole.getModifiedBy());
			query.setDate("modifieddate", userRole.getModifiedDate());
			query.setInteger("userid", userRole.getUserRoleComp().getUserId());
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return query.executeUpdate();
	}

	

	@Override
	public UserRole getByUserAndRoleId(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		try {
			Criterion lhs = Restrictions.eq("userRoleComp.userId", userId);
			Criterion rhs = Restrictions.eq("userRoleComp.roleId", roleId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			UserRole ur = findOne(crit);
			if(ur != null){
				return ur;
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
	public int deleteByUserAndRole(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		try {
			List<UserRole> list = getByIds(userId, roleId);
			Session session = getSession();
			if(list != null){
				Iterator<UserRole> iterator = list.iterator();
				while(iterator.hasNext()){
					UserRole ur = iterator.next();
					session.delete(ur);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	
}
