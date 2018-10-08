select * from tb1_board;
select * from tb2_board;
select * from reply;
select * from logininfo;
select * from user;
select * from Test;
select last_insert_id()
from tb1_board;

select * from tb1_board order by bno desc;
select * from attach order by regdate desc;

create table attach /*0928*/
(
	fullName varchar(150) not null primary key,
    bno int not null,
    regdate timestamp default now()
);

create table reply /*1005*/
(
	rno int not null auto_increment primary key,
	bno int not null,
    replytext varchar(1000) not null,
    replyer varchar(50) not null,
    regdate timestamp not null default now(),
    updatedate timestamp not null default now()
);

alter table attach drop constraint fk_board_attach;

alter table attach add constraint fk_board_attach
foreign key (bno) references tb1_board (bno);

alter table reply add constraint fk_board_reply
foreign key (bno) references tb1_board(bno);

select * from attach;

update tb1_board set replycnt = null;

UPDATE tb1_board
SET replycnt = NULL
WHERE replycnt is not null;

delete from tb1_board where bno > 0 ;
delete from reply where rno > 0 ;
delete from attach where bno = 7891 ;

drop table attach435;

drop table reply23213;

insert into reply(rno, bno, replytext, replyer)
values(1, 1,"댓", "글");

create table asd
(
    bno int not null primary key
);

insert into asd value(2);

select * from asd;

select last_insert_id();

insert into tb1_board(title, content, writer)
		values("231313", "dd", "dd");

insert into attach( bno, fullName )
				   values( last_insert_id(), fullName );
                   
ALTER TABLE tb1_board AUTO_INCREMENT= 1;

select * from user where userid = "user6" and userpw =  password(1234);

delete from user 
where userid = 'user1';