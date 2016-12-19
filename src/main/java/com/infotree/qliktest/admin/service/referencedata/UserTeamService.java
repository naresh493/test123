package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface UserTeamService extends QTAdminService<UserTeam> {
	
		public int insertNewUserTeamEntity(UserTeam userteam);
			
		public List<User> getUsersByTeamId(Integer teamId);
		
		public List<UserTeam> findByUserId(Integer userId);
		
		public int deleteMultUsersFromTeam(Integer teamId,String users);
		
		List<UserTeam> getByteamId(Integer teamId);
		
		public int getNoOfUsers(Integer teamId);
		
		int deleteByTeamId(Integer teamId);
		
		int deleteByUserId(Integer userId);
}