create table role (
                      id       int primary key auto_increment,
                      role    varchar(30) not null
);

create table user
(
  id       int primary key auto_increment,
  login    varchar(30) not null,
  name     varchar (20) not null,
  surname  varchar (20) not null,
  password varchar(80) not null,
  role_id int not null,
  constraint fk_role_to_user foreign key (role_id) references role(id) ON DELETE CASCADE,
  unique uniq_login (login)
);





create table brand
(
    id       int primary key auto_increment,
    name    varchar(30) not null,
    country    varchar(30) null
);

create table set
(
    id       int primary key auto_increment,
    name    varchar(30) not null,
    capacity    float not null,
    image varchar(200) not null,
    brand_id int not null,
    constraint fk_brand_to_set foreign key (brand_id) references brand(id) ON DELETE CASCADE
);

create table collection
(
    id       int primary key auto_increment,
    name    varchar(30) not null,
    user_id int not null,
    constraint fk_user_to_set foreign key (user_id) references user(id) ON DELETE CASCADE
);



create table colour
(
    id       int primary key auto_increment,
    name    varchar(30) not null,
    type    varchar(30) not null,
    pigments    varchar(60)  null,
    lightfast    varchar(30)  null,
    staining    varchar(30)  null,
    granulation    boolean  null,
    grade    varchar(30) not null,
    confirmedASTM    boolean  not null,
    approved    boolean  not null,
    opacity    varchar(30)  null,
    binder    varchar(30)  null,
    additives    varchar(30)  null,
    image varchar(200) not null,
    brand_id int not null,
    constraint fk_brand_to_colour foreign key (brand_id) references brand(id) ON DELETE CASCADE
);

create table set_to_colour
(
    set_id int not null,
    constraint fk_set_to_colour_set foreign key (set_id) references set(id) ON DELETE CASCADE,
    colour_id int not null,
    constraint fk_set_to_colour_colour foreign key (colour_id) references colour(id) ON DELETE CASCADE
);

create table collection_to_colour
(
    collection_id int not null,
    constraint fk_collection_to_colour_set foreign key (collection_id) references collection(id) ON DELETE CASCADE,
    colour_id int not null,
    constraint fk_collection_to_colour_colour foreign key (colour_id) references colour(id) ON DELETE CASCADE
);


insert into brand (name, country) values
                                        ('Daniel Smith', 'America'),
                                        ('Schmincke', 'Germany');

insert into role (role) values
                            ('CLIENT'),
                            ('ADMIN'),
                            ('OWNER');

insert into user (login, name, surname, password, role_id) values
                                                      ('login', 'Anna', 'Hinkul', '$2a$10$jGt4OvlZv4sbhNlAeG64leBg9fKBEOSVXwN/HiIwl.DFflkurYHrO',1),
                                                      ('login2', 'Ivan', 'Ivanov', '$2a$10$jGt4OvlZv4sbhNlAeG64leBg9fKBEOSVXwN/HiIwl.DFflkurYHrO',2);




insert into colour(name, type, pigments, lightfast, staining, granulation, grade, confirmedASTM, approved, opacity, binder, image, brand_id) values
                ('Ultramarine blue', 'Watercolour', 'pb29', 'Excellent', '3', true, 'Professional', true, true, 'Transperent', 'Gumarabic', '/img/colour1.jpg', 1),
                ('French ultramarine', 'Watercolour', 'pb29', 'Excellent', '2', true, 'Professional', true, true, 'Transperent', 'Gumarabic', '/img/colour2.jpg', 2),
                ('Yellow ochre', 'Watercolour', 'po42', 'Excellent', '1', true, 'Professional', true, true, 'Opique', 'Gumarabic', '/img/colour3.jpg', 1),
                ('Quinacridone rose', 'Watercolour', 'pv19', 'Excellent', '2', false, 'Professional', true, true, 'Transperent', 'Gumarabic', '/img/colour4.jpg', 1),
                ('Phtalo blue (GS)', 'Watercolour', 'pb15', 'Excellent', '3', false, 'Professional', true, true, 'Transperent', 'Gumarabic', '/img/colour5.jpg', 1),
                ('Hansa yellow light', 'Watercolour', null, 'Excellent', '1', null, 'Professional', true, true, 'Transperent', 'Gumarabic', '/img/colour6.jpg', 1),
                ('Pyrrol scarlet', 'Watercolour', 'pr255', 'Excellent', '1', false, 'Professional', false, true, 'Transperent', 'Gumarabic', '/img/colour7.jpg', 1),
                ('New gamboge', 'Watercolour', null, 'Good', '2', null, 'Professional', true, true, 'Transperent', null, '/img/colour8.jpg', 1);



insert into set (name, capacity, image, brand_id) values
                                      ('Essentials set', 5.0, '/img/set1.jpg', 1);

insert into set_to_colour (set_id, colour_id) values
                                                      (1,1),
                                                      (1,4),
                                                      (1,5),
                                                      (1,7),
                                                      (1,6),
                                                      (1,8);

insert into collection (name, user_id) values
                                                  ('Selected',1),
                                                  ('Selected',2);