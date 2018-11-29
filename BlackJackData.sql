drop table BlackJackData;

create table BlackJackData
	(username varchar(20) not null,
	password varchar(20) not null,
	Balance INTEGER);
alter table BlackJackData
	add constraint username_pk primary key(username);
	
insert into BlackJackData values('gjacobus', aes_encrypt('hello', 'blackjack'), 50);

insert into BlackJackData values('mdodd', aes_encrypt('hello123', 'blackjack'), 50);

	