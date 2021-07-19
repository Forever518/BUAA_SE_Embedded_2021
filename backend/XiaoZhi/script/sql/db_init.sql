create database fri01;
use fri01;

create table map
(
	Map_name varchar(128) not null,
	Map_path varchar(128) not null,
	Index_path varchar(128) null,
	Map_show_path varchar(128) not null,
	constraint map_pk
		primary key (Map_name)
);


