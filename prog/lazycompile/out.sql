
delete from nodes where name='welcome';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'welcome',
 0,
''''', ''<<??include a=MYTERM.header&amp;u='', ''//??>>'', ''<hr/>'', ''<b'', ''>'', ''Queries<br/>'', ''</b>'', ''Example: search: progressive beam%, from english to french '', ''<a href="ns?a=MYTERM.querybysota&amp;u='', ''beam%'', ''&amp;u='', ''EN-GB'', ''&amp;u='', ''FR'', ''">'', ''Do a query at term level'', ''</a>'', ''<br/>'', ''Example: search:lhc%, from french to english '', ''<a href="ns?a=MYTERM.querybysota&amp;u='', ''lhc%'', ''&amp;u='', ''FR'', ''&amp;u='', ''EN-GB'', ''">'', ''Do a query at term level'', ''</a>'', ''<br/>'', ''Example:search: beam%, in english '', ''<a href="ns?a=MYTERM.queryconcept&amp;u='', ''beam%'', ''&amp;u='', ''EN-GB'', ''">'', ''Do a query at concept level'', ''</a>'', ''<br/>'', ''Example:search: lhc%, in french '', ''<a href="ns?a=MYTERM.queryconcept&amp;u='', ''lhc%'', ''&amp;u='', ''FR'', ''">'', ''Do a query at concept level'', ''</a>'', ''<br/>'', ''<b'', ''>'', ''Statistics<br/>'', ''</b>'', ''<a href="ns?a=MYTERM.resources_stat&amp;u='', ''">'', ''Statistics on ressources (wait ... needs to recompute all stats)<br/>'', ''</a>''',
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

delete from nodes where name='header';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'header',
 0,
''''', ''<h2'', ''>'', ''<<??include a=MYTERM.olanto&amp;u='', ''//??>>'', '' myTERM - Welcome to terminology browser - Olanto Foundation'', ''</h2>'', ''<h4'', ''>'', ''This interface is only to check the data upload.'', ''</h4>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='olanto';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'olanto',
 0,
'''''',
''''', ''<LOGO'', ''>'', ''<COMMENT'', ''>'', ''http://www.olanto.org'', ''</COMMENT>'', ''<IMG'', ''>'', ''olanto'', ''</IMG>'', ''</LOGO>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='resources_stat';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'resources_stat',
 0,
''''', ''<<??include a=MYTERM.top&amp;u='', ''//??>>'', ''statistics on resources <hr/>'', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''Statistics on Resources'', ''</CAPTION>'', ''<ROW'', ''>'', ''<CELL_TITLE'', ''>'', ''Resource Name'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Privacy'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Nb. Concepts'', ''</CELL_TITLE>'', ''<CELL_TITLE'', ''>'', ''Languages'', ''</CELL_TITLE>'', ''</ROW>''',
''''', ''<ROW'', ''>'', ''<CELL'', ''>'', resource_name, ''</CELL>'', ''<CELL'', ''>'', resource_privacy, ''</CELL>'', ''<CELL'', ''>'', nb_concepts, ''</CELL>'', ''<CELL'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.reslang&amp;u='', resource_name as ZZZZ_001, ''">'', ''detail'', ''</a>'', ''</CELL>'', ''</ROW>''',
''''', ''</TABLE>'', ''<<??include a=MYTERM.foot&amp;u='', ''//??>>''',
'v_rescon',
'NODEF',
'resource_name'
);

delete from nodes where name='all_resources';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'all_resources',
 0,
''''', ''<<??include a=MYTERM.top&amp;u='', ''//??>>'', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''<b>Ressources</b>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.new_resources&amp;u='', ''">'', ''<<??include a=ICON.new&amp;u='', ''//??>>'', ''</a>'', ''</CAPTION>''',
''''', ''<ROW'', ''>'', ''<<??include a=MYTERM.resources&amp;u='', id_resource, ''//??>>'', ''</ROW>''',
''''', ''</TABLE>''',
'resources',
'NODEF',
'resource_name'
);

delete from nodes where name='resources';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'resources',
 1,
'''''',
''''', ''<CELL_BG1'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.del_resources&amp;u='', <<??param-0//??>>, ''">'', ''<<??include a=ICON.del&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.maj_resources&amp;u='', <<??param-0//??>>, ''">'', ''<<??include a=ICON.maj&amp;u='', ''//??>>'', ''</a>'', ''<a href="ns?a=MYTERM.manage_concept&amp;u='', id_resource, ''&amp;u='', ''%'', ''">'', resource_name, ''</a>'', ''</CELL_BG1>'', ''<CELL'', ''>'', resource_name as ZZZZ_002, ''</CELL>'', ''<CELL'', ''>'', ''<<??include a=MYTERM.decode_owner&amp;u='', id_owner, ''//??>>'', ''</CELL>'', ''<CELL'', ''>'', resource_privacy, ''</CELL>'', ''<CELL'', ''>'', resource_note, ''</CELL>''',
'''''',
'resources',
'id_resource=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='new_resources';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'new_resources',
 0,
