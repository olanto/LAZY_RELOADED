
delete from nodes where name='welcome';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'welcome',
 0,
''''', ''<<??include a=MYTERM.header&amp;u='', ''//??>>'', ''<hr/>'', ''<b'', ''>'', ''Queries<br/>'', ''</b>'', ''Example: search: progressive lens, from english to french '', ''<a href="ns?a=MYTERM.querybysota&amp;u='', ''progressive lens%'', ''&amp;u='', ''EN'', ''&amp;u='', ''FR'', ''">'', ''Do a query at term level'', ''</a>'', ''<br/>'', ''Example: search:tuna%, from english to french '', ''<a href="ns?a=MYTERM.querybysota&amp;u='', ''tuna%'', ''&amp;u='', ''EN'', ''&amp;u='', ''FR'', ''">'', ''Do a query at term level'', ''</a>'', ''<br/>'', ''Example:search: progressive lens, in english '', ''<a href="ns?a=MYTERM.queryconcept&amp;u='', ''progressive lens%'', ''&amp;u='', ''EN'', ''">'', ''Do a query at concept level'', ''</a>'', ''<br/>'', ''Example:search: tunas%, in english '', ''<a href="ns?a=MYTERM.queryconcept&amp;u='', ''tunas%'', ''&amp;u='', ''EN'', ''">'', ''Do a query at concept level'', ''</a>'', ''<br/>'', ''<b'', ''>'', ''Statistics<br/>'', ''</b>'', ''<a href="ns?a=MYTERM.ressources&amp;u='', ''">'', ''Statistics on ressources (wait ... needs to recompute all stats)<br/>'', ''</a>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='header';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'header',
 0,
''''', ''<h2'', ''>'', ''myTERM - Welcome to terminology browser - Olanto Foundation'', ''</h2>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='top';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'top',
 0,
''''', ''<<??include a=MYTERM.header&amp;u='', ''//??>>'', ''<a href="ns?a=MYTERM.welcome&amp;u='', ''">'', ''main menu'', ''</a>'', ''<hr/>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='foot';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'foot',
 0,
''''', ''<hr/>'', ''<a href="ns?a=MYTERM.welcome&amp;u='', ''">'', ''main menu'', ''</a>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='querymodifyconcept';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'querymodifyconcept',
 2,
'''''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="MYTERM.queryconcept"/>'', ''<input type="submit" name="bidon" value="'', ''Execute'', ''"/>'', '' Request : '', ''<input type="text" name="u" size="'', 32, ''" value="'', <<??param-0//??>>, ''"/>'', '' Source lang. '', ''<select name="u" >'', ''<<??include a=MYTERM.list_lang&amp;u='', <<??param-1//??>>, ''//??>>'', ''</select>'', ''<hr/>'', ''</form>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='queryconcept';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'queryconcept',
 2,
''''', ''<<??include a=MYTERM.top&amp;u='', ''//??>>'', ''<<??include a=MYTERM.querymodifyconcept&amp;u='', <<??param-0//??>>, ''&amp;u='', <<??param-1//??>>, ''//??>>'', ''<<??include a=MYTERM.getresultconcept&amp;u='', <<??param-0//??>>, ''&amp;u='', <<??param-1//??>>, ''//??>>'', ''<<??include a=MYTERM.foot&amp;u='', ''//??>>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='getresultconcept';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'getresultconcept',
 2,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''Result by concept'', ''</CAPTION>'', ''<ROW'', ''>'', ''<CELL_TITLE'', ''>'', ''Detail'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''<<??include a=MYTERM.decode_lang&amp;u='', <<??param-1//??>>, ''//??>>'', ''</CELL_TITLE>'', ''</ROW>''',
''''', ''<ROW_ALT'', ''>'', ''<CELL'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.detail_concept&amp;u='', id_concept, ''">'', ''?'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', source, ''</CELL>'', ''</ROW_ALT>''',
''''', ''</TABLE>''',
'v_source',
'source like <<??param-0//??>> and solang=<<??param-1//??>>',
'source'
);

delete from nodes where name='querybysota';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'querybysota',
 3,
''''', ''<<??include a=MYTERM.top&amp;u='', ''//??>>'', ''<<??include a=MYTERM.querymodify&amp;u='', <<??param-0//??>>, ''&amp;u='', <<??param-1//??>>, ''&amp;u='', <<??param-2//??>>, ''//??>>'', ''<<??include a=MYTERM.getresult&amp;u='', <<??param-0//??>>, ''&amp;u='', <<??param-1//??>>, ''&amp;u='', <<??param-2//??>>, ''//??>>'', ''<<??include a=MYTERM.foot&amp;u='', ''//??>>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='querymodify';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'querymodify',
 3,
'''''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="MYTERM.querybysota"/>'', ''<input type="submit" name="bidon" value="'', ''Execute'', ''"/>'', '' Request : '', ''<input type="text" name="u" size="'', 32, ''" value="'', <<??param-0//??>>, ''"/>'', '' Source lang. '', ''<select name="u" >'', ''<<??include a=MYTERM.list_lang&amp;u='', <<??param-1//??>>, ''//??>>'', ''</select>'', '' Target lang. '', ''<select name="u" >'', ''<<??include a=MYTERM.list_lang&amp;u='', <<??param-2//??>>, ''//??>>'', ''</select>'', ''<hr/>'', ''</form>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='getresult';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'getresult',
 3,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''Result'', ''</CAPTION>'', ''<ROW'', ''>'', ''<CELL_TITLE'', ''>'', ''Detail'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''<<??include a=MYTERM.decode_lang&amp;u='', <<??param-1//??>>, ''//??>>'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''<<??include a=MYTERM.decode_lang&amp;u='', <<??param-2//??>>, ''//??>>'', ''</CELL_TITLE>'', ''</ROW>''',
''''', ''<ROW_ALT'', ''>'', ''<CELL'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.detailbiterm&amp;u='', id_term_source, ''&amp;u='', id_term_target, ''&amp;u='', resource_name, ''">'', ''?'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', source, ''</CELL>'', ''<CELL'', ''>'', target, ''</CELL>'', ''</ROW_ALT>''',
''''', ''</TABLE>''',
'v_sourcetarget',
'source like <<??param-0//??>> and solang=<<??param-1//??>> and talang=<<??param-2//??>>',
'source limit 20'
);

delete from nodes where name='detailbiterm';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detailbiterm',
 3,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''Ressource: '', <<??param-2//??>>, ''</CAPTION>'', ''<ROW'', ''>'', ''<CELL'', ''>'', ''<<??include a=MYTERM.detail_term&amp;u='', <<??param-0//??>>, ''//??>>'', ''</CELL>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL'', ''>'', ''<<??include a=MYTERM.detail_term&amp;u='', <<??param-1//??>>, ''//??>>'', ''</CELL>'', ''</ROW>'', ''</TABLE>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='detail_term';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term',
 1,
'''''',
''''', ''<i'', ''>'', ''<<??include a=MYTERM.decode_lang&amp;u='', id_language, ''//??>>'', '': '', ''</i>'', ''<b'', ''>'', term_form, ''</b>'', ''<<??include a=MYTERM.detail_term_partofspeech&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_type&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_admin_status&amp;u='', <<??param-0//??>>, ''//??>>''',
'''''',
'terms',
'id_term=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='detail_term_partofspeech';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term_partofspeech',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Part of speech: '', ''</i>'', term_partofspeech',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_partofspeech is  not  null ',
'NODEF'
);

delete from nodes where name='detail_term_type';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term_type',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Type: '', ''</i>'', term_type',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_type is  not  null ',
'NODEF'
);

delete from nodes where name='detail_term_admin_status';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term_admin_status',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Admin Status: '', ''</i>'', term_admin_status',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_admin_status is  not  null ',
'NODEF'
);

delete from nodes where name='detail_concept';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_concept',
 1,
''''', ''<<??include a=MYTERM.header_concept&amp;u='', <<??param-0//??>>, ''//??>>'', ''<TABLE'', ''>''',
''''', ''<ROW_ALT'', ''>'', ''<CELL'', ''>'', ''<<??include a=MYTERM.detail_term&amp;u='', id_term_source, ''//??>>'', ''</CELL>'', ''</ROW_ALT>''',
''''', ''</TABLE>''',
'v_source',
'id_concept=<<??param-0//??>>',
'solang,source'
);

delete from nodes where name='header_concept';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'header_concept',
 1,
