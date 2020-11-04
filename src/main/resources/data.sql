drop table if exists CLIENT cascade;


create table CLIENT(
    ID int not null AUTO_INCREMENT,
    NAME varchar(50),
    EMAIL varchar(50),
    API_KEY varchar(50),
    PRIMARY KEY(ID)
);