use EmployeeAccounting

--Написать 4 запроса по строковым функциям

--1) Вывести список сотрудников в формате Фамилия Инициалы
SELECT 
	CONCAT(Surname, ' ', LEFT(Name, 1), '. ', LEFT(Patronymic, 1), '.') AS [Список сотрудников]
FROM Employee

--2) Найти запись о сотрудниках, фамилии которых начинаются с буквы Т
SELECT *
FROM Employee
WHERE CHARINDEX('Т', Surname) = 1

--3) Заменить фамилию у сотрудницы с фамилией Малодумина на Роненко 
UPDATE Employee
SET Surname = 'Роненко'
WHERE Surname = 'Малодумина'
SELECT * FROM Employee

--4) Вывести список всех должностей из назначений заглавными буквами
SELECT UPPER(Post) AS [Список должностей]
FROM Appointment

-- Написать 4 запроса по числовым функциям

--1) Вывести среднюю зарплату по отделам, округлив до 3 знаков после запятой
SELECT Department, ROUND(AVG(Salary), 2) AS [Avarage Salary]
FROM Appointment
GROUP BY Department

--2) Вывести средний возраст сотрудников, округлив до целого числа в большую сторону 
SELECT FLOOR(AVG(DATEDIFF(Year, B_Date, GETDATE()))) AS [Средний возраст сотрудников]
FROM Employee

--3) Вывести среднюю длину отпуска, округлив до целого числа в меньшую сторону
SELECT CEILING(AVG(DATEDIFF(Day, Inc_Beginning, Inc_End))) AS [Средняя длина отпуска]
FROM Vacation

--Создайте 5 представлений по своей БД

--1) Представление, содержащее ФИО сотрудников и все их должности из назначений
GO
CREATE VIEW EmplPosts
(Surname, Name, Patronymic, Post, App_Date)
AS 
SELECT Surname, Name, Patronymic, Post, App_Date
FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
GO
SELECT * FROM EmplPosts

--2) Вывести ФИО сотрудников и информацию об УО, в которых они обучались
GO
CREATE VIEW EmplEducation
(Surname, Name, Patronymic, Edu_Name, Edu_Raiting)
AS 
SELECT Surname, Name, Patronymic, Edu_Name, Edu_Rating
FROM Education E 
		INNER JOIN Employee Empl ON E.Empl_ID = Empl.Empl_ID
		INNER JOIN EducationInstitution EI ON E.Edu_ID = EI.Edu_ID
GO
SELECT * FROM EmplEducation

--3) Вывести ФИО сотрудников, количество их отпусков и больничных за все время
GO
CREATE VIEW EmplInc
(Surname, Name, Patronymic, [Num of vacations], [Num of seak leaves])
AS 
SELECT Surname, Name, Patronymic, COUNT(V.Order_Num), COUNT(S.Sertificate_Num)
FROM Employee E 
		INNER JOIN Vacation V ON E.Empl_ID = V.Empl_ID
		INNER JOIN SickLeave S ON E.Empl_ID = S.Empl_ID
GROUP BY E.Empl_ID, Surname, Name, Patronymic
GO
SELECT * FROM EmplInc

--4) Вывести сотрудников 1 отдела 
GO
CREATE VIEW EmplFrom1Department
(Surname, Name, Patronymic)
AS 
SELECT Surname, Name, Patronymic
FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
WHERE Department = 1
GO
SELECT * FROM EmplFrom1Department

--5) Вывести список УО и их рейтинг, а также количество обучавшихся в каждом сотрудников
GO
CREATE VIEW EduInfo
(Edu_ID, Edu_Name, Edu_Raiting, [Num of employeees])
AS 
SELECT EI.Edu_ID, Edu_Name, Edu_Rating, COUNT(Empl.Empl_ID)
FROM Education E 
		INNER JOIN Employee Empl ON E.Empl_ID = Empl.Empl_ID
		INNER JOIN EducationInstitution EI ON E.Edu_ID = EI.Edu_ID
GROUP BY EI.Edu_ID, Edu_Name, Edu_Rating
GO
SELECT * FROM EduInfo

--Покажите применение табличных переменных, временные
--локальных и глобальных таблиц, а так же обобщенных табличных
--выражений.

-- Табличные переменные
-- Вывести назначения в 7 отдел за 2023 г и ФИО сотрудников, получивших назначения
DECLARE @TempTable TABLE(
	Empl_ID INT,
	Surname NVARCHAR(20),
	Name NVARCHAR(20),
	Patronymic NVARCHAR(20),
	Order_Num INT,
	Post NVARCHAR(40),
	Department SMALLINT,
	App_Date DATE)
