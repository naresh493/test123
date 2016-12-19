package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.UserDao;
import com.infotree.qliktest.admin.entity.referencedata.Person;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class UserProfileServiceImpl extends AbstractQTAdminService<User> implements UserProfileService{

	private static final Logger LOG = LoggerFactory.getLogger(UserProfileServiceImpl.class);
	
	@Autowired
	private UserDao userDao;


	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	public User findByName(String name) {
		try {
			return userDao.findByName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getUsersByCreated(String id){
		try {
			return userDao.getUsersByCreated(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public int deleteByTenantName(String name){
		try {
			return userDao.deleteByTenantName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Sets the collection of system functions that users asigned to the given user profile have access to.
	 * @return the updated instance of UserProfile that has been persisted to the database
	 */
	/*public UserProfile updatePermissions(Integer userProfileId, Set<Function> permissions) {
		UserProfile userProfile = userProfileDao.getById(userProfileId);
		return userProfileDao.updatePermissions(userProfile, permissions);
	}*/
	
	
	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.service.AbstractQTAdminService#getTDao()
	 */
	public QTAdminDao<User> getTDao() {
		try {
			return userDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.service.referencedata.UserProfileService#getActiveUsers()
	 */
	public List<User> getActiveUsers() {
		try {
			return userDao.getActiveUsers();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.service.referencedata.UserProfileService#findExistingUser(com.infotree.qliktest.admin.entity.referencedata.User)
	 */
	public User findExistingUser(User user) {
		
		User newuser = null;
		try {
			newuser = userDao.findByNameAndSurName(user);
		} catch (NoResultException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return newuser;
	}

	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.service.referencedata.UserProfileService#getUserRoles(com.infotree.qliktest.admin.entity.referencedata.User)
	 */
	public String getUserRoles(User user) {
		try {
			return userDao.getUserRoles(user);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.service.referencedata.UserProfileService#getUserProjects(com.infotree.qliktest.admin.entity.referencedata.User)
	 */
	public String getUserProjects(User user) {
		try {
			return userDao.getUserProjects(user);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.service.referencedata.UserProfileService#getUserTeams(com.infotree.qliktest.admin.entity.referencedata.User)
	 */
	public String getUserTeams(User user) {
		try {
			return userDao.getUserTeams(user);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.service.referencedata.UserProfileService#deleteUserRelations(com.infotree.qliktest.admin.entity.referencedata.User)
	 */
	public String deleteOldRelations(User entity) {
		try {
			return userDao.deleteOldRelations(entity);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.infotree.qliktest.admin.service.referencedata.UserProfileService#insertUserRelations(com.infotree.qliktest.admin.entity.referencedata.User)
	 */
	public String insertNewRelations(User entity) {
		try {
			return userDao.insertNewRelations(entity);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	

	public List<User> getUsersFromLdap(Integer userId,String tenantName) {
		try {
			return userDao.getUsersFromLdap(userId,tenantName);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public User checkUsersFromLdap(){
		try {
			return userDao.checkUsersFromLdap();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	public String getUsersById(String id){
		try {
			return userDao.getUserById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<User> getUsersByUserNameAndPassword(String uname,String pwd){
		try {
			return userDao.getUsersByUserNameAndPassword(uname, pwd);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getUsersBasedOnCreatedBy(Integer id) {
		try {
			return userDao.getUsersBasedOnCreatedBy(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateUserById(User u) {
		try {
			return userDao.updateUserById(u);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<User> getListNotIn(Integer id) {
		try {
			return userDao.getListNotIn(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getTenantNames(Integer id) {
		try {
			return userDao.getTenantNames(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public User findByFirstName(String name){
		try {
			return userDao.findByFirstName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	
	
	@Override
	public User findByFirstNameAndEmail(User entity){
		try {
			return userDao.findByFirstNameAndEmail(entity);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	
	public int updateAppUser(User user){
		try {
			return userDao.updateAppUser(user);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<User> getImportedUsers(Integer userId) {
		try {
			return userDao.getImportedUsers(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getCreatedUsers(Integer userId) {
		try {
			return userDao.getCreatedUsers(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> executeSqlQuery(String query) {
		try {
			return userDao.executeSqlQuery(query);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getTenantName(Integer userId){
		try {
			return userDao.getTenantName(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByUserName(String username) {
		try {
			return userDao.findByUserName(username);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updatePasswordByUserName(String username, String password) {
		try {
			return userDao.updatePasswordByUserName(username, password);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updatePasswordById(Integer id, String password,java.sql.Date date) {
		try {
			return userDao.updatePasswordById(id, password,date);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updatePasswordByIdAndRequired(Integer id, String password) {
		try {
			return userDao.updatePasswordByIdAndRequired(id, password);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		    return 0;
		}
	}

	@Override
	public User findByUserNameAndMail(String username, String mail) {
		try {
			return userDao.findByUserNameandMailId(mail, username);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getAllOrderby() {
		try {
			return userDao.getAllOrderby();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByUserDetailsToUpdate(User user) {
		try {
			return userDao.findByUserDetailsToUpdate(user);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByUserNameOrMailId(String mail, String username) {
		try {
			return userDao.findByUserNameOrMailId(mail, username);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByEmail(String email) {
		try {
			return userDao.findByEmail(email);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updatePasswordByUserIdAndRequired(Integer userid,
			String password) {
		try {
			return userDao.updatePasswordByUserIdAndRequired(userid, password);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public List<User> getUsersNotAssignedProjectAndCreated(Integer projectId,
			Integer userId) {
		try {
			return userDao.getUsersNotAssignedProjectAndCreated(projectId, userId);
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
			return userDao.getUsersAssignedProjectAndCreated(projectId, userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<User> getUsersByTeamAndCreatedBy(Integer teamId, Integer userId) {
		try {
			return userDao.getUsersByTeamAndCreatedBy(teamId, userId);
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
			return userDao.getUsersNotByTeamAndCreatedBy(teamId, userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<User> getUsersByTeamId(Integer teamId) {
		return userDao.getUsersByTeamId(teamId);
	}

	@Override
	public List<User> insertLdapUsers(List<Person> personList, Integer userId,
			String tenantName) {
		
		return userDao.insertLdapUsers(personList, userId, tenantName);
	}
}
