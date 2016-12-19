package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;


import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface TeamService extends QTAdminService<Team>{

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	Team findByName(String teamName);
	
	List<Team> list();
	
	List<Team> getActiveTeams();
	
	List<Team> getTeamsById(String id);

	int deleteUsersByTeamId(Integer teamId);
	
	List<Team> executeSqlQuery(String query);
	
	Team findByNameAndIdNot(String name, Integer id);
	
	int updateTeam(Team team);
	
	List<Team> getAssignedTeamsForUser(Integer userId,Integer createdBy);
	
	List<Team> getAvailableTeamsForUser(Integer userId,Integer createdBy);
	/**
	 * Sets the collection of system functions that users asigned to the given user profile have access to.
	 * @return the updated instance of UserProfile that has been persisted to the database
	 */
	//UserProfile updatePermissions(Integer userProfileId, Set<Function> permissions);
}
