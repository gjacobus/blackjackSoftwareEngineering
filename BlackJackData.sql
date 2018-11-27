drop table 'BlackJackData;

create table BlackJackData
	(username varchar(20) not null,
	password varchar(20) not null,
	Balance float);
alter table BlackJackData
	add constraint username_pk primary key(username);
	