''''', ''<<??include a=MYTERM.detail_concept_resource&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_concept_subject_field&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_concept_definition&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_concept_source_definition&amp;u='', <<??param-0//??>>, ''//??>>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='detail_concept_resource';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_concept_resource',
 1,
'''''',
''''', ''<i'', ''>'', ''Resource: '', ''</i>'', resource_name',
'''''',
'v_conceptres',
'id_concept=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='detail_concept_subject_field';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_concept_subject_field',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Subject field: '', ''</i>'', subject_field',
'''''',
'concepts',
'id_concept=<<??param-0//??>> and subject_field is  not  null ',
'NODEF'
);

delete from nodes where name='detail_concept_source_definition';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_concept_source_definition',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Source defintion: '', ''</i>'', concept_source_definition',
'''''',
'concepts',
'id_concept=<<??param-0//??>> and concept_source_definition is  not  null ',
'NODEF'
);

delete from nodes where name='detail_concept_definition';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_concept_definition',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Definition: '', ''</i>'', concept_definition',
'''''',
'concepts',
'id_concept=<<??param-0//??>> and concept_definition is  not  null ',
'NODEF'
);

delete from nodes where name='list_lang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_lang',
 1,
'''''',
''''', ''<option'', ''>'', if(id_language=<<??param-0//??>>,''<selected_option/>'',''''), ''<value'', ''>'', id_language, ''</value>'', language_default_name, ''</option>''',
'''''',
'languages',
'NODEF',
'id_language'
);

delete from nodes where name='decode_lang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'decode_lang',
 1,
'''''',
''''', language_default_name',
'''''',
'languages',
'id_language=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='ressources';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'ressources',
 0,
''''', ''<<??include a=MYTERM.top&amp;u='', ''//??>>'', ''statistics on resources <hr/>'', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''Statistics on Resources'', ''</CAPTION>'', ''<ROW'', ''>'', ''<CELL_TITLE'', ''>'', ''Resource Name'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Privacy'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Nb. Concepts'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Languages'', ''</CELL_TITLE>'', ''</ROW>''',
''''', ''<ROW'', ''>'', ''<CELL'', ''>'', resource_name, ''</CELL>'', ''<CELL'', ''>'', resource_privacy, ''</CELL>'', ''<CELL'', ''>'', nb_concepts, ''</CELL>'', ''<CELL'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.reslang&amp;u='', resource_name as ZZZZ_001, ''">'', ''detail'', ''</a>'', ''</CELL>'', ''</ROW>''',
''''', ''</TABLE>'', ''<<??include a=MYTERM.foot&amp;u='', ''//??>>''',
'v_rescon',
'NODEF',
'resource_name'
);

delete from nodes where name='reslang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'reslang',
 1,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''Information on Languages'', ''</CAPTION>'', ''<ROW'', ''>'', ''<CELL_TITLE'', ''>'', ''ID'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Nb. Terms'', ''</CELL_TITLE>'', ''</ROW>''',
''''', ''<ROW'', ''>'', ''<CELL'', ''>'', id_language, ''</CELL>'', ''<CELL'', ''>'', nb_terms, ''</CELL>'', ''</ROW>''',
''''', ''</TABLE>''',
'v_reslang',
'resource_name=<<??param-0//??>>',
'id_language'
);
commit;

delete from nodes where name='readme';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'readme',
 0,
'''''',
''''', ''dummy''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='home';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'home',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''retour au menu principal de l''''utilisateur'', ''</COMMENT>'', ''<IMG'', ''>'', ''home'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''supprimer l''''élément'', ''</COMMENT>'', ''<IMG'', ''>'', ''del'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='maj';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''modifier le champ'', ''</COMMENT>'', ''<IMG'', ''>'', ''maj'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='new';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''ajouter un élément'', ''</COMMENT>'', ''<IMG'', ''>'', ''new'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del2';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del2',
 0,
'''''',
''''', ''<ICON_12'', ''>'', ''<COMMENT'', ''>'', ''supprimer l''''élément'', ''</COMMENT>'', ''<IMG'', ''>'', ''del12'', ''</IMG>'', ''</ICON_12>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='maj2';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj2',
 0,
'''''',
''''', ''<ICON_12'', ''>'', ''<COMMENT'', ''>'', ''modifier le champ'', ''</COMMENT>'', ''<IMG'', ''>'', ''maj12'', ''</IMG>'', ''</ICON_12>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='new2';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new2',
 0,
'''''',
''''', ''<ICON_12'', ''>'', ''<COMMENT'', ''>'', ''ajouter un élément'', ''</COMMENT>'', ''<IMG'', ''>'', ''new12'', ''</IMG>'', ''</ICON_12>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='print2';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'print2',
 0,
'''''',
''''', ''<ICON_12'', ''>'', ''<COMMENT'', ''>'', ''version imprimable'', ''</COMMENT>'', ''<IMG'', ''>'', ''print12'', ''</IMG>'', ''</ICON_12>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='refresh2';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'refresh2',
 0,
'''''',
''''', ''<ICON_12'', ''>'', ''<COMMENT'', ''>'', ''rafraîchir'', ''</COMMENT>'', ''<IMG'', ''>'', ''refresh3'', ''</IMG>'', ''</ICON_12>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='print';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'print',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''version imprimable'', ''</COMMENT>'', ''<IMG'', ''>'', ''print'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='pwd';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'pwd',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''changer le mot de passe'', ''</COMMENT>'', ''<IMG'', ''>'', ''pwd'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='definition';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'definition',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''voir les définitions de cet élément'', ''</COMMENT>'', ''<IMG'', ''>'', ''adm'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='histo';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'histo',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''voir l''''historique'', ''</COMMENT>'', ''<IMG'', ''>'', ''histo'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='personne';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'personne',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''ajouter une personne'', ''</COMMENT>'', ''<IMG'', ''>'', ''contact'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='valid';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'valid',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''OUI'', ''</COMMENT>'', ''<IMG'', ''>'', ''VALID'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='invalid';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'invalid',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''NON'', ''</COMMENT>'', ''<IMG'', ''>'', ''INVALID'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='info';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'info',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''Informations supplémentaires'', ''</COMMENT>'', ''<IMG'', ''>'', ''info'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='refresh';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'refresh',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''rafraîchir...'', ''</COMMENT>'', ''<IMG'', ''>'', ''refresh2'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='plus12';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'plus12',
 0,
'''''',
''''', ''<ICON_12'', ''>'', ''<COMMENT'', ''>'', ''ajouter un objet lié'', ''</COMMENT>'', ''<IMG'', ''>'', ''plus12'', ''</IMG>'', ''</ICON_12>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='plus';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'plus',
 0,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''Ajouter un élément'', ''</COMMENT>'', ''<IMG'', ''>'', ''plus'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='docClasse';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'docClasse',
 1,
'''''',
''''', ''<<??include a=ICON.docPublic&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=ICON.docInterne&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=ICON.docConfidentiel&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=ICON.docSecret&amp;u='', <<??param-0//??>>, ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='docPublic';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'docPublic',
 1,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''Document PUBLIC

L''''accès est libre à toute personne interne ou externe à l''''entreprise. La possibilité de classifier l''''information comme publique est limitée à des personnes prédéterminées (par ex. Département Communication). 

Exemple: Site www.entreprise.com'', ''</COMMENT>'', ''<IMG'', ''>'', ''doc-public'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'<<??param-0//??>>=''P''',
'NODEF'
);

delete from nodes where name='docInterne';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'docInterne',
 1,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''Document INTERNE

L''''accès est permis uniquement au personnel l''''entreprise. Par défaut toute information est classée comme interne.

Exemple: Journal CALVIN, communications internes'', ''</COMMENT>'', ''<IMG'', ''>'', ''doc-interne'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'<<??param-0//??>>=''I''',
'NODEF'
);

delete from nodes where name='docConfidentiel';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'docConfidentiel',
 1,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''Document CONFIDENTIEL

Les données sont réservées à des groupes d''''utilisateurs. Il peut s''''agir de fonctions d''''organisation (par ex. Département Informatique) ou des activités (par ex. les achats). Cette classification est utilisée pour diffuser d''''une façon contrôlée l''''information auprès des personnes qui doivent y accéder pour des raisons de travail.

Exemple: liste des fournisseurs réservée aux personnes qui font des achats, serveurs de fichiers départementaux, projets spéciaux.'', ''</COMMENT>'', ''<IMG'', ''>'', ''doc-confidentiel'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'<<??param-0//??>>=''C''',
'NODEF'
);

delete from nodes where name='docSecret';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'docSecret',
 1,
'''''',
''''', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''Document SECRET

L''''accès est restreint et nominatif. L''''information ne doit pas être diffusée par les personnes qui y ont accès.

Exemple: les mots de passe (le détenteur), le salaire (l''''employé, le responsable Ressources Humaines et le service de paiement des salaires). Les données sont transmises avec extrême attention (par ex. après chiffrement).'', ''</COMMENT>'', ''<IMG'', ''>'', ''doc-secret'', ''</IMG>'', ''</ICON>''',
'''''',
'dual',
'<<??param-0//??>>=''S''',
'NODEF'
);
commit;

