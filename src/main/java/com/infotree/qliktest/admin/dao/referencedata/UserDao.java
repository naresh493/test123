package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Person;
import com.infotree.qliktest.admin.entity.referencedata.User;


/**
 * Contract for a type-safe implementation of AbstractQTAdminDao that adds additional search functionality.
 */
/**
 * @author v.kona
 *
 */
public interface UserDao extends QTAdminDao<User> {

	/**
	 * get a user by using the userId
	 * 
	 * @param userId
	 * @return
	 */
	User getById(String userId);
	
	/**
	 * find user by login credentials
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	User findByLoginCredentials(String userId, String password);
	
	/**
	 * find a user by user first name
	 * 
	 * @param username
	 * @return
	 */
	User findByName(String username);
	
	/**
	 * find a user by using users first name and password
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	User getUserByUserNameAndPassword(String username, String password);
	
	/**
	 * find a users role from users first name 
	 * 
	 * @param username
	 * @return
	 */
	List<User> getUsersByUserNameAndPassword(String username, String password);
	String getUserRole(String username);
	
	/**
	 * find user permissions from users first name
	 * @param username
	 * @return
	 */
	List<String> getPermissions(String username);
	
	/**
	 * fist list of active user (is_disabled=0)
	 * @return
	 */
	List<User> getActiveUsers();
	
	/**
	 * find user by first name and surname
	 * @param user
	 * @return
	 */
	User findByNameAndSurName(User user);
	
	/**
	 * find users roles
	 * 
	 * @param user
	 * @return
	 */
	String getUserRoles(User user);
	
	/**
	 * find users projects
	 * @param user
	 * @return
	 */
	String getUserProjects(User user);
	
	/**
	 * find users teams
	 * @param user
	 * @return
	 */
	String getUserTeams(User user);
	
	/**
	 * delete existing relation of a user (roles, projects, teams)
	 * @param user
	 * @return
	 */
	public String deleteOldRelations(User user);
	
	/**
	 * insert new relation for a user (roles, projects, teams)
	 * @param user
	 * @return
	 */
	public String insertNewRelations(User user);

	
	
	/**
	 * @return
	 */
	List<User> getUsersFromLdap(Integer userId,String tenantName);
	
	/**
	 * @return
	 */
	User checkUsersFromLdap();
	
	
	String getNameById(String id);
	
	
	String getUserById(String id);
	
	User findIdByFirstName(String name);
	
	List<User> getUsersByCreated(String id);
	
	int deleteByTenantName(String name);
	
	List<User> getUsersBasedOnCreatedBy(Integer id);
	
	int updateUserById(User u);
	
	List<User> getListNotIn(Integer id);
	
	List<User> getTenantNames(Integer id);
	
	User findByFirstName(String name);
	
	
	User findByFirstNameAndEmail(User entity);
	
	int updateAppUser(User user);
	
	List<User> getImportedUsers(Integer userId);
	
	List<User> getCreatedUsers(Integer userId);
	
	List<User> executeSqlQuery(String query);
	
	String getTenantName(Integer userid);
	
	User findByUserName(String username);
	
	int updatePasswordByUserName(String username,String password);
	
	int updatePasswordById(Integer id,String password,java.sql.Date date);
	
	int updatePasswordByIdAndRequired(Integer id, String password);
	
	User findByUserNameandMailId(String mail,String username);
	
	User findByUserNameOrMailId(String mail,String username);
	
	User findByUserDetailsToUpdate(User user);
	
	List<User> getAllOrderby();
	
	User findByEmail(String email);
	
	int updatePasswordByUserIdAndRequired(Integer userid,String password);
	
	List<User> getUsersNotAssignedProjectAndCreated(Integer projectId,Integer userId);
	
	List<User> getUsersAssignedProjectAndCreated(Integer projectId,Integer userId);
	
	List<User> getUsersByTeamAndCreatedBy(Integer teamId,Integer userId);
	
	List<User> getUsersNotByTeamAndCreatedBy(Integer teamId,Integer userId);
	
	List<User> getUsersByTeamId(Integer teamId);
	
	List<User> insertLdapUsers(List<Person> personList,Integer userId,String tenantName);
}
