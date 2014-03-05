
rem USERS
insert into secure_users values ('J_GUYOT','ADM','Jacques Guyot','Dévellopeur','AA0DB04EF46FD30D','ADMIN','SYSTEM','FRE','TABLE');
INSERT INTO SECURE_USERS VALUES('DEMO','ADM','demonstration user','','5EB260862BB0BDBE','NO','PUBLIC','ENG','TABLE')


rem GRANTS
                     
insert into secure_grants values ('J_GUYOT','SYSTEM','ADMIN_SEC');
insert into secure_grants values ('J_GUYOT','SYSTEM','ADMIN_NODE');
insert into secure_grants values ('J_GUYOT','PUBLIC','ANONYM');
INSERT INTO SECURE_GRANTS VALUES('DEMO','PUBLIC','ANONYM')



commit;

INSERT INTO SECURE_PROJECTS VALUES('MYTERM','Terminology Man')

INSERT INTO SECURE_ROLENODE VALUES('ANONYM','MYTERM.*','LAZY','ON MENU',999,'pour tous les noeuds')
INSERT INTO SECURE_ROLENODE VALUES('ANONYM','MYTERM.welcome','LAZY','ON MENU',3,'Welcome to Terminology Viewer')

INSERT INTO SECURE_TXT VALUES('MYTERM','!A_BCKGND','neutral.jpg')
INSERT INTO SECURE_TXT VALUES('MYTERM','!A_CSS','lazy.css')
INSERT INTO SECURE_TXT VALUES('MYTERM','!A_DB','MYTERM')
INSERT INTO SECURE_TXT VALUES('MYTERM','!A_XSL','lazy.xsl')

INSERT INTO SECURE_CONNECTS VALUES('MYTERM','','com.mysql.jdbc.Driver','jdbc:mysql://localhost:3306/myterm?zeroDateTimeBehavior=convertToNull','adminmyterm','AA0DB04EF46FD30D')





