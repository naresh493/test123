package com.infotree.qliktest.admin.service.referencedata;

import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface UserTenantService extends QTAdminService<UserTenant>{

	int deleteByUserIdAndTenantId(Integer userId, Integer tenantId);
	
	int deleteByTenantId(Integer tenantId);
	
	UserTenant findByUserId(Integer userId);
	
	UserTenant findByTenantId(Integer tenantId);
	
	UserTenant findByTenantIdAndCreatedBy(Integer tenantId,Integer createdBy);
}