'''''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="MYTERM.all_resources"/>'', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''Create a new ressource'', ''</CAPTION>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''resource_name : '', ''</CELL_WARNING>'', ''<CELL_WARNING'', ''>'', ''<input type="hidden" name="an" value="<<$$resource_name$$>>"/>'', ''<input type="text" name="av" size="'', 32, ''" value="'', '''', ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''id_owner      : '', ''</CELL_WARNING>'', ''<CELL_WARNING'', ''>'', ''<input type="hidden" name="an" value="<<$$id_owner$$>>"/>'', ''<select name="av" >'', ''<<??include a=MYTERM.list_owner&amp;u='', ''0'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''resource_privacy: '', ''</CELL_WARNING>'', ''<CELL_WARNING'', ''>'', ''<input type="hidden" name="an" value="<<$$resource_privacy$$>>"/>'', ''<select name="av" >'', ''<<??include a=MYTERM.list_privacy&amp;u='', ''PUBLIC'', ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Note          : '', ''</CELL_WARNING>'', ''<CELL_WARNING'', ''>'', ''<input type="hidden" name="an" value="<<$$resource_note$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 80, ''">'', '''', ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>'', ''<input type="submit" name="bidon" value="'', ''confirm insert'', ''"/>'', ''<input type="hidden" name="act" value="<<$$new$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$resources$$>>"/>'', ''<b'', ''>'', ''Insert a new ressource'', ''</b>'', ''</form>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='maj_resources';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'maj_resources',
 1,
'''''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="MYTERM.all_resources"/>'', ''<TABLE'', ''>'', ''<CAPTION'', ''>'', ''Modify a ressource'', ''</CAPTION>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''resource_name : '', ''</CELL_WARNING>'', ''<CELL_WARNING'', ''>'', ''<input type="hidden" name="an" value="<<$$resource_name$$>>"/>'', ''<input type="text" name="av" size="'', 32, ''" value="'', resource_name, ''"/>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''id_owner      : '', ''</CELL_WARNING>'', ''<CELL_WARNING'', ''>'', ''<input type="hidden" name="an" value="<<$$id_owner$$>>"/>'', ''<select name="av" >'', ''<<??include a=MYTERM.list_owner&amp;u='', id_owner, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''resource_privacy: '', ''</CELL_WARNING>'', ''<CELL_WARNING'', ''>'', ''<input type="hidden" name="an" value="<<$$resource_privacy$$>>"/>'', ''<select name="av" >'', ''<<??include a=MYTERM.list_privacy&amp;u='', resource_privacy, ''//??>>'', ''</select>'', ''</CELL_WARNING>'', ''</ROW>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''Note          : '', ''</CELL_WARNING>'', ''<CELL_WARNING'', ''>'', ''<input type="hidden" name="an" value="<<$$resource_note$$>>"/>'', ''<textarea name="av" rows="'', 3, ''" cols="'', 80, ''">'', resource_note, ''</textarea>'', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>'', ''<input type="submit" name="bidon" value="'', ''confirm update'', ''"/>'', ''<input type="hidden" name="act" value="<<$$upd$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$resources$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$id_resource$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', id_resource, ''$$>>"/>'', ''<b'', ''>'', ''Modify of: '', resource_name as ZZZZ_003, ''</b>'', ''</form>''',
'''''',
'resources',
'id_resource=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='del_resources';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'del_resources',
 1,
'''''',
''''', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="MYTERM.all_resources"/>'', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_WARNING'', ''>'', ''<input type="submit" name="bidon" value="'', ''confirm delete'', ''"/>'', ''<input type="hidden" name="act" value="<<$$del$$>>"/><input  type="hidden" name="con" value="<<$$[[?!A_DB]]$$>>"/>'', ''<input type="hidden" name="tbl" value="<<$$resources$$>>"/>'', ''<input type="hidden" name="kn" value="<<$$id_resource$$>>"/>'', ''<input type="hidden" name="kv" value="<<$$'', id_resource, ''$$>>"/>'', ''<b'', ''>'', ''Delete of: '', resource_name, ''</b>'', ''</CELL_WARNING>'', ''</ROW>'', ''</TABLE>'', ''</form>''',
'''''',
'resources',
'id_resource=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='manage_concept';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'manage_concept',
 2,
''''', ''<<??include a=MYTERM.top&amp;u='', ''//??>>'', ''<<??include a=MYTERM.manage_concept_query&amp;u='', <<??param-0//??>>, ''&amp;u='', <<??param-1//??>>, ''//??>>'', ''<<??include a=MYTERM.manage_getresultconcept&amp;u='', <<??param-0//??>>, ''&amp;u='', <<??param-1//??>>, ''//??>>'', ''<<??include a=MYTERM.foot&amp;u='', ''//??>>''',
''' ''',
''' ''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='manage_concept_query';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'manage_concept_query',
 2,
'''''',
''''', ''<b'', ''>'', decode_resource(<<??param-0//??>>), ''</b>'', ''<form action="ns" method="post">'', ''<input type="hidden" name="a" value="MYTERM.queryconcept"/>'', ''<input type="submit" name="bidon" value="'', ''Execute'', ''"/>'', '' Request : '', ''<input type="text" name="u" size="'', 32, ''" value="'', _query, ''"/>'', ''<hr/>'', ''</form>''',
'''''',
'dual',
'NODEF',
'NODEF'
);

