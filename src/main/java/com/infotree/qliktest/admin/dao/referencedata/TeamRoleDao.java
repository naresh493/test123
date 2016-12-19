package com.infotree.qliktest.admin.dao.referencedata;


import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.TeamRole;

public interface TeamRoleDao extends QTAdminDao<TeamRole> {
	
	int deleteByTeamId(Integer teamId);
	
	List<TeamRole> findByTeamId(Integer teamId);

	
}
