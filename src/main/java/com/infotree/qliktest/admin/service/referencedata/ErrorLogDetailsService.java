package com.infotree.qliktest.admin.service.referencedata;

import java.io.InputStream;
import java.util.List;

import com.infotree.qliktest.admin.entity.errorlogs.ErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.SocketWebLogPojo;
import com.infotree.qliktest.admin.helperpojo.ErrorLogFormPojo;

public interface ErrorLogDetailsService {

	List<ErrorLogs> getErrorLogsByDate(ErrorLogFormPojo errorLogFormPojo,int page,int pageLimit);
	/*List<ErrorLogs> getAllErrorLogs();*/
	List<SocketWebLogPojo> getSocketWebLogs(ErrorLogFormPojo errorLogFormPojo);
	InputStream getSocketWebFile(String filename);
	int getColumnCount(ErrorLogFormPojo errorLogFormPojo);
}
