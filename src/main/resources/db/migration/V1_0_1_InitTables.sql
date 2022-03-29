BEGIN
INSERT INTO exam_type (id, type_name, description) VALUES (1,'Wirtschaft','Wirtschaft') ON CONFLICT DO NOTHING;
INSERT INTO exam_type (id, type_name, description) VALUES (2,'Jugendliche','Prüfung für Jugendliche unter 16') ON CONFLICT DO NOTHING;
INSERT INTO exam_type (id, type_name, description) VALUES (3,'Erwachsene','Prüfung für Erwachsene ab 16') ON CONFLICT DO NOTHING;
INSERT INTO exam_type (id, type_name, description) VALUES (4,'Migration','') ON CONFLICT DO NOTHING;
INSERT INTO exam_type (id, type_name, description) VALUES (5,'Geflüchtete','') ON CONFLICT DO NOTHING;
INSERT INTO exam_type (id, type_name, description) VALUES (6,'Medizin','') ON CONFLICT DO NOTHING;
COMMIT
