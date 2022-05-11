create table user
(
  id       int primary key auto_increment,
  login    varchar(30) not null,
  name     varchar (20) not null,
  surname  varchar (20) not null,
  password varchar(80) not null,
  unique uniq_login (login)
);

insert into user (login, name, surname, password) values
('login', 'Anna', 'Hinkul', '$2a$10$jGt4OvlZv4sbhNlAeG64leBg9fKBEOSVXwN/HiIwl.DFflkurYHrO'),
('login2', 'Ivan', 'Ivanov', '$2a$10$jGt4OvlZv4sbhNlAeG64leBg9fKBEOSVXwN/HiIwl.DFflkurYHrO');