delete from nodes where name='lazy_compileProject';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'lazy_compileProject',
 2,
'''''',
''''', ''<<??include a=NODE.project&amp;u='', <<??param-0//??>>, ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='lazydiag';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'lazydiag',
 0,
'''''',
''''', ''<HR'', ''>'', ''<APPLET'', '' code="'', ''Figure.class'', ''"'', '' width="'', ''550'', ''"'', '' height="'', ''350'', ''"'', ''>'', '''', ''</APPLET>'', ''</HR>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='getDynamicInfo';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'getDynamicInfo',
 3,
'''''',
''''', ''is executed by node server''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='all_projects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_projects',
 0,
'''''',
''''', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''menu principal'', ''</a>'', ''<hr/>'', ''<<??include a=SECURE.all_projects&amp;u='', ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='project';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'project',
 1,
''''', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''menu principal'', ''</a>'', '' | '', ''<a href="ns?a=NODE.all_projects&amp;u='', ''">'', ''&lt;&lt;&lt;'', ''</a>'', '' | '', ''<a href="ns?a=NODE.project_showtxt&amp;u='', <<??param-0//??>>, ''">'', ''show source'', ''</a>'', '' | '', ''<a target="_blank" href="ns?a=NODE.lazydiag&amp;u='', ''">'', ''syntax'', ''</a>'', '' | '', ''<a href="ns?a=NODE.statistic&amp;u='', <<??param-0//??>>, ''">'', ''statistic'', ''</a>'', ''<hr/>'', ''<TABLE_BG2'', ''>'', ''<CAPTION'', ''>'', ''Project: '', <<??param-0//??>>, ''<a href="ns?a=NODE.new_node&amp;u='', <<??param-0//??>>, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<CELL_BG2'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=NODE.del_node&amp;u='', projectid, ''&amp;u='', name, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<ICON'', ''>'', ''<COMMENT'', ''>'', ''compilation state'', ''</COMMENT>'', ''<IMG'', ''>'', status, ''</IMG>'', ''</ICON>'', ''<a href="ns?a=NODE.maj_node&amp;u='', projectid as ZZZZ_001, ''&amp;u='', name as ZZZZ_002, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', name as ZZZZ_003, ''</CELL_BG2>'', ''</ROW>''',
''''', ''</TABLE_BG2>''',
'nodes',
'projectid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='statistic';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'statistic',
 1,
'''''',
''''', ''<a href="ns?a=NODE.project&amp;u='', <<??param-0//??>>, ''">'', ''&lt;&lt;&lt;'', ''</a>'', '' | '', ''<hr/>'', ''<<??include a=NODE.statistic_global&amp;u='', ''//??>>'', ''<hr/>'', ''<<??include a=NODE.statistic_def&amp;u='', ''//??>>'', ''<hr/>'', ''<<??include a=NODE.statistic_project&amp;u='', <<??param-0//??>>, ''//??>>'', ''<hr/>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='statistic_header';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'statistic_header',
 1,
'''''',
''''', ''<CAPTION'', ''>'', <<??param-0//??>>, ''</CAPTION>'', ''<CELL_TITLE'', ''>'', ''Object'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Cache size'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Nb. Put'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''PutSize'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Nb. Get'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Nb. Found'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Query Time'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Save Time'', ''</CELL_TITLE>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='statistic_global';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'statistic_global',
 0,
''''', ''<TABLE_BG2'', ''>'', ''<<??include a=NODE.statistic_header&amp;u='', ''Global Statistic'', ''//??>>''',
''''', ''<ROW'', ''>'', ''<CELL'', ''>'', ''Total'', ''</CELL>'', ''<<??include a=NODE.getDynamicInfo&amp;u='', ''dummy'', ''&amp;u='', ''dummy'', ''&amp;u='', ''GLOBAL'', ''//??>>'', ''</ROW>''',
''''', ''</TABLE_BG2>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='statistic_def';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'statistic_def',
 0,
''''', ''<TABLE_BG2'', ''>'', ''<<??include a=NODE.statistic_header&amp;u='', ''Definition Statistic'', ''//??>>''',
''''', ''<ROW'', ''>'', ''<CELL'', ''>'', ''Definition Cache'', ''</CELL>'', ''<<??include a=NODE.getDynamicInfo&amp;u='', ''dummy'', ''&amp;u='', ''dummy'', ''&amp;u='', ''DEF'', ''//??>>'', ''</ROW>''',
''''', ''</TABLE_BG2>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='statistic_project';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'statistic_project',
 1,
''''', ''<TABLE_BG2'', ''>'', ''<<??include a=NODE.statistic_header&amp;u='', concat(''project Statistic: '',<<??param-0//??>>), ''//??>>''',
''''', ''<ROW'', ''>'', ''<CELL'', ''>'', name, ''</CELL>'', ''<<??include a=NODE.getDynamicInfo&amp;u='', projectid, ''&amp;u='', name as ZZZZ_004, ''&amp;u='', ''PROJECT'', ''//??>>'', ''</ROW>''',
''''', ''</TABLE_BG2>''',
'nodes',
'projectid=<<??param-0//??>>',
'name'
);

delete from nodes where name='new_node';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_node',
 1,
''''', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''menu principal'', ''</a>'', '' | '', ''<a href="ns?a=NODE.project&amp;u='', <<??param-0//??>>, ''">'', ''&lt;&lt;&lt;'', ''</a>'', '' | '', ''<hr/>'', ''<TABLE_BG2'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="NODE.lazy_compileProject"/>'', ''<input type="hidden" name="u" value="'', <<??param-0//??>>, ''"/>'', ''<input type="hidden" name="u" value="'', ''[[!name]]'', ''"/>'', ''<input type="hidden" name="u_enc" value="'', ''CDATA'', ''"/>'', ''<input type="hidden" name="hn" value="<<$$status$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', ''INVALID'', ''$$>>"/>'', ''<input type="hidden" name="hn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_BG2'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm compile &amp; insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$nodes$$>>"/>'', ''<b'', ''>'', <<??param-0//??>>, ''</b>'', ''</CELL_BG2>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_BG2'', ''>'', ''<input type="hidden" name="an" value="<<$$name$$>>"/>'', ''<input'', '' name="'', ''av'', ''"'', '' size="'', ''50'', ''"'', ''>'', ''node_name'', ''</input>'', ''</CELL_BG2>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_BG2'', ''>'', ''<input type="hidden" name="an" value="<<$$plaintxt$$>>"/>'', ''<textarea'', '' name="'', ''av'', ''"'', '' rows="'', ''25'', ''"'', '' cols="'', ''100'', ''"'', ''>'', ''node
 {}'', ''</textarea>'', ''</CELL_BG2>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE_BG2>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='maj_node';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_node',
 2,
''''', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''menu principal'', ''</a>'', '' | '', ''<a href="ns?a=NODE.project&amp;u='', <<??param-0//??>>, ''">'', ''&lt;&lt;&lt;'', ''</a>'', '' | '', ''<hr/>'', ''<TABLE_BG2'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="NODE.lazy_compileProject"/>'', ''<input type="hidden" name="u" value="'', <<??param-0//??>>, ''"/>'', ''<input type="hidden" name="u" value="'', <<??param-1//??>>, ''"/>'', ''<input type="hidden" name="u_enc" value="'', ''CDATA'', ''"/>'', ''<input type="hidden" name="hn" value="<<$$status$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', ''INVALID'', ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_BG2'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm compile &amp; update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$nodes$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', projectid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$name$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', name, ''$$>>"/>'', ''<b'', ''>'', name as ZZZZ_005, ''</b>'', ''</CELL_BG2>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_BG2'', ''>'', ''<input type="hidden" name="an" value="<<$$plaintxt$$>>"/>'', ''<textarea'', '' name="'', ''av'', ''"'', '' rows="'', ''25'', ''"'', '' cols="'', ''100'', ''"'', ''>'', ''<!['', ''CDATA['', plaintxt, '']'', '']>'', ''</textarea>'', ''</CELL_BG2>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<b'', '' textcolor="'', ''red'', ''"'', ''>'', error, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE_BG2>''',
'nodes',
'projectid=<<??param-0//??>> and name=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='del_node';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_node',
 2,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="NODE.project"/>'', ''<input type="hidden" name="u" value="'', <<??param-0//??>>, ''"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$nodes$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', projectid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$name$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', name, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', name as ZZZZ_006, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'nodes',
