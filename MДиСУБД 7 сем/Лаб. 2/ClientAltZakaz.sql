use TestDatabase
if OBJECT_ID('dbo.Zakaz', 'U') is Not NULL
Drop Table dbo.Zakaz;
Create Table Zakaz
(
	ID int Identity,
	ClientID int,
	Created Date
);

Alter Table Zakaz
Add Foreign Key (ClientID) References Client_alt(ID);

Alter Table Zakaz
Add Primary Key (ID);