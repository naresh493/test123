package com.infotree.qliktest.admin.service.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.system.AuditRecordDao;
import com.infotree.qliktest.admin.entity.system.AuditRecord;
@Service
public class AuditServiceImpl implements AuditService {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuditServiceImpl.class);
	@Autowired
	private AuditRecordDao auditRecord;
	@Override
	public AuditRecord saveRecord(AuditRecord record) {
		try {
			return auditRecord.create(record);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

}
