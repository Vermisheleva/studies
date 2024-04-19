use TestDB1
Create table Client_PR_K_ID
(
	ID int Primary Key Identity,
	Age int,
	Name NVARCHAR(20),
	Surname NVARCHAR(20),
	Email VARCHAR(30),
	Phone VARCHAR(20)
)