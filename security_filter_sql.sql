drop table if exists SECURITY_POLICY;
drop table if exists POLICY_TYPE;
drop table if exists USER_ROLE;


create table USER_ROLE (
id int auto_increment primary key,
name varchar(45) not null
);

insert into USER_ROLE values (1, "ADMINISTRATOR");
insert into USER_ROLE values (2, "STUDENT");
insert into USER_ROLE values (3, "PROVIDER");
insert into USER_ROLE value (4, "SUPERUSER");

create table POLICY_TYPE (
id int auto_increment primary key,
name varchar(45) not null
);

insert into POLICY_TYPE values (1, "ALL");
insert into POLICY_TYPE values (2, "NONE");
insert into POLICY_TYPE values (3, "INCLUDE");
insert into POLICY_TYPE values (4, "EXCLUDE");

create table SECURITY_POLICY (
id int auto_increment primary key,
type_id int not null,
role_id int not null,
url varchar(200) not null,
filters varchar(200),
foreign key(type_id) references POLICY_TYPE(id),
foreign key(role_id) references USER_ROLE(id)
);