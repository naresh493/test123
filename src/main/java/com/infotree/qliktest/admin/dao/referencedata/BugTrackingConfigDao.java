package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.configs.BugTracking;

public interface BugTrackingConfigDao extends QTAdminDao<BugTracking> {

	public BugTracking findByConfigName(String name);
	
	public List<BugTracking> getBugTrackingByCreated(String id);
	
	public String getBugTrackingById(String id);
	
	BugTracking findByNameAndIdNot(String name,Integer id);
	
	int updateBugTrackingConfiguration(BugTracking spy);
	
	List<BugTracking> findByOrder();
}
