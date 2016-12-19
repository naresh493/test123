package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.TeamRoleDao;
import com.infotree.qliktest.admin.entity.referencedata.TeamRole;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class TeamRoleServiceImpl extends AbstractQTAdminService<TeamRole>
		implements TeamRoleService {
	private static final Logger LOG = LoggerFactory.getLogger(TeamRoleServiceImpl.class);
	@Autowired
	private TeamRoleDao teamRoleDao;
	
	@Override
	public TeamRole getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamRole save(TeamRole t) {
		// TODO Auto-generated method stub
		try {
			return teamRoleDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(TeamRole t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamRole> list() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected QTAdminDao<TeamRole> getTDao() {
		// TODO Auto-generated method stub
		try {
			return teamRoleDao;
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
			return teamRoleDao.deleteByTeamId(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	

}
