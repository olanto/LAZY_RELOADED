drop table nodes;
create table nodes(
   projectid varchar(12),
   name varchar(100),
   nbparam numeric, 
   pre varchar(4000), 
   items varchar(4000), 
   post varchar(4000), 
   collection varchar(4000), 
   selector varchar(4000), 
   ordering varchar(4000),
   groupby varchar(4000),
   plaintxt varchar(4000), 
   status varchar(12),
   error varchar(4000),
   CACHESIZE  numeric,
   primary key (projectid,name));
