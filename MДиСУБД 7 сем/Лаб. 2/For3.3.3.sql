use TestDB1
/*
CREATE TABLE TestTable1
(
	iID int DEFAULT 1,
	Constraint CK_iID Check(iID is NOT NULL)
)

INSERT INTO TestTable1
VALUES(10)

UPDATE TestTable1
SET iID = NULL;

CREATE TABLE TestTable2
(
	ID int DEFAULT 1 NOT NULL,
	vcName varchar(50) NOT NULL,
	dBirthDate datetime,
	CONSTRAINT CK_birthdate
	CHECK (dBirthDate > '01-01-1900' AND dBirthDate < getdate())
)

*/

CREATE TABLE TestTable4
(
	ID int DEFAULT 1 NOT NULL,
	vcName varchar(50) NOT NULL,
	dBirthDate datetime,
	dDocDate datetime,
	CONSTRAINT CK_DDocDate
	CHECK (dDocDate > dBirthDate AND dDocDate < getdate())
)