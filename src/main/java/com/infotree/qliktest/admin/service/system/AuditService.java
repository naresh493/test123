package com.infotree.qliktest.admin.service.system;

import com.infotree.qliktest.admin.entity.system.AuditRecord;

public interface AuditService  {
	public AuditRecord saveRecord(AuditRecord record);
}
