use TestDB1
Create table Client_VKK
(
	ID int Primary Key Identity,
	Age int Default 18,
	Name NVARCHAR(20) Not Null,
	Surname NVARCHAR(20) Not Null,
	Email VARCHAR(30) UNIQUE,
	Phone VARCHAR(20) UNIQUE
)

Create table Zakaz_VKK
(	
	ID int Primary Key Identity,
	ClientID int REFERENCES Client_VKK(ID),
	Created DATE
)