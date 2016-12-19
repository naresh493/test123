package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface ProjectTenantDao extends QTAdminDao<ProjectTenant> {

	ProjectTenant findByProjectId(Integer userId);
	
	ProjectTenant findByTenantId(Integer roleId);
	
	ProjectTenant findByIds(Integer projectId, Integer tenantId);
	
	List<ProjectTenant> getByProjectId(Integer projectId);
	
	List<ProjectTenant> getByTenantId(Integer tenantId);
	
	List<ProjectTenant> getByIds(Integer projectId, Integer tenantId);
	
	int deleteByProjectId(Integer projectId);
	
	int insertNewProjectTenantEntity(ProjectTenant projectTenant);
	
	
	
	Integer getCountByTenantId(Integer tenantId);

	
	int deleteByTenantId(Integer tenantId);
	
	
	
}
