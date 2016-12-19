package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.configs.BugTracking;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface BugTrackingConfigService extends QTAdminService<BugTracking> {
	
	public BugTracking findByConfigName(String name);
	
	public List<BugTracking> getBugTrackingByCreated(String id);
	
	public String getBugTrackingById(String id);
	
	public BugTracking findByNameAndIdNotIn(String name,Integer id);
	
	int updateBugTrackingConfiguration(BugTracking spy);
	
	List<BugTracking> findByOrder();

}
