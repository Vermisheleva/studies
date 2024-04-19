use EmployeeAccounting

--������� ��� ������ �� ������
SELECT *
FROM Employee;

SELECT *
FROM EducationInstitution;

SELECT *
FROM Education;

SELECT *
FROM Appointment;

SELECT *
FROM SickLeave;

SELECT *
FROM Vacation;

--��� ����������� � �� ���� ������, ��������������� �� ������� ����������� � ���������� �������
SELECT Surname, Name, Patronymic, Work_Exp
FROM Employee
ORDER BY Surname;

--ID, �������, ���� �������� � ���� �����������, ��������������� �� �������� ����� ������
--� �������� (��������� ���� ��������) 
SELECT Empl_ID, Surname, B_Date, Work_Exp
FROM Employee
ORDER BY Work_Exp DESC, B_Date DESC;

--������ ����� ��������� �� ��� ���������� � ���������� ���,
--��������� � ����� ����, ������������� �� ���������� ��������� ��� 
SELECT DISTINCT Grad_Year,
				YEAR(GETDATE()) - Grad_Year AS Years_Ago
FROM Education
ORDER BY Years_Ago;

--������� 30% ��������� �� ���� ����� ������� ����������
SELECT TOP 30 PERCENT *
FROM Appointment
ORDER BY App_Date DESC;

--������ 5 ����� �� ������� ��������������� ����������, ��������������� �� �������� ��������
SELECT TOP 5 *
FROM EducationInstitution
ORDER BY Edu_Rating DESC;

--������ �� ������� �����������, ������� � 4 ������ �� 7, ��������������� 
--� �������� ������� �� �����
SELECT *
FROM Employee
ORDER BY Work_Exp DESC
OFFSET 3 ROWS
FETCH NEXT 4 ROWS ONLY;

--������ ������� �������, ID ����������� � ����� ����������� � ����, 
--������������ �� �������� ����� �����������
SELECT Sertificate_Num, Empl_Id,
	   DATEDIFF(day, Inc_Beginning, Inc_End) AS Inc_Period
FROM SickLeave
ORDER BY Inc_Period DESC;

--ID ���������� � ���������� ��� �������� �� ��� ����� ������ � ��������� �������
--(������� � �����������)
SELECT Empl_ID,
	   COUNT(Empl_ID) AS Num_of_Vacations
INTO Vacation_Info
FROM Vacation
GROUP BY Empl_ID;

SELECT * 
FROM Vacation_Info
ORDER BY Num_of_Vacations DESC;
