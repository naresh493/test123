package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.TeamProject;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class ProjectDaoImpl extends AbstractQTAdminDao<Project> implements ProjectDao {

	private static final Logger LOG = LoggerFactory.getLogger(ProjectDaoImpl.class);
	
	/**
	 * Gets the contacts for a given organisation.
	 * @param organisation - the organisation to find contacts for.
	 * @return a list of users of this system who are contacts for this organisation
	 */
	public List<Project> getProjectList() {
		return super.list();
	}

	public Project save(Project t) {
		return super.save(t);
	}

	public List<Project> getById(String userId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", userId));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<Project> getProjectsIn(Integer projectId){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", projectId));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
			
		}
		
	}
	public Project findByName(String projectName) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("name", projectName));
			List<Project> projectList = findMany(crit);
			if(projectList == null || projectList.size() == 0){
				return null;
			}else{
				return projectList.get(0);
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public void save(List<Project> projectList) {	
		
		try {
			Session session = null;
			for (Project project : projectList) {
				session = getSession();
				session.beginTransaction();
				try {
					session.save(project);
				}catch(Exception e){
					LOG.error("Error in saving Project " + e.getMessage());
				}
				session.flush();
				session.clear();
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	public List<Project> getProjectsByUserId(Integer id){
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
			subCrit.add(Restrictions.eq("projectTenantComp.tenantId", id));
			subCrit.setProjection(Projections.property("projectTenantComp.projectId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public String delete(Project t) {
		
		return null;
	}
	
	public List<Project> getActiveProjects() {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("disabled",(byte)0));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<Project> findByTenant(Integer tenantId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("tenantId", tenantId));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public Project findByBothIds(Integer projectId, Integer tenantId) {
		Project p;
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("id", projectId);
			Criterion rhs = Restrictions.eq("tenantId", tenantId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			p = (Project)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return p;
	}
	
	public int updateProject(Project t) {
		try {
			Project p = getById(t.getId());
			p.setId(t.getId());
			p.setName(t.getName());
			p.setStartDate(t.getStartDate());
			p.setEndDate(t.getEndDate());
			p.setDisabled(t.isDisabled());
			p.setModifiedBy(t.getModifiedBy());
			p.setModifiedDate(t.getModifiedDate());
			getSession().save(p);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			
		}
		return 1;
	}

	@Override
	public List<Project> findAll() {
		try {
			Criteria crit = createBaseCriteria();
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> executeQuery(String sqlquery){
		SQLQuery query=null;
		try {
			Session session = getSession();
			query = session.createSQLQuery(sqlquery);
			query.addEntity(Project.class);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return query.list();
	}

	@Override
	public List<Project> getListOfProjects(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Project> getProjectsByTeamI(String id){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", id));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
			
		}
		
	}

	@Override
	public int updateStatusById(Integer id) {
		try {
			Project p = getById(id);
			p.setId(id);
			p.setDisabled((byte)1);
			Session session = getSession();
			session.save(p);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	@Override
	public int updateStatus1ById(Integer id) {
		try {
			Project p = getById(id);
			p.setId(id);
			p.setDisabled((byte)0);
			
			Session session = getSession();
			session.save(p);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public List<Project> getByModuleId(Integer tenantid) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
			subCrit.add(Restrictions.eq("projectTenantComp.tenantId", tenantid));
			subCrit.setProjection(Projections.property("projectTenantComp.projectId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> getByModuleNotInId(Integer tenantid) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
			subCrit.add(Restrictions.eq("projectTenantComp.tenantId", tenantid));
			subCrit.setProjection(Projections.property("projectTenantComp.projectId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Project> getByTenantId(Integer id) {
		try {
			DetachedCriteria subsubcrit = DetachedCriteria.forClass(UserTenant.class);
			subsubcrit.add(Restrictions.eq("userTenantComp.userId", id));
			subsubcrit.setProjection(Projections.property("userTenantComp.tenantId"));
			DetachedCriteria subcrit = DetachedCriteria.forClass(ProjectTenant.class);
			subcrit.add(Property.forName("projectTenantComp.tenantId").in(subsubcrit));
			subcrit.setProjection(Projections.property("projectTenantComp.projectId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subcrit));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Project> searchByTenantId(Integer tenantId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
			subCrit.add(Restrictions.eq("projectTenantComp.tenantId", tenantId));
			subCrit.setProjection(Projections.property("projectTenantComp.projectId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Project> searchByNotInTenantId(Integer tenantId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
			subCrit.add(Restrictions.eq("projectTenantComp.tenantId", tenantId));
			subCrit.setProjection(Projections.property("projectTenantComp.projectId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
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
			DetachedCriteria subCrit = DetachedCriteria.forClass(TeamProject.class);
			subCrit.setProjection(Projections.property("teamProjId.projectId"));
			subCrit.add(Restrictions.eq("teamProjId.teamId", teamId));
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", subCrit));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
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
			DetachedCriteria subCrit = DetachedCriteria.forClass(TeamProject.class);
			subCrit.setProjection(Projections.property("teamProjId.projectId"));
			subCrit.add(Restrictions.eq("teamProjId.teamId", teamId));
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.ne("id", subCrit));
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public List<Project> findByTeamIdAndTenantId(Integer teamId,Integer tenantId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
			subCrit.add(Restrictions.eq("projectTenantComp.tenantId", tenantId));
			subCrit.setProjection(Projections.property("projectTenantComp.projectId"));
			Criterion lhs = Property.forName("id").in(subCrit);
			
			DetachedCriteria subCriteria = DetachedCriteria.forClass(TeamProject.class);
			subCriteria.add(Restrictions.in("teamProjId.teamId", new Integer[]{teamId}));
			subCriteria.setProjection(Projections.property("teamProjId.projectId"));
			Criterion rhs = Restrictions.not(Property.forName("id").in(subCriteria));
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
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
			DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
			subCrit.add(Restrictions.eq("projectTenantComp.tenantId", tenantId));
			subCrit.setProjection(Projections.property("projectTenantComp.projectId"));
			Criterion lhs = Property.forName("id").in(subCrit);
			
			DetachedCriteria subCriteria = DetachedCriteria.forClass(TeamProject.class);
			subCriteria.add(Restrictions.in("teamProjId.teamId", new Integer[]{teamId}));
			subCriteria.setProjection(Projections.property("teamProjId.projectId"));
			Criterion rhs = Property.forName("id").in(subCriteria);
			
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			List<Project> list = findMany(crit);
			if(list != null){
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Project> findByOrder() {
		Criteria crit = createBaseCriteria();
		crit.addOrder(Order.desc("id"));
		List<Project> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<Project> getProjectsNotAssignedToUser(Integer userId,
			Integer tenantId) {
		DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectTenant.class);
		subCrit.add(Restrictions.eq("projectTenantComp.tenantId", tenantId));
		subCrit.setProjection(Projections.property("projectTenantComp.projectId"));
		
		Criterion lhs = Property.forName("id").in(subCrit);
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(UserProject.class);
		subCriteria.add(Restrictions.in("userProjectComp.userId", new Integer[]{userId}));
		subCriteria.setProjection(Projections.property("userProjectComp.projectId"));
		Criterion rhs = Property.forName("id").notIn(subCriteria);
		LogicalExpression exp = Restrictions.and(lhs, rhs);
		
		Criteria crit = createBaseCriteria();
		crit.add(exp);
		List<Project> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<Project> getAssignedProjectsForUser(Integer userId) {
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(UserProject.class);
		subCriteria.add(Restrictions.in("userProjectComp.userId", new Integer[]{userId}));
		subCriteria.setProjection(Projections.property("userProjectComp.projectId"));
		Criteria crit = createBaseCriteria();
		crit.add(Property.forName("id").in(subCriteria));
		List<Project> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
}