delete from nodes where name='manage_getresultconcept';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'manage_getresultconcept',
 2,
''''', ''<TABLE'', ''>''',
''''', ''<ROW_ALT'', ''>'', ''<CELL'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.detail_concept&amp;u='', id_concept, ''">'', id_concept as ZZZZ_004, ''</a>'', ''</CELL>'', ''</ROW_ALT>''',
''''', ''</TABLE>''',
'concepts',
'source like <<??param-1//??>> and id_resource=<<??param-0//??>>',
'id_concept limit 10'
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
''''', ''<<??include a=MYTERM.detail_concept_resource&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_concept_subject_field&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_concept_definition&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_concept_source_definition&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_concept_note&amp;u='', <<??param-0//??>>, ''//??>>''',
''' ''',
''' ''',
'dual',
'NODEF',
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
''''', ''<i'', ''>'', '' Subject: '', ''</i>'', subject_field',
'''''',
'v_conceptres',
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

delete from nodes where name='detail_concept_note';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_concept_note',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Note: '', ''</i>'', concept_note',
'''''',
'concepts',
'id_concept=<<??param-0//??>> and concept_note is  not  null ',
'NODEF'
);

delete from nodes where name='detail_term';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term',
 1,
'''''',
''''', ''<i'', ''>'', ''<<??include a=MYTERM.decode_lang&amp;u='', id_language, ''//??>>'', '': '', ''</i>'', ''<b'', ''>'', term_form, ''</b>'', ''<<??include a=MYTERM.detail_term_partofspeech&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_type&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_source&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_definition&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_source_definition&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_context&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_note&amp;u='', <<??param-0//??>>, ''//??>>'', ''<<??include a=MYTERM.detail_term_admin_status&amp;u='', <<??param-0//??>>, ''//??>>''',
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
''''', ''<i'', ''>'', '' (Part of speech: '', ''</i>'', term_partofspeech, ''<i'', ''>'', '')'', ''</i>''',
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
''''', ''<i'', ''>'', '', Type: '', ''</i>'', term_type',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_type is  not  null ',
'NODEF'
);

delete from nodes where name='detail_term_source';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term_source',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Source: '', ''</i>'', term_source',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_source is  not  null ',
'NODEF'
);

delete from nodes where name='detail_term_definition';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term_definition',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Definition: '', ''</i>'', term_definition',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_definition is  not  null ',
'NODEF'
);

delete from nodes where name='detail_term_source_definition';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term_source_definition',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Source definition: '', ''</i>'', term_source_definition',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_source_definition is  not  null ',
'NODEF'
);

delete from nodes where name='detail_term_context';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term_context',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Context: '', ''</i>'', term_context',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_context is  not  null ',
'NODEF'
);

delete from nodes where name='detail_term_note';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'detail_term_note',
 1,
'''''',
''''', ''<br/>'', ''<i'', ''>'', ''Note: '', ''</i>'', term_note',
'''''',
'terms',
'id_term=<<??param-0//??>> and term_note is  not  null ',
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

delete from nodes where name='getresultconcept';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'getresultconcept',
 2,
''''', ''<TABLE'', ''>'', ''<ROW'', ''>'', ''<CELL_TITLE'', ''>'', ''Result by concept for '', ''<<??include a=MYTERM.decode_lang&amp;u='', <<??param-1//??>>, ''//??>>'', ''</CELL_TITLE>'', ''</ROW>''',
''''', ''<ROW_ALT'', ''>'', ''<CELL'', ''>'', ''<a href="ns?eip=ZYX0000XYZ&amp;a=MYTERM.detail_concept&amp;u='', id_concept, ''">'', source, ''</a>'', ''</CELL>'', ''</ROW_ALT>''',
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

delete from nodes where name='decode_owner';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'decode_owner',
 1,
'''''',
''''', owner_last_name',
'''''',
'owners',
'id_owner=<<??param-0//??>>',
'NODEF'
);

delete from nodes where name='list_owner';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_owner',
 1,
'''''',
''''', ''<option'', ''>'', if(id_owner=<<??param-0//??>>,''<selected_option/>'',''''), ''<value'', ''>'', id_owner, ''</value>'', owner_last_name, ''</option>''',
'''''',
'owners',
'NODEF',
'owner_last_name'
);

delete from nodes where name='list_privacy';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'list_privacy',
 1,
'''''',
''''', ''<option'', ''>'', if(label=<<??param-0//??>>,''<selected_option/>'',''''), ''<value'', ''>'', label, ''</value>'', label as ZZZZ_005, ''</option>''',
'''''',
'translations',
'id_language=''EN'' and type_obj=''LOV_PRIVACY''',
'label'
);

delete from nodes where name='decode_resource';

insert into nodes(name, nbparam, pre, items, post, collection, selector, ordering)
values(
'decode_resource',
 1,
'''''',
''''', resource_name',
'''''',
'resources',
'id_resource=<<??param-0//??>>',
'NODEF'
);
commit;
