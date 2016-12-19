package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.UserRole;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface UserRoleService extends QTAdminService<UserRole>{

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	UserRole findByUserId(Integer userId);
	
	UserRole findByRoleId(Integer roleId);
	
	List<UserRole> findByIds(Integer userId, Integer roleId);
	
	List<UserRole> getByUserId(Integer userId);
	
	List<UserRole> getByRoleId(Integer roleId);
	
	List<UserRole> getByIds(Integer userId, Integer roleId);
	
	int deleteByUserId(Integer userId);
	
	int insertNewUserRoleEntity(UserRole userRole);
	
	List<UserRole> list();
	
	List<UserRole> getAllRecords();
	
	int updateByUserId(UserRole userRole);
	
	
	
	UserRole getByUserAndRoleId(Integer userId,Integer roleId);
	
	int deleteByUserAndRole(Integer userId,Integer roleId);
	

	/**
	 * Sets the collection of system functions that users asigned to the given user profile have access to.
	 * @return the updated instance of UserProfile that has been persisted to the database
	 */
	//UserProfile updatePermissions(Integer userProfileId, Set<Function> permissions);
}
