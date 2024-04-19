use EmployeeAccounting
-- ������� ������� ���������
-- ������ ��������� ����, �������� �� ��������� ��� ����� �������� � ���� ������,
-- ������������ ����
-- ���������, ����� ��������� ���� �������� �������� � �����. ��������
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
-- ������� ������� ���������� �����������
-- ������ ��������� ����, ��������� ����, ������ 1 ������ ��� ������ �� - �������� �� ���������
-- ���������, ����� �������� �������� ���������� � ��������� �� 1 �� 10
Create Table EducationInstitution
(
	Edu_ID int Identity,
	Edu_Name nvarchar(100) NOT NULL,
	Edu_Rating smallint,
	Constraint PK_EducationInstitution_EduID Primary Key (Edu_ID),
	Constraint UQ_EducationInstitution_EduName UNIQUE(Edu_Name),
	Constraint CK_EducationInstitution_EduRating Check (Edu_Rating > 0 AND Edu_Rating <= 10)
)
-- ������� ������� ����������� (��� ���������� ����� N:M)
-- ������ ������� �����, ��������� ����
-- ���������, ����� ��� ��������� �� ��������� � �����. ���������
-- ������ ��������� ��������� ����
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
-- ������� ������� ����������
-- ������ ���������, ������� �����, ��������� ����
-- ���������, ����� ���� ���������� ���������� � �����. ���������
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
-- ������� ������� ������
-- ������ ��������� � ������� �����, ��������� ����
-- ���������, ����� ���� ������ � ��������� ������� ���������� � �����. ���������
-- � �� �������� ������ (���� ������ ������ ���� ���������)
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
-- ������� ������� ���������� ����
-- ������ ��������� � ������� �����, ��������� ����
-- ���������, ����� ���� ������ � ��������� ������� ��������� ������������������
-- ���������� � �����. ��������� � �� �������� ������ (���� ������ ������ ���� ���������)
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