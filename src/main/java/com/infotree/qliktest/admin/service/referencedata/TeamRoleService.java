package com.infotree.qliktest.admin.service.referencedata;

import com.infotree.qliktest.admin.entity.referencedata.TeamRole;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface TeamRoleService extends QTAdminService<TeamRole> {

	int deleteByTeamId(Integer teamId);

	
}
