use EmployeeAccounting

--вывести все данные из таблиц
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

--ФИО сотрудников и их опыт работы, отсортированные по фамилии сотрудников в алфавитном порядке
SELECT Surname, Name, Patronymic, Work_Exp
FROM Employee
ORDER BY Surname;

--ID, фамилия, дата рождения и стаж сотрудников, отсортированные по убыванию стажу работы
--и возраста (используя дату рождения) 
SELECT Empl_ID, Surname, B_Date, Work_Exp
FROM Employee
ORDER BY Work_Exp DESC, B_Date DESC;

--список годов окончания УО без дубликатов и количество лет,
--прошедших с этого года, отсортировать по количеству прошедших лет 
SELECT DISTINCT Grad_Year,
				YEAR(GETDATE()) - Grad_Year AS Years_Ago
FROM Education
ORDER BY Years_Ago;

--вывести 30% последних по дате строк таблицы назначений
SELECT TOP 30 PERCENT *
FROM Appointment
ORDER BY App_Date DESC;

--первые 5 строк из таблицы образовательных учреждений, отсортированных по убыванию рейтинга
SELECT TOP 5 *
FROM EducationInstitution
ORDER BY Edu_Rating DESC;

--строки из таблицы сотрудников, начиная с 4 строки по 7, отсортированные 
--в обратном порядке по стажу
SELECT *
FROM Employee
ORDER BY Work_Exp DESC
OFFSET 3 ROWS
FETCH NEXT 4 ROWS ONLY;

--список номеров справок, ID сотрудников и длину больничного в днях, 
--отсортировав по убыванию длины больничного
SELECT Sertificate_Num, Empl_Id,
	   DATEDIFF(day, Inc_Beginning, Inc_End) AS Inc_Period
FROM SickLeave
ORDER BY Inc_Period DESC;

--ID сотрудника и количество его отпусков за все время работы в отдельной таблице
--(выборка с добавлением)
SELECT Empl_ID,
	   COUNT(Empl_ID) AS Num_of_Vacations
INTO Vacation_Info
FROM Vacation
GROUP BY Empl_ID;

SELECT * 
FROM Vacation_Info
ORDER BY Num_of_Vacations DESC;
