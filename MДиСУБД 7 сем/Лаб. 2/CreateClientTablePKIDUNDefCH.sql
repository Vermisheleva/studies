use TestDB1
Create table Client_PR_K_ID_UN_Def_CH
(
	ID int Primary Key Identity,
	Age int Default 18,
	Name NVARCHAR(20) Not Null,
	Surname NVARCHAR(20) Not Null,
	Email VARCHAR(30) UNIQUE,
	Phone VARCHAR(20) UNIQUE,
	Check(Age > 0 And Age < 100 And Email != '' And Phone!='')
)