CREATE DATABASE TestDatabase
ON
(
	NAME = TestDatabase_data,
	FILENAME  = 'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\test.mdf',
	SIZE = 10MB,
	MAXSIZE = 100MB,
	FILEGROWTH = 5MB
)
LOG ON
(
	NAME = 'TestDatabase_log',
	FILENAME  = 'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\test.ldf',
	SIZE = 10MB,
	MAXSIZE = 100MB,
	FILEGROWTH = 5%
)