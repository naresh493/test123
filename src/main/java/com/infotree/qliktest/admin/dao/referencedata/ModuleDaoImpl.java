package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import org.hibernate.Criteria;
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
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModule;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.RoleModule;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class ModuleDaoImpl extends AbstractQTAdminDao<Module> implements ModuleDao {
	

	private static final Logger LOG = LoggerFactory.getLogger(ModuleDaoImpl.class);
	
	public static final int limitsPerPage = 10;
	/**
	 * Gets the contacts for a given organisation.
	 * @param organisation - the organisation to find contacts for.
	 * @return a list of users of this system who are contacts for this organisation
	 */
	@Override
	public List<Module> getModuleList() {
		return super.list();
	}

	public Module save(Module t) {
		return super.save(t);
	}

	public Module findByName(String name) {
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("name", name));
		Module mod = (Module)crit.uniqueResult();
		return mod;
				
	}
	
	public void save(List<Module> ModuleList) {	
		
		Session session = null;
		for (Module Module : ModuleList) {
			session = getSession();
			session.beginTransaction();
			try {
				session.save(Module);
			}catch(Exception e){
				LOG.error("Error in saving Module " + e.getMessage());
			}
			session.flush();
			session.clear();
		}
	}
	
	public String delete(Module t) {
		return null;
	}
	
	public List<Module> getActiveModules() {
		Criteria crit = createBaseCriteria();
		Criterion isDisabled = Restrictions.eq("disabled",(byte)0);
		crit.add(isDisabled);
		List<Module> mod = findMany(crit);
		if(mod != null){
			return mod;
		}else{
			return null;
		}
	}
	public List<Module> getModulesByUserId(Integer id){
		DetachedCriteria subsubCrit = DetachedCriteria.forClass(ProjectTenant.class);
		subsubCrit.add(Restrictions.eq("projectTenantComp.tenantId", id));
		subsubCrit.setProjection(Projections.property("projectTenantComp.projectId"));
		DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectModule.class);
		subCrit.add(Restrictions.eq("projectModuleComp.projectId", subsubCrit));
		Session session=getSession();
		Criteria crit = session.createCriteria(Module.class);
		crit.add(Restrictions.eq("id", subCrit));
		List<Module> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}


	

	public int updateModule(Module module) {
		Module mod = getById(module.getId());
		mod.setId(module.getId());
		mod.setName(module.getName());
		mod.setDescription(module.getDescription());
		mod.setModifiedBy(module.getModifiedBy());
		mod.setModifiedDate(module.getModifiedDate());
		Session session = getSession();
		session.save(mod);
		return 1;
	}

	@Override
	public Module getModuleByNameAndId(String name, Integer id) {
		Criteria crit = createBaseCriteria();
		Criterion namecrit = Restrictions.eq("name", name);
		Criterion idcrit = Restrictions.ne("id", id);
		LogicalExpression exp = Restrictions.and(namecrit, idcrit);
		crit.add(exp);
		Module module = (Module)crit.uniqueResult();
		return module;
	}
	
	@Override
	public List<Module> getOrderBydesc() {
		Criteria crit = createBaseCriteria();
		crit.addOrder(Order.desc("id"));
		List<Module> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<Module> getModulesByProjId(String projid) {
		String[] projectId = projid.split(",");
		Integer[] projids = new Integer[projectId.length];
		for(int i = 0; i<projectId.length; i++) {
			projids[i] = Integer.parseInt(projectId[i]); 
		}
		DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectModule.class);
		subCrit.add(Restrictions.in("projectModuleComp.projectId", projids));
		subCrit.setProjection(Projections.property("projectModuleComp.moduleId"));
		Criteria mainCrit = createBaseCriteria();
		mainCrit.add(Property.forName("id").in(subCrit));
		List<Module> list = findMany(mainCrit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<Module> getByModuleId(Integer projectid) {
		DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectModule.class);
		subCrit.add(Restrictions.eq("projectModuleComp.projectId", projectid));
		subCrit.setProjection(Projections.property("projectModuleComp.moduleId"));
		Criteria mainCrit = createBaseCriteria();
		mainCrit.add(Property.forName("id").in(subCrit));
		List<Module> list = findMany(mainCrit);
		if(list != null){
			return list;
		}else{
			return null;
		}
		
	}

	@Override
	public List<Module> getByNotInModuleId(Integer projectid) {
		DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectModule.class);
		subCrit.add(Restrictions.eq("projectModuleComp.projectId", projectid));
		subCrit.setProjection(Projections.property("projectModuleComp.moduleId"));
		Criteria mainCrit = createBaseCriteria();
		mainCrit.add(Property.forName("id").notIn(subCrit));
		List<Module> list = findMany(mainCrit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	public int getNumberOfModules(Integer projectId) {
		Session session = getSession();
		Criteria crit = session.createCriteria(ProjectModule.class);
		crit.add(Restrictions.eq("projectModuleComp.projectId", projectId));
		crit.setProjection(Projections.max("projectModuleComp.moduleId"));
		int count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
		return count;
	}
	
	@Override
	public List<Module> getByProjectId(Integer projectId) {
		DetachedCriteria subCrit = DetachedCriteria.forClass(ProjectModule.class);
		subCrit.add(Restrictions.eq("projectModuleComp.projectId", projectId));
		subCrit.setProjection(Projections.property("projectModuleComp.moduleId"));
		Criteria crit = createBaseCriteria();
		crit.add(Property.forName("id").in(subCrit));
		List<Module> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	public List<Module> getByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		DetachedCriteria subCrit = DetachedCriteria.forClass(RoleModule.class);
		subCrit.add(Restrictions.eq("roleModuleComp.roleId", roleId));
		subCrit.setProjection(Projections.property("roleModuleComp.moduleId"));
		Criteria crit = createBaseCriteria();
		crit.add(Property.forName("id").in(subCrit));
		List<Module> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
		
	}
	
	@Override
	public List<Module> executeQuery(String query) {
		Session session = getSession();
		SQLQuery sqlQuery = session.createSQLQuery(query);
		sqlQuery.addEntity(Module.class);
		return sqlQuery.list();
	}

	@Override
	public List<Module> findByModuleIdAndPermissionId(Integer moduleId,
			Integer permissionId) {
		try{
			DetachedCriteria subCrit = DetachedCriteria.forClass(ModulePermissions.class);
			subCrit.setProjection(Projections.property("modulePermissionsComp.moduleId"));
			Criterion lhs = Restrictions.eq("modulePermissionsComp.moduleId", moduleId);
			Criterion rhs = Restrictions.eq("modulePermissionsComp.permissionId", permissionId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			subCrit.add(exp);
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<Module> moduleList = findMany(crit);
			if(moduleList != null){
				return moduleList;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			
			return null;
		}
		
	}

	@Override
	public List<Module> findByPermissionId(Integer permId) {
		DetachedCriteria subCrit = DetachedCriteria.forClass(ModulePermissions.class);
		subCrit.add(Restrictions.eq("modulePermissionsComp.permissionId", permId));
		subCrit.setProjection(Property.forName("modulePermissionsComp.moduleId"));
		Criteria crit = createBaseCriteria();
		crit.add(Property.forName("id").in(subCrit));
		List<Module> moduleList = findMany(crit);
		if(moduleList != null){
			return moduleList;
		}else{
			return null;
		}
	}

	@Override
	public List<Module> findByModuleId(Integer moduleId) {
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("id", moduleId));
		List<Module> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public int getModuleCount() {
		try{
			Criteria crit = createBaseCriteria();
			int count = (int)crit.setProjection(Projections.rowCount()).uniqueResult().hashCode();
			return count;
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public List<Module> getModulesByPageAndCount(int page, int limitsperpage) {
		
		try{
			Criteria crit = createBaseCriteria();
			crit.setFirstResult(page * limitsperpage);
			crit.setMaxResults(limitsperpage);
			List<Module> list = findMany(crit);
			if(list != null) {
				return list;
			}else{
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(String.valueOf(e));
			return null;
			
		}
	}

	@Override
	public List<Module> getByPage(Long page) {
		try {
			Criteria crit = createBaseCriteria();
			crit.setFirstResult((int) (0 * limitsPerPage));
			crit.setMaxResults(limitsPerPage);
			List<Module> list = findMany(crit);
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
	public List<Module> executeQueryPerPage(String query, int pageNumber,
			int pageLimit) {

		Session session = getSession();
		SQLQuery sqlQuery = session.createSQLQuery(query);
		sqlQuery.addEntity(Module.class);
		sqlQuery.setFirstResult(pageNumber * pageLimit);
		sqlQuery.setMaxResults(pageLimit);
		return sqlQuery.list();
		
	}

	@Override
	public int executeQueryCount(String query) {
		// TODO Auto-generated method stub
		Session session = getSession();
		SQLQuery sqlQuery = session.createSQLQuery(query);
		sqlQuery.addEntity(Module.class);
		List list = sqlQuery.list();
		int count = 0;
		if(list != null){
			count = list.size();
		}
		return count;
	}

}
