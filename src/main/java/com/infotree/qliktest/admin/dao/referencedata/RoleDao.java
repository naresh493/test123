package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Role;



/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface RoleDao extends QTAdminDao<Role> {

	Role getById(String userId);
	
	Role findByName(String username);
	
	String getNameById(Integer id);
	
	Role findByNameAndIdNot(String name,Integer id);
	
	int updateRole(Role role);
	
	List<Role> searchByPattern(String name);
	
	List<Role> getRolesByModuleId(Integer moduleId);
	
	List<Role> getRolesByModuleId1(Integer moduleId);
	
	List<Role> getByModuleId(Integer moduleId);

	List<Role> getByModuleNotInId(Integer moduleId);
	
	List<Role> findByPermId(Integer permissionId);
	
	List<Role> getByRoleIdAndPermId(Integer roleId,Integer permId);
	
	List<Role> findByTeamId(Integer teamId);
	
	List<Role> getRolesNotAssignedToTeam(Integer teamId);
	
	List<Role> getRolesByUser(Integer userId);
	
	List<Role> getRolesNotAssignedToUser(Integer userId);
	
	List<Role> findByOrder();
}