'projectid=<<??param-0//??>> and name=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='project_showtxt';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'project_showtxt',
 1,
''''', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''menu principal'', ''</a>'', '' | '', ''<a href="ns?a=NODE.project&amp;u='', <<??param-0//??>>, ''">'', ''&lt;&lt;&lt;'', ''</a>'', '' | '', ''<hr/>''',
''''', ''<pre'', ''>'', ''<<??include a=NODE.project_gettxt&amp;u='', <<??param-0//??>>, ''//??>>'', ''</pre>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='project_gettxt';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'project_gettxt',
 1,
''''', ''define project '', <<??param-0//??>>, ''
''',
''''', ''<!['', ''CDATA['', plaintxt, '']'', '']>''',
''''', ''
end''',
'nodes',
'projectid=<<??param-0//??>>',
'name'
);

delete from nodes where name='node_gettxt';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'node_gettxt',
 2,
'''''',
''''', ''<pre'', ''>'', ''<!['', ''CDATA['', plaintxt, '']'', '']>'', ''</pre>''',
'''''',
'nodes',
'projectid=<<??param-0//??>> and name=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='test';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'test',
 0,
'''''',
''''', ''<P_small'', ''>'', name, ''</P_small>''',
'''''',
'nodes',
'NODEF',
'1'
);
commit;

delete from nodes where name='top';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'top',
 0,
'''''',
''''', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''Menu principal'', ''</a>'', ''<hr/>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='all';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all',
 0,
''''', ''<<??include a=SECURE.top&amp;u='', ''//??>>'', ''<TITLE_PAGE'', ''>'', ''SECURE LOGIN APPLICATION: [[USER]] [[GRP]] [[STYLE]] '', ''</TITLE_PAGE>''',
''''', ''<<??include a=SECURE.all_users&amp;u='', ''//??>>'', ''<hr/>'', ''<<??include a=SECURE.all_roles&amp;u='', ''//??>>'', ''<hr/>'', ''<<??include a=SECURE.all_data&amp;u='', ''//??>>'', ''<hr/>'', ''<<??include a=SECURE.all_categories&amp;u='', ''//??>>'', ''<hr/>'', ''<<??include a=SECURE.all_projects&amp;u='', ''//??>>'', ''<hr/>'', ''<<??include a=SECURE.all_connects&amp;u='', ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='all_users';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_users',
 0,
''''', ''<<??include a=SECURE.top&amp;u='', ''//??>>'', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_users&amp;u='', ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b>[[?TITLE_USER]]</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.users&amp;u='', UserId, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>'', ''<<??include a=SECURE.all_managers&amp;u='', ''//??>>''',
'secure_users',
'NODEF',
'UserId'
);

delete from nodes where name='users';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'users',
 1,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_users&amp;u='', Userid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_users&amp;u='', Userid as ZZZZ_001, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', Userid as ZZZZ_002, ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.all_grants&amp;u='', Userid as ZZZZ_003, ''">'', ''<<??include a=ICON.definition&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_grants&amp;u='', Userid as ZZZZ_004, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''</CELL_BG1>'', ''<CELL'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_pwd_users&amp;u='', Userid as ZZZZ_005, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', ''password'', ''</CELL>'', ''<CELL'', ''>'', admin, ''</CELL>'', ''<CELL'', ''>'', defaultgrpid, ''</CELL>'', ''<CELL'', ''>'', lang, ''</CELL>'', ''<CELL'', ''>'', style, ''</CELL>'', ''<CELL'', ''>'', infouser, ''</CELL>'', ''<CELL'', ''>'', com, ''</CELL>'', ''<CELL'', ''>'', ''<<??include a=SECURE.getManager&amp;u='', managerid, ''//??>>'', ''</CELL>''',
'''''',
'secure_users',
'Userid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='new_users';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_users',
 0,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''User_id      : '', ''<input type="hidden" name="an" value="<<$$userid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''password     : '', ''<input type="hidden" name="an" value="<<$$encoded|pwd$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''admin        : '', ''<input type="hidden" name="an" value="<<$$admin$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''ADMI'', ''&amp;u='', ''NO'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Defaut_Grp   : '', ''<input type="hidden" name="an" value="<<$$defaultgrpid$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_grpid&amp;u='', ''PUBLIC'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Responsable  : '', ''<input type="hidden" name="an" value="<<$$MANAGERID$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_manager&amp;u='', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Lang         : '', ''<input type="hidden" name="an" value="<<$$lang$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''LANG'', ''&amp;u='', ''FR'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Style        : '', ''<input type="hidden" name="an" value="<<$$style$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''STYLE'', ''&amp;u='', ''TABLE'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''User_info    : '', ''<input type="hidden" name="an" value="<<$$infouser$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment      : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_users$$>>"/>'', ''<b'', ''>'', ''Création d''''un utilisateur'', ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='maj_users';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_users',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''admin        : '', ''<input type="hidden" name="an" value="<<$$admin$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''ADMI'', ''&amp;u='', admin, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Defaut_Grp   : '', ''<input type="hidden" name="an" value="<<$$defaultgrpid$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_grpid&amp;u='', defaultgrpid, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Responsable  : '', ''<input type="hidden" name="an" value="<<$$MANAGERID$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_manager&amp;u='', MANAGERID, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Lang         : '', ''<input type="hidden" name="an" value="<<$$lang$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''LANG'', ''&amp;u='', lang, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Style        : '', ''<input type="hidden" name="an" value="<<$$style$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''STYLE'', ''&amp;u='', style, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''User_info    : '', ''<input type="hidden" name="an" value="<<$$infouser$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', infouser, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment      : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', com, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_users$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$userid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', userid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_users',
'Userid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='del_users';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_users',
 1,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_users$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$userid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', userid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', <<??param-0//??>>, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_users',
'userid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='maj_pwd_users';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_pwd_users',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.menu"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''password     : '', ''<input type="hidden" name="an" value="<<$$encoded|pwd$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', ''*****'', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_pwdmodif$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$userid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', userid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_pwdmodif',
'Userid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='all_managers';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_managers',
 0,
''''', ''<<??include a=SECURE.top&amp;u='', ''//??>>'', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_managers&amp;u='', ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b>[[?TITLE_MANAGER]]</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.managers&amp;u='', MANAGERID, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_manager',
'NODEF',
'MANAGERID'
);

delete from nodes where name='managers';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'managers',
 1,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_managers&amp;u='', MANAGERID, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_managers&amp;u='', MANAGERID as ZZZZ_006, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', MANAGERID as ZZZZ_007, ''</CELL_BG1>'', ''<CELL'', ''>'', manager, ''</CELL>'', ''<CELL'', ''>'', com, ''</CELL>''',
'''''',
'secure_manager',
'MANAGERID=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='new_managers';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_managers',
 0,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Departemnt_ID: '', ''<input type="hidden" name="an" value="<<$$managerid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Responsable(s): '', ''<input type="hidden" name="an" value="<<$$manager$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment      : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_manager$$>>"/>'', ''<b'', ''>'', ''Création d''''un reponsable'', ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='maj_managers';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_managers',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Departemnt_ID: '', ''<input type="hidden" name="an" value="<<$$managerid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', managerid, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Responsable(s): '', ''<input type="hidden" name="an" value="<<$$manager$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', manager, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment      : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', com, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_manager$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$managerid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', managerid as ZZZZ_008, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_manager',
'MANAGERID=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='del_managers';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_managers',
 1,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_manager$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$userid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', userid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', <<??param-0//??>>, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_manager',
'MANAGERID=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='all_data';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_data',
 0,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_data&amp;u='', ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b>[[?TITLE_DATA]]</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.data&amp;u='', grpId, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_data',
'NODEF',
'grpid'
);

delete from nodes where name='data';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'data',
 1,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_data&amp;u='', grpid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_data&amp;u='', grpid as ZZZZ_009, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', grpid as ZZZZ_0010, ''</CELL_BG1>'', ''<CELL'', ''>'', com, ''</CELL>''',
'''''',
'secure_data',
'grpid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='new_data';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_data',
 0,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Grp_id       : '', ''<input type="hidden" name="an" value="<<$$grpid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment      : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_data$$>>"/>'', ''<b'', ''>'', ''Création d''''un groupe de données'', ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_data';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_data',
 1,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_data$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$grpid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', grpid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', <<??param-0//??>>, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_data',
'grpid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='maj_data';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_data',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment     : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', com, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_data$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$grpid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', grpid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_data',
'grpid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='all_roles';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_roles',
 0,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_roles&amp;u='', ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b>[[?TITLE_ROLE]]</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.roles&amp;u='', roleid, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_roles',
'NODEF',
'roleid'
);

delete from nodes where name='roles';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'roles',
 1,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_roles&amp;u='', roleid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_roles&amp;u='', roleid as ZZZZ_0011, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', roleid as ZZZZ_0012, ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.all_rolenode&amp;u='', roleid as ZZZZ_0013, ''">'', ''<<??include a=ICON.definition&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_rolenode&amp;u='', roleid as ZZZZ_0014, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''</CELL_BG1>'', ''<CELL'', ''>'', com, ''</CELL>''',
