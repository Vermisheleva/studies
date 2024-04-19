use EmployeeAccounting
-- Создаем таблицу Сотрудник
-- Задаем первичный ключ, значения по умолчанию для полей Отчество и Стаж работы,
-- обязательные поля
-- Проверяем, чтобы введенная дата рождения попадала в опред. диапазон
Create table Employee
(
	Empl_ID int Constraint PK_Employee_ID Primary Key Identity,
	Name nvarchar(20) NOT NULL,
	Patronymic nvarchar(20) Constraint DF_Employee_Patronymic Default NULL,
	Surname nvarchar(20) NOT NULL,
	B_Date date NOT NULL,
	Work_Exp smallint Constraint DF_Employee_WorkExp Default 0,
	Constraint CK_Employee_BDate Check (B_Date > '01-01-1900' AND B_Date < getdate())
)
-- Создаем таблицу Учреждение Образования
-- Задаем первичный ключ, ненулевые поля, только 1 запись про каждое УО - Название УО уникально
-- Проверяем, чтобы значение рейтинга находилось в диапазоне от 1 до 10
Create Table EducationInstitution
(
	Edu_ID int Identity,
	Edu_Name nvarchar(100) NOT NULL,
	Edu_Rating smallint,
	Constraint PK_EducationInstitution_EduID Primary Key (Edu_ID),
	Constraint UQ_EducationInstitution_EduName UNIQUE(Edu_Name),
	Constraint CK_EducationInstitution_EduRating Check (Edu_Rating > 0 AND Edu_Rating <= 10)
)
-- Создаем таблицу Образование (для реализации связи N:M)
-- Задаем внешние ключи, ненулевые поля
-- Проверяем, чтобы год окончания УО находился в опред. диапазоне
-- Задаем составной первичный ключ
Create Table Education
(
	Empl_ID int Constraint FK_Education_EmplID
	Foreign Key REFERENCES Employee(Empl_ID),
	Edu_ID int Constraint FK_Education_EduID
	Foreign Key REFERENCES EducationInstitution(Edu_ID),
	Education nvarchar(60) NOT NULL,
	Speciality nvarchar(60) NOT NULL,
	Grad_Year smallint Constraint CK_Education_GradYear 
	Check (Grad_Year > 1900 AND Grad_Year <= year(getdate())),
	Constraint PK_Education_EmplID_EduID Primary Key (Empl_ID, Edu_ID)
)
-- Создаем таблицу Назначение
-- Задаем первичный, внешний ключи, ненулевые поля
-- Проверяем, чтобы дата назначения находилась в опред. диапазоне
Create Table Appointment
(
	Order_Num int Constraint PK_Appointment_OrderNum
	Primary Key Identity,
	Empl_ID int Constraint FK_Appointment_EmplID
	Foreign Key REFERENCES Employee(Empl_ID),
	Post nvarchar(40) NOT NULL,
	Salary decimal(10, 2) NOT NULL,
	Department smallint NOT NULL,
	App_Date date NOT NULL,
	Constraint CK_Appointment_AppDate
	Check (App_Date > '01-01-1900' AND App_Date < getdate())
)
-- Создаем таблицу Отпуск
-- Задаем первичный и внешний ключи, ненулевые поля
-- Проверяем, чтобы даты начала и окончания отпуска находились в опред. диапазоне
-- и не нарушали логики (дата начала раньше даты окончания)
Create Table Vacation
(
	Order_Num int Identity,
	Empl_ID int NOT NULL,
	Inc_Beginning date NOT NULL,
	Inc_End date NOT NULL,
	Constraint PK_Vacation_OrderNum Primary Key (Order_Num),
	Constraint FK_Vacation_EmplID
	Foreign Key (Empl_ID) REFERENCES Employee(Empl_ID),
	Constraint CK_Vacation_IncPeriod
	Check (Inc_Beginning > '01-01-1900' AND Inc_End > Inc_Beginning AND Inc_Beginning <= getdate())
)
-- Создаем таблицу Больничный лист
-- Задаем первичный и внешний ключи, ненулевые поля
-- Проверяем, чтобы даты начала и окончания периода временной нетрудоспособности
-- находились в опред. диапазоне и не нарушали логики (дата начала раньше даты окончания)
Create Table SickLeave
(
	Sertificate_Num int Identity,
	Empl_ID int NOT NULL,
	Inc_Beginning date NOT NULL,
	Inc_End date NOT NULL,
	Constraint PK_SickLeave_OrderNum Primary Key (Sertificate_Num),
	Constraint FK_SickLeave_EmplID
	Foreign Key (Empl_ID) REFERENCES Employee(Empl_ID),
	Constraint CK_SickLeave_IncPeriod
	Check (Inc_Beginning > '01-01-1900' AND Inc_End > Inc_Beginning AND Inc_Beginning <= getdate())
)