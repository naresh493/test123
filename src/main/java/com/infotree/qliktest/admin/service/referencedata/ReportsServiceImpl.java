/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.OtherReportsDao;
import com.infotree.qliktest.admin.entity.referencedata.DashBoardReports;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class ReportsServiceImpl extends AbstractQTAdminService<DashBoardReports> implements ReportsService {
	private static final Logger LOG = LoggerFactory.getLogger(ReportsServiceImpl.class);
	@Autowired
	private OtherReportsDao otherReportsDao;
	
	@Override
	public DashBoardReports getById(Serializable id) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	 
	@Override
	public DashBoardReports save(DashBoardReports t) {
		// TODO Auto-generated method stub
		try {
			return  otherReportsDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(DashBoardReports t) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<DashBoardReports> getOtherReports() {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.getOtherReports();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<DashBoardReports> getTDao() {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DashBoardReports> getNames() {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.getNames();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DashBoardReports> getBYReportId(List<Integer> reportIds) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.getByReportId(reportIds);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateReports(DashBoardReports d) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.updateReport(d);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public DashBoardReports findByName(String name) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.findByName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DashBoardReports> getReportsByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.getReportsByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DashBoardReports> getReportsByModuleId1(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.getReportsByModuleId1(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<DashBoardReports> getReportsByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.getReportsByRoleId(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<DashBoardReports> getReportsByRoleId1(Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.getReportsByRoleId1(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<DashBoardReports> findByModuleId(String moduleId) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.findByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DashBoardReports> findByNotInModuleId(String moduleId) {
		// TODO Auto-generated method stub
		try {
			return otherReportsDao.findByNotInModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	

}
