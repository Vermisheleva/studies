use TestDB1
Create table Client_Im_Ogr
(
	ID int Constraint PK_Client_ID Primary Key Identity,
	Age int 
	Constraint DF_Client_age Default 18
	Constraint CK_Client_age Check(Age > 0 And Age < 100),
	Name NVARCHAR(20) Not Null,
	Surname NVARCHAR(20) Not Null,
	Email VARCHAR(30) Constraint UQ_Client_Email UNIQUE,
	Phone VARCHAR(20) Constraint UQ_Client_Phone UNIQUE
)

Create table Client_Im_Ogr1
(
	ID int Identity,
	Age int 
	Constraint DF_Client_age1 Default 18,
	Name NVARCHAR(20) Not Null,
	Surname NVARCHAR(20) Not Null,
	Email VARCHAR(30),
	Phone VARCHAR(20),
	Constraint PK_Client_ID1 Primary Key(ID),
	Constraint CK_Client_age1 Check(Age > 0 And Age < 100),
	Constraint UQ_Client_Email1 UNIQUE(Email),
	Constraint UQ_Client_Phone1 UNIQUE(Phone)
)