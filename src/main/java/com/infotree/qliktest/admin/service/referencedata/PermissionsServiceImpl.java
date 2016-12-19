/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.PermissionsDao;
import com.infotree.qliktest.admin.entity.referencedata.Permissions;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class PermissionsServiceImpl extends AbstractQTAdminService<Permissions> implements PermissionsService{
	private static final Logger LOG = LoggerFactory.getLogger(PermissionsServiceImpl.class);
	@Autowired
	private PermissionsDao permissionsDao;


	public QTAdminDao<Permissions> getTDao() {
		try {
			return permissionsDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	public List<Permissions> getPermissionsByRoleId(List<Integer> roleIds) {
		try {
			return permissionsDao.getPermissionsByRoleId(roleIds);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Permissions> getPermissionsNotInRoleId(List<Integer> roleIds) {
		try {
			return permissionsDao.getPermissionsNotInRoleId(roleIds);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public Permissions findByName(String name) {
		try {
			return permissionsDao.findByName(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public void save(List<Permissions> permissionsList){
		try {
			permissionsDao.save(permissionsList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	public List<Permissions> getpermissionsByRoelId(Integer roleId){
		try {
			return permissionsDao.getpermissionsByRoleId(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	
	public int getMaxId(){
		try {
			return permissionsDao.getMaxId();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}


	@Override
	public List<Permissions> getPermissionsNotIn(Integer id) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getPermissionsNotIn(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public Permissions getPermissionsByNameAndId(String name, Integer id) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getPermissionsByNameAndId(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public int updatePermissions(Permissions permission) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.updatePermissions(permission);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}


	@Override
	public List<Permissions> getOrderBydesc() {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getOrderBydesc();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<Permissions> searchByPettern(String name) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.searchByPettern(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<Permissions> getActivePermissions() {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getActivePermissions();
		} catch (Exception e) {

			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Permissions> getPermissionsNotInModule(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getPermissionsNotInModule(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Permissions> getByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Permissions> getByRoleId(Integer roleid) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getByroleId(roleid);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Permissions> getNotByRoleId(Integer roleid) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getNotByRoleId(roleid);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Permissions> getPermissionsByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getPermissionsByRoleId(roleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public List<Permissions> getByPage(Long page) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getByPage(page);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Permissions> getByPageByPattern(String pattern, Long page) {
		// TODO Auto-generated method stub
		try {
			return permissionsDao.getByPageByPattern(pattern, page);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Permissions> getStartingRecords() {
		try{
			return permissionsDao.getStartingRecords();
		}catch(Exception e){
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public int permissionsCount() {
		try{
			return permissionsDao.permissionsCount();
		}catch(Exception e){
			LOG.error(String.valueOf(e));
			return 0;
		}
	}


	@Override
	public List<Permissions> getPermissionsByPageAndCount(int page,
			int limitsPerPage) {
		try{
			return permissionsDao.getPermissionsByPageAndCount(page, limitsPerPage);
		}catch(Exception e){
			LOG.error(String.valueOf(e));
			return null;
		}
	}

	@Override
	public List<Permissions> getPermissionsByPageAndCountByPattern(
			String pattern, int page, int limitsPerPage) {
		try{
			return permissionsDao.getPermissionsByPageAndCountByPattern(pattern,page, limitsPerPage);
		}catch(Exception e){
			LOG.error(String.valueOf(e));
			return null;
		}
	}

	@Override
	public int permissionsCountByPattern(String pattern) {
		try{
			return permissionsDao.permissionCountByPattern(pattern);
		}catch(Exception e){
			LOG.error(String.valueOf(e));
			return 0;
		}
	}


	@Override
	public List<Permissions> getByPageEdit(Long page) {
		try {
			return permissionsDao.getByPage(page);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}


	

	
}