'''''',
'secure_roles',
'roleid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='new_roles';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_roles',
 0,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Role_id      : '', ''<input type="hidden" name="an" value="<<$$roleid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment      : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_roles$$>>"/>'', ''<b'', ''>'', ''Création d''''un rôle'', ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_roles';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_roles',
 1,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_roles$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$roleid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', roleid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', <<??param-0//??>>, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_roles',
'roleid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='maj_roles';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_roles',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment     : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', com, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_roles$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$roleid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', roleid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_roles',
'roleid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='all_rolenode';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_rolenode',
 1,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_rolenode&amp;u='', roleid, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b'', ''>'', ''[[?TITLE_NODE]]: '', <<??param-0//??>>, ''</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.rolenode&amp;u='', roleid, ''&amp;u='', nodeid, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_rolenode',
'roleid=<<??param-0//??>>',
'nodeid'
);

delete from nodes where name='rolenode';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'rolenode',
 2,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_rolenode&amp;u='', roleid, ''&amp;u='', nodeid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_rolenode&amp;u='', roleid as ZZZZ_0015, ''&amp;u='', nodeid as ZZZZ_0016, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', nodeid as ZZZZ_0017, ''</CELL_BG1>'', ''<CELL'', ''>'', typeid, ''</CELL>'', ''<CELL'', ''>'', display, ''</CELL>'', ''<CELL'', ''>'', seq, ''</CELL>'', ''<CELL'', ''>'', lib, ''</CELL>''',
'''''',
'secure_rolenode',
'roleid=<<??param-0//??>> and nodeid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='new_rolenode';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_rolenode',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$roleid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Node_id      : '', ''<input type="hidden" name="an" value="<<$$nodeid$$>>"/>'', ''<input type="text" name="av" size="'', 40, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Type         : '', ''<input type="hidden" name="an" value="<<$$typeid$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''NODETYPE'', ''&amp;u='', ''LAZY'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Display      : '', ''<input type="hidden" name="an" value="<<$$display$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''DISPLAYTYPE'', ''&amp;u='', ''ON MENU'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Seq          : '', ''<input type="hidden" name="an" value="<<$$seq$$>>"/>'', ''<input type="text" name="av" size="'', 10, ''" value="'', ''999'', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''libellé      : '', ''<input type="hidden" name="an" value="<<$$lib$$>>"/>'', ''<input type="text" name="av" size="'', 40, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_rolenode$$>>"/>'', ''<b'', ''>'', ''Création d''''un noeud pour le rôle '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_rolenode';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_rolenode',
 2,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_rolenode$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$roleid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', roleid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$nodeid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', nodeid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', <<??param-1//??>>, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_rolenode',
'roleid=<<??param-0//??>> and nodeid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='maj_rolenode';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_rolenode',
 2,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$roleid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<input type="hidden" name="hn" value="<<$$nodeid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-1//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Type         : '', ''<input type="hidden" name="an" value="<<$$typeid$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''NODETYPE'', ''&amp;u='', typeid, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Display      : '', ''<input type="hidden" name="an" value="<<$$display$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_codeid&amp;u='', ''DISPLAYTYPE'', ''&amp;u='', display, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Seq          : '', ''<input type="hidden" name="an" value="<<$$seq$$>>"/>'', ''<input type="text" name="av" size="'', 10, ''" value="'', seq, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''libellé      : '', ''<input type="hidden" name="an" value="<<$$lib$$>>"/>'', ''<input type="text" name="av" size="'', 40, ''" value="'', lib, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_rolenode$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$roleid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', roleid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$nodeid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', nodeid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-1//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_rolenode',
'roleid=<<??param-0//??>> and nodeid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='all_categories';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_categories',
 0,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_categories&amp;u='', ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b>[[?TITLE_CATEGORIE]]</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.categories&amp;u='', catid, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_categories',
'NODEF',
'catid'
);

delete from nodes where name='categories';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'categories',
 1,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_categories&amp;u='', catid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_categories&amp;u='', catid as ZZZZ_0018, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', catid as ZZZZ_0019, ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.all_codes&amp;u='', catid as ZZZZ_0020, ''">'', ''<<??include a=ICON.definition&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_codes&amp;u='', catid as ZZZZ_0021, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''</CELL_BG1>'', ''<CELL'', ''>'', com, ''</CELL>''',
'''''',
'secure_categories',
'catid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='new_categories';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_categories',
 0,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Categ_id     : '', ''<input type="hidden" name="an" value="<<$$catid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment      : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_categories$$>>"/>'', ''<b'', ''>'', ''Création d''''une catégorie'', ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_categories';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_categories',
 1,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_categories$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$catid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', catid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', <<??param-0//??>>, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_categories',
'catid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='maj_categories';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_categories',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment     : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', com, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_categories$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$catid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', catid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_categories',
'catid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='all_codes';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_codes',
 1,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_codes&amp;u='', catid, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b'', ''>'', ''[[?TITLE_CODE]]: '', <<??param-0//??>>, ''</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.codes&amp;u='', catid, ''&amp;u='', codeid, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_codes',
'catid=<<??param-0//??>>',
'codeid'
);

delete from nodes where name='codes';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'codes',
 2,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_codes&amp;u='', catid, ''&amp;u='', codeid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_codes&amp;u='', catid as ZZZZ_0022, ''&amp;u='', codeid as ZZZZ_0023, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', codeid as ZZZZ_0024, ''</CELL_BG1>'', ''<CELL'', ''>'', abr, ''</CELL>'', ''<CELL'', ''>'', listdef, ''</CELL>''',
'''''',
'secure_codes',
'catid=<<??param-0//??>> and codeid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='new_codes';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_codes',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$catid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Code_id      : '', ''<input type="hidden" name="an" value="<<$$codeid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Abr.         : '', ''<input type="hidden" name="an" value="<<$$abr$$>>"/>'', ''<input type="text" name="av" size="'', 40, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''List Def.    : '', ''<input type="hidden" name="an" value="<<$$listdef$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_admin_YN&amp;u='', ''N'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_codes$$>>"/>'', ''<b'', ''>'', ''Création d''''un code pour la catégorie '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_codes';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_codes',
 2,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_codes$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$catid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', catid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$codeid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', codeid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', codeid as ZZZZ_0025, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_codes',
'catid=<<??param-0//??>> and codeid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='maj_codes';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_codes',
 2,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$catid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<input type="hidden" name="hn" value="<<$$codeid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-1//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Abr.         : '', ''<input type="hidden" name="an" value="<<$$abr$$>>"/>'', ''<input type="text" name="av" size="'', 40, ''" value="'', abr, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''List Def.    : '', ''<input type="hidden" name="an" value="<<$$listdef$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_admin_YN&amp;u='', listdef, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_codes$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$catid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', catid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$codeid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', codeid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-1//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_codes',
'catid=<<??param-0//??>> and codeid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='all_grants';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_grants',
 1,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_grants&amp;u='', userid, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b'', ''>'', ''[[?TITLE_GRANT]]: '', <<??param-0//??>>, ''</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.grants&amp;u='', userid, ''&amp;u='', grpid, ''&amp;u='', roleid, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_grants',
'userid=<<??param-0//??>>',
'grpid,roleid'
);

delete from nodes where name='grants';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'grants',
 3,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_grants&amp;u='', userid, ''&amp;u='', grpid, ''&amp;u='', roleid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', grpid as ZZZZ_0026, ''</CELL_BG1>'', ''<CELL'', ''>'', roleid as ZZZZ_0027, ''</CELL>''',
'''''',
'secure_grants',
'userid=<<??param-0//??>> and roleid=<<??param-2//??>> and grpid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='new_grants';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_grants',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$userid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''groupe data   : '', ''<input type="hidden" name="an" value="<<$$grpid$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_grpid&amp;u='', ''PUBLIC'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''role          : '', ''<input type="hidden" name="an" value="<<$$roleid$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_roleid&amp;u='', ''ANONYM'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_grants$$>>"/>'', ''<b'', ''>'', ''Création d''''une autorisation pour l''''utilisateur '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_grants';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_grants',
 3,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_grants$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$userid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', userid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$grpid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', grpid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$roleid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', roleid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', grpid as ZZZZ_0028, ''-'', roleid as ZZZZ_0029, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_grants',
