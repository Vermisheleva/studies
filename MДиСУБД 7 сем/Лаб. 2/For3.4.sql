/*CREATE DATABASE ProductsDB
ON
(
	NAME = ProductsDB,
	FILENAME = 'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\ProductsDB.mdf'
)
GO
USE ProductsDB;
CREATE TABLE Product
(
	ID int IDENTITY PRIMARY KEY,
	Name nvarchar(30) NOT NULL,
	Proizvod nvarchar(20) NOT NULL,
	ProductCount int DEFAULT 0,
	Price money NOT NULL
)

USE ProductsDB
INSERT Product(Name, Price, Proizvod)
VALUES('iPhone 6', 6100, 'apple')

INSERT Product VALUES('IPhone 7', 'Apple', 5, 5100)


USE ProductsDB
INSERT INTO Product
VALUES ('iPhone 6', 'Apple', 3, 36000),
('Galaxy S8', 'Samsung', 2, 46000),
('Galaxy S8 Plus', 'Samsung', 1, 56000) 
*/

USE ProductsDB
INSERT Product (Name, Proizvod, ProductCount, Price)
VALUES ('Mi6', 'Xiaomi', DEFAULT, 28000)
