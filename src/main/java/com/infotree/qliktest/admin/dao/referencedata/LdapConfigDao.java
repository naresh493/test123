
package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.LdapConfig;

public interface LdapConfigDao extends QTAdminDao<LdapConfig> {

	LdapConfig findByName(String name);
	
	LdapConfig findByNameAndIdNot(Integer id,String name);
	
	int updateLdapConfig(LdapConfig config);
	
	List<LdapConfig> findByTenantId(Integer tenatId);
}
