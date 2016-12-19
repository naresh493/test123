package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.PerformanceConfigDao;
import com.infotree.qliktest.admin.entity.configs.PerformanceTesting;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class PerformanceConfigServiceImpl extends AbstractQTAdminService<PerformanceTesting>
		implements PerformanceConfigService {
	private static final Logger LOG = LoggerFactory.getLogger(PerformanceConfigServiceImpl.class);
	@Autowired
	private PerformanceConfigDao performanceConfigDao;
	
	
	@Override
	public PerformanceTesting getById(Serializable id) {
		try {
			return performanceConfigDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PerformanceTesting save(PerformanceTesting t) {
		try {
			return performanceConfigDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(PerformanceTesting t) {
		return null;
	}

	@Override
	public List<PerformanceTesting> list() {
		try {
			return performanceConfigDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PerformanceTesting findByName(String name) {
		try {
			return performanceConfigDao.findByName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<PerformanceTesting> getTDao() {
		try {
			return performanceConfigDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PerformanceTesting findByNameAndIdNotIn(String name, Integer id) {
		try {
			return performanceConfigDao.findByNameAndIdNotIn(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updatePerformanceConfig(PerformanceTesting performance) {
		try {
			return performanceConfigDao.updatePerformanceConfig(performance);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<PerformanceTesting> getByProjectId(Integer projectId) {
		try {
			return performanceConfigDao.getByProjectId(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PerformanceTesting> getByOrder() {
		return performanceConfigDao.getByOrder();
	}
}
