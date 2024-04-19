use TestDatabase
If Object_ID('dbo.Client_alt', 'U') is not Null
Drop table dbo.Client_alt;
Create table Client_alt
(
	ID int Primary Key,
	Age int Not NULL,
	Name NVARCHAR(20) Not NULL,
	Surname NVARCHAR(20) Not NULL,
	Email VARCHAR(30) Not NULL,
	Phone VARCHAR(20) Not NULL
);
Alter table Client_alt
Add Address NVARCHAR(50) Not NULL Default 'нет';