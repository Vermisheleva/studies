use EmployeeAccounting

--список всех сотрудников, у которых стаж работы больше 5 лет
SELECT *
FROM Employee
WHERE Work_Exp > 5;

--информация о высшем образовании всех сотрудников, у которых оно есть
SELECT *
FROM Education
WHERE Education = 'высшее';

--информация обо всех назначениях в отдел 6, у которых зарплата больше 18000
SELECT *
FROM Appointment
WHERE Department = 6 AND Salary > 18000;

--информация о назначениях за 2020 - 2023 год
SELECT *
FROM Appointment
WHERE YEAR(App_Date) BETWEEN 2020 AND 2023;

--информация о назначениях в 6 и 7 отделы
SELECT *
FROM Appointment
WHERE Department In (6, 7);

--все назначения сотрудника по фамилии Чебупелин
SELECT *
FROM Appointment
WHERE Empl_ID = (SELECT Empl_ID FROM Employee WHERE Surname = 'Чебупелин');

--информация о всех сотрудниках, закончивших УО с рейтингом 10
SELECT *
FROM Employee
WHERE Empl_ID In (SELECT Empl_ID FROM Education WHERE Edu_ID In 
(SELECT Edu_ID FROM EducationInstitution WHERE Edu_Rating = 10));