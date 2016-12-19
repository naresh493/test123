SETLOCAL

DEL qtadmin_hsqldb.log
DEL qtadmin_hsqldb.lck

ECHO CREATE SCHEMA PUBLIC AUTHORIZATION DBA>qtadmin_hsqldb.script
ECHO CREATE USER SA PASSWORD "">>qtadmin_hsqldb.script
ECHO GRANT DBA TO SA>>qtadmin_hsqldb.script
ECHO SET WRITE_DELAY 10>>qtadmin_hsqldb.script

SET HSQLDB_HOME=D:\hsqldb


START java -classpath %HSQLDB_HOME%/lib/hsqldb.jar org.hsqldb.Server -database.0 file:qtadmin_hsqldb -dbname.0 qtadmin_hsqldb

START javaw -classpath %HSQLDB_HOME%/lib/hsqldb.jar org.hsqldb.util.DatabaseManager --driver org.hsqldb.jdbcDriver --url jdbc:hsqldb:hsql://localhost/qtadmin_hsqldb --user SA --script E:\Hadoop\QlikTestAdmin\database\001_createTables.sql
