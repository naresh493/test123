/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.infotree.qliktest.admin.dao.AbstractQTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.system.AuditType;

@Repository
public class AuditLogRecordDaoImpl extends AbstractQTAdminDao<AuditLogRecord> implements AuditLogRecordDao {

	private static final Logger LOG = LoggerFactory.getLogger(AuditLogRecordDaoImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public int saveRecord(AuditLogRecord auditRecord) {
		try {
			
			mongoTemplate.save(auditRecord);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<AuditLogRecord> getAllRecords() {
		List<AuditLogRecord> list=null;
		try {
			list = mongoTemplate.findAll(AuditLogRecord.class);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<AuditLogRecord> executeCommand(AuditType command) {
		List<AuditLogRecord> list=null;
		try {
			list = mongoTemplate.find(new Query(Criteria.where("actionType").is(command)), AuditLogRecord.class);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<AuditLogRecord> executeQuery(Query query) {
		List<AuditLogRecord> list=null;
		try {
			list = mongoTemplate.find(query, AuditLogRecord.class);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<AuditLogRecord> getByUserId(Integer userId) {
		List<AuditLogRecord> list=null;
		try {
			list = mongoTemplate.find(new Query(Criteria.where("userId").is(userId)), AuditLogRecord.class);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int logCount(Integer userId) {
		long count = mongoTemplate.count(new Query(Criteria.where("userId").is(userId)), AuditLogRecord.class);
		return (int) count;
	}

	@Override
	public List<AuditLogRecord> getByPage(int page,int limitperpage,int userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		query.skip(page*limitperpage);
		query.limit(limitperpage);
		List<AuditLogRecord> list = mongoTemplate.find(query, AuditLogRecord.class);
		return list;
	}

	@Override
	public int getNumberOfRecords(Query query) {
		return (int)mongoTemplate.count(query, AuditLogRecord.class);
	}

	@Override
	public int allLogCount() {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").ne(null));
		return (int)mongoTemplate.count(query, AuditLogRecord.class);
	}

	@Override
	public List<AuditLogRecord> getAllLogsByPage(int page, int limitperpage) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").ne(null));
		query.skip(page*limitperpage);
		query.limit(limitperpage);
		List<AuditLogRecord> list = mongoTemplate.find(query, AuditLogRecord.class);
		return list;		
	}
}
