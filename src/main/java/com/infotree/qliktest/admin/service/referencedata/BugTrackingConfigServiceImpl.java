package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.BugTrackingConfigDao;
import com.infotree.qliktest.admin.entity.configs.BugTracking;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service
public class BugTrackingConfigServiceImpl extends AbstractQTAdminService<BugTracking>
		implements BugTrackingConfigService {
	private static final Logger LOG = LoggerFactory.getLogger(BugTrackingConfigServiceImpl.class);

	@Autowired
	private BugTrackingConfigDao bugtrackingDao;
	
	@Override
	public BugTracking save(BugTracking t) {
		try {
			return bugtrackingDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(BugTracking t) {
		return null;
	}

	@Override
	public List<BugTracking> list() {
		try {
			return bugtrackingDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<BugTracking> getTDao() {
		try {
			return bugtrackingDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BugTracking findByConfigName(String name) {
		try {
			return bugtrackingDao.findByConfigName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<BugTracking> getBugTrackingByCreated(String id){
		try {
			return bugtrackingDao.getBugTrackingByCreated(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getBugTrackingById(String id){
		try {
			return bugtrackingDao.getBugTrackingById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BugTracking getById(Serializable id){
		try {
			return bugtrackingDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BugTracking findByNameAndIdNotIn(String name, Integer id) {
		try {
			return bugtrackingDao.findByNameAndIdNot(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateBugTrackingConfiguration(BugTracking spy) {
		try {
			return bugtrackingDao.updateBugTrackingConfiguration(spy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public List<BugTracking> findByOrder() {
		return bugtrackingDao.findByOrder();
	}
}
