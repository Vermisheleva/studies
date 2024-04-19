USE EmployeeAccounting

--Внутреннее соединение
--1. Вывести ФИО всех сотрудников, которые работают, и их должности, которые они занимали или занимают
SELECT E.Empl_ID, E.Name, E.Surname, E.Patronymic, A.Post
FROM
Employee E INNER JOIN Appointment A ON E.Empl_ID = A.Empl_ID;

--2. Вывести ФИО всех сотрудников, которые были в отпуске в 2022 - 2023 году, и даты начала и окончания отпуска 
SELECT E.Empl_ID, E.Name, E.Surname, E.Patronymic, V.Inc_Beginning, V.Inc_End
FROM
Employee E INNER JOIN Vacation V ON E.Empl_ID = V.Empl_ID
WHERE YEAR(V.Inc_Beginning) IN (2022, 2023);

--Внешнее левое
--3. Вывести список всех сотрудников и даты начала и конца всех их отпусков, если они были в отпуске
SELECT E.Empl_ID, E.Name, E.Surname, E.Patronymic, S.Inc_Beginning, S.Inc_End
FROM
Employee E LEFT OUTER JOIN SickLeave S ON E.Empl_ID = S.Empl_ID
ORDER BY S.Inc_Beginning;

--4. Вывести список всех университетов и количество обучавшихся в каждом сотрудников
SELECT EI.Edu_Name, COUNT(E.Empl_ID) AS NumOfEmployees
FROM EducationInstitution EI LEFT OUTER JOIN Education E ON EI.Edu_ID = E.Edu_ID
GROUP BY EI.Edu_Name;

--Внешнее правое
--5. Вывести список всех специальностей сотрудников, и сотрудников, обучавшихся по каждой специальности
SELECT Edu.Speciality, Empl.Surname, Empl.Name
FROM Employee Empl RIGHT OUTER JOIN Education Edu ON Empl.Empl_ID = Edu.Empl_ID
ORDER BY Edu.Speciality;

--6. Вывести список сотрудников, работающих в 4 - 6 отделах
SELECT DISTINCT A.Department, Empl.Surname, Empl.Name, Empl.Patronymic
FROM Employee Empl RIGHT OUTER JOIN Appointment A ON Empl.Empl_ID = A.Empl_ID
WHERE A.Department IN (4, 5, 6)
ORDER BY A.Department;

--Полное внешнее
--7. Вывести ФИО, должность и зарплату сотрудников, зарплата которых больше 30000.
SELECT DISTINCT Empl.Empl_ID, Empl.Surname, Empl.Name, A.Post, A.Salary
FROM Employee Empl FULL OUTER JOIN Appointment A ON Empl.Empl_ID = A.Empl_ID
WHERE A.Salary > 30000
ORDER BY A.Salary DESC;

--8. Вывести фамилию, имя, специальность и рейтинг УО для сотрудников, которые получили образование в УО с рейтингом больше 8.
SELECT Empl.Surname, Empl.Name, Edu.Speciality, EI.Edu_Rating
FROM Education Edu FULL OUTER JOIN Employee Empl ON Empl.Empl_ID = Edu.Edu_ID
				   INNER JOIN EducationInstitution EI ON Edu.Edu_ID = EI.Edu_ID
WHERE EI.Edu_Rating > 8;

--Перекресное соединение 
--9. Вывести декартово произведение сотрудников и образования, где год окончания УО больше 2020
SELECT *
FROM Employee Empl CROSS JOIN Education Edu
WHERE Edu.Grad_Year > 2020;

--10. Вывести информацию о сотрудниках и всех их больничных
SELECT *
FROM Employee Empl CROSS JOIN SickLeave S
WHERE Empl.Empl_ID = S.Empl_ID;

--Пересечение
--11. Вывести записи о сотрудниках, опыт работы которых больше 6 лет, а возраст больше 40 лет.
SELECT *
FROM Employee
WHERE Work_Exp > 6
INTERSECT
SELECT *
FROM Employee
WHERE DATEDIFF(year, B_Date, GETDATE()) > 40;

--12. Вывести информацию о назначениях в 6 и 7 отделы с 2016 г.
SELECT *
FROM Appointment
WHERE YEAR(App_Date) > 2016
INTERSECT
SELECT *
FROM Appointment
WHERE Department IN (6, 7);

--Объединение
--13. Вывести информацию о больничных и отпусках за 2023 г.
SELECT *
FROM SickLeave
WHERE YEAR(Inc_Beginning) = 2023
UNION
SELECT *
FROM Vacation
WHERE YEAR(Inc_Beginning) = 2023;

--14. Вывести список сотрудников, опыт работы которых больше 10 лет, и тех, кто никогда не ходил в отпуск
SELECT *
FROM Employee
WHERE Work_Exp > 10
UNION
SELECT *
FROM Employee E
WHERE (SELECT COUNT(Order_Num) FROM Vacation V WHERE E.Empl_ID = V.Empl_ID) = 0;

--Разность
--15.Вывести записи об образовании сотрудников в УО с рейтингом не ниже 9, дающими высшее образование.
SELECT *
FROM Education Edu
WHERE (SELECT Edu_Rating FROM EducationInstitution EI WHERE Edu.Edu_ID = EI.Edu_ID) >= 9
EXCEPT 
SELECT *
FROM Education
WHERE Education = 'среднее';

--16. Вывести список сотрудников, которые получили высшее образование в УО с рейтингом не меньше 9
SELECT *
FROM Employee
WHERE Empl_ID IN (SELECT Empl_ID FROM Education WHERE Education = 'высшее') 
EXCEPT
SELECT *
FROM Employee
WHERE Empl_ID IN (SELECT Empl_ID FROM Education WHERE Edu_ID IN (SELECT Edu_ID FROM EducationInstitution WHERE Edu_Rating < 9));

--4 запроса с использованием подзапросов, используя операторы
--сравнения, операторы IN, ANY|SOME и ALL, предикат EXISTS
--17. Вывести сотрудников из 5 и 6 отделов
SELECT *
FROM Employee
WHERE Empl_ID IN (SELECT Empl_ID FROM Appointment WHERE Department IN (5, 6));

--18. Вывести сотрудников, получивших назначения в 2022 и 2023 гг.
SELECT *
FROM Employee
WHERE Empl_ID = ANY (SELECT Empl_ID FROM Appointment WHERE YEAR(App_Date) IN (2022, 2023));

--19. Вывести назначения сотрудников, зарплата которых меньше, чем зарплата любого сотрудника из 1 отдела
SELECT *
FROM Appointment
WHERE Salary < ALL (SELECT Salary FROM Appointment WHERE Department = 1);

--20. Если есть сотрудники в 3 отделе, вывести их список
SELECT *
FROM Appointment A INNER JOIN Employee E ON A.Empl_ID = E.Empl_ID
WHERE A.Department = 3 AND EXISTS(SELECT Empl_ID FROM Appointment WHERE Department = 3);
