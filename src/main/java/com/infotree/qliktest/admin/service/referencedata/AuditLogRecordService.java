/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface AuditLogRecordService extends QTAdminService<AuditLogRecord> {

	int saveRecord(AuditLogRecord auditRecord);
	
	List<AuditLogRecord> getAllRecords();
	
	List<AuditLogRecord> executeCommand(AuditType command);
	
	List<AuditLogRecord> executeQuery(Query query);
	
	List<AuditLogRecord> getByUserId(Integer userId);
	
	int logCount(Integer userId);
	
	List<AuditLogRecord> getByPage(int page,int limitperpage,int userId);
	
	int getNumberOfRecords(Query query);
	
	int allLogCount();

	List<AuditLogRecord> getAllLogsByPage(int page, int limitperpage);
}
