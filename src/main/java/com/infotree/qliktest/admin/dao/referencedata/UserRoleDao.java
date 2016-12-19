package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface UserRoleDao extends QTAdminDao<UserRole> {

	UserRole findByUserId(Integer userId);
	
	UserRole findByRoleId(Integer roleId);
	
	List<UserRole> findByIds(Integer userId, Integer roleId);
	
	List<UserRole> getByUserId(Integer userId);
	
	List<UserRole> getByRoleId(Integer roleId);
	
	List<UserRole> getByIds(Integer userId, Integer roleId);
	
	int deleteByUserId(Integer userId);
	
	int insertNewUserRoleEntity(UserRole userRole);
	
	List<UserRole> getAllRecords();
	
	int updateByUserId(UserRole userRole);
	
	
	
	UserRole getByUserAndRoleId(Integer userId,Integer roleId);
	
	int deleteByUserAndRole(Integer userId,Integer roleId);
	
	
}
