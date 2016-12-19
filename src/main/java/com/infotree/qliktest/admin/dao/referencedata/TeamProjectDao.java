package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.TeamProject;

public interface TeamProjectDao extends QTAdminDao<TeamProject> {

	public List<TeamProject> getProjectsByTeamId(Integer teamId);
	
	int deleteByTeamId(Integer teamId);
}
