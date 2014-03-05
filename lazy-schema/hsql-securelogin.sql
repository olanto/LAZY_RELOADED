CREATE MEMORY TABLE DUAL(X VARCHAR(1));
insert into dual values('X');
 
--drop table secure_manager;
create table secure_manager(ManagerId varchar(12) primary key,
			    manager varchar(2000)not null,
			    com varchar(2000)
);

insert into secure_manager values ('ADM','Administrateur (JG)', null);
commit;


--drop table secure_users;
create table secure_users(UserId varchar(12) primary key,
                          ManagerId varchar(12),
                          InfoUser varchar(2000)not null,
                          com varchar(2000),
                          pwd varchar(2000)not null,
                          admin varchar(12)not null,
                          defaultGrpId varchar(12)not null,
                          lang varchar(12),
                          style varchar(12)
                          
);



--drop table secure_data;
create table secure_data(GrpId varchar(12) primary key,
                         com varchar(2000)not null
);

insert into secure_data values ('SYSTEM','groupe de données de la gestion du serveur');
insert into secure_data values ('PUBLIC','groupe de données à caractère non confidentiel');
insert into secure_data values ('TEST','groupe de données pour les tests');
insert into secure_data values ('INFO','groupe de données pour le groupe Informatique');


commit;

                     
--drop table secure_roles;
create table secure_roles(roleId varchar(12) primary key,
                          com varchar(2000)not null
);



insert into secure_roles values ('ADMIN_SEC','autorisations pour l''administration de la sécurité');
insert into secure_roles values ('ADMIN_NODE','autorisations pour l''administration des noeuds');
insert into secure_roles values ('ANONYM','autorisations pour une personnes sans mot de passe');

--drop table secure_RoleNode;
create table secure_RoleNode(roleId varchar(12) not null,
                             NodeId varchar(100)not null,
                             TypeId varchar(12)not null,
                             Display varchar(12)not null,
                             Seq numeric not null,
                             lib varchar(100)not null,
                             primary key( roleid,nodeid,typeid)
);

insert into secure_RoleNode values ('ADMIN_NODE','NODE.all_projects','LAZY','ON MENU','999','Gérer les noeuds');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.lazy_admin_clearAllNodes','LAZY','MO','999','Effacer les caches');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.top','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all','LAZY','ON MENU','999','tous les objets de sécurité');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_users','LAZY','ON MENU','999','gérer les utilisateurs');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_managers','LAZY','ON MENU','999','gérer les responsables');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.users','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_users','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_users','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_users','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_pwd_users','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_data','LAZY','ON MENU','999','gérer les groupes de données');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.data','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_data','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_data','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_data','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_roles','LAZY','ON MENU','999','gérer les rôles');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.roles','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_roles','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_roles','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_roles','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_rolenode','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.rolenode','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_rolenode','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_rolenode','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_rolenode','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_categories','LAZY','ON MENU','999','gérer les catégories');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.categories','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_categories','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_categories','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_categories','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_codes','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.codes','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_codes','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_codes','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_codes','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_grants','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.grants','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_grants','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_grants','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_projects','LAZY','ON MENU','999','gérer les projets');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.projects','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_projects','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_projects','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_projects','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_txt','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.txt','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_txt','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_txt','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_txt','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.all_txtlang','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.txtlang','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.new_txtlang','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.del_txtlang','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.maj_txtlang','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.list_admin_YN','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.list_grpid','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.list_roleid','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.list_codeid','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.list_superlang','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ADMIN_SEC','SECURE.checkDB','LAZY','NO','999','nodef');



insert into secure_RoleNode values ('ANONYM','ICON.home','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.del','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.maj','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.new','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.pwd','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.definition','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.histo','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.docClasse','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.docPublic','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.docInterne','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.docConfidentiel','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.docSecret','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.personne','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','ICON.refresh','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.menu','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.menuitem','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.gprid_askmodify','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.grpid_modify','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.grpid_modify_with_node','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.grpid_modify_no_node','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.list_usergrpid','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.list_usergrpid_selected','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.call_lazy_admin_clearCache_window','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.maj_pwd_users','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE_PWDMODIF','TABLE','NO','999','(VUE) pour la mise à jour du mot de passe');
insert into secure_RoleNode values ('ANONYM','SECURE.genPwd','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.buildNode','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.dynamicNode','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','UTIL.header','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','UTIL.headerALL','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','UTIL.headerGEN_FIN','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','UTIL.headerACT_CH','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','UTIL.header55','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','UTIL.headerWithOutHR','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','UTIL.nowDateEtHeure','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','UTIL.nowDate','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.checkDB','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.lazy_admin_clearAllNodes','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.lazy_admin_clearDependentNodes','LAZY','NO','999','nodef');
insert into secure_RoleNode values ('ANONYM','SECURE.menuitemparam','LAZY','NO','999','nodef');


