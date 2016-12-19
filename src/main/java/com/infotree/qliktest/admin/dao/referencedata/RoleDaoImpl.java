package com.infotree.qliktest.admin.dao.referencedata;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.RoleModule;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;
import com.infotree.qliktest.admin.entity.referencedata.TeamRole;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;


/**
 * Data access object for the Role entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class RoleDaoImpl extends AbstractQTAdminDao<Role> implements RoleDao {

	private static final Logger LOG = LoggerFactory.getLogger(RoleDaoImpl.class);
	/**
	 * Gets the list of roles
	 * @return a list of roles
	 */
	public List<Role> getRoleList() {
		return super.list();
	}

	public User save(User t) {
		return null;
	}

	public String delete(User t) {
		return null;
	}
	public String getNameById(Integer id){
		String name=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", id));
			Iterator<Role> itr = findMany(crit).iterator();
			name = "";
			while(itr.hasNext()){
				Role r = itr.next();
				name = r.getName();
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return name;
				
		
	}

	public Role getById(String userId) {
		return super.getById(userId);
	}

	public Role findByName(String rolename) {
		Role role=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("name", rolename));
			role = (Role)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public Role findByNameAndIdNot(String name, Integer id) {
		Role r=null;
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("name", name);
			Criterion rhs = Restrictions.ne("id", id);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			r = (Role)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public int updateRole(Role role) {
		try {
			Role r = getById(role.getId());
			r.setId(role.getId());
			r.setName(role.getName());
			r.setDescription(role.getDescription());
			r.setModifiedBy(role.getModifiedBy());
			r.setModifiedDate(role.getModifiedDate());
			Session session=getSession();
			session.save(r);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public List<Role> searchByPattern(String name) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.like("name", name));
			List<Role> roleList = findMany(crit);
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
	public List<Role> getRolesByModuleId(Integer moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RoleModule.class);
			subCrit.add(Restrictions.eq("roleModuleComp.moduleId", moduleId));
			subCrit.setProjection(Projections.property("roleModuleComp.roleId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> getRolesByModuleId1(Integer moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RoleModule.class);
			subCrit.add(Restrictions.eq("roleModuleComp.moduleId", moduleId));
			subCrit.setProjection(Projections.property("roleModuleComp.roleId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> getByModuleId(Integer moduleId) {
		
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RoleModule.class);
			subCrit.add(Restrictions.eq("roleModuleComp.moduleId", moduleId));
			subCrit.setProjection(Projections.property("roleModuleComp.roleId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> getByModuleNotInId(Integer moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RoleModule.class);
			subCrit.add(Restrictions.eq("roleModuleComp.moduleId", moduleId));
			subCrit.setProjection(Projections.property("roleModuleComp.roleId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> findByPermId(Integer permissionId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RolePermissions.class);
			subCrit.add(Restrictions.eq("userPermissionsComp.permissionId", permissionId));
			subCrit.setProjection(Projections.property("userPermissionsComp.roleId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> getByRoleIdAndPermId(Integer roleId, Integer permId) {
		try {
			Criterion lhs = Restrictions.eq("userPermissionsComp.permissionId", permId);
			Criterion rhs = Restrictions.eq("userPermissionsComp.roleId", roleId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			
			DetachedCriteria subCrit = DetachedCriteria.forClass(RolePermissions.class);
			subCrit.setProjection(Projections.property("userPermissionsComp.roleId"));
			subCrit.add(exp);
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> findByTeamId(Integer teamId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(TeamRole.class);
			subCrit.setProjection(Projections.property("teamRoleComp.roleId"));
			subCrit.add(Restrictions.eq("teamRoleComp.teamId", teamId));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> getRolesNotAssignedToTeam(Integer teamId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(TeamRole.class);
			subCrit.setProjection(Projections.property("teamRoleComp.roleId"));
			subCrit.add(Restrictions.eq("teamRoleComp.teamId", teamId));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> getRolesByUser(Integer userId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(UserRole.class);
			subCrit.setProjection(Projections.property("userRoleComp.roleId"));
			subCrit.add(Restrictions.eq("userRoleComp.userId", userId));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> getRolesNotAssignedToUser(Integer userId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(UserRole.class);
			subCrit.setProjection(Projections.property("userRoleComp.roleId"));
			subCrit.add(Restrictions.eq("userRoleComp.userId", userId));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<Role> roleList = findMany(crit);
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
	public List<Role> findByOrder() {
		Criteria crit = createBaseCriteria();
		crit.addOrder(Order.desc("createdDate"));
		List<Role> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
}
