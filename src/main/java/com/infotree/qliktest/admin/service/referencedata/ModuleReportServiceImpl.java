package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.ModuleReportDao;
import com.infotree.qliktest.admin.entity.referencedata.ModuleReportsAssign;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class ModuleReportServiceImpl extends AbstractQTAdminService<ModuleReportsAssign> implements ModuleReportService{

	private static final Logger LOG = LoggerFactory.getLogger(ModuleReportServiceImpl.class);
	@Autowired
	private ModuleReportDao moduleReportDao;
	
	@Override
	public List<ModuleReportsAssign> findByIds(Integer moduleId,
			Integer reportId) {
		// TODO Auto-generated method stub
		try {
			return moduleReportDao.findByIds(moduleId, reportId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModuleReportsAssign> getByReportId(Integer reportId) {
		// TODO Auto-generated method stub
		try {
			return moduleReportDao.getByReportId(reportId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ModuleReportsAssign> getByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return moduleReportDao.getByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insertNewModuleReportEntity(
			ModuleReportsAssign moduleReportsAssign) {
		// TODO Auto-generated method stub
		try {
			return moduleReportDao.insertNewModuleReportEntity(moduleReportsAssign);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	protected QTAdminDao<ModuleReportsAssign> getTDao() {
		// TODO Auto-generated method stub
		try {
			return moduleReportDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	

	@Override
	public int getReportCountByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return moduleReportDao.getReportCountByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return moduleReportDao.deleteByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

}
