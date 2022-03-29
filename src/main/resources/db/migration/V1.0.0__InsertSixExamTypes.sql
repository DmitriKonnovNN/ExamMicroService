
insert into exam_type(id,type_name,description) values (1,'Wirtschaft','') on conflict do nothing;
insert into exam_type(id,type_name,description) values (2,'Jugendliche','Prüfung für Jugendliche unter 16') on conflict do nothing;
insert into exam_type(id,type_name,description) values (3,'Erwachsene','Prüfung für Erwachsene ab 16') on conflict do nothing;
insert into exam_type(id,type_name,description) values (4,'Migration','') on conflict do nothing;
insert into exam_type(id,type_name,description) values (5,'Geflüchtete','') on conflict do nothing;
insert into exam_type(id,type_name,description) values (6,'Medizin','') on conflict do nothing;
