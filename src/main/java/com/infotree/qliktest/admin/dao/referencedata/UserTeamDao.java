package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface UserTeamDao extends QTAdminDao<UserTeam> {

	List<UserTeam> findByUserId(Integer userId);
	
	UserTeam findByteamId(Integer teamId);
	
	UserTeam findByIds(Integer userId, Integer teamId);
	
	List<UserTeam> getByUserId(Integer userId);
	
	List<UserTeam> getByteamId(Integer teamId);
	
	List<UserTeam> getByIds(Integer userId, Integer teamId);
	
	int deleteByUserId(Integer userId);
	
	int getNoOfUsers(Integer TeamId);
	
	
	int insertNewUserTeamEntity(UserTeam userTeam);
	
	List<User> getUsersByTeamId(Integer teamId);
	
	public int deleteMultUsersFromTeam(Integer teamId,String users);
	
	int deleteByTeamId(Integer teamId);
	
}
