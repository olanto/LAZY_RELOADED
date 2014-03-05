drop table profil_user;
create table profil_user(UserId varchar(12) not null,
                   ProfilId varchar(12) not null,
                   content varchar(20000)not null,
   primary key (UserId, ProfilId)
   );
   
insert into profil_user values ('ALICE','p1','texte p1');
insert into profil_user values ('ALICE','p2','texte p2');
insert into profil_user values ('ALICE','p3','texte p3');
insert into profil_user values ('BOB','java prog','texte prog');
insert into profil_user values ('BOB','java ile','texte ile');
commit;
