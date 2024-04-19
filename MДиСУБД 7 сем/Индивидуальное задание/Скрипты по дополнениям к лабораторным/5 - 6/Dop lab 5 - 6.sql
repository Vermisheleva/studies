USE EmployeeAccounting

--���������� ����������
--1. ������� ��� ���� �����������, ������� ��������, � �� ���������, ������� ��� �������� ��� ��������
SELECT E.Empl_ID, E.Name, E.Surname, E.Patronymic, A.Post
FROM
Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID;

--2. ������� ��� ���� �����������, ������� ���� � ������� � 2022 - 2023 ����, � ���� ������ � ��������� ������� 
SELECT E.Empl_ID, E.Name, E.Surname, E.Patronymic, V.Inc_Beginning, V.Inc_End
FROM
Employee E INNER JOIN Vacation V ON E.Empl_ID = V.Empl_ID
WHERE YEAR(V.Inc_Beginning) IN (2022, 2023);

--������� �����
--3. ������� ������ ���� ����������� � ���� ������ � ����� ���� �� ��������, ���� ��� ���� � �������
SELECT E.Empl_ID, E.Name, E.Surname, E.Patronymic, S.Inc_Beginning, S.Inc_End
FROM
Employee E LEFT OUTER JOIN SickLeave S ON E.Empl_ID = S.Empl_ID
ORDER BY S.Inc_Beginning;

--4. ������� ������ ���� ������������� � ���������� ����������� � ������ �����������
SELECT EI.Edu_Name, COUNT(E.Empl_ID) AS NumOfEmployees
FROM EducationInstitution EI LEFT OUTER JOIN Education E ON EI.Edu_ID = E.Edu_ID
GROUP BY EI.Edu_Name;

--������� ������
--5. ������� ������ ���� �������������� �����������, � �����������, ����������� �� ������ �������������
SELECT Edu.Speciality, Empl.Surname, Empl.Name
FROM Employee Empl RIGHT OUTER JOIN Education Edu ON Empl.Empl_ID = Edu.Empl_ID
ORDER BY Edu.Speciality;

--6. ������� ������ �����������, ���������� � 4 - 6 �������
SELECT DISTINCT A.Department, Empl.Surname, Empl.Name, Empl.Patronymic
FROM Employee Empl RIGHT OUTER JOIN Appointment A ON Empl.Empl_ID = A.Empl_ID
WHERE A.Department IN (4, 5, 6)
ORDER BY A.Department;

--������ �������
--7. ������� ���, ��������� � �������� �����������, �������� ������� ������ 30000.
SELECT DISTINCT Empl.Empl_ID, Empl.Surname, Empl.Name, A.Post, A.Salary
FROM Employee Empl FULL OUTER JOIN Appointment A ON Empl.Empl_ID = A.Empl_ID
WHERE A.Salary > 30000
ORDER BY A.Salary DESC;

--8. ������� �������, ���, ������������� � ������� �� ��� �����������, ������� �������� ����������� � �� � ��������� ������ 8.
SELECT Empl.Surname, Empl.Name, Edu.Speciality, EI.Edu_Rating
FROM Education Edu FULL OUTER JOIN Employee Empl ON Empl.Empl_ID = Edu.Edu_ID
				   INNER JOIN EducationInstitution EI ON Edu.Edu_ID = EI.Edu_ID
WHERE EI.Edu_Rating > 8;

--����������� ���������� 
--9. ������� ��������� ������������ ����������� � �����������, ��� ��� ��������� �� ������ 2020
SELECT *
FROM Employee Empl CROSS JOIN Education Edu
WHERE Edu.Grad_Year > 2020;

--10. ������� ���������� � ����������� � ���� �� ����������
SELECT *
FROM Employee Empl CROSS JOIN SickLeave S
WHERE Empl.Empl_ID = S.Empl_ID;

--�����������
--11. ������� ������ � �����������, ���� ������ ������� ������ 6 ���, � ������� ������ 40 ���.
SELECT *
FROM Employee
WHERE Work_Exp > 6
INTERSECT
SELECT *
FROM Employee
WHERE DATEDIFF(year, B_Date, GETDATE()) > 40;

--12. ������� ���������� � ����������� � 6 � 7 ������ � 2016 �.
SELECT *
FROM Appointment
WHERE YEAR(App_Date) > 2016
INTERSECT
SELECT *
FROM Appointment
WHERE Department IN (6, 7);

--�����������
--13. ������� ���������� � ���������� � �������� �� 2023 �.
SELECT *
FROM SickLeave
WHERE YEAR(Inc_Beginning) = 2023
UNION
SELECT *
FROM Vacation
WHERE YEAR(Inc_Beginning) = 2023;

--14. ������� ������ �����������, ���� ������ ������� ������ 10 ���, � ���, ��� ������� �� ����� � ������
SELECT *
FROM Employee
WHERE Work_Exp > 10
UNION
SELECT *
FROM Employee E
WHERE (SELECT COUNT(Order_Num) FROM Vacation V WHERE E.Empl_ID = V.Empl_ID) = 0;

--��������
--15.������� ������ �� ����������� ����������� � �� � ��������� �� ���� 9, ������� ������ �����������.
SELECT *
FROM Education Edu
WHERE (SELECT Edu_Rating FROM EducationInstitution EI WHERE Edu.Edu_ID = EI.Edu_ID) >= 9
EXCEPT 
SELECT *
FROM Education
WHERE Education = '�������';

--16. ������� ������ �����������, ������� �������� ������ ����������� � �� � ��������� �� ������ 9
SELECT *
FROM Employee
WHERE Empl_ID IN (SELECT Empl_ID FROM Education WHERE Education = '������') 
EXCEPT
SELECT *
FROM Employee
WHERE Empl_ID IN (SELECT Empl_ID FROM Education WHERE Edu_ID IN (SELECT Edu_ID FROM EducationInstitution WHERE Edu_Rating < 9));

--4 ������� � �������������� �����������, ��������� ���������
--���������, ��������� IN, ANY|SOME � ALL, �������� EXISTS
--17. ������� ����������� �� 5 � 6 �������
SELECT *
FROM Employee
WHERE Empl_ID IN (SELECT Empl_ID FROM Appointment WHERE Department IN (5, 6));

--18. ������� �����������, ���������� ���������� � 2022 � 2023 ��.
SELECT *
FROM Employee
WHERE Empl_ID = ANY (SELECT Empl_ID FROM Appointment WHERE YEAR(App_Date) IN (2022, 2023));

--19. ������� ���������� �����������, �������� ������� ������, ��� �������� ������ ���������� �� 1 ������
SELECT *
FROM Appointment
WHERE Salary < ALL (SELECT Salary FROM Appointment WHERE Department = 1);

--20. ���� ���� ���������� � 3 ������, ������� �� ������
SELECT *
FROM Appointment A INNER JOIN Employee E ON A.Empl_ID = E.Empl_ID
WHERE A.Department = 3 AND EXISTS(SELECT Empl_ID FROM Appointment WHERE Department = 3);
