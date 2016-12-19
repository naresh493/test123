/**
 * 
 */
package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.RoleReportDao;
import com.infotree.qliktest.admin.entity.referencedata.RoleReport;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service
public class RoleReportServiceImpl extends AbstractQTAdminService<RoleReport> implements RoleReportService{

	private static final Logger LOG = LoggerFactory.getLogger(RoleReportServiceImpl.class);
	@Autowired
	private RoleReportDao roleReportDao;
	
	public List<RoleReport> findByIds(Integer roleId, Integer reportId) {
		try {
			return roleReportDao.findByIds(roleId, reportId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	 public List<RoleReport> getByReportId(Integer reportId){
		 
		 try {
			return roleReportDao.getByReportId(reportId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		 
	 }

	

	@Override
	public List<RoleReport> getByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return roleReportDao.getByRoleId(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	

	@Override
	public int insertNewRoleReportEntity(RoleReport roleReport) {
		// TODO Auto-generated method stub
		try {
			return  roleReportDao.insertNewRoleReportEntity(roleReport);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public RoleReport save(RoleReport report){
		try {
			return roleReportDao.save(report);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected QTAdminDao<RoleReport> getTDao() {
		// TODO Auto-generated method stub
		try {
			return roleReportDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public int deleteByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return roleReportDao.deleteByRoleId(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	
}
