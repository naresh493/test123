package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface RoleService extends QTAdminService<Role>{

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	Role findByName(String roleName);
	
	List<Role> list();
	
	String getNameById(Integer id);
	
	Role findByNameAndIdNot(String name, Integer id);
	
	int updateRole(Role role);
	
	List<Role> searchByPattern(String name);
	
	List<Role> getRolesByModuleId(Integer moduleId);
	
	List<Role> getRolesByModuleId1(Integer moduleId);
	
	List<Role> getByModuleId(Integer licenseid);
	
	List<Role> getByModuleNotInId(Integer licenseid);
	
	List<Role> findByPermId(Integer permissionId);
	
	List<Role> getByRoleIdAndPermId(Integer roleId,Integer permId);
	
	List<Role> findByTeamId(Integer teamId);
	
	List<Role> getRolesNotAssignedToTeam(Integer teamId);
	
	List<Role> getRolesByUser(Integer userId);
	
	List<Role> getRolesNotAssignedToUser(Integer userId);
	
	List<Role> findByOrder();
	
	/**
	 * Sets the collection of system functions that users asigned to the given user profile have access to.
	 * @return the updated instance of UserProfile that has been persisted to the database
	 */
	//UserProfile updatePermissions(Integer userProfileId, Set<Function> permissions);
}
