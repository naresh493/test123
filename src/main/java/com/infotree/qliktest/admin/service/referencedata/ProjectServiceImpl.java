package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.ProjectDao;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class ProjectServiceImpl extends AbstractQTAdminService<Project> implements ProjectService{

	private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);
	@Autowired
	private ProjectDao projectDao;


	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	public List<Project> list() {
		try {
			return projectDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			
			e.printStackTrace();
			return null;
		}
	}
	
	public QTAdminDao<Project> getTDao() {
		try {
			return projectDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public Project findByName(String name) {
		try {
			return projectDao.findByName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public void save(List<Project> projectList){
		try {
			projectDao.save(projectList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	public List<Project> findByTenant(Integer tenantId){
		try {
			return projectDao.findByTenant(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public Project findByBothIds(Integer projectId, Integer tenantId) {
		try {
			return projectDao.findByBothIds(projectId, tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public int updateProject(Project t){
		try {
			return projectDao.updateProject(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	public List<Project> getActiveProjects() {
		try {
			return projectDao.getActiveProjects();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public String delete(Project t){
		try {
			return projectDao.delete(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Project> getProjectsByUserId(Integer id){
		try {
			return projectDao.getProjectsByUserId(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	public List<Project> getProjectsByTeamI(String id){
		try {
			return projectDao.getProjectsByTeamI(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Project> getProjectsIn(Integer projectId) {
		try {
			return projectDao.getProjectsIn(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> findAll() {
		try {
			return projectDao.findAll();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Project> executeQuery(String query){
		try {
			return projectDao.executeQuery(query);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int UpdateStatusById(Integer id) {
		try {
			return projectDao.updateStatusById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public int UpdateStatus1ById(Integer id) {
		try {
			return projectDao.updateStatus1ById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Project> getByModuleId(Integer tenantid) {
		try {
			return projectDao.getByModuleId(tenantid);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> getByModuleNotInId(Integer tenantid) {
		try {
			return projectDao.getByModuleNotInId(tenantid);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Project> getByTenantId(Integer id) {
		try {
			return projectDao.getByTenantId(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> searchByTenantId(Integer tenantId) {
		try {
			return projectDao.searchByTenantId(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> searchByNotInTenantId(Integer tenantId) {
		try {
			return projectDao.searchByNotInTenantId(tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Project> getProjectsByTeamAndAssignedto(Integer teamId,
			Integer tenantId) {
		try {
			return projectDao.getProjectsByTeamAndAssignedto(teamId, tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> getProjectsNotByTeamAndAssignedto(Integer teamId,
			Integer tenantId) {
		try {
			return projectDao.getProjectsNotByTeamAndAssignedto(teamId, tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> findByTeamIdAndTenantId(Integer teamId,Integer tenantId){
		try {
			return projectDao.findByTeamIdAndTenantId(teamId, tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> findByTenantIdNotAssignedToTeam(Integer teamId,
			Integer tenantId) {
		try {
			return projectDao.findByTenantIdNotAssignedToTeam(teamId, tenantId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Project> findByOrder() {
		return projectDao.findByOrder();
	}

	@Override
	public List<Project> getAssignedProjectsForUser(Integer userId) {
		try{
			return projectDao.getAssignedProjectsForUser(userId);
		}catch(Exception e){
			return null;
		}
		
	}

	@Override
	public List<Project> getProjectsNotAssignedToUser(Integer userId,
			Integer tenantId) {
		try{
			return projectDao.getProjectsNotAssignedToUser(userId, tenantId);
		}catch(Exception e){
			return null;
		}
		
	}
}
