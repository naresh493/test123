package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.RoleReport;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface RoleReportService extends QTAdminService<RoleReport>{
	
	List<RoleReport> findByIds(Integer roleId, Integer reportId);
	
	List<RoleReport> getByReportId(Integer reportId);
	
	List<RoleReport> getByRoleId(Integer roleId);
	
	int insertNewRoleReportEntity(RoleReport roleReport);
	
	int deleteByRoleId(Integer roleId);
	
}
