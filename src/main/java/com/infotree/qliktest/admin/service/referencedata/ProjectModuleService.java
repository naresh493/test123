package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.ProjectModule;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface ProjectModuleService extends QTAdminService<ProjectModule> {
	List<ProjectModule> findByProjectId(Integer projId);
	
	int getModuleCountByProjectId(Integer projectId);
	
	
	
	int deleteByProjectId(Integer projectId);
}
