drop table if exists JOB_POSITION cascade;
drop table if exists CLIENT cascade;


create table CLIENT(
    ID int not null AUTO_INCREMENT,
    NAME varchar(50),
    EMAIL varchar(50),
    API_KEY varchar(50),
    PRIMARY KEY(ID)
);

create table JOB_POSITION(
    ID int not null AUTO_INCREMENT,
    NAME varchar(50),
    LOCAL varchar(50),
    CLIENT_ID int,
    PRIMARY KEY(ID),
    CONSTRAINT position_client_fk FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT (ID)
);

insert into CLIENT (NAME, EMAIL, API_KEY) values ('Oracle', 'oracle@oracle.com', 'a3b9431e-1e72-11eb-adc1-0242ac120002');
insert into JOB_POSITION (NAME, LOCAL, CLIENT_ID) values ('Dev', 'London', 1);
insert into JOB_POSITION (NAME, LOCAL, CLIENT_ID) values ('DevOps', 'Berlin', 1);