package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.ParlNodesConfigDao;
import com.infotree.qliktest.admin.entity.configs.ParallelNodes;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service
public class ParlNodesConfigServiceImpl extends AbstractQTAdminService<ParallelNodes>
		implements ParlNodesConfigService {
	private static final Logger LOG = LoggerFactory.getLogger(ParlNodesConfigServiceImpl.class);
	@Autowired
	private ParlNodesConfigDao parallelNodesDao;
	
	@Override
	public ParallelNodes save(ParallelNodes t) {
		
		try {
			return parallelNodesDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(ParallelNodes t) {
		return null;
	}

	@Override
	public List<ParallelNodes> list() {
		try {
			return parallelNodesDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<ParallelNodes> getTDao() {
		try {
			return parallelNodesDao;
		} catch (Exception e) {

			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ParallelNodes findByConfigName(String name) {
		try {
			return parallelNodesDao.findByConfigName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<ParallelNodes> getParallelNodesCreated(String id){
		try {
			return parallelNodesDao.getParallelNodesByCreated(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getParallelNodesById(String id){
		try {
			return parallelNodesDao.getParallelNodesById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ParallelNodes getById(Serializable id){
		try {
			return parallelNodesDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ParallelNodes findByNameAndIdNotIn(String name, Integer id) {
		try {
			return parallelNodesDao.findByNameAndIdNot(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateParallelNodesConfiguration(ParallelNodes spy) {
		try {
			return parallelNodesDao.updateParallelNodesConfiguration(spy);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public List<ParallelNodes> findByOrder() {
		return parallelNodesDao.findByOrder();
	}
}
