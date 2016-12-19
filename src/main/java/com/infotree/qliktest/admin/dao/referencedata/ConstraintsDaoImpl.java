/**
 * This is the implementation class for the constraints dao 
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Constraints;
import com.infotree.qliktest.admin.entity.referencedata.ModuleConstraints;

@Repository
public class ConstraintsDaoImpl extends AbstractQTAdminDao<Constraints> implements ConstraintsDao {

	private static final Logger LOG = LoggerFactory.getLogger(ConstraintsDaoImpl.class);
	
	public List<Constraints> list(){
		return super.list();
	}
	
	public Constraints save(Constraints p) {
		return super.save(p);
	}

	public String delete(Constraints t) {
		return null;
	}

	public Constraints getById(String id) {
		return super.getById(id);
	}
	@Override
	public Constraints findByName(String name) {
		
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("name", name));
			Constraints con = (Constraints)crit.uniqueResult();
			if(con != null){
				return con;
			}else{
				return null;
			}
		
	}

	@Override
	public Constraints findByNameAndNotId(String name, Integer id) {
		Criterion lhs = Restrictions.eq("name", name);
		Criterion rhs = Restrictions.ne("id", id);
		LogicalExpression exp = Restrictions.and(lhs, rhs);
		Criteria crit = createBaseCriteria();
		crit.add(exp);
		Constraints con = (Constraints)crit.uniqueResult();
		if(con != null){
			return con;
		}else{
			return null;
		}
	}

	@Override
	public int updateConstraint(Constraints constraints) {
		Session session = getSession();
		try{
			Constraints con = getById(constraints.getId());
			con.setId(constraints.getId());
			con.setName(constraints.getName());
			con.setDescription(constraints.getDescription());
			con.setModifiedBy(constraints.getModifiedBy());
			con.setModifiedDate(constraints.getModifiedDate());
			session.save(con);
		}catch(Exception e){
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		
		return 1;
	}

	@Override
	public List<Constraints> findNotById(Integer id) {
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.ne("id", id));
		List<Constraints> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<Constraints> getByOrder() {
		Criteria crit = createBaseCriteria();
		crit.addOrder(Order.desc("createdDate"));
		List<Constraints> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<Constraints> findByModuleId(Integer moduleId) {
		DetachedCriteria subCrit = DetachedCriteria.forClass(ModuleConstraints.class);
		subCrit.setProjection(Projections.property("moduleConstraintComp.constraintId"));
		subCrit.add(Restrictions.eq("moduleConstraintComp.moduleId", moduleId));
		Criteria crit = createBaseCriteria();
		crit.add(Property.forName("id").in(subCrit));
		List<Constraints> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<Constraints> findByConstraintNamePattern(String name) {
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.like("name", name,MatchMode.START));
		List<Constraints> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public List<Constraints> findByModIdAndConstraintNamePattern(
			Integer moduleId, String name) {
		DetachedCriteria subCrit = DetachedCriteria.forClass(ModuleConstraints.class);
		subCrit.setProjection(Projections.property("moduleConstraintComp.constraintId"));
		subCrit.add(Restrictions.eq("moduleConstraintComp.moduleId", moduleId));
		Criterion lhs = Restrictions.like("name", name, MatchMode.START);
		Criterion rhs = Property.forName("id").in(subCrit);
		LogicalExpression exp = Restrictions.and(lhs, rhs);
		Criteria crit = createBaseCriteria();
		crit.add(exp);
		List<Constraints> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}

}
