package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.configs.PerformanceTesting;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface PerformanceConfigService extends
		QTAdminService<PerformanceTesting> {

	PerformanceTesting findByName(String name);
	
	PerformanceTesting findByNameAndIdNotIn(String name,Integer id);
	
	int updatePerformanceConfig(PerformanceTesting performance);
	
	List<PerformanceTesting> getByProjectId(Integer projectId);
	
	List<PerformanceTesting> getByOrder();
}
