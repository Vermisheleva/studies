use TestDatabase
ALTER TABLE dbo.Client_alt WITH NOCHECK
	ADD Check (Age > 21)