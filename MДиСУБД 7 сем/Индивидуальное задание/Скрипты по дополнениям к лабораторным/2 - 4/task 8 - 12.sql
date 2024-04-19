use EmployeeAccounting

--список отделов и количество работающих в нем сотрудников, отсортировать 
--по убыванию количества сотрудников в отделе
SELECT Department,
	   COUNT(Empl_ID) AS Num_of_Employees
FROM Appointment
GROUP BY Department
ORDER BY Num_of_Employees DESC;

--средняя зарплата по всем назначениям
SELECT AVG(Salary) AS Avg_Salary
FROM Appointment;

--максимальный стаж сотрудников
SELECT MAX(Work_Exp) AS Max_WorkExp
FROM Employee;

--минимальный возраст сотрудников (в годах)
SELECT MIN (DATEDIFF(year, B_Date, GETDATE())) AS Min_Age
FROM Employee;

--суммарная зарплата сотрудников 1 отдела
SELECT SUM(Salary) AS Sum_Salary
FROM Appointment
WHERE Department = 1;

--количество больничных по каждому сотруднику,
--отсортировать по убыванию кол-ва больничных
SELECT Empl_ID,
	   COUNT(Sertificate_Num) AS NumOfSickLeaves
FROM SickLeave
GROUP BY Empl_ID
ORDER BY NumOfSickLeaves DESC;

--средняя зарплата по каждому отделу, отсортировать по убыванию средней з/п
SELECT Department,
	   AVG(Salary) AS Avg_Salary
FROM Appointment
GROUP BY Department
ORDER BY Avg_Salary DESC;

--список отделов, в которых средняя з/п больше 30000
SELECT Department,
	   AVG(Salary) AS Avg_Salary
FROM Appointment
GROUP BY Department
HAVING AVG(Salary) > 30000;

--список сотрудников, у которых больше 1 записи об образовании
SELECT Empl_ID,
	   COUNT(Edu_ID) AS NumOfEduInstitutions
FROM Education
GROUP BY Empl_ID
HAVING COUNT(Edu_ID) > 1;

--кол-во записей о высшем и среднем образовании, кол-во сотрудников, обучавшихся
--в каждом учреждении образования
SELECT Edu_ID, Education,
	   COUNT(Empl_ID) AS NumOfEmployees
FROM Education
GROUP BY GROUPING SETS(Edu_ID, Education);

--кол-во назначений по отделам по каждому году и промежуточные итоги
SELECT IIF(GROUPING(Department) = 1, 'Итого', CONVERT(NVARCHAR, Department)) AS Department,
	   IIF(GROUPING(YEAR(App_Date)) = 1, 'Итого', CONVERT(NVARCHAR, YEAR(App_Date))) AS AppYear,
	   COUNT(Order_Num) AS NumOfAppointments
FROM Appointment
GROUP BY Department, YEAR(App_Date) WITH ROLLUP;

--кол-во отпусков по сотрудникам и по годам и промежуточные итоги
SELECT Empl_ID,
	   YEAR(Inc_Beginning) AS Inc_Year,
	   COUNT(Order_Num) AS NumOfVacations
FROM Vacation
GROUP BY Empl_ID, YEAR(Inc_Beginning) WITH CUBE;

--кол-во сотрудников по образованию в столбцы
SELECT 'Количество сотрудиков' AS [NumOfEmployees],
	   Высшее, Среднее
FROM (SELECT Empl_ID, Education FROM Education) AS SOURCE_TABLE
PIVOT (COUNT(Empl_ID) FOR Education In (высшее, среднее)) AS PIVOT_TABLE;

--специальность в один столбец, а в другой - ID сотрудника или УО
SELECT Speciality, [Empl_ID or Edu_ID]
FROM Education
UNPIVOT ([Empl_ID or Edu_ID] FOR Val In (Empl_ID, Edu_ID))
unpvt;