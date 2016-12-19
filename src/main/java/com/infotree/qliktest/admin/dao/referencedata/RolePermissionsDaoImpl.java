/**
 * @author koteswara.g
 */
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
import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;


@Repository
public class RolePermissionsDaoImpl extends AbstractQTAdminDao<RolePermissions> implements
		RolePermissionsDao {

	private static final Logger LOG = LoggerFactory.getLogger(RolePermissionsDaoImpl.class);
	@Override
	public RolePermissions getById(Serializable id) {
		return null;
	}

	@Override
	public RolePermissions save(RolePermissions t) {
		return super.save(t);
	}

	@Override
	public String delete(RolePermissions t) {
		return null;
	}

	@Override
	public List<RolePermissions> list() {
		return super.list();
	}

	@Override
	public List<RolePermissions> findByRoleId(Integer roleId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userPermissionsComp.roleId", roleId));
			List<RolePermissions> roleList = findMany(crit);
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
	public Integer getPermissionsCountByRole(Integer roleId) {
		int count=0;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userPermissionsComp.roleId", roleId));
			
			count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		return count;
	}
	
	

	@Override
	public List<RolePermissions> findByRoleIdAndPermissionId(Integer roleId,
			Integer permissionId) {
		try {
			Criterion lhs = Restrictions.eq("userPermissionsComp.roleId", roleId);
			Criterion rhs = Restrictions.eq("userPermissionsComp.permissionId", permissionId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			List<RolePermissions> roleList = findMany(crit);
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
	public int deleteByRoleId(Integer roleId) {
		try {
			List<RolePermissions> list = findByRoleId(roleId);
			Session session = getSession();
			if(list != null){
				Iterator<RolePermissions> iterator = list.iterator();
				while(iterator.hasNext()){
					RolePermissions rolePermissions =iterator.next();
					session.delete(rolePermissions);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	

	@Override
	public List<RolePermissions> findByPermissionId(Integer permissionId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userPermissionsComp.permissionId", permissionId));
			List<RolePermissions> roleList = findMany(crit);
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

}
