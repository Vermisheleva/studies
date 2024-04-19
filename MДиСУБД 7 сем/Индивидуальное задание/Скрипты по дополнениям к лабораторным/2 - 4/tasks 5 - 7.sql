use EmployeeAccounting

--переименовать таблицу
EXEC sp_rename 'dbo.Vacation_Info', 'VacationInfo';

--добавить по две записи в каждую из таблиц БД
INSERT INTO Employee(Name, Patronymic, Surname, B_Date, Work_Exp)
VALUES
('Наталья', 'Николаевна', 'Негожина', '1985-07-15', 3),
('Вечеслав', 'Васильевич', 'Тополь', '1978-12-21', 5);

INSERT INTO EducationInstitution(Edu_Name, Edu_Rating)
VALUES
('Томатский Университет Лженаук', 9),
('Всепланетарная Академия Ракетомоделирования', 8);

INSERT INTO Education(Empl_ID, Edu_ID, Education, Speciality, Grad_Year)
VALUES
(11, 11, 'высшее', 'магистр лженаук', 2008),
(12, 12, 'высшее', 'специалист по ракетомоделированию', 1999);

INSERT INTO Appointment(Empl_ID, Post, Salary, Department, App_Date)
VALUES
(11, 'лженаучная сотрудница', 35467.18, 1, '2020-09-16'),
(12, 'главный ракетолеп', 20345, 7, '2018-05-05');

INSERT INTO Vacation(Empl_ID, Inc_Beginning, Inc_End)
VALUES
(11, '2023-09-24', '2023-10-04'),
(3, '2023-10-05', '2023-11-09');

INSERT INTO SickLeave(Empl_ID, Inc_Beginning, Inc_End)
VALUES
(12, '2023-09-20', '2023-10-01'),
(3, '2023-09-23', '2023-10-01');

--добавить в таблицу назначений дату окончания рабочего контракта
--значение по умолчанию для даты окончания рабочего контракта равно
--нынешней дате + 3 года
ALTER TABLE Appointment
ADD End_Date DATE DEFAULT CONVERT(DATE, DATEADD(yy, 3, GETDATE()));

--задаем ограничение на новый столбец - значение по умолчанию для даты окончания
--рабочего контракта равно нынешней дате + 5 лет
ALTER TABLE Appointment
ADD CONSTRAINT CK_Appointment_EndDate CHECK (End_Date > App_Date);

SELECT * FROM Appointment

