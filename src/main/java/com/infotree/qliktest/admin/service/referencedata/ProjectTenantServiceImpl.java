package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.ProjectTenantDao;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class ProjectTenantServiceImpl extends AbstractQTAdminService<ProjectTenant> implements ProjectTenantService{

	private static final Logger LOG = LoggerFactory.getLogger(ProjectTenantServiceImpl.class);
	@Autowired
	private ProjectTenantDao projectTenantDao;

	public ProjectTenant save(ProjectTenant t) {
		try{
		return super.save(t);
		}catch(Exception e){
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public String delete(ProjectTenant t) {
		try {
			return super.delete(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public ProjectTenant findByProjectId(Integer projectId) {
		try {
			return projectTenantDao.findByProjectId(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public ProjectTenant findByTenantId(Integer tenantId) {
		try {
			return projectTenantDao.findByTenantId(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public ProjectTenant findByIds(Integer projectId, Integer tenantId) {
		try {
			return projectTenantDao.findByIds(projectId, tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<ProjectTenant> getByProjectId(Integer projectId) {
		try {
			return projectTenantDao.getByProjectId(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<ProjectTenant> getByTenantId(Integer tenantId) {
		try {
			return projectTenantDao.getByTenantId(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<ProjectTenant> getByIds(Integer projectId, Integer tenantId) {
		try {
			return projectTenantDao.getByIds(projectId, tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public int deleteByProjectId(Integer projectId) {
		try {
			return projectTenantDao.deleteByProjectId(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
			
		}
	}

	public int insertNewProjectTenantEntity(ProjectTenant projectTenant) {
		try {
			return projectTenantDao.insertNewProjectTenantEntity(projectTenant);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	protected QTAdminDao<ProjectTenant> getTDao() {
		try {
			return projectTenantDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Integer getCountByTenantId(Integer tenantId) {
		// TODO Auto-generated method stub
		try {
			return projectTenantDao.getCountByTenantId(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	
	@Override
	public int deleteByTenantId(Integer tenantId) {
		// TODO Auto-generated method stub
		try {
			return projectTenantDao.deleteByTenantId(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	

}
