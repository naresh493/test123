package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.entity.referencedata.User;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface TeamDao extends QTAdminDao<Team> {

	User getById(String userId);
	
	List<Team> getTeamList();
	
	Team findByName(String teamName);
	
	int deleteUsersByTeamId(Integer teamId);
	
	List<Team> getTeamsById(String id);
	
	List<Team> executeSQLQuery(String query);
	
	Team findByNameAndIdNot(String name,Integer id);
	
	int updateTeam(Team team);
	
	List<Team> getAssignedTeamsForUser(Integer userId, Integer createdBy);
	
	List<Team> getAvailableTeamsForUser(Integer userId,Integer createdBy);
}
