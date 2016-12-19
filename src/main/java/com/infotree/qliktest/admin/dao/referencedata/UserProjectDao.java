package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface UserProjectDao extends QTAdminDao<UserProject> {

	UserProject findByUserId(Integer userId);
	
	UserProject findByprojectId(Integer projectId);
	
	UserProject findByIds(Integer userId, Integer projectId);
	
	List<UserProject> getByUserId(Integer userId);
	
	List<UserProject> getByprojectId(Integer projectId);
	
	List<UserProject> getByIds(Integer userId, Integer projectId);
	
	int deleteByUserId(Integer userId);
	
	int deleteByProjectId(Integer projectId);
	
	int deleteByUserIdAndProjectId(Integer userId, Integer projectId);
	
	int insertNewUserProjectEntity(UserProject userProject);
	
	List<User> getUsersByProjectId(Integer projectId);
	
	int deleteMultUsersFromProject(Integer projectId,String users);
	
	List<UserProject> getByProjectId(Integer projectId);
	
}