'userid=<<??param-0//??>> and roleid=<<??param-2//??>> and grpid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='all_projects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_projects',
 0,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_projects&amp;u='', ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b>[[?TITLE_PROJECT]]</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.projects&amp;u='', projectid, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_projects',
'NODEF',
'projectid'
);

delete from nodes where name='projects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'projects',
 1,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_projects&amp;u='', projectid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?a=NODE.project&amp;u='', projectid as ZZZZ_0030, ''">'', ''nodes def.'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_projects&amp;u='', projectid as ZZZZ_0031, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', projectid as ZZZZ_0032, '': '', com, ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.all_txt&amp;u='', projectid as ZZZZ_0033, ''">'', ''<<??include a=ICON.definition&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_txt&amp;u='', projectid as ZZZZ_0034, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''</CELL_BG1>''',
'''''',
'secure_projects',
'projectid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='new_projects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_projects',
 0,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all_projects"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Project_id   : '', ''<input type="hidden" name="an" value="<<$$projectid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment      : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_projects$$>>"/>'', ''<b'', ''>'', ''Création d''''un projet'', ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_projects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_projects',
 1,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all_projects"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_projects$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', projectid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', <<??param-0//??>>, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_projects',
'projectid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='maj_projects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_projects',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all_projects"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Comment     : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', com, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_projects$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', projectid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_projects',
'projectid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='all_txt';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_txt',
 1,
''''', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_txt&amp;u='', projectid, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b'', ''>'', ''[[?TITLE_TXT]]: '', <<??param-0//??>>, ''</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.txt&amp;u='', projectid, ''&amp;u='', txtid, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_txt',
'projectid=<<??param-0//??>>',
'txtid'
);

delete from nodes where name='txt';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'txt',
 2,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_txt&amp;u='', projectid, ''&amp;u='', txtid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_txt&amp;u='', projectid as ZZZZ_0035, ''&amp;u='', txtid as ZZZZ_0036, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', txtid as ZZZZ_0037, '': '', lib, ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_txtlang&amp;u='', projectid as ZZZZ_0038, ''&amp;u='', txtid as ZZZZ_0039, ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<<??include a=SECURE.all_txtlang&amp;u='', projectid as ZZZZ_0040, ''&amp;u='', txtid as ZZZZ_0041, ''//??>>'', ''</CELL_BG1>''',
'''''',
'secure_txt',
'projectid=<<??param-0//??>> and txtid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='new_txt';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_txt',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''txt_id       : '', ''<input type="hidden" name="an" value="<<$$txtid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''lib          : '', ''<input type="hidden" name="an" value="<<$$lib$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_txt$$>>"/>'', ''<b'', ''>'', ''Création d''''un texte pour le projet '', <<??param-0//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_txt';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_txt',
 2,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_txt$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', projectid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$txtid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', txtid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', txtid as ZZZZ_0042, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_txt',
'projectid=<<??param-0//??>> and txtid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='maj_txt';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_txt',
 2,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<input type="hidden" name="hn" value="<<$$txtid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-1//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''lib          : '', ''<input type="hidden" name="an" value="<<$$lib$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', lib, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_txt$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', projectid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$txtid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', txtid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', <<??param-1//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_txt',
'projectid=<<??param-0//??>> and txtid=<<??param-1//??>>',
'NODEF'
);

delete from nodes where name='all_txtlang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_txtlang',
 2,
''''', ''<TABLE'', ''>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.txtlang&amp;u='', projectid, ''&amp;u='', txtid, ''&amp;u='', lang, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_txtlang',
'projectid=<<??param-0//??>> and txtid=<<??param-1//??>>',
'lang'
);

delete from nodes where name='txtlang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'txtlang',
 3,
''''', ''<ROW'', ''>''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_txtlang&amp;u='', projectid, ''&amp;u='', txtid, ''&amp;u='', lang, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_txtlang&amp;u='', projectid as ZZZZ_0043, ''&amp;u='', txtid as ZZZZ_0044, ''&amp;u='', lang as ZZZZ_0045, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', lang as ZZZZ_0046, ''</CELL_BG1>'', ''<CELL'', ''>'', lib, ''</CELL>''',
''''', ''</ROW>''',
'secure_txtlang',
'projectid=<<??param-0//??>> and txtid=<<??param-1//??>> and lang=<<??param-2//??>>',
'NODEF'
);

delete from nodes where name='new_txtlang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_txtlang',
 2,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<input type="hidden" name="hn" value="<<$$txtid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-1//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''lang         : '', ''<input type="hidden" name="an" value="<<$$lang$$>>"/>'', ''<select name="av" >'', ''<<??include a=SECURE.list_superlang&amp;u='', ''ENG'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''lib          : '', ''<input type="hidden" name="an" value="<<$$lib$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_txtlang$$>>"/>'', ''<b'', ''>'', ''Création d''''une langue pour le texte '', <<??param-1//??>>, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='del_txtlang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_txtlang',
 3,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_txtlang$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', projectid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$txtid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', txtid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$lang$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', lang, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', lang as ZZZZ_0047, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_txtlang',
'projectid=<<??param-0//??>> and txtid=<<??param-1//??>> and lang=<<??param-2//??>>',
'NODEF'
);

delete from nodes where name='maj_txtlang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_txtlang',
 3,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="hidden" name="hn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-0//??>>, ''$$>>"/>'', ''<input type="hidden" name="hn" value="<<$$txtid$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-1//??>>, ''$$>>"/>'', ''<input type="hidden" name="hn" value="<<$$lang$$>>"/>'', ''<input type="hidden" name="hv" value="<<$$'', <<??param-2//??>>, ''$$>>"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''lib          : '', ''<input type="hidden" name="an" value="<<$$lib$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 40, ''">'', lib, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_txtlang$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$projectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', projectid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$txtid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', txtid, ''$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$lang$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', lang, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', lang as ZZZZ_0048, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_txtlang',
'projectid=<<??param-0//??>> and txtid=<<??param-1//??>> and lang=<<??param-2//??>>',
'NODEF'
);

delete from nodes where name='list_admin_YN';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_admin_YN',
 1,
'''''',
''''', ''<option'', ''>'', casewhen(''Y''=<<??param-0//??>>,''<selected_option/>'',''''), ''Y'', ''</option>'', ''<option'', ''>'', casewhen(''N''=<<??param-0//??>>,''<selected_option/>'',''''), ''N'', ''</option>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='list_grpid';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_grpid',
 1,
''''', ''<<??include a=SECURE.list_grpid_selected&amp;u='', <<??param-0//??>>, ''//??>>''',
''''', ''<option'', ''>'', grpid, ''</option>''',
'''''',
'secure_data',
'grpid<><<??param-0//??>>',
'grpid'
);

delete from nodes where name='list_manager';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_manager',
 1,
'''''',
''''', ''<option'', ''>'', casewhen(MANAGERID=<<??param-0//??>>,''<selected_option/>'',''''), ''<value'', ''>'', MANAGERID, ''</value>'', MANAGER, ''</option>''',
'''''',
'secure_manager',
'NODEF',
'MANAGER'
);

delete from nodes where name='list_grpid_selected';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_grpid_selected',
 1,
'''''',
''''', ''<option'', ''>'', ''<selected_option/>'', grpid, ''</option>''',
'''''',
'secure_data',
'grpid=<<??param-0//??>>',
'grpid'
);

delete from nodes where name='list_roleid';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_roleid',
 1,
'''''',
''''', ''<option'', ''>'', casewhen(roleid=<<??param-0//??>>,''<selected_option/>'',''''), roleid, ''</option>''',
'''''',
'secure_roles',
'NODEF',
'roleid'
);

delete from nodes where name='list_codeid';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_codeid',
 2,
'''''',
''''', ''<option'', ''>'', if(codeid=<<??param-1//??>>,''<selected_option/>'',''''), ''<value'', ''>'', codeid, ''</value>'', abr, ''</option>''',
'''''',
'secure_codes',
'catid=<<??param-0//??>>',
'codeid'
);

delete from nodes where name='list_superlang';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_superlang',
 1,
''''', ''<<??include a=SECURE.list_codeid&amp;u='', ''LANG'', ''&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=SECURE.list_codeid&amp;u='', ''STYLE'', ''&amp;u='', <<??param-0//??>>, ''//??>>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='getManager';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'getManager',
 1,
'''''',
''''', manager',
'''''',
'secure_manager',
'MANAGERID=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='all_connects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_connects',
 0,
