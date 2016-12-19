package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.DashBoardReports;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface ReportsService extends QTAdminService<DashBoardReports>{
	List<DashBoardReports> getOtherReports();
	List<DashBoardReports> getNames();

	List<DashBoardReports> getBYReportId(List<Integer> list);
	
	int updateReports(DashBoardReports d);
	
	DashBoardReports findByName(String name);
	
	List<DashBoardReports> getReportsByModuleId(Integer moduleId);
	
	List<DashBoardReports> getReportsByModuleId1(Integer moduleId);
	
	List<DashBoardReports> getReportsByRoleId(Integer roleId);
	
	List<DashBoardReports> getReportsByRoleId1(Integer roleId);
	
	List<DashBoardReports> findByModuleId(String moduleId);
	
	List<DashBoardReports> findByNotInModuleId(String moduleId);
	
}
