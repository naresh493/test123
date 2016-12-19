package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.DashBoardReports;

public interface OtherReportsDao extends QTAdminDao<DashBoardReports> {
	List<DashBoardReports> getOtherReports();
	List<DashBoardReports> getNames();
	List<DashBoardReports> getByReportId(List<Integer> reportIds);
	int updateReport(DashBoardReports d);
	
	List<DashBoardReports> getReportsByModuleId(Integer moduleId);

	List<DashBoardReports> getReportsByModuleId1(Integer moduleId);
	
	DashBoardReports findByName(String name);
	
	List<DashBoardReports> getReportsByRoleId(Integer roleId);
	
	List<DashBoardReports> getReportsByRoleId1(Integer roleId);
	
	List<DashBoardReports> findByModuleId(String moduleId);
	
	List<DashBoardReports> findByNotInModuleId(String moduleId);
}
