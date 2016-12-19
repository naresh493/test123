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
import com.infotree.qliktest.admin.dao.referencedata.ProjectModuleDao;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModule;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service

public class ProjectModuleServiceImpl extends AbstractQTAdminService<ProjectModule>
		implements ProjectModuleService {
	private static final Logger LOG = LoggerFactory.getLogger(ProjectModuleServiceImpl.class);
	@Autowired
	private ProjectModuleDao projectModuleDao;

	@Override
	public ProjectModule getById(Serializable id) {
		// TODO Auto-generated method stub
		try {
			return projectModuleDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ProjectModule save(ProjectModule t) {
		// TODO Auto-generated method stub
		try {
			return projectModuleDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(ProjectModule t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjectModule> list() {
		// TODO Auto-generated method stub
		try {
			return projectModuleDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<ProjectModule> getTDao() {
		// TODO Auto-generated method stub
		try {
			return projectModuleDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ProjectModule> findByProjectId(Integer projId) {
		// TODO Auto-generated method stub
		try {
			return projectModuleDao.findByProjectId(projId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getModuleCountByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		try {
			return projectModuleDao.getModuleCountByProjectId(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	
	@Override
	public int deleteByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		try {
			return projectModuleDao.deleteByProjectId(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

}