''''', ''<<??include a=SECURE.top&amp;u='', ''//??>>'', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.new_connects&amp;u='', ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''<b>[[?TITLE_CONNECT]]</b>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=SECURE.connects&amp;u='', connectId, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'secure_connects',
'NODEF',
'connectId'
);

delete from nodes where name='connects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'connects',
 1,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.del_connects&amp;u='', connectid, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_connects&amp;u='', connectid as ZZZZ_0049, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', ''<<??include a=SECURE.testDB&amp;u='', connectid as ZZZZ_0050, ''//??>>'', connectid as ZZZZ_0051, ''</CELL_BG1>'', ''<CELL'', ''>'', driver, ''</CELL>'', ''<CELL'', ''>'', url, ''</CELL>'', ''<CELL'', ''>'', userdb, ''</CELL>'', ''<CELL'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_pwd_connects&amp;u='', connectid as ZZZZ_0052, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', ''pwd'', ''</CELL>'', ''<CELL'', ''>'', com, ''</CELL>''',
'''''',
'secure_connects',
'connectid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='new_connects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_connects',
 0,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''connect_id  : '', ''<input type="hidden" name="an" value="<<$$connectid$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''driver      : '', ''<input type="hidden" name="an" value="<<$$driver$$>>"/>'', ''<input type="text" name="av" size="'', 80, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''url         : '', ''<input type="hidden" name="an" value="<<$$url$$>>"/>'', ''<input type="text" name="av" size="'', 80, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''userdb      : '', ''<input type="hidden" name="an" value="<<$$userdb$$>>"/>'', ''<input type="text" name="av" size="'', 80, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''pwddb         : '', ''<input type="hidden" name="an" value="<<$$encoded|pwddb$$>>"/>'', ''<input type="text" name="av" size="'', 80, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''com         : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 80, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_connects$$>>"/>'', ''<b'', ''>'', ''Création d''''une connection'', ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='maj_connects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_connects',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''driver      : '', ''<input type="hidden" name="an" value="<<$$driver$$>>"/>'', ''<input type="text" name="av" size="'', 80, ''" value="'', driver, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''url         : '', ''<input type="hidden" name="an" value="<<$$url$$>>"/>'', ''<input type="text" name="av" size="'', 80, ''" value="'', url, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''userdb      : '', ''<input type="hidden" name="an" value="<<$$userdb$$>>"/>'', ''<input type="text" name="av" size="'', 80, ''" value="'', userdb, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''com         : '', ''<input type="hidden" name="an" value="<<$$com$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 80, ''">'', com, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_connects$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$connectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', connectid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', connectid as ZZZZ_0053, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_connects',
'connectid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='del_connects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_connects',
 1,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_connects$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$connectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', connectid, ''$$>>"/>'', ''<b'', ''>'', ''suppression de: '', connectid as ZZZZ_0054, ''</b>'', ''</form>''',
''''', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>''',
'secure_connects',
'connectid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='maj_pwd_connects';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_pwd_connects',
 1,
''''', ''<TABLE'', ''>''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.all"/>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''password     : '', ''<input type="hidden" name="an" value="<<$$encoded|pwddb$$>>"/>'', ''<input type="text" name="av" size="'', 12, ''" value="'', ''*****'', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$secure_connects$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$connectid$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', connectid, ''$$>>"/>'', ''<b'', ''>'', ''modification de: '', connectid as ZZZZ_0055, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</form>''',
''''', ''</TABLE>''',
'secure_connects',
'connectid=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='menu';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'menu',
 0,
''''', ''<LIST'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=SECURE.maj_pwd_users&amp;u='', ''[[USER]]'', ''">'', ''<<??include a=ICON.pwd&amp;u='', ''//??>>'', ''</a>'', ''Roles autorisés: [[USER]]-[[GRP]]'', ''<hr/>'', ''<<??include a=SECURE.gprid_askmodify&amp;u='', ''//??>>''',
''''', ''<ITEM'', ''>'', roleid, '' - '', com, ''<<??include a=SECURE.menuitem&amp;u='', userid, ''&amp;u='', grpid, ''&amp;u='', roleid as ZZZZ_0056, ''//??>>'', ''<<??include a=SECURE.menuitemparam&amp;u='', userid as ZZZZ_0057, ''&amp;u='', grpid as ZZZZ_0058, ''&amp;u='', roleid as ZZZZ_0059, ''//??>>'', ''</ITEM>''',
''''', ''<<??include a=SECURE.call_lazy_admin_clearCache_window&amp;u='', ''[[GRP]]'', ''//??>>'', ''<<??include a=SECURE.genPwd&amp;u='', ''[[GRP]]'', ''//??>>'', ''<<??include a=SECURE.checkDB&amp;u='', ''[[GRP]]'', ''//??>>'', ''</LIST>''',
'secure_menu',
'userid=''[[USER]]'' and grpid=''[[GRP]]''',
'roleid'
);

delete from nodes where name='menuitem';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'menuitem',
 3,
''''', ''<LIST'', ''>''',
''''', ''<ITEM'', ''>'', ''<a href="ns?a='', nodeid, ''&amp;u=">'', lib, ''</a>'', ''</ITEM>''',
''''', ''</LIST>''',
'secure_menuitem',
'userid=<<??param-0//??>> and grpid=<<??param-1//??>> and roleid=<<??param-2//??>> and nodeid<>''SINISTRES.start_sinistres''',
'seq,nodeid'
);

delete from nodes where name='menuitemparam';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'menuitemparam',
 3,
''''', ''<LIST'', ''>''',
''''', ''<ITEM'', ''>'', ''<a href="ns?a='', nodeid, ''&amp;u='', to_char(sysdate,''yyyy''), ''">'', lib, ''</a>'', ''</ITEM>''',
''''', ''</LIST>''',
'secure_menuitem',
'userid=<<??param-0//??>> and grpid=<<??param-1//??>> and roleid=<<??param-2//??>> and nodeid=''SINISTRES.start_sinistres''',
'seq,nodeid'
);

delete from nodes where name='gprid_askmodify';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'gprid_askmodify',
 0,
'''''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.grpid_modify"/>'', ''<input type="submit" name="bidon" value="'', ''changer de groupe'', ''"/>'', '' grpid '', ''<select name="u" >'', ''<<??include a=SECURE.list_usergrpid&amp;u='', ''//??>>'', ''</select>'', ''<input type="hidden" name="u" value="'', ''NO_NODE'', ''"/>'', ''</form>''',
'''''',
'secure_usergrpid',
'userid=''[[USER]]'' and grpid<>''[[GRP]]''',
'grpid'
);

delete from nodes where name='grpid_modify';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'grpid_modify',
 2,
'''''',
''''', ''<<??include a=SECURE.grpid_modify_with_node&amp;u='', <<??param-0//??>>, ''&amp;u='', <<??param-1//??>>, ''//??>>'', ''<<??include a=SECURE.grpid_modify_no_node&amp;u='', <<??param-0//??>>, ''&amp;u='', <<??param-1//??>>, ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='grpid_modify_with_node';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'grpid_modify_with_node',
 2,
'''''',
''''', ''<<??include a=SECURE.buildNode&amp;u='', ''SECURE'', ''&amp;u='', ''change_GRP_and_call_node'', ''&amp;u='', ''SECURE'', ''&amp;u='', ''_n1_[[USER]]'', ''&amp;u='', <<??param-1//??>>, ''//??>>'', ''<<??include a=SECURE.dynamicNode&amp;u='', ''SECURE'', ''&amp;u='', ''_n1_[[USER]]'', ''&amp;u='', <<??param-1//??>>, ''//??>>''',
'''''',
'dual',
'<<??param-1//??>><>''NO_NODE''',
'NODEF'
);

delete from nodes where name='change_GRP_and_call_node';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'change_GRP_and_call_node',
 3,
'''''',
''''', ''node '', <<??param-1//??>>, ''[selectNode]'', ''
{include '', <<??param-2//??>>, ''}''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='grpid_modify_no_node';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'grpid_modify_no_node',
 2,
'''''',
''''', ''<<??include a=SECURE.menu&amp;u='', ''//??>>''',
'''''',
'dual',
'<<??param-1//??>>=''NO_NODE''',
'NODEF'
);

delete from nodes where name='lazy_admin_clearCache';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'lazy_admin_clearCache',
 0,
'''''',
''''', ''<<??include a=SECURE.lazy_admin_clearAllNodes&amp;u='', ''//??>>'', ''clear cache at :'', SYSDATE, ''<<??include a=SECURE.menu&amp;u='', ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='call_lazy_admin_clearCache_window';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'call_lazy_admin_clearCache_window',
 1,
'''''',
''''', ''<ITEM'', ''>'', ''<NEW_WINDOW'', '' a_node="'', ''SECURE.lazy_admin_clearCache_window'', ''"'', '' a_title="'', ''clear_cache'', ''"'', '' a_param="'', ''width=200,height=30,resizable=no, top=200, left=200 scrollbars=no'', ''"'', '' a_value="'', ''Effacer les caches'', ''"'', ''/>'', ''</ITEM>''',
'''''',
'dual',
'<<??param-0//??>>=''SYSTEM''',
'NODEF'
);