INSERT INTO @TempTable
SELECT E.Empl_ID, Surname, Name, Patronymic, Order_Num, Post, Department, App_Date
FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
WHERE Department = 7
SELECT * FROM @TempTable
WHERE DATEPART(YEAR, App_Date) = 2023

--Локальная временная таблица
--Создать таблицу с информацией по отпускам и выбрать оттуда записи за 2023 г
SELECT Surname, Name, Patronymic, V.Inc_Beginning AS [Начало отпуска], V.Inc_End AS [Конец отпуска]
INTO #EmplIncInfo
FROM Employee E 
		INNER JOIN Vacation V ON E.Empl_ID = V.Empl_ID
SELECT * FROM #EmplIncInfo WHERE DATEPART(YEAR, [Начало отпуска]) = 2023

--Глобальная временная таблица
--Создать таблицу с ФИО и назначениями сотрудников и вывести данные из нее, отсортировав по зарплате
SELECT Surname, Name, Patronymic, Order_Num, Post, Salary, Department, App_Date
INTO ##EmplAppointments
FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
SELECT * FROM ##EmplAppointments ORDER BY Salary DESC;

--Обобщенное табличное выражение
--Вывести список сотрудников, у которых стаж работы меньше одного года, отсортировать по фамилии в алфавитном
--порядке
WITH EmployeeesWithNoWorkExp AS(
SELECT Surname, Name, Patronymic, Work_Exp
FROM Employee
WHERE Work_Exp < 1)
SELECT * FROM EmployeeesWithNoWorkExp ORDER BY Surname

--Создать 2 хранимые процедуры, одна из которых будет иметь
--выходные параметры.

--1) Найти количество назначений в отдел
GO
CREATE PROC EmplNum
	@Department SMALLINT,
	@NumOfEmpl INT OUTPUT
AS 
BEGIN
	SELECT @NumOfEmpl = COUNT(*)
	FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
	WHERE Department = @Department
END

DECLARE @D_Num SMALLINT = 7, @EmplNum INT
EXEC EmplNum @D_Num, @EmplNum OUTPUT 
SELECT @EmplNum AS [Количество назначений в 7 отдел]

--2) Вывести топ 2 назначений с самой большой зарплатой в 1 отделе
GO
CREATE PROC Top5Salaries
	@Department SMALLINT = 7
AS
BEGIN
	SELECT TOP 2 *
	FROM Appointment
	WHERE Department = @Department
END

EXEC Top5Salaries DEFAULT

--Создать 2 определяемые пользователем функции, одна из
--которых скалярная функция, другая возвращает табличное значение

--1) Вывести все назначения сотрудника по его фамилии
GO
CREATE FUNCTION GetEmployeesAppointments (@Surname AS NVARCHAR(20))
RETURNS TABLE
AS
RETURN(
	SELECT E.Empl_ID, E.Surname, E.Name, E.Patronymic, A.Order_Num, A.Post, A.Department, 
		   A.Salary, A.App_Date
	FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
	WHERE Surname = @Surname)
GO
DECLARE @Surn NVARCHAR(20) = 'Громова'
SELECT * FROM dbo.GetEmployeesAppointments(@Surn);

--2) Вывести количество отпусков сотрудника по фамилии
GO
CREATE FUNCTION NumOfVacations (@Surname AS NVARCHAR(20))
RETURNS INT
AS
BEGIN
	DECLARE @Num AS INT
	SELECT @Num = COUNT(*)
	FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
	WHERE Surname = @Surname
RETURN @Num
END
GO
DECLARE @Surn NVARCHAR(20) = 'Громова'
SELECT dbo.NumOfVacations(@Surn)

--Создайте два триггера: триггер AFTER и триггер INSTEAD OF 

--1) Триггер на добавление записи в таблицу Сотрудники 
GO
CREATE TRIGGER TriggerInsert ON Employee FOR INSERT
AS
BEGIN
	PRINT 'Запись добавлена'
END
GO 
INSERT INTO Employee(Name, Surname, B_Date, Work_Exp)
VALUES
('Наталья', 'Морецкая', '2002-05-07', 1)

--2) Триггер на удаление записи из таблицы сотрудники
GO
CREATE TRIGGER TriggerDelete ON Employee INSTEAD OF DELETE
AS
BEGIN
	PRINT 'Нельзя удалить данные'
END
GO
DELETE FROM Employee
WHERE Surname = 'Морецкая'
