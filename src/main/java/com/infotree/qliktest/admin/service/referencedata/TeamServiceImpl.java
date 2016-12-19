package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.TeamDao;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class TeamServiceImpl extends AbstractQTAdminService<Team> implements TeamService{

	private static final Logger LOG = LoggerFactory.getLogger(TeamServiceImpl.class);
	
	@Autowired
	private TeamDao teamDao;


	public Team findByName(String teamName) {
		try {
			return teamDao.findByName(teamName);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	public List<Team> list() {
		try {
			return teamDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> getActiveTeams(){
		try {
			return teamDao.getTeamList();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> getTeamsById(String id){
		try {
			return teamDao.getTeamsById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public QTAdminDao<Team> getTDao() {
		try {
			return teamDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	public int deleteUsersByTeamId(Integer teamId){
		int i=0;
		try {
			i = teamDao.deleteUsersByTeamId(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<Team> executeSqlQuery(String query) {
		// TODO Auto-generated method stub
		try {
			return teamDao.executeSQLQuery(query);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Team findByNameAndIdNot(String name, Integer id) {
		// TODO Auto-generated method stub
		try {
			return teamDao.findByNameAndIdNot(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateTeam(Team team) {
		// TODO Auto-generated method stub
		try {
			return teamDao.updateTeam(team);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Team> getAssignedTeamsForUser(Integer userId, Integer createdBy) {
		try{
			return teamDao.getAssignedTeamsForUser(userId, createdBy);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Team> getAvailableTeamsForUser(Integer userId, Integer createdBy) {
		try{
			return teamDao.getAvailableTeamsForUser(userId, createdBy);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
