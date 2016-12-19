package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface UserProjectService extends QTAdminService<UserProject>{

	int deleteByUserIdAndProjectId(Integer userId, Integer projectId);
	
	int deleteByProjectId(Integer projectId);
	
	List<User> getUsersByProjectId(Integer projectid);
	
	int deleteMultUsersFromProject(Integer projectId,String users);
	
	List<UserProject> getByProjectId(Integer projectid);
	
	List<UserProject> getByUserId(Integer userId);
	
	int deleteByUserId(Integer userId);
	
}
