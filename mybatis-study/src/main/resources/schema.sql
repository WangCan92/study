
drop table if exists user;

create table user(
    id bigint identity,  /*identity 表示自增*/
    name varchar(50),
    address varchar(50),
    primary key (id)
    );
