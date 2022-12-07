drop table student;
drop table faculty;
drop table department;
drop table college;
create table college(
id long primary key not null auto_increment,
name varchar(50),
phone_number varchar(10)
);


create table department(
id long primary key not null auto_increment,
name varchar(50),
college_id long,
foreign key(college_id) references college(id),
UNIQUE(name,college_id)

);


create table faculty(
id long primary key not null auto_increment,
name varchar(50),
designation varchar(50),
subject varchar(50),
department_id long,
foreign key(department_id) references department(id)
);


create table student(
id long primary key not null auto_increment,
name varchar(50),
department_id long,
mark_1 int,
mark_2 int,
mark_3 int,
foreign key(department_id) references department(id)
);