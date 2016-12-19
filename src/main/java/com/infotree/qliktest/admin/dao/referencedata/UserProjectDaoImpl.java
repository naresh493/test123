/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.TeamProject;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.entity.referencedata.UserProjectComp;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@SuppressWarnings({"unchecked","unused"})

@Repository
public class UserProjectDaoImpl extends AbstractQTAdminDao<UserProject> implements UserProjectDao {
	private static final Logger LOG = LoggerFactory.getLogger(UserProjectDaoImpl.class);

	public UserProject getById(String userId) {
		return super.getById(userId);
	}

	public UserProject save(UserProject userproject) {
		return super.save(userproject);
	}

	public String delete(Project t) {
		return null;
	}

	public UserProject findByUserId(Integer userId) {
		return null;
	}

	public UserProject findByprojectId(Integer projectId) {
		return null;
	}

	public UserProject findByIds(Integer userId, Integer projectId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion userCrit = Restrictions.eq("userProjectComp.userId", userId);
			Criterion projCrit = Restrictions.eq("userProjectComp.projectId", projectId);
			LogicalExpression exp = Restrictions.and(userCrit, projCrit);
			crit.add(exp);
			UserProject up = (UserProject)crit.uniqueResult();
			if(up != null){
				return up;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	
	public List<UserProject> getByUserId(Integer userId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userProjectComp.userId", userId));
			List<UserProject> list = findMany(crit);
			if(list == null || list.size() == 0){
				return null;
			}else{
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<UserProject> getByprojectId(Integer projectId) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userProjectComp.projectId", projectId));
			List<UserProject> list = findMany(crit);
			if(list == null || list.size() == 0){
				return null;
			}else{
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public List<UserProject> getByIds(Integer userId, Integer projectId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("userProjectComp.userId", userId);
			Criterion rhs = Restrictions.eq("userProjectComp.projectId", projectId);
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			List<UserProject> list = findMany(crit);
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

	public String delete(UserProject t) {
		return super.delete(t);
	}

	public int deleteByUserId(Integer userId) {
		try {
			Session session = getSession();
			List<UserProject> list = getByUserId(userId);
			if(list != null){
				Iterator<UserProject> iterator = list.iterator();
				while(iterator.hasNext()){
					UserProject up = iterator.next();
					session.delete(up);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	
	public int deleteByProjectId(Integer projectId) {
		try {
			Session session = getSession();
			List<UserProject> list = getByprojectId(projectId);
			if(list != null){
				Iterator<UserProject> iterator = list.iterator();
				while(iterator.hasNext()){
					UserProject up = iterator.next();
					session.delete(up);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	
	public int deleteByUserIdAndProjectId(Integer userId, Integer projectId) {
		try {
			Session session = getSession();
			List<UserProject> list = getByIds(userId, projectId);
			if(list != null){
				Iterator<UserProject> iterator = list.iterator();
				while(iterator.hasNext()){
					UserProject up = iterator.next();
					session.delete(up);
				}
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	public int insertNewUserProjectEntity(UserProject userProject) {
		try {
			Session session = getSession();
			session.save(userProject);
			session.beginTransaction();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	public int deleteMultUsersFromProject(Integer projectId,String users){
		try {
			String arr[] = users.split(",");
			Session session = getSession();
			for(int i=0;i<arr.length;i++){
				List<UserProject> list = getByIds(Integer.parseInt(arr[i]), projectId);
				if(list != null){
					Iterator<UserProject> iterator = list.iterator();
					while(iterator.hasNext()){
						UserProject up = iterator.next();
						session.delete(up);
					}
				}
			}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	public List<UserProject> getByProjectId(Integer projectId){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userProjectComp.projectId", projectId));
			List<UserProject> list = findMany(crit);
			if(list == null || list.size() == 0){
				return null;
			}else{
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
}

	
	public List<User> getUsersByProjectId(Integer projectid){
		List<User> list=null;
		try {
			Session session=getSession();
			SQLQuery query=null;
			
			query=session.createSQLQuery("select user_id from user_project where project_id=:projectid");
			query.setInteger("projectid",projectid);
			list = new ArrayList<User>();
			
			List<Integer> l=query.list();
			if(l.isEmpty()){
				list.add(null);
			}else{
				String userid="";
				Iterator<Integer> iterator=l.iterator();
				while(iterator.hasNext()){
					if(userid==""){
						userid=userid+iterator.next();
					}
					else{
					userid=userid+","+iterator.next();
					}
				}
				query=session.createSQLQuery("select * from user where id in("+userid+")");
				query.addEntity(User.class);
				list=query.list();
			}
			if(list.isEmpty()){
				list.add(null);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	
}
