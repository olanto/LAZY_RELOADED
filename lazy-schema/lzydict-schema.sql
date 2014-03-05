rename nodes to nodes_lazy30;
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
   CACHESIZE  NUMBER,
   primary key (projectid,name));






for ms server


drop table nodes;
create table nodes(
   projectid varchar(12),
   name varchar(100) primary key,
   nbparam numeric, 
   pre varchar(1000), 
   items varchar(4000), 
   post varchar(1000), 
   collection varchar(500), 
   selector varchar(500), 
   ordering varchar(500));
