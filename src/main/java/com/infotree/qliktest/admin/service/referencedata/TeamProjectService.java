package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.TeamProject;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface TeamProjectService extends QTAdminService<TeamProject> {

	public List<TeamProject> getProjectsByTeamId(Integer teamId);
	
	
	int deleteByTeamId(Integer teamId);
	
	
}
