rem cache management
--drop table secure_objdep;
create table secure_objdep(
       obj1 varchar(100),
       obj1type varchar(50),
       obj2isdept varchar(100),
       obj2type varchar(5),
       primary key (obj1,obj2isdept));

insert into secure_objdep 
   select * from secure_manualobjdep;

insert into secure_objdep select distinct * from secure_dictdep;


commit;


--drop table secure_nodedep;
create table secure_nodedep(
           projectid varchar(12),
           name varchar(100),
           getvaluefrom varchar(100));


--drop table secure_manualobjdep;
create table secure_manualobjdep(
       obj1 varchar(100),
       obj1type varchar(5),
       obj2isdept varchar(100),
       obj2type varchar(5),
       primary key (obj1,obj1type,obj2isdept,obj2type));

rem insert into secure_manualobjdep values('EMP','TABLE','XXX1','VIEW');
rem insert into secure_manualobjdep values('XXX1','VIEW','XXX2','VIEW');

       

--drop view secure_depclosure;
create view secure_depclosure as
   select getvaluefrom obj1,getvaluefrom obj2isdept
   from secure_nodedep
union
   select k1.obj1,k1.obj2isdept
   from secure_objdep k1
union
   select k1.obj1,k2.obj2isdept 
   from secure_objdep k1, secure_objdep k2
   where k1.obj2isdept=k2.obj1
union
   select k1.obj1,k3.obj2isdept 
   from secure_objdep k1, secure_objdep k2, secure_objdep k3
   where k1.obj2isdept=k2.obj1
     and k2.obj2isdept=k3.obj1
union
   select k1.obj1,k4.obj2isdept 
   from secure_objdep k1, secure_objdep k2, secure_objdep k3, secure_objdep k4
   where k1.obj2isdept=k2.obj1
     and k2.obj2isdept=k3.obj1
     and k3.obj2isdept=k4.obj1
union
   select k1.obj1,k5.obj2isdept 
   from secure_objdep k1, secure_objdep k2, secure_objdep k3, secure_objdep k4, secure_objdep k5
   where k1.obj2isdept=k2.obj1
     and k2.obj2isdept=k3.obj1
     and k3.obj2isdept=k4.obj1
     and k4.obj2isdept=k5.obj1
   ;


rem select * from secure_depclosure where obj1='EMP';
rem select * from secure_depclosure where obj1='SECURE_USERS';
rem select * from secure_depclosure where obj1='ELEMENTS';
rem select * from secure_depclosure where obj2isdept='EMPVIEW2';

--drop view  secure_nodedepclosure;
create view  secure_nodedepclosure as
   select obj1,name,projectid
   from secure_nodedep n, secure_depclosure c
   where c.obj2isdept=n.getvaluefrom;

rem select distinct * from secure_nodedepclosure where obj1='DEPT';
rem select distinct * from secure_nodedepclosure where obj1='ELEMENTS';
rem select distinct * from secure_nodedepclosure where obj1='SECURE_USERS';
rem select distinct * from secure_nodedepclosure where obj1='NODES';
rem select distinct * from secure_nodedepclosure where obj1='EMPVIEW1';


--drop view secure_nodecachereset;
create view secure_nodecachereset as
   select obj2isdept objismodify, n.obj1, n.name, n.projectid
   from  secure_nodedepclosure n, secure_depclosure c
   where c.obj1=n.obj1;
 
*/   

rem select distinct name,  projectid from secure_nodecachereset where objismodify='DEPT';
rem select distinct name,  projectid from secure_nodecachereset where objismodify='ELEMENTS';
rem select distinct name,  projectid from secure_nodecachereset where objismodify='SECURE_USERS';
rem select distinct name,  projectid from secure_nodecachereset where objismodify='NODES';
rem select distinct name,  projectid from secure_nodecachereset where objismodify='EMPVIEW1';
rem select distinct name,  projectid from secure_nodecachereset where objismodify='XXX2';

select distinct name,  projectid from secure_nodecachereset where objismodify='T';


rem initialize view dependencies


   
   
--drop table secure_dictdep;
create table secure_dictdep(
       obj1 varchar(100),
       obj1type varchar(50),
       obj2isdept varchar(100),
       obj2type varchar(5));


/* a initialiser en fonction du SGBD
insert into secure_dictdep 
select  REFERENCED_NAME,REFERENCED_TYPE, NAME, TYPE 
   from dba_dependencies 
   where TYPE='VIEW'
   and REFERENCED_TYPE in('VIEW','TABLE')
   and owner in ('LAZY', 'PFR', 'PFT_V', 'PFT', 'OLAP')
   ;

rem étendre aux autres bd ... avec un database link
*/


drop table secure_objdep;
create table secure_objdep(
       obj1 varchar(100),
       obj1type varchar(50),
       obj2isdept varchar(100),
       obj2type varchar(5),
       primary key (obj1,obj2isdept));

insert into secure_objdep 
   select * from secure_manualobjdep;

insert into secure_objdep select distinct * from secure_dictdep;


commit;

