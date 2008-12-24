create database FaceLockDB;
use FaceLockDB;

create table Country(
	id		int primary key,
	name	varchar(300)
);
create table City(
	id			int primary key,
	name		varchar(300),
	country_id	int,
	foreign key(country_id) references Country(id)
);

create table Town(
	id			int primary key,
	name		varchar(300),
	city_id		int,
	foreign key(city_id) references City(id)
);


create table Address(
	id	int primary key,
	town_id	int,
	street varchar(300)
);

create table Contact(
	id		int primary key,
	firstname	varchar(250),
	middlename varchar(250),
	lastname	varchar(250),
	sex		int, 
	Birthday	datetime,
	location	int, 
	status	int, 
	Foreign Key(location) references Address(id)
);


/* Creating FLUser with FLPassword*/
GRANT ALL PRIVILEGES ON *.* TO 'FLUser'@'localhost' IDENTIFIED BY 'FLPassword' WITH GRANT OPTION;
