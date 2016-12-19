package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface TenantDao extends QTAdminDao<Tenant> {

	Tenant getById(String userId);
	
	Tenant findByName(String tenanName);
	
	void save(List<Tenant> tenantList);
	
	List<Tenant> getActiveTenants();
	
	int updateTenant(Tenant t);
	
	Tenant findByTenantId(Integer id);
	
	Tenant findByNameAndNotId(String name,Integer id);
	
	Tenant findByTenantIdAndCreatedBy(Integer tenantId,Integer createdBy);
	
	Tenant findByProject(Integer projectId);
}
