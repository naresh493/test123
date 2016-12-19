package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;
import com.infotree.qliktest.admin.entity.referencedata.Permissions;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class PermissionsDaoImpl extends AbstractQTAdminDao<Permissions> implements PermissionsDao {

	private static final Logger LOG = LoggerFactory.getLogger(PermissionsDaoImpl.class);
	
	public static final int limitsPerPage = 10;
	public Permissions save(Permissions p) {
		return super.save(p);
	}

	public String delete(Permissions t) {
		return null;
	}

	public Permissions getById(String userId) {
		return null;
	}

	public Permissions findByName(String name) {
		Permissions perm=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("name", name));
			perm = (Permissions)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return perm;
	}

	public List<Permissions> getPermissionsByRoleId(List<Integer> roleIds) {
		try {
			DetachedCriteria subcrit = DetachedCriteria.forClass(RolePermissions.class);
			subcrit.add(Property.forName("userPermissionsComp.roleId").eq(roleIds));
			subcrit.setProjection(Projections.property("userPermissionsComp.permissionId"));
			Criteria mainCrit = createBaseCriteria();
			mainCrit.add(Property.forName("id").in(subcrit));
			List<Permissions> list = findMany(mainCrit);
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
	
	public List<Permissions> getPermissionsNotInRoleId(List<Integer> roleIds){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.not(Restrictions.in("roleId", roleIds)));
			List<Permissions> list = findMany(crit);
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
	
	public void save(List<Permissions> permissionsList) {	
		
		try {
			Session session = null;
			for (Permissions permission : permissionsList) {
				session = getSession();
				session.beginTransaction();
				try {
					session.save(permission);
				}catch(Exception e){
					LOG.error("Error in saving Permission " + e.getMessage());
				}
				session.flush();
				session.clear();
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	

	public List<Permissions> getpermissionsByRoleId(Integer roleId){
			try {
				DetachedCriteria subcrit = DetachedCriteria.forClass(RolePermissions.class);
				subcrit.add(Property.forName("userPermissionsComp.roleId").eq(roleId));
				subcrit.setProjection(Projections.property("userPermissionsComp.permissionId"));
				Criteria mainCrit = createBaseCriteria();
				mainCrit.add(Property.forName("id").in(subcrit));
				List<Permissions> list = findMany(mainCrit);
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
	
	public int getMaxId(){
		Criteria crit=null;
		try {
			crit = createBaseCriteria().setProjection(Projections.max("id"));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return (Integer)crit.uniqueResult();
		
	}

	@Override
	public List<Permissions> getPermissionsNotIn(Integer id) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.ne("id", id));
			List<Permissions> list = findMany(crit);
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
	public Permissions getPermissionsByNameAndId(String name, Integer id) {
		Permissions perm = null;
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("name", name);
			Criterion rhs = Restrictions.ne("id", id);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			perm = (Permissions)crit.uniqueResult();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		if(perm != null){
			return perm;
		}else{
			return null;
		}
		
	}

	@Override
	public int updatePermissions(Permissions permission) {
		try {
			Permissions perm = getById(permission.getId());
			perm.setId(permission.getId());
			perm.setName(permission.getName());
			perm.setDescription(permission.getDescription());
			perm.setModifiedBy(permission.getModifiedBy());
			perm.setModifiedDate(permission.getModifiedDate());
			perm.setDisabled(permission.getDisabled());
			Session session = getSession();
			session.save(perm);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public List<Permissions> getOrderBydesc() {
		try {
			Criteria criteria = createBaseCriteria();
			criteria.addOrder(Order.desc("id"));
			List<Permissions> list = findMany(criteria);
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
	public List<Permissions> searchByPettern(String name) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.like("name", name,MatchMode.START));
			List<Permissions> list = findMany(crit);
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
	public List<Permissions> getActivePermissions() {
		try {
			Criteria criteria = createBaseCriteria();
			criteria.add(Restrictions.eq("disabled", (byte)0));
			List<Permissions> list = findMany(criteria);
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
	public List<Permissions> getPermissionsNotInModule(Integer moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ModulePermissions.class);
			subCrit.add(Restrictions.eq("modulePermissionsComp.moduleId", moduleId));
			subCrit.setProjection(Projections.property("modulePermissionsComp.permissionId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<Permissions> list = findMany(crit);
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
	public List<Permissions> getByModuleId(Integer moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ModulePermissions.class);
			subCrit.add(Restrictions.eq("modulePermissionsComp.moduleId", moduleId));
			subCrit.setProjection(Projections.property("modulePermissionsComp.permissionId"));
			
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Permissions> list = findMany(crit);
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
	public List<Permissions> getByroleId(Integer roleid) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RolePermissions.class);
			subCrit.add(Restrictions.eq("userPermissionsComp.roleId", roleid));
			subCrit.setProjection(Projections.property("userPermissionsComp.permissionId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Permissions> list = findMany(crit);
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
	public List<Permissions> getNotByRoleId(Integer roleid) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RolePermissions.class);
			subCrit.add(Restrictions.eq("userPermissionsComp.roleId", roleid));
			subCrit.setProjection(Projections.property("userPermissionsComp.permissionId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<Permissions> list = findMany(crit);
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
	public List<Permissions> getPermissionsByRoleId(Integer roleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RolePermissions.class);
			subCrit.add(Restrictions.eq("userPermissionsComp.roleId", roleId));
			subCrit.setProjection(Property.forName("userPermissionsComp.permissionId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Permissions> list = findMany(crit);
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
	public List<Permissions> getByPage(Long page) {
		try {
			Criteria crit = createBaseCriteria();
			crit.addOrder(Order.desc("id"));
			crit.setFirstResult((int) (0 * limitsPerPage));
			crit.setMaxResults(limitsPerPage);
			List<Permissions> list = findMany(crit);
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
	public List<Permissions> getByPageByPattern(String pattern, Long page) {
		try {
			Criteria crit = createBaseCriteria();
			crit.addOrder(Order.desc("id"));
			crit.add(Restrictions.like("name", pattern,MatchMode.START));
			crit.setFirstResult((int) (0 * limitsPerPage));
			crit.setMaxResults(limitsPerPage);
			List<Permissions> list = findMany(crit);
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
	public List<Permissions> getStartingRecords() {
		try{
			Criteria crit = createBaseCriteria();
			crit.setFirstResult((int)(0*limitsPerPage));
			crit.setMaxResults(limitsPerPage);
			List<Permissions> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(String.valueOf(e));
			return null;
		}
	}

	@Override
	public int permissionsCount() {
		try{
			Criteria crit = createBaseCriteria();
			int count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
			return count;
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(String.valueOf(e));
			return 0;
		}
		
	}

	@Override
	public int permissionCountByPattern(String pattern) {
		try{
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.like("name", pattern,MatchMode.START));
			int count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
			return count;
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(String.valueOf(e));
			return 0;
		}
		
	}
	
	@Override
	public List<Permissions> getPermissionsByPageAndCount(int page,
			int limitsPerPage) {
		try{
			Criteria crit = createBaseCriteria();
			crit.addOrder(Order.desc("id"));
			crit.setFirstResult(page * limitsPerPage);
			crit.setMaxResults(limitsPerPage);
			List<Permissions> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(String.valueOf(e));
			return null;
			
		}
	}

	@Override
	public List<Permissions> getPermissionsByPageAndCountByPattern(
			String pattern, int page, int limitsPerPage) {
		try{
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.like("name", pattern,MatchMode.START));
			crit.setFirstResult(page * limitsPerPage);
			crit.setMaxResults(limitsPerPage);
			List<Permissions> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(String.valueOf(e));
			return null;
			
		}
	}

	@Override
	public List<Permissions> getByPageEdit(Long page) {
		try {
			Criteria crit = createBaseCriteria();
			crit.addOrder(Order.desc("id"));
			crit.setFirstResult((int) (0 * limitsPerPage));
			crit.setMaxResults(limitsPerPage);
			List<Permissions> list = findMany(crit);
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

	
}
