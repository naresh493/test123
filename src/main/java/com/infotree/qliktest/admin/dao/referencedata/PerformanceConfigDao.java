package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.configs.PerformanceTesting;

public interface PerformanceConfigDao extends QTAdminDao<PerformanceTesting> {

	PerformanceTesting findByName(String name);
	
	PerformanceTesting findByNameAndIdNotIn(String name,Integer id);
	
	int updatePerformanceConfig(PerformanceTesting performance);
	
	List<PerformanceTesting> getByProjectId(Integer projectId);
	
	List<PerformanceTesting> getByOrder();
}
