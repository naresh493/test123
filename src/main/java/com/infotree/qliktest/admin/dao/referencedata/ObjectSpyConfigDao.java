package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.configs.ObjectSpy;

public interface ObjectSpyConfigDao extends QTAdminDao<ObjectSpy> {

	public ObjectSpy findByConfigName(String name);
	
	public List<ObjectSpy> getObjectspyByCreated(String id);
	
	public String getObjectspyById(String id);
	
	ObjectSpy findByNameAndIdNot(String name,Integer id);
	
	int updateObjectspyConfiguration(ObjectSpy spy);
	
	List<ObjectSpy> findByOrder();
}
