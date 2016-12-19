package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModule;

public interface ProjectModuleDao extends QTAdminDao<ProjectModule> {
	
	List<ProjectModule> findByProjectId(Integer projId);
	
	int getModuleCountByProjectId(Integer projectId);
	
	

	int deleteByProjectId(Integer projectId);
}
