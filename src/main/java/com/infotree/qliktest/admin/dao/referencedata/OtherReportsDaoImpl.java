package com.infotree.qliktest.admin.dao.referencedata;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.DashBoardReports;
import com.infotree.qliktest.admin.entity.referencedata.ModuleReportsAssign;
import com.infotree.qliktest.admin.entity.referencedata.RoleReport;

@Repository
public class OtherReportsDaoImpl extends AbstractQTAdminDao<DashBoardReports>
		implements OtherReportsDao {
	private static final Logger LOG = LoggerFactory.getLogger(OtherReportsDaoImpl.class);
	
	@Override
	public DashBoardReports getById(Serializable id) {
		// TODO Auto-generated method stub
		return super.getById(id);
	}
	 
	@Override
	public DashBoardReports save(DashBoardReports t) {
		// TODO Auto-generated method stub
		return  super.save(t);
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
			Criteria crit = createBaseCriteria();
			List<DashBoardReports> list = findMany(crit);
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
	public List<DashBoardReports> getNames() {
		try {
			Criteria crit = createBaseCriteria();
			List<DashBoardReports> list = findMany(crit);
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
	public List<DashBoardReports> getByReportId(List<Integer> reportIds) {
		
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("id", reportIds));
			List<DashBoardReports> list = findMany(crit);
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
	public int updateReport(DashBoardReports d) {
		try {
			Session session = getSession();
			DashBoardReports dashBoardReports = getById(d.getId());
			dashBoardReports.setDescription(d.getDescription());
			dashBoardReports.setDisplayname(d.getDisplayname());
			dashBoardReports.setModifiedBy(d.getModifiedBy());
			dashBoardReports.setModifiedDate(d.getModifiedDate());
			session.save(dashBoardReports);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public DashBoardReports findByName(String name) {
		DashBoardReports list=null;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("name", name));
			list = (DashBoardReports)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return list;
		
	}
	
	@Override
	public List<DashBoardReports> getReportsByModuleId(Integer moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ModuleReportsAssign.class);
			subCrit.add(Restrictions.eq("moduleReportComp.moduleId", moduleId));
			subCrit.setProjection(Projections.property("moduleReportComp.reportId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").notIn(subCrit));
			List<DashBoardReports> list = findMany(crit);
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
	public List<DashBoardReports> getReportsByModuleId1(Integer moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ModuleReportsAssign.class);
			subCrit.add(Restrictions.eq("moduleReportComp.moduleId", moduleId));
			subCrit.setProjection(Projections.property("moduleReportComp.reportId"));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<DashBoardReports> list = findMany(crit);
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
	public List<DashBoardReports> getReportsByRoleId(Integer roleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RoleReport.class);
			subCrit.setProjection(Projections.property("roleReportComp.reportId"));
			subCrit.add(Property.forName("roleReportComp.roleId").in(new Integer[]{roleId}));
			
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<DashBoardReports> list = findMany(crit);
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
	public List<DashBoardReports> getReportsByRoleId1(Integer roleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(RoleReport.class);
			subCrit.setProjection(Projections.property("roleReportComp.reportId"));
			subCrit.add(Property.forName("roleReportComp.roleId").in(new Integer[]{roleId}));
			
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.not(Property.forName("id").in(subCrit)));
			List<DashBoardReports> list = findMany(crit);
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
	public List<DashBoardReports> findByModuleId(String moduleId) {
		try {
			if(moduleId != null){
				String modArr[] = moduleId.split(",");
				Integer arr[] = new Integer[modArr.length];
				for(int i=1;i<modArr.length;i++){
					arr[i] = Integer.parseInt(modArr[i]);
				}
				
				DetachedCriteria subCrit = DetachedCriteria.forClass(ModuleReportsAssign.class);
				subCrit.setProjection(Projections.property("moduleReportComp.reportId"));
				subCrit.add(Restrictions.in("moduleReportComp.moduleId", arr));
				Criteria crit = createBaseCriteria();
				crit.add(Property.forName("id").in(subCrit));
				List<DashBoardReports> list = findMany(crit);
				if(list != null){
					return list;
				}else{
					return null;
				}
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
	public List<DashBoardReports> findByNotInModuleId(String moduleId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(ModuleReportsAssign.class);
			subCrit.setProjection(Projections.property("moduleReportComp.reportId"));
			subCrit.add(Restrictions.ne("moduleReportComp.moduleId", new Integer[]{Integer.parseInt(moduleId)}));
			Criteria crit = createBaseCriteria();
			crit.add(Property.forName("id").in(subCrit));
			List<DashBoardReports> list = findMany(crit);
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
	
    
}
