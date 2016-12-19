package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.ModuleReportsAssign;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface ModuleReportService extends QTAdminService<ModuleReportsAssign> {
List<ModuleReportsAssign> findByIds(Integer moduleId, Integer reportId);
	
	List<ModuleReportsAssign> getByReportId(Integer reportId);
	
	List<ModuleReportsAssign> getByModuleId(Integer ModuleId);
	
	int insertNewModuleReportEntity(ModuleReportsAssign moduleReportsAssign);
	
	
	
	int getReportCountByModuleId(Integer moduleId);
	
	int deleteByModuleId(Integer moduleId);

}
