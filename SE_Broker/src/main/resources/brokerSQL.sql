drop table if exists ROLES_FOR_ENDPOINT;
drop table if exists META_SCHEME_ATTRIBUTE;
drop table if exists ENDPOINT_META_SCHEME;
drop table if exists ENDPOINT;
drop table if exists AGGREGATE_FUNCTION;
drop table if exists COMPOSITE_SERVICES;
drop table if exists SERVICE;
drop table if exists PROVIDER;
drop table if exists ROLES_FOR_USER;
drop table if exists USER_ROLE;
drop table if exists APP_USER;
drop table if exists ROLES_FOR_USER;

create table APP_USER (
username varchar(45) primary key,
password varchar(100) not null
);

insert into APP_USER values ("vdabic", "$2y$12$NyOf2b/Qm/i1ZyjFyCSIyuC7xyoLmN2hKmOkAOWKXNfRT8ScBBCMO");
insert into APP_USER values ("ikocic", "$2y$12$NyOf2b/Qm/i1ZyjFyCSIyuC7xyoLmN2hKmOkAOWKXNfRT8ScBBCMO");
insert into APP_USER values ("superuser", "$2y$12$NyOf2b/Qm/i1ZyjFyCSIyuC7xyoLmN2hKmOkAOWKXNfRT8ScBBCMO");
insert into APP_USER values ("heavyClient", "$2y$12$NyOf2b/Qm/i1ZyjFyCSIyuC7xyoLmN2hKmOkAOWKXNfRT8ScBBCMO");
insert into APP_USER values ("students", "$2y$12$NyOf2b/Qm/i1ZyjFyCSIyuC7xyoLmN2hKmOkAOWKXNfRT8ScBBCMO");

create table USER_ROLE (
id int auto_increment primary key,
name varchar(45) not null
);

insert into USER_ROLE values (1, "ADMINISTRATOR");
insert into USER_ROLE values (2, "STUDENT");
insert into USER_ROLE values (3, "PROVIDER");
insert into USER_ROLE value (4, "SUPERUSER");

create table ROLES_FOR_USER (
id int auto_increment primary key,
user_id varchar(45) not null,
role_id int not null,
foreign key(user_id) references APP_USER(username),
foreign key(role_id) references USER_ROLE(id)
);

insert into ROLES_FOR_USER values (1, "vdabic", 1);
insert into ROLES_FOR_USER values (2, "ikocic", 2);
insert into ROLES_FOR_USER values (3, "superuser", 4);
insert into ROLES_FOR_USER values (4, "heavyClient", 3);
insert into ROLES_FOR_USER values (5, "students", 3);

create table PROVIDER (
username varchar(45) primary key,
foreign key(username) references APP_USER(username),
url varchar(50) not null,
port int not null
);

insert into PROVIDER values ("heavyClient", "heavy-client.com", 8081);
insert into PROVIDER values ("students", "students.com", 8082);

create table SERVICE (
name varchar(50) primary key,
url varchar(50) not null,
composite boolean,
provider_id varchar(45) not null,
foreign key(provider_id) references PROVIDER(username)
);

create table AGGREGATE_FUNCTION (
id int auto_increment primary key,
category varchar(50) not null,
subcategory varchar(50) not null
);

insert into AGGREGATE_FUNCTION values (1, "FUNCTION", "SUM");
insert into AGGREGATE_FUNCTION values (2, "FUNCTION", "AVG");


create table ENDPOINT (
id int auto_increment primary key,
url varchar(50) not null,
method varchar(50) not null,
aggregate_function_id int,
service_id varchar(50) not null,
foreign key(service_id) references SERVICE(name),
foreign key(aggregate_function_id) references AGGREGATE_FUNCTION(id)
);

create table ENDPOINT_META_SCHEME(
id int auto_increment primary key,
type varchar(50),
endpoint_id int not null,
foreign key(endpoint_id) references ENDPOINT(id)
);

create table META_SCHEME_ATTRIBUTE (
id int auto_increment primary key,
attribute_key varchar(50) not null,
attribute_value varchar(50) not null,
mandatory boolean,
transmission varchar(50) not null,
meta_scheme_id int not null,
foreign key(meta_scheme_id) references ENDPOINT_META_SCHEME(id) 
);

create table COMPOSITE_SERVICES (
id int auto_increment primary key,
composite_service_id varchar(50),
service_id varchar(50),
foreign key(composite_service_id) references SERVICE(name),
foreign key(service_id) references SERVICE(name)
);

create table ROLES_FOR_ENDPOINT (
id int auto_increment primary key,
endpoint_id int not null,
role_id int not null,
foreign key(endpoint_id) references ENDPOINT(id),
foreign key(role_id) references USER_ROLE(id)
);

alter table ROLES_FOR_USER auto_increment=1;
alter table USER_ROLE auto_increment=1;
alter table META_SCHEME_ATTRIBUTE auto_increment=1;
alter table ENDPOINT_META_SCHEME auto_increment=1;
alter table ENDPOINT auto_increment=1;
alter table COMPOSITE_SERVICES auto_increment=1;
alter table SERVICE auto_increment=1;
alter table ROLES_FOR_ENDPOINT auto_increment=1;
alter table AGGREGATE_FUNCTION auto_increment=1;



