use TestDB1
Create table Client_PR_K_ID_UN1
(
	ID int Primary Key Identity,
	Age int,
	Name NVARCHAR(20),
	Surname NVARCHAR(20),
	Email VARCHAR(30) UNIQUE,
	Phone VARCHAR(20) UNIQUE
)