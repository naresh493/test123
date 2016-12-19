package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.LdapConfig;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface LdapConfigService extends QTAdminService<LdapConfig> {

	LdapConfig findByName(String name);
	
	LdapConfig findByNameAndIdNot(Integer id,String name);
	
	List<LdapConfig> findByTenantId(Integer tenatId);
	
	int updateLdapConfig(LdapConfig config);
}
