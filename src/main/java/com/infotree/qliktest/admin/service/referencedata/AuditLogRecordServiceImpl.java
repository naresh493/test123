package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.AuditLogRecordDao;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;

@Service
public class AuditLogRecordServiceImpl extends AbstractQTAdminService<AuditLogRecord> implements
		AuditLogRecordService {

	private static final Logger LOG = LoggerFactory.getLogger(AuditLogRecordServiceImpl.class);
	@Autowired
	private AuditLogRecordDao auditRecordDao;
	
	@Override
	public AuditLogRecord getById(Serializable id) {
		return null;
	}

	@Override
	public AuditLogRecord save(AuditLogRecord t) {
		return null;
	}

	@Override
	public String delete(AuditLogRecord t) {
		return null;
	}

	@Override
	public List<AuditLogRecord> list() {
		return null;
	}

	@Override
	public int saveRecord(AuditLogRecord auditRecord) {
		try {
			return auditRecordDao.saveRecord(auditRecord);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<AuditLogRecord> getAllRecords() {
		try {
			return auditRecordDao.getAllRecords();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<AuditLogRecord> getTDao() {
		try {
			return auditRecordDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AuditLogRecord> executeCommand(AuditType command) {
		try {
			return auditRecordDao.executeCommand(command);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AuditLogRecord> executeQuery(Query query) {
		try {
			return auditRecordDao.executeQuery(query);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AuditLogRecord> getByUserId(Integer userId) {
		try {
			return auditRecordDao.getByUserId(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int logCount(Integer userId) {
		return auditRecordDao.logCount(userId);
	}

	@Override
	public List<AuditLogRecord> getByPage(int page, int limitperpage,int userId) {
		return auditRecordDao.getByPage(page, limitperpage,userId);
	}

	@Override
	public int getNumberOfRecords(Query query) {
		return auditRecordDao.getNumberOfRecords(query);
	}

	@Override
	public int allLogCount() {
		return auditRecordDao.allLogCount();
	}
	
	@Override
	public List<AuditLogRecord> getAllLogsByPage(int page, int limitperpage) {
		return auditRecordDao.getAllLogsByPage(page, limitperpage);
	}


}
