package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Project;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface ProjectDao extends QTAdminDao<Project> {

	List<Project> getById(String userId);
	
	Project findByName(String projectName);
	
	void save(List<Project> projectList);
	
	List<Project> findByTenant(Integer tenantId);
	
	Project findByBothIds(Integer projectId, Integer tenantId);
	
	int updateProject(Project t);
	
	List<Project> getActiveProjects();
	
	String delete(Project t);
	
	List<Project> getProjectsByUserId(Integer id);
	
	List<Project> getProjectsIn(Integer projectId);
	
	List<Project> findAll();
	
	List<Project> executeQuery(String query);
	
	List<Project> getListOfProjects(String id);
	
	int updateStatusById(Integer id);
	
	int updateStatus1ById(Integer id);

	List<Project> getProjectsByTeamI(String id);

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
	
	List<Project> getAssignedProjectsForUser(Integer userId);
	
	List<Project> getProjectsNotAssignedToUser(Integer userId,Integer tenantId);
}
