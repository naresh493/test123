package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.ModuleReportsAssign;

public interface ModuleReportDao  extends QTAdminDao<ModuleReportsAssign>  {
List<ModuleReportsAssign> findByIds(Integer moduleId, Integer reportId);
	
	List<ModuleReportsAssign> getByReportId(Integer reportId);
	
	List<ModuleReportsAssign> getByModuleId(Integer moduleId);
	
	int insertNewModuleReportEntity(ModuleReportsAssign moduleReportsAssign);

	
	
	int getReportCountByModuleId(Integer moduleId);
	
	int deleteByModuleId(Integer moduleId);

}
