package com.infotree.qliktest.admin.dao.referencedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.common.QlikTestAdminException;
import com.infotree.qliktest.admin.common.exception.ExceptionCode;
import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Person;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;
import com.infotree.qliktest.admin.web.controller.PasswordHashing;


/**
 * Data access object for the User entity.
 * Provides a type-safe implementation of AbstractVRDao and adds additional search functionality.
 */
@Repository
public class UserDaoImpl extends AbstractQTAdminDao<User> implements UserDao {

	private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
	
	
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Autowired
	private ContextSource contextSource;
	
	public User getById(String userId) {
		return super.getById(userId);
	}

	/**
	 * @param userId
	 * @param password
	 * @return
	 */
	public User getByName(String userId, String password) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion newuser = Restrictions.eq("firstName",userId);
			
			Criterion newpass = Restrictions.eq("password",password);
			LogicalExpression andExp = Restrictions.and(newuser, newpass);
			
			crit.add(andExp);
			User user = (User)crit.uniqueResult();
			if(user != null){
				return user;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public User findByLoginCredentials(String userId, String password) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion newuser = Restrictions.eq("userName",userId);
			Criterion newpass = Restrictions.eq("password",password);
			LogicalExpression andExp = Restrictions.and(newuser, newpass);
			crit.add(andExp);
			User user = (User)crit.uniqueResult();
			if(user != null){
				return user;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	

	public User findByName(String username) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userName", username));
			User user = (User)crit.uniqueResult();
			if(user != null){
				return user;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	public String getUserById(String id){
	
		Criteria crit = createBaseCriteria();
		crit.add(Restrictions.eq("id", Integer.parseInt(id)));
		User u = findOne(crit);
		return u.getUserName();
	}
	public User findByFirstName(String name){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("firstName", name));
			User user = (User)crit.uniqueResult();
			if(user != null){
				return user;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public User getUserByUserNameAndPassword(String username, String password) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion newuser = Restrictions.eq("userName",username);
			Criterion newpass = Restrictions.eq("password",password);
			LogicalExpression andExp = Restrictions.and(newuser, newpass);
			crit.add(andExp);
			User user = (User)crit.uniqueResult();
			if(user != null){
				return user;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}
	public List<User> getUsersByUserNameAndPassword(String username, String password) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion newuser = Restrictions.eq("userName",username);
			Criterion newpass = Restrictions.eq("password",password);
			LogicalExpression andExp = Restrictions.and(newuser, newpass);
			crit.add(andExp);
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null; 
		}
		
	}
	
	public List<User> getUsersByCreated(String id){
		try {
			Criteria crt = createBaseCriteria();
			crt.add(Restrictions.eq("createdBy", id));
			List<User> userList = findMany(crt);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}
	

	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.dao.referencedata.UserDao#getUserRole(java.lang.String)
	 */
	public String getUserRole(String username) {
		String result;
		try {
			Session session = getSession();
			session.beginTransaction();
			result = "";
			List<String> listResult = null;
			listResult = (List<String>)session.createSQLQuery("select r.name from user u, role r, user_role ur where u.id = ur.user_id and r.id = ur.role_id and u.user_name ='"+username+"'").list();
			if(listResult != null && listResult.size() > 0){
				for (String string : listResult) {
					result += string + ",";
				}
				result = result.substring(0,result.length()-1);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return result;
		
	}
	
	public List<String> getPermissions(String username) {
		Session session=null;
		try {
			session = getSession();
			session.beginTransaction();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return (List<String>)session.createSQLQuery(" select name from permissions where id in(select permission_id from role_permissions where role_id in(select role_id from user_role where user_id in(select id from user where user_name='"+username+"')))").list();
		
	}

	public List<User> getActiveUsers() {
		try {
			Criteria crit = createBaseCriteria();
			Criterion isDisabled = Restrictions.eq("disabled",(byte)0);
			crit.add(isDisabled);
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public User findByNameAndSurName(User user) {

		try {
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.eq("firstName",user.getFirstName());
			Criterion rhs = Restrictions.eq("surName",user.getSurName());
			Criterion ths = Restrictions.eq("emailAddress",user.getEmailAddress());
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			LogicalExpression logicExp = Restrictions.and(exp, ths);
			crit.add(logicExp);
			User u = (User)crit.uniqueResult();
			if(u != null){
				return u;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	

	public String getUserRoles(User user) {
		String result=null;
		try {
			Session session = getSession();
			session.beginTransaction();
			result = "";
			List<String> listResult = null;
			listResult = (List<String>)session.createSQLQuery("select r.name from user u, role r, user_role ur "
					+ "where u.id = ur.user_id and r.id = ur.role_id and u.first_name='"+user.getFirstName()+"'").list();
			if(listResult != null && listResult.size() > 0){
				for (String string : listResult) {
					result += string + ",";
				}
				result = result.substring(0,result.length()-1);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return result;
	}

	public String getUserProjects(User user) {
		
		String result=null;
		try {
			Session session = getSession();
			session.beginTransaction();
			result = "";
			List<String> listResult = null;
			listResult = (List<String>)session.createSQLQuery("select p.name from user u, project p, user_project up "
					+ "where u.id = up.user_id and p.id = up.project_id and u.first_name='"+user.getFirstName()+"'").list();
			if(listResult != null && listResult.size() > 0){
				for (String string : listResult) {
					result += string + ",";
				}
				result = result.substring(0,result.length()-1);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return result;
		
	}

	public String getUserTeams(User user) {
		String result=null;
		try {
			Session session = getSession();
			session.beginTransaction();
			result = "";
			List<String> listResult = null;
			listResult = (List<String>)session.createSQLQuery("select p.name from user u, team p, user_team up "
					+ "where u.id = up.user_id and p.id = up.team_id and u.first_name='"+user.getFirstName()+"'").list();
			if(listResult != null && listResult.size() > 0){
				for (String string : listResult) {
					result += string + ",";
				}
				result = result.substring(0,result.length()-1);
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return result;
	}

	
	public String insertNewRelations(User entity){
		return "success";
	}
	

	@Override
	public int updateAppUser(User u){
		try {
			Session session=getSession();
			User user = getById(u.getId());
			user.setUserName(u.getUserName());
			user.setFirstName(u.getFirstName());
			user.setSurName(u.getSurName());
			user.setEmailAddress(u.getEmailAddress());
			user.setLandline(u.getLandline());
			user.setMobile(u.getMobile());
			user.setModifiedBy(u.getModifiedBy());
			user.setModifiedDate(u.getModifiedDate());
			user.setId(u.getId());
			user.setDomain(u.getDomain());
			user.setExperience(u.getExperience());
			user.setNoOfProjectsWorked(u.getNoOfProjectsWorked());
			session.save(user);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public List<User> getImportedUsers(Integer userId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion createdCrit = Restrictions.eq("createdBy", userId);
			Criterion impCrit = Restrictions.eq("importedFromLdap", 1);
			LogicalExpression exp = Restrictions.and(createdCrit, impCrit);
			crit.add(exp);
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getCreatedUsers(Integer userId) {
		try {
			Criteria crit = createBaseCriteria();
			Criterion createdCrit = Restrictions.eq("createdBy", userId);
			Criterion impCrit = Restrictions.eq("importedFromLdap", 0);
			LogicalExpression exp = Restrictions.and(createdCrit, impCrit);
			crit.add(exp);
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<User> executeSqlQuery(String sqlquery){
		SQLQuery query;
		try {
			Session session = getSession();
			query = session.createSQLQuery(sqlquery);
			query.addEntity(User.class);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return query.list();
	}
	
	

	@Override
	public String deleteOldRelations(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersFromLdap(Integer userId, String tenantName) {
		List<Person> personList = null;
		try {
			personList = ldapTemplate.search("", "objectClass=person", new PersonAttributesMapper());
		} catch (Exception e) {
			throw new QlikTestAdminException(ExceptionCode.LDAPERROR, e.getMessage());
		}finally{
			try{
				LdapUtils.closeContext(contextSource.getReadWriteContext());
			} catch (Exception e2) {
				throw new QlikTestAdminException(ExceptionCode.LDAPERROR, e2.getMessage());
			}
		}
		return insertLdapUsers(personList,userId,tenantName);
	}

	public List<User> insertLdapUsers(List<Person> personList,Integer userId,String tenantName){
		List<User> userList = new ArrayList<User>();
		try {
			if(personList != null && personList.size() > 0){
				for (Person p: personList) {
					User userMail = null;
					User user = null;
					if(p != null){
						if(p.geteMail() != null){
							 userMail = findByEmail(p.geteMail());
						}
						if(p.getUserName() != null){
							user = findByUserName(p.getUserName());
						}	
					}
					
					 
					if(p != null && userMail == null && user == null){
						char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
						StringBuilder sb = new StringBuilder();
						Random random = new Random();
						for (int i = 0; i < 10; i++) {
						    char c = chars[random.nextInt(chars.length)];
						    sb.append(c);
						}
						User u = new User();
						u.setFirstName(p.getFirstName());
						u.setUserName(p.getUserName());
						u.setSurName(p.getSurName());
						u.setEmailAddress(p.geteMail());
						u.setLandline(p.getLandline());
						u.setMobile(p.getMobile());
						u.setPassword(PasswordHashing.cryptWithMD5(sb.toString()));
						u.setDisabled(false);
						u.setCreatedBy(userId+"");
						u.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
						u.setModifiedBy(userId+"");
						u.setModifiedDate(new java.sql.Date(System.currentTimeMillis()));
						u.setIsPasswordChangeRequired((byte)1);
						u.setImportedFromLdap((byte)1);
						u.setNda((byte)1);
						userList.add(u);
						save(u);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return userList;
	}
	private class PersonAttributesMapper implements AttributesMapper{
		public Person mapFromAttributes(Attributes attrs) throws NamingException {
			Person person = null;
			try {
				if(((String)attrs.get("cn").get() != null) && !((String)attrs.get("cn").get()).startsWith("SD")){
					person = new Person();
					person.setFullName((attrs.get("cn") == null) ? "" : (String)attrs.get("cn").get());
					String firstName = (attrs.get("cn") == null) ? "" : ((String)attrs.get("cn").get()).substring(0,((String)attrs.get("cn").get()).indexOf(" ")) ;
					person.setFirstName(firstName);
					
					String surname = (attrs.get("sn") == null) ? "" : (String)attrs.get("sn").get();
					person.setSurName((surname == null || surname.isEmpty()) ? firstName : surname );
					person.setUserName(firstName+surname);
					String email = (attrs.get("mail") == null) ? "" : (String)attrs.get("mail").get();
					person.seteMail((email == null || email.isEmpty()) ? (String)attrs.get("userPrincipalName").get() : email );
					person.setLandline("0");
					String mobile = (attrs.get("mobile") == null) ? "" : (String)attrs.get("mobile").get();
					String telephoneNumber = (attrs.get("telephoneNumber") == null) ? "" : (String)attrs.get("telephoneNumber").get();
					if(mobile != null && !mobile.isEmpty()){
						person.setMobile(mobile);
					}else if(telephoneNumber != null && !telephoneNumber.isEmpty()){
						person.setMobile(telephoneNumber);
					}else if((mobile == null || mobile.isEmpty()) && (telephoneNumber == null || telephoneNumber.isEmpty())){
						person.setMobile("");
					}
					String description = (attrs.get("title") == null) ? "" : (String)attrs.get("title").get();
					person.setDescription((description == null || description.isEmpty()) ? "" : description );
				}
			} catch (Exception e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
			return person;
		}
	}
	public User checkUsersFromLdap(){
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("importedFromLdap", 1));
			User u = (User)crit.uniqueResult();
			if(u != null){
				return u;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
			
		}
		
	}
	

	@Override
	public String getNameById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findIdByFirstName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByTenantName(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> getUsersBasedOnCreatedBy(Integer id) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("createdBy", id+""));
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateUserById(User u) {
		// TODO Auto-generated method stub
		try {
			User user = getById(u.getId());
			user.setId(u.getId());
			user.setFirstName(u.getFirstName());
			user.setSurName(u.getSurName());
			user.setEmailAddress(u.getEmailAddress());
			user.setLandline(u.getLandline());
			user.setMobile(u.getMobile());
			user.setModifiedBy(u.getModifiedBy());
			user.setModifiedDate(u.getModifiedDate());
			Session session=getSession();
			session.save(user);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
		
	}

	@Override
	public List<User> getListNotIn(Integer id) {
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.ne("id", id));
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
			
		}
	}

	@Override
	public List<User> getTenantNames(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByFirstNameAndEmail(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTenantName(Integer userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUserName(String username) {
		User user;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("userName", username));
			user = (User)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return user;
	}

	@Override
	public int updatePasswordByUserName(String username,String password) {
		try {
			User u = findByName(username);
			u.setPassword(password);
			u.setIsPasswordChangeRequired((byte)1);
			Session session = getSession();
			session.save(u);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
		
	}

	@Override
	public int updatePasswordById(Integer id, String password,java.sql.Date date) {
		try {
			User u = getById(id);
			u.setId(id);
			u.setPassword(password);
			u.setPasswordChangedDate(new java.util.Date());
			Session session = getSession();
			session.save(u);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	
	@Override
	public int updatePasswordByIdAndRequired(Integer id, String password) {
		// TODO Auto-generated method stub
		try {
			User u = getById(id);
			u.setId(id);
			u.setPassword(password);
			u.setIsPasswordChangeRequired((byte)0);
			Session session = getSession();
			session.save(u);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
		
	}

	@Override
	public User findByUserNameandMailId(String mail, String username) {
		// TODO Auto-generated method stub
		try {
			Criteria crit = createBaseCriteria();
			Criterion namecrt = Restrictions.eq("userName", username);
			Criterion emailcrit = Restrictions.eq("emailAddress", mail);
			LogicalExpression exp = Restrictions.and(namecrt, emailcrit);
			crit.add(exp);
			User u = (User)crit.uniqueResult();
			if(u != null){
				return u;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<User> getAllOrderby() {
		// TODO Auto-generated method stub
		try {
			Criteria crit = createBaseCriteria();
			crit.addOrder(Order.desc("id"));
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public User findByUserDetailsToUpdate(User u) {
		// TODO Auto-generated method stub
		try {
			Criteria crit = createBaseCriteria();
			Criterion namecrt = Restrictions.eq("userName", u.getUserName());
			Criterion emailcrit = Restrictions.eq("emailAddress", u.getEmailAddress());
			Criterion idcrit = Restrictions.ne("id", u.getId());
			LogicalExpression exp = Restrictions.and(namecrt, emailcrit);
			LogicalExpression expression = Restrictions.and(exp, idcrit);
			crit.add(expression);
			User user = (User)crit.uniqueResult();
			if(user != null){
				return user;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public User findByUserNameOrMailId(String mail, String username) {
		// TODO Auto-generated method stub
		try {
			Criteria crit = createBaseCriteria();
			Criterion namecrt = Restrictions.eq("userName", username);
			Criterion emailcrit = Restrictions.eq("emailAddress", mail);
			LogicalExpression exp = Restrictions.or(namecrt, emailcrit);
			crit.add(exp);
			User u = (User)crit.uniqueResult();
			if(u != null){
				return u;
			}else{
				return null;
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		User u;
		try {
			Criteria crit = createBaseCriteria();
			crit.add(Restrictions.eq("emailAddress", email));
			u = (User)crit.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return u;
		
	}
	
	@Override
	public int updatePasswordByUserIdAndRequired(Integer userid,String password) {
		// TODO Auto-generated method stub
		
		try {
			User u = getById(userid);
			u.setId(userid);
			u.setPassword(password);
			u.setIsPasswordChangeRequired((byte)1);
			Session session = getSession();
			session.save(u);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 1;
	}
	@Override
	public List<User> getUsersNotAssignedProjectAndCreated(Integer projectId,
			Integer userId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(UserProject.class);
			subCrit.add(Restrictions.eq("userProjectComp.projectId", projectId));
			subCrit.setProjection(Projections.property("userProjectComp.userId"));
			
			Criteria crit = createBaseCriteria();
			Criterion lhs = Restrictions.not(Property.forName("id").in(subCrit));
			Criterion rhs = Restrictions.eq("createdBy", userId+"");
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getUsersAssignedProjectAndCreated(Integer projectId,
			Integer userId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(UserProject.class);
			subCrit.add(Restrictions.eq("userProjectComp.projectId", projectId));
			subCrit.setProjection(Projections.property("userProjectComp.userId"));
			
			Criteria crit = createBaseCriteria();
			Criterion lhs = Property.forName("id").in(subCrit);
			Criterion rhs = Restrictions.eq("createdBy", userId+"");
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			crit.add(exp);
			List<User> userList = findMany(crit);
			
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public List<User> getUsersByTeamAndCreatedBy(Integer teamId, Integer userId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(UserTeam.class);
			subCrit.setProjection(Projections.property("userTeamComp.userId"));
			subCrit.add(Restrictions.eq("userTeamComp.teamId", teamId));
			Criterion lhs = Property.forName("id").in(subCrit);
			Criterion rhs = Restrictions.eq("createdBy", userId+"");
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getUsersNotByTeamAndCreatedBy(Integer teamId,
			Integer userId) {
		try {
			DetachedCriteria subCrit = DetachedCriteria.forClass(UserTeam.class);
			subCrit.setProjection(Projections.property("userTeamComp.userId"));
			subCrit.add(Restrictions.eq("userTeamComp.teamId", teamId));
			Criterion lhs = Restrictions.not(Property.forName("id").in(subCrit));
			Criterion rhs = Restrictions.eq("createdBy", userId+"");
			LogicalExpression exp = Restrictions.and(lhs, rhs);
			Criteria crit = createBaseCriteria();
			crit.add(exp);
			List<User> userList = findMany(crit);
			if(userList == null || userList.size() == 0){
				return null;
			}else{
				return userList;
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<User> getUsersByTeamId(Integer teamId) {
		DetachedCriteria subCrit = DetachedCriteria.forClass(UserTeam.class);
		subCrit.add(Restrictions.eq("userTeamComp.teamId", teamId));
		subCrit.setProjection(Property.forName("userTeamComp.userId"));
		Criteria crit = createBaseCriteria();
		crit.add(Property.forName("id").in(subCrit));
		List<User> list = findMany(crit);
		if(list != null){
			return list;
		}else{
			return null;
		}
	}
	
}