commit;
 
--drop table secure_categories;
create table secure_categories(CatId varchar(12) primary key,
                          com varchar(2000)not null
);

insert into secure_categories values ('ADMI','codes de droit d''administration');
insert into secure_categories values ('LANG','codes des langues');
insert into secure_categories values ('STYLE','codes des langues');
insert into secure_categories values ('NODETYPE','types des noeuds');
insert into secure_categories values ('DISPLAYTYPE','types d''affichage');

drop table secure_codes;
create table secure_codes (catId varchar(12) not null,
                           codeid varchar(12)not null,
                           abr    varchar(40)not null,
                           listdef char(1)not null,
                           primary key(catid,codeid)
);

insert into secure_codes values ('ADMI','ADMIN','autorise l''administration','N');
insert into secure_codes values ('ADMI','NO','non autorise l''administration','Y');
insert into secure_codes values ('LANG','FRE','Français','Y');
insert into secure_codes values ('LANG','ENG','Anglais','N');
insert into secure_codes values ('LANG','SPA','Espagnol','N');
insert into secure_codes values ('LANG','GER','Allemand','N');
insert into secure_codes values ('STYLE','TABLE','type tabulaire','Y');
insert into secure_codes values ('STYLE','LIST','type liste','N');
insert into secure_codes values ('NODETYPE','LAZY','Lazy noeud','Y');
insert into secure_codes values ('NODETYPE','TABLE','Modification de table','N');
insert into secure_codes values ('DISPLAYTYPE','ON MENU','à afficher dans un menu','Y');
insert into secure_codes values ('DISPLAYTYPE','NO','Ne pas afficher','N');
commit;

--drop view secure_defcode;
create view secure_defcode as
  select catid,codeid,abr from secure_codes where listdef='Y';

 
--drop table secure_grants;
create table secure_grants (userid varchar(12) not null,
                           grpid varchar(12)not null,
                           roleid varchar(12)not null,
                           primary key(userid,grpid,roleid));
                           
commit;

--drop table secure_projects;
create table secure_projects(projectId varchar(12) primary key,
                             com varchar(2000)not null
);

insert into secure_projects values ('SECURE','sécurité du serveur lazy');
insert into secure_projects values ('ICON','icone du serveur lazy');
insert into secure_projects values ('UTIL','noeuds utilitaires du serveur lazy');
insert into secure_projects values ('NODE','compilateur et noeuds lazy');
insert into secure_projects values ('SHARE','pour tous les projets');
commit;

--drop table secure_txt;
create table secure_txt(projectId varchar(12),
                        txtId varchar(24),
                        lib varchar(4000)not null,
                        primary key(projectId,txtId)
);

insert into secure_txt values ('SECURE','TITLE_USER','Utilisateurs');
insert into secure_txt values ('SECURE','TITLE_MANAGER','Responsables');
insert into secure_txt values ('SECURE','TITLE_DATA','Groupes de données');
insert into secure_txt values ('SECURE','TITLE_ROLE','Rôles');
insert into secure_txt values ('SECURE','TITLE_NODE','Noeuds');
insert into secure_txt values ('SECURE','TITLE_CODE','Codes');
insert into secure_txt values ('SECURE','TITLE_GRANT','Autorisations');
insert into secure_txt values ('SECURE','TITLE_PROJECT','Projets');
insert into secure_txt values ('SECURE','TITLE_TXT','Textes');
insert into secure_txt values ('SECURE','TITLE_TXTLANG','Langues');
insert into secure_txt values ('SECURE','TITLE_CATEGORIE','Catégories');
insert into secure_txt values ('SECURE','TITLE_CONNECT','Connexions');
insert into secure_txt values ('SECURE','!A_XSL','lazy.xsl');
insert into secure_txt values ('SECURE','!A_CSS','lazy.css');
insert into secure_txt values ('SECURE','!A_DB','DICTLAZY');
insert into secure_txt values ('SECURE','!A_BCKGND','neutral.jpg');


