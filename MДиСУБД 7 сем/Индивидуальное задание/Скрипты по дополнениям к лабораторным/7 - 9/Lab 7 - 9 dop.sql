use EmployeeAccounting

--�������� 4 ������� �� ��������� ��������

--1) ������� ������ ����������� � ������� ������� ��������
SELECT 
	CONCAT(Surname, ' ', LEFT(Name, 1), '. ', LEFT(Patronymic, 1), '.') AS [������ �����������]
FROM Employee

--2) ����� ������ � �����������, ������� ������� ���������� � ����� �
SELECT *
FROM Employee
WHERE CHARINDEX('�', Surname) = 1

--3) �������� ������� � ���������� � �������� ���������� �� ������� 
UPDATE Employee
SET Surname = '�������'
WHERE Surname = '����������'
SELECT * FROM Employee

--4) ������� ������ ���� ���������� �� ���������� ���������� �������
SELECT UPPER(Post) AS [������ ����������]
FROM Appointment

-- �������� 4 ������� �� �������� ��������

--1) ������� ������� �������� �� �������, �������� �� 3 ������ ����� �������
SELECT Department, ROUND(AVG(Salary), 2) AS [Avarage Salary]
FROM Appointment
GROUP BY Department

--2) ������� ������� ������� �����������, �������� �� ������ ����� � ������� ������� 
SELECT FLOOR(AVG(DATEDIFF(Year, B_Date, GETDATE()))) AS [������� ������� �����������]
FROM Employee

--3) ������� ������� ����� �������, �������� �� ������ ����� � ������� �������
SELECT CEILING(AVG(DATEDIFF(Day, Inc_Beginning, Inc_End))) AS [������� ����� �������]
FROM Vacation

--�������� 5 ������������� �� ����� ��

--1) �������������, ���������� ��� ����������� � ��� �� ��������� �� ����������
GO
CREATE VIEW EmplPosts
(Surname, Name, Patronymic, Post, App_Date)
AS 
SELECT Surname, Name, Patronymic, Post, App_Date
FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
GO
SELECT * FROM EmplPosts

--2) ������� ��� ����������� � ���������� �� ��, � ������� ��� ���������
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

--3) ������� ��� �����������, ���������� �� �������� � ���������� �� ��� �����
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

--4) ������� ����������� 1 ������ 
GO
CREATE VIEW EmplFrom1Department
(Surname, Name, Patronymic)
AS 
SELECT Surname, Name, Patronymic
FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
WHERE Department = 1
GO
SELECT * FROM EmplFrom1Department

--5) ������� ������ �� � �� �������, � ����� ���������� ����������� � ������ �����������
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

--�������� ���������� ��������� ����������, ���������
--��������� � ���������� ������, � ��� �� ���������� ���������
--���������.

-- ��������� ����������
-- ������� ���������� � 7 ����� �� 2023 � � ��� �����������, ���������� ����������
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

--��������� ��������� �������
--������� ������� � ����������� �� �������� � ������� ������ ������ �� 2023 �
SELECT Surname, Name, Patronymic, V.Inc_Beginning AS [������ �������], V.Inc_End AS [����� �������]
INTO #EmplIncInfo
FROM Employee E 
		INNER JOIN Vacation V ON E.Empl_ID = V.Empl_ID
SELECT * FROM #EmplIncInfo WHERE DATEPART(YEAR, [������ �������]) = 2023

--���������� ��������� �������
--������� ������� � ��� � ������������ ����������� � ������� ������ �� ���, ������������ �� ��������
SELECT Surname, Name, Patronymic, Order_Num, Post, Salary, Department, App_Date
INTO ##EmplAppointments
FROM Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID
SELECT * FROM ##EmplAppointments ORDER BY Salary DESC;

--���������� ��������� ���������
--������� ������ �����������, � ������� ���� ������ ������ ������ ����, ������������� �� ������� � ����������
--�������
WITH EmployeeesWithNoWorkExp AS(
SELECT Surname, Name, Patronymic, Work_Exp
FROM Employee
WHERE Work_Exp < 1)
SELECT * FROM EmployeeesWithNoWorkExp ORDER BY Surname

--������� 2 �������� ���������, ���� �� ������� ����� �����
--�������� ���������.

--1) ����� ���������� ���������� � �����
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
SELECT @EmplNum AS [���������� ���������� � 7 �����]

--2) ������� ��� 2 ���������� � ����� ������� ��������� � 1 ������
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

--������� 2 ������������ ������������� �������, ���� ��
--������� ��������� �������, ������ ���������� ��������� ��������

--1) ������� ��� ���������� ���������� �� ��� �������
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
DECLARE @Surn NVARCHAR(20) = '�������'
SELECT * FROM dbo.GetEmployeesAppointments(@Surn);

--2) ������� ���������� �������� ���������� �� �������
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
DECLARE @Surn NVARCHAR(20) = '�������'
SELECT dbo.NumOfVacations(@Surn)

--�������� ��� ��������: ������� AFTER � ������� INSTEAD OF 

--1) ������� �� ���������� ������ � ������� ���������� 
GO
CREATE TRIGGER TriggerInsert ON Employee FOR INSERT
AS
BEGIN
	PRINT '������ ���������'
END
GO 
INSERT INTO Employee(Name, Surname, B_Date, Work_Exp)
VALUES
('�������', '��������', '2002-05-07', 1)

--2) ������� �� �������� ������ �� ������� ����������
GO
CREATE TRIGGER TriggerDelete ON Employee INSTEAD OF DELETE
AS
BEGIN
	PRINT '������ ������� ������'
END
GO
DELETE FROM Employee
WHERE Surname = '��������'
