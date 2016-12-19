package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.referencedata.UserTeamDao;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;
@Service
public class UserTeamServiceImpl implements UserTeamService {

	private static final Logger LOG = LoggerFactory.getLogger(UserTeamServiceImpl.class);
	@Autowired
	private UserTeamDao userTeamDao;
	
	
	
	@Override
	public UserTeam getById(Serializable id) {
		// TODO Auto-generated method stub
		try {
			return userTeamDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserTeam save(UserTeam userTeam) {
		// TODO Auto-generated method stub
		
		try {
			return userTeamDao.save(userTeam);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(UserTeam t) {
		// TODO Auto-generated method stub
		try {
			return userTeamDao.delete(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<UserTeam> list() {
		// TODO Auto-generated method stub
		try {
			return userTeamDao.list();
		} catch (Exception e) {

			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public int insertNewUserTeamEntity(UserTeam userteam){
		try {
			return userTeamDao.insertNewUserTeamEntity(userteam);
		} catch (Exception e) {

			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public List<User> getUsersByTeamId(Integer teamId){
		
		List<User> lusers;
		try {
			lusers = userTeamDao.getUsersByTeamId(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
		return lusers;
	}
	
	@Override
	public int deleteMultUsersFromTeam(Integer teamId,String users){
		
		try {
			return userTeamDao.deleteMultUsersFromTeam(teamId, users);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	
	}
	@Override
	public int getNoOfUsers(Integer teamId){
		try {
			return userTeamDao.getNoOfUsers(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public List<UserTeam> getByteamId(Integer teamId){
		try {
			return userTeamDao.getByteamId(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<UserTeam> findByUserId(Integer userId) {
		// TODO Auto-generated method stub
		try {
			return userTeamDao.findByUserId(userId);
		} catch (Exception e) {

			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	

	@Override
	public int deleteByTeamId(Integer teamId) {
		// TODO Auto-generated method stub
		try {
			return userTeamDao.deleteByTeamId(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteByUserId(Integer userId) {
		try{
			return userTeamDao.deleteByUserId(userId);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	
	
}
