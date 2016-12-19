package com.infotree.qliktest.admin.dao.referencedata;
import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.RoleReport;

public interface RoleReportDao extends QTAdminDao<RoleReport> {
	
	List<RoleReport> findByIds(Integer roleId, Integer reportId);
	
	List<RoleReport> getByReportId(Integer reportId);
	
	List<RoleReport> getByRoleId(Integer roleId);
	
	int insertNewRoleReportEntity(RoleReport roleReport);
	
	
	
	int deleteByRoleId(Integer roleId);
	
	
}