delete from nodes where name='lazy_admin_clearCache_window';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'lazy_admin_clearCache_window',
 0,
'''''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.lazy_admin_clearCache_window"/>'', ''<input type="submit" name="bidon" value="'', ''Effacer les caches'', ''"/>'', ''<BR/>'', ''<<??include a=SECURE.lazy_admin_clearAllNodes&amp;u='', ''//??>>'', ''clear cache at :'', to_char(SYSDATE,''HH:MI:ss''), ''</form>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='lazy_admin_clearAllNodes';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'lazy_admin_clearAllNodes',
 0,
'''''',
''''', null',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='list_usergrpid';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_usergrpid',
 0,
''''', ''<<??include a=SECURE.list_usergrpid_selected&amp;u='', ''//??>>''',
''''', ''<option'', ''>'', grpid, ''</option>''',
'''''',
'secure_usergrpid',
'userid=''[[USER]]'' and grpid<>''[[GRP]]''',
'grpid'
);

delete from nodes where name='list_usergrpid_selected';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_usergrpid_selected',
 0,
'''''',
''''', ''<option'', ''>'', ''<selected_option/>'', grpid, ''</option>''',
'''''',
'secure_usergrpid',
'userid=''[[USER]]'' and grpid=''[[GRP]]''',
'grpid'
);

delete from nodes where name='genPwd';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'genPwd',
 1,
'''''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="SECURE.pwd_modify"/>'', ''<ITEM'', ''>'', ''<input type="submit" name="bidon" value="'', ''Crypter un mot de passe'', ''"/>'', '' : '', ''<input type="text" name="u" size="'', 20, ''"/>'', ''</ITEM>'', ''</form>''',
'''''',
'dual',
'<<??param-0//??>>=''SYSTEM''',
'NODEF'
);

delete from nodes where name='pwd_modify';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'pwd_modify',
 1,
'''''',
''''', ''<<??include a=SECURE.menu&amp;u='', ''//??>>'', ''<LIST'', ''>'', ''<ITEM'', ''>'', '''''''', <<??param-0//??>>, ''''''  = '', ''<<??include a=SECURE.encryptpwd&amp;u='', <<??param-0//??>>, ''//??>>'', ''</ITEM>'', ''</LIST>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='checkDB';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'checkDB',
 1,
'''''',
''''', ''Dictionnary Lazy is '', ''<<??include a=SECURE.testDB&amp;u='', ''DICTLAZY'', ''//??>>'', ''<P'', ''>'', ''<a href="ns?a=SECURE.restartDataBase&amp;u='', ''">'', ''redémarrer toutes les connections BD'', ''</a>'', ''</P>''',
'''''',
'dual',
'<<??param-0//??>>=''SYSTEM''',
'NODEF'
);

delete from nodes where name='restartDataBase';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'restartDataBase',
 1,
'''''',
''''', ''<<??include a=SECURE.restartDB&amp;u='', ''//??>>'', ''<<??include a=SECURE.all_connects&amp;u='', ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);
commit;

delete from nodes where name='header';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'header',
 0,
'''''',
''''', ''<<??include a=UTIL.header&amp;u='', ''S'', ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);
commit;

delete from nodes where name='baz_fonctions';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'baz_fonctions',
 0,
'''''',
''''', ''dummy''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='header55';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'header55',
 1,
'''''',
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL'', ''>'', ''[[USER]] - [[GRP]]'', ''</CELL>'', ''<CELL_R'', ''>'', ''<<??include a=UTIL.nowDateEtHeure&amp;u='', ''//??>>'', '' '', ''<<??include a=ICON.docClasse&amp;u='', <<??param-0//??>>, ''//??>>'', ''</CELL_R>'', ''</ROW>'', ''</TABLE>'', ''<hr/>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='header';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'header',
 1,
'''''',
''''', ''<<??include a=UTIL.headerALL&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=UTIL.headerGEN_FIN&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=UTIL.headerACT_CH&amp;u='', <<??param-0//??>>, ''//??>>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='headerALL';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'headerALL',
 1,
'''''',
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL'', ''>'', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''<<??include a=ICON.home&amp;u='', ''//??>>'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', ''[[USER]] - [[GRP]]'', ''</CELL>'', ''<CELL_R'', ''>'', ''<<??include a=UTIL.nowDateEtHeure&amp;u='', ''//??>>'', '' '', ''<<??include a=ICON.docClasse&amp;u='', <<??param-0//??>>, ''//??>>'', ''</CELL_R>'', ''</ROW>'', ''</TABLE>'', ''<hr/>''',
'''''',
'dual',
'''GEN_FIN''<>''[[GRP]]'' and ''ACT_CH''<>''[[GRP]]''',
'NODEF'
);

delete from nodes where name='headerGEN_FIN';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'headerGEN_FIN',
 1,
'''''',
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL'', ''>'', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''<<??include a=ICON.home&amp;u='', ''//??>>'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', ''<a href="ns?a=PFTUTIL.menu&amp;u='', ''">'', ''<ICON'', ''>'', ''up'', ''</ICON>'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', ''<a href="ns?a=CONTACT.start_contact&amp;u='', ''">'', ''<ICON'', ''>'', ''user'', ''</ICON>'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', ''<a href="http://mediawiki/mediawiki/index.php/Org_Patrimoine"><ICON><COMMENT>documentation et procédures</COMMENT><IMG>documentation</IMG></ICON></a>'', ''</CELL>'', ''<CELL'', ''>'', ''[[USER]] - [[GRP]]'', ''</CELL>'', ''<CELL_R'', ''>'', ''<<??include a=UTIL.nowDateEtHeure&amp;u='', ''//??>>'', '' '', ''<<??include a=ICON.docClasse&amp;u='', <<??param-0//??>>, ''//??>>'', ''</CELL_R>'', ''</ROW>'', ''</TABLE>''',
'''''',
'dual',
'''GEN_FIN''=''[[GRP]]''',
'NODEF'
);

delete from nodes where name='headerACT_CH';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'headerACT_CH',
 1,
'''''',
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL'', ''>'', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''<<??include a=ICON.home&amp;u='', ''//??>>'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', ''<a href="ns?a=PFTUTIL.menu&amp;u='', ''">'', ''<ICON'', ''>'', ''up'', ''</ICON>'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', ''<a href="ns?a=CONTACT.start_contact&amp;u='', ''">'', ''<ICON'', ''>'', ''user'', ''</ICON>'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', ''[[USER]] - [[GRP]]'', ''</CELL>'', ''<CELL_R'', ''>'', ''<<??include a=UTIL.nowDateEtHeure&amp;u='', ''//??>>'', '' '', ''<<??include a=ICON.docClasse&amp;u='', <<??param-0//??>>, ''//??>>'', ''</CELL_R>'', ''</ROW>'', ''</TABLE>''',
'''''',
'dual',
'''ACT_CH''=''[[GRP]]''',
'NODEF'
);

delete from nodes where name='headerWithOutHR';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'headerWithOutHR',
 1,
'''''',
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL'', ''>'', ''<a href="ns?a=SECURE.menu&amp;u='', ''">'', ''<<??include a=ICON.home&amp;u='', ''//??>>'', ''</a>'', ''</CELL>'', ''<CELL'', ''>'', ''[[USER]] - [[GRP]]'', ''</CELL>'', ''<CELL_R'', ''>'', ''<<??include a=UTIL.nowDateEtHeure&amp;u='', ''//??>>'', '' '', ''<<??include a=ICON.docClasse&amp;u='', <<??param-0//??>>, ''//??>>'', ''</CELL_R>'', ''</ROW>'', ''</TABLE>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='nowDateEtHeure';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'nowDateEtHeure',
 0,
'''''',
''''', to_char(sysdate,''dd.mm.yyyy hh24:mi:ss'')',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='nowDate';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'nowDate',
 0,
'''''',
''''', to_char(sysdate,''dd.mm.yyyy'')',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='upload';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'upload',
 1,
'''''',
''''', ''<a href="javascript:window.open(''''ns?a=UTIL.execUpload&amp;u='', <<??param-0//??>>, '''''',''''upload'''',''''width=450,height=200,resizable=no, top=50, left=200, scrollbars=no'''');window.back;">charger un fichier</a>''',
'''''',
'dual',
'NODEF',
'NODEF'
);
commit;
