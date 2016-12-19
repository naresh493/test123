package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.configs.EnterpriseSpy;

public interface EnterpriseSpyConfigDao extends QTAdminDao<EnterpriseSpy> {

	public EnterpriseSpy findByConfigName(String name);
	
	public List<EnterpriseSpy> getEnterpriseSpyByCreated(String id);
	
	public String getEnterpriseSpyById(String id);
	
	List<EnterpriseSpy> findByConfigType(String type);
	
	EnterpriseSpy findByNameAndIdNot(String name,Integer id);
	
	int updateEnterpriseSpyConfiguration(EnterpriseSpy spy);
	
	List<EnterpriseSpy> findByOrder();
	
	
}
