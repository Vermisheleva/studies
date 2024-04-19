use EmployeeAccounting

--������ ������� � ���������� ���������� � ��� �����������, ������������� 
--�� �������� ���������� ����������� � ������
SELECT Department,
	   COUNT(Empl_ID) AS Num_of_Employees
FROM Appointment
GROUP BY Department
ORDER BY Num_of_Employees DESC;

--������� �������� �� ���� �����������
SELECT AVG(Salary) AS Avg_Salary
FROM Appointment;

--������������ ���� �����������
SELECT MAX(Work_Exp) AS Max_WorkExp
FROM Employee;

--����������� ������� ����������� (� �����)
SELECT MIN (DATEDIFF(year, B_Date, GETDATE())) AS Min_Age
FROM Employee;

--��������� �������� ����������� 1 ������
SELECT SUM(Salary) AS Sum_Salary
FROM Appointment
WHERE Department = 1;

--���������� ���������� �� ������� ����������,
--������������� �� �������� ���-�� ����������
SELECT Empl_ID,
	   COUNT(Sertificate_Num) AS NumOfSickLeaves
FROM SickLeave
GROUP BY Empl_ID
ORDER BY NumOfSickLeaves DESC;

--������� �������� �� ������� ������, ������������� �� �������� ������� �/�
SELECT Department,
	   AVG(Salary) AS Avg_Salary
FROM Appointment
GROUP BY Department
ORDER BY Avg_Salary DESC;

--������ �������, � ������� ������� �/� ������ 30000
SELECT Department,
	   AVG(Salary) AS Avg_Salary
FROM Appointment
GROUP BY Department
HAVING AVG(Salary) > 30000;

--������ �����������, � ������� ������ 1 ������ �� �����������
SELECT Empl_ID,
	   COUNT(Edu_ID) AS NumOfEduInstitutions
FROM Education
GROUP BY Empl_ID
HAVING COUNT(Edu_ID) > 1;

--���-�� ������� � ������ � ������� �����������, ���-�� �����������, �����������
--� ������ ���������� �����������
SELECT Edu_ID, Education,
	   COUNT(Empl_ID) AS NumOfEmployees
FROM Education
GROUP BY GROUPING SETS(Edu_ID, Education);

--���-�� ���������� �� ������� �� ������� ���� � ������������� �����
SELECT IIF(GROUPING(Department) = 1, '�����', CONVERT(NVARCHAR, Department)) AS Department,
	   IIF(GROUPING(YEAR(App_Date)) = 1, '�����', CONVERT(NVARCHAR, YEAR(App_Date))) AS AppYear,
	   COUNT(Order_Num) AS NumOfAppointments
FROM Appointment
GROUP BY Department, YEAR(App_Date) WITH ROLLUP;

--���-�� �������� �� ����������� � �� ����� � ������������� �����
SELECT Empl_ID,
	   YEAR(Inc_Beginning) AS Inc_Year,
	   COUNT(Order_Num) AS NumOfVacations
FROM Vacation
GROUP BY Empl_ID, YEAR(Inc_Beginning) WITH CUBE;

--���-�� ����������� �� ����������� � �������
SELECT '���������� ����������' AS [NumOfEmployees],
	   ������, �������
FROM (SELECT Empl_ID, Education FROM Education) AS SOURCE_TABLE
PIVOT (COUNT(Empl_ID) FOR Education In (������, �������)) AS PIVOT_TABLE;

--������������� � ���� �������, � � ������ - ID ���������� ��� ��
SELECT Speciality, [Empl_ID or Edu_ID]
FROM Education
UNPIVOT ([Empl_ID or Edu_ID] FOR Val In (Empl_ID, Edu_ID))
unpvt;