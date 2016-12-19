package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
public interface UserTenantDao extends QTAdminDao<UserTenant> {

	UserTenant findByUserId(Integer userId);
	
	UserTenant findByTenantId(Integer tenantId);
	
	
	
	UserTenant findByIds(Integer userId, Integer tenantId);
	
	List<UserTenant> getByUserId(Integer userId);
	
	List<UserTenant> getByTenantId(Integer tenantId);
	
	List<UserTenant> getByIds(Integer userId, Integer tenantId);
	
	int deleteByUserId(Integer userId);
	
	int deleteByTenantId(Integer tenantId);
	
	int deleteByUserIdAndTenantId(Integer userId, Integer tenantId);
	
	int insertNewUserTenantEntity(UserTenant userTenant);
	
	UserTenant findByTenantIdAndCreatedBy(Integer tenantId,Integer createdBy);
	
}
