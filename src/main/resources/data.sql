drop table if exists CLIENT cascade;


create table CLIENT(
    ID int not null AUTO_INCREMENT,
    NAME varchar(50),
    EMAIL varchar(50),
    API_KEY varchar(50),
    PRIMARY KEY(ID)
);

insert into CLIENT (NAME, EMAIL, API_KEY) values ('Oracle', 'oracle@oracle.com', 'a3b9431e-1e72-11eb-adc1-0242ac120002');