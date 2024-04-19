use EmployeeAccounting

--������ ���� �����������, � ������� ���� ������ ������ 5 ���
SELECT *
FROM Employee
WHERE Work_Exp > 5;

--���������� � ������ ����������� ���� �����������, � ������� ��� ����
SELECT *
FROM Education
WHERE Education = '������';

--���������� ��� ���� ����������� � ����� 6, � ������� �������� ������ 18000
SELECT *
FROM Appointment
WHERE Department = 6 AND Salary > 18000;

--���������� � ����������� �� 2020 - 2023 ���
SELECT *
FROM Appointment
WHERE YEAR(App_Date) BETWEEN 2020 AND 2023;

--���������� � ����������� � 6 � 7 ������
SELECT *
FROM Appointment
WHERE Department In (6, 7);

--��� ���������� ���������� �� ������� ���������
SELECT *
FROM Appointment
WHERE Empl_ID = (SELECT Empl_ID FROM Employee WHERE Surname = '���������');

--���������� � ���� �����������, ����������� �� � ��������� 10
SELECT *
FROM Employee
WHERE Empl_ID In (SELECT Empl_ID FROM Education WHERE Edu_ID In 
(SELECT Edu_ID FROM EducationInstitution WHERE Edu_Rating = 10));