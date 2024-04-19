use TestDB1
Create table Zakaz_L
(
	Zakaz int,
	Produkt int,
	Kol_vo int,
	Cena money,
	Primary Key(Zakaz, Produkt)
)