insert into secure_txt values ('NODE','!A_XSL','lazy.xsl');
insert into secure_txt values ('NODE','!A_CSS','lazy.css');
insert into secure_txt values ('NODE','!A_DB','DICTLAZY');
insert into secure_txt values ('NODE','!A_BCKGND','neutral.jpg');


insert into secure_txt values ('ICON','!A_XSL','lazy.xsl');
insert into secure_txt values ('ICON','!A_CSS','lazy.css');
insert into secure_txt values ('ICON','!A_DB','DICTLAZY');
insert into secure_txt values ('ICON','!A_BCKGND','neutral.jpg');

insert into secure_txt values ('UTIL','!A_XSL','lazy.xsl');
insert into secure_txt values ('UTIL','!A_CSS','lazy.css');
insert into secure_txt values ('UTIL','!A_DB','DICTLAZY');
insert into secure_txt values ('UTIL','!A_BCKGND','neutral.jpg');

insert into secure_txt values ('SHARE','STRUCT1','Structure de niveau 1');
insert into secure_txt values ('SHARE','STRUCT2','Structure de niveau 2');
insert into secure_txt values ('SHARE','STRUCT3','Structure de niveau 3');


--drop table secure_txtlang;
create table secure_txtlang(projectId varchar(12),
                        lang varchar(12),
                        txtId varchar(24),
                        lib varchar(4000)not null,
                        primary key(projectId,lang,txtId)
);

insert into secure_txtlang values ('SECURE','ENG','TITLE_USER','Users');
insert into secure_txtlang values ('SECURE','ENG','TITLE_MANAGER','Managers');
insert into secure_txtlang values ('SECURE','ENG','TITLE_DATA','Groups of data');
insert into secure_txtlang values ('SECURE','ENG','TITLE_ROLE','Roles');
insert into secure_txtlang values ('SECURE','ENG','TITLE_NODE','Nodes');
insert into secure_txtlang values ('SECURE','ENG','TITLE_CODE','Codes');
insert into secure_txtlang values ('SECURE','ENG','TITLE_GRANT','Grants');
insert into secure_txtlang values ('SECURE','ENG','TITLE_PROJECT','Projects');
insert into secure_txtlang values ('SECURE','ENG','TITLE_TXT','Texts');
insert into secure_txtlang values ('SECURE','ENG','TITLE_TXTLANG','Languages');
insert into secure_txtlang values ('SECURE','ENG','TITLE_CATEGORIE','Categories');
insert into secure_txtlang values ('SECURE','ENG','TITLE_CONNECT','Connections');
insert into secure_txtlang values ('SHARE','TABLE','STRUCT1','TABLE');
insert into secure_txtlang values ('SHARE','TABLE','STRUCT2','ROW');
insert into secure_txtlang values ('SHARE','TABLE','STRUCT3','CELL');
insert into secure_txtlang values ('SHARE','LIST','STRUCT1','LIST');
insert into secure_txtlang values ('SHARE','LIST','STRUCT2','ITEM');
insert into secure_txtlang values ('SHARE','LIST','STRUCT3','P');

commit;

--drop view secure_pwdmodif;
create view secure_pwdmodif as
   select userid, pwd from secure_users;


--drop view secure_alltxt;
create view secure_alltxt as
   select projectid,lang, txtid,lib from secure_txtlang
   union
   select projectid, 'FRE', txtid,lib from secure_txt;

--drop view secure_menu;
create view secure_menu as
   select distinct g.userid,g.grpid,g.roleid,r.com 
      from secure_grants g, secure_roles r, secure_rolenode n
      where g.roleid=r.roleid
      and g.roleid=n.roleid and display='ON MENU';


--drop view secure_usergrpid;
create view secure_usergrpid as
   select distinct userid,grpid 
      from secure_menu;


--drop view secure_menuitem;
create view secure_menuitem as
   select g.userid,g.grpid,g.roleid,n.nodeid,n.seq,n.lib 
      from secure_grants g, secure_rolenode n
      where g.roleid=n.roleid and display='ON MENU';

--drop view secure_grantnodes;
create view secure_grantnodes as
   select distinct g.userid,g.grpid,n.nodeid,n.typeid 
      from secure_grants g, secure_rolenode n
      where g.roleid=n.roleid;

--drop table secure_connects;
create table secure_connects(connectId varchar(12) primary key,
                          com varchar(2000),
                          driver varchar(100)not null,
                          url varchar(100)not null,
                          userdb varchar(100)not null,
                          pwddb varchar(100)not null
                          );



exit

