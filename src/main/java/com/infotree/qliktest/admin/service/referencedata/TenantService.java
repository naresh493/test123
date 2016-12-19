package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface TenantService extends QTAdminService<Tenant>{

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	Tenant findByName(String tenantName);
	
	List<Tenant> list();
	
	void save(List<Tenant> tenantList);
	
	List<Tenant> getActiveTenants();
	
	int updateTenant(Tenant t);
	
	Tenant findByTenantId(Integer id);
	
	Tenant findByNameAndNotId(String name,Integer id);
	
	Tenant findByTenantIdAndCreatedBy(Integer tenantId,Integer createdBy);
	
	Tenant findByProject(Integer projectId);
	

	/**
	 * Sets the collection of system functions that users asigned to the given user profile have access to.
	 * @return the updated instance of UserProfile that has been persisted to the database
	 */
	//UserProfile updatePermissions(Integer userProfileId, Set<Function> permissions);
}
