package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.configs.ObjectSpy;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface ObjectspyConfigService extends QTAdminService<ObjectSpy> {
	
	public ObjectSpy findByConfigName(String name);
	
	public List<ObjectSpy> getObjectspyByCreated(String id);
	
	public String getObjectspyById(String id);
	
	public ObjectSpy findByNameAndIdNotIn(String name,Integer id);
	
	int updateObjspyConfiguration(ObjectSpy spy);
	
	List<ObjectSpy> findByOrder();

}
