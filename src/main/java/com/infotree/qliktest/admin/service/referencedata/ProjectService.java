package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface ProjectService extends QTAdminService<Project>{

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	Project findByName(String name);
	
	List<Project> list();
	
	
	void save(List<Project> projectList);
	
	List<Project> findByTenant(Integer tenantId);
	
	Project findByBothIds(Integer projectId, Integer tenantId);
	
	public int updateProject(Project t);
	
	List<Project> getActiveProjects();
	
	String delete(Project t);
	
	List<Project> getProjectsByUserId(Integer id);
	
	List<Project> getProjectsIn(Integer projectId);
	
	List<Project> findAll();
	
	List<Project> executeQuery(String query);
	
	List<Project> getProjectsByTeamI(String id); 
	
	int UpdateStatusById(Integer id);
	
	int UpdateStatus1ById(Integer id);
	
	List<Project> getAssignedProjectsForUser(Integer userId);
	/**
	 * Sets the collection of system functions that users asigned to the given user profile have access to.
	 * @return the updated instance of UserProfile that has been persisted to the database
	 */
	//UserProfile updatePermissions(Integer userProfileId, Set<Function> permissions);

	List<Project> getByModuleId(Integer tenantid);

	List<Project> getByModuleNotInId(Integer tenantid);

	List<Project> getByTenantId(Integer id);
	
	List<Project> searchByTenantId(Integer tenantId);
	
	List<Project> searchByNotInTenantId(Integer tenantId);
	
	List<Project> getProjectsByTeamAndAssignedto(Integer teamId,Integer tenantId);
	
	List<Project> getProjectsNotByTeamAndAssignedto(Integer teamId,Integer tenantId);
	
	List<Project> findByTeamIdAndTenantId(Integer teamId,Integer tenantId);
	
	List<Project> findByTenantIdNotAssignedToTeam(Integer teamId,Integer tenantId);
	
	List<Project> findByOrder();
	
	List<Project> getProjectsNotAssignedToUser(Integer userId,Integer tenantId);
}
