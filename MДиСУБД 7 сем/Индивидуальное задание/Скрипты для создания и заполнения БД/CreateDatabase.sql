--Создаем базу данных для учета сотрудников
CREATE DATABASE EmployeeAccounting
ON
(
	NAME = EmployeeAccounting_data,
	FILENAME  = 'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\EmployeeAccounting.mdf',
	SIZE = 50MB,
	MAXSIZE = 350MB,
	FILEGROWTH = 5MB
)
-- Журнал транзакций
LOG ON
(
	NAME = 'EmployeeAccounting_log',
	FILENAME  = 'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\EmployeeAccounting.ldf',
	SIZE = 50MB,
	MAXSIZE = 350MB,
	FILEGROWTH = 5%
)