package com.infotree.qliktest.admin.entity.errorlogs;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="parallelExecution")
public class ParallelExecutionErrorLogs extends ErrorLogs {

}
