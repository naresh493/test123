SETLOCAL
SET HSQLDB_HOME=E:\hsqldb-1.8.0.10
javaw -classpath %HSQLDB_HOME%/lib/hsqldb.jar org.hsqldb.util.DatabaseManager --driver org.hsqldb.jdbcDriver --url jdbc:hsqldb:hsql://localhost/qtadmin_hsqldb --user SA
