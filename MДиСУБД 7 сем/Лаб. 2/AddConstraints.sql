use TestDatabase

ALTER TABLE dbo.Zakaz
ADD Constraint PK_Zakaz_ID Primary Key(ID);

ALTER TABLE dbo.Zakaz
ADD Constraint FK_zakaz_To_Client_alt
Foreign Key (ClientID) REFERENCES Client_alt(ID);

ALTER TABLE Client_alt
ADD Constraint CK_Client_alt_age2 Check (Age > 0)