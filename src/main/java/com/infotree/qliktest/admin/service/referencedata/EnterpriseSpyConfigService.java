package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.configs.EnterpriseSpy;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface EnterpriseSpyConfigService extends QTAdminService<EnterpriseSpy> {
	
	public EnterpriseSpy findByConfigName(String name);
	
	public List<EnterpriseSpy> getEnterpriseSpyByCreated(String id);
	
	public String getEnterpriseSpyById(String id);
	
	List<EnterpriseSpy> findByConfigType(String type);
	
	public EnterpriseSpy findByNameAndIdNotIn(String name,Integer id);
	
	int updateEnterpriseSpyConfiguration(EnterpriseSpy spy);
	
	List<EnterpriseSpy> findByOrder();
	

}
