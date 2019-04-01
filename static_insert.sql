-- ----------------------------
-- Records of areacientifica
-- ----------------------------
INSERT INTO "public"."areacientifica" VALUES (1, 'Fisica');
INSERT INTO "public"."areacientifica" VALUES (2, 'Matematica');
INSERT INTO "public"."areacientifica" VALUES (3, 'Quimica');
INSERT INTO "public"."areacientifica" VALUES (4, 'Literatura');
INSERT INTO "public"."areacientifica" VALUES (5, 'Biologia');

-- Records of config
-- ----------------------------
INSERT INTO "public"."config" VALUES ('MAXIMUM_TIME', '"TEMPO MAXIMO". EM MINUTOS QUE UM EXEMPLAR FICA A DISPONIBILIDADE DE UM UTENTE, APOS A REQUISICAO', '60');
INSERT INTO "public"."config" VALUES ('MINIMUM_NUMBER_OF_COPIES', '"QUANTIDADE MINIMA DE EXEMPLARES" QUE DEVEM FICAR NA BIBLIOTECA', '2');
INSERT INTO "public"."config" VALUES ('ENTRY_TIME_ON_WEEKDAYS', 'HORARIO INICIAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOS DIAS DA SEMANA', '7');
INSERT INTO "public"."config" VALUES ('ENTRY_TIME_ON_SATURDAY', 'HORARIO INICIAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOE SABADO', '9');
INSERT INTO "public"."config" VALUES ('EXIT_TIME_ON_WEEKDAYS', 'HORARIO FINAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOS DIAS DA SEMANA', '17');
INSERT INTO "public"."config" VALUES ('EXIT_TIME_ON_SATURDAY', 'HORARIO FINAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOE SABADO', '13');
INSERT INTO "public"."config" VALUES ('DAILY_RATE_FINE', 'TAXA DIARIA DA MULTA PARA ATRASO DE EVOLUCAO DE LIVRO', '25');
INSERT INTO "public"."config" VALUES ('DEADLINE_REQUESTED_BOOKS', 'tempo maximo em minuto, que uma obra requesitada por um utente, permanece disponivel para o levatamento.', '60');
INSERT INTO "public"."config" VALUES ('DEADLINE_RESERVED_BOOKS', 'tempo maximo em dias, que uma obra reservada por um utente, permanece disponivel para o levatamento.', '2');
INSERT INTO "public"."config" VALUES ('DEADLINE_STUDENT_BORROWED_BOOKS', 'tempo maximo em dias, que uma obra emprestada permanece na posse de um estudante', '3');
INSERT INTO "public"."config" VALUES ('DEADLINE_TEACHER_BORROWED_BOOKS', 'tempo maximo em dias, que uma obra emprestada permanece na posse de um docente', '7');

-- ----------------------------
-- Records of estadodevolucao
-- ----------------------------
INSERT INTO "public"."estadodevolucao" VALUES (1, 'UNDETERMINED');
INSERT INTO "public"."estadodevolucao" VALUES (3, 'RETURNED');
INSERT INTO "public"."estadodevolucao" VALUES (2, 'NOT_RETURNED');

-- ----------------------------
-- Records of estadomulta
-- ----------------------------
INSERT INTO "public"."estadomulta" VALUES (1, 'Indeferido');
INSERT INTO "public"."estadomulta" VALUES (3, 'NOT_PAID');
INSERT INTO "public"."estadomulta" VALUES (2, 'PAID');
INSERT INTO "public"."estadomulta" VALUES (4, 'REVOKED');

-- ----------------------------
-- Records of estadopedido
-- ----------------------------
INSERT INTO "public"."estadopedido" VALUES (3, 'ACCEPTED');
INSERT INTO "public"."estadopedido" VALUES (5, 'CANCELED');
INSERT INTO "public"."estadopedido" VALUES (2, 'REJECTED');
INSERT INTO "public"."estadopedido" VALUES (4, 'ON_WAINTING_QUEUE');
INSERT INTO "public"."estadopedido" VALUES (6, 'PENDING_BOOKING');
INSERT INTO "public"."estadopedido" VALUES (1, 'PENDING_MINI_BOOKING');

-- ----------------------------
-- Records of formaaquisicao
-- ----------------------------
INSERT INTO "public"."formaaquisicao" VALUES (1, 'Compra');
INSERT INTO "public"."formaaquisicao" VALUES (2, 'Doacao');
INSERT INTO "public"."formaaquisicao" VALUES (3, 'Permuta');

-- ----------------------------
-- Records of formatocd
-- ----------------------------
INSERT INTO "public"."formatocd" VALUES (1, 'Audio');
INSERT INTO "public"."formatocd" VALUES (2, 'Texto');
INSERT INTO "public"."formatocd" VALUES (3, 'Video');
INSERT INTO "public"."formatocd" VALUES (4, 'Sistema');

-- ----------------------------
-- Records of idioma
-- ----------------------------
INSERT INTO "public"."idioma" VALUES (1, 'Portugues');
INSERT INTO "public"."idioma" VALUES (2, 'Ingles');
INSERT INTO "public"."idioma" VALUES (3, 'Espanhol');


-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO "public"."role" VALUES (3, 'TEACHER', 4);
INSERT INTO "public"."role" VALUES (1, 'ADMIN', 0);
INSERT INTO "public"."role" VALUES (2, 'STUDENT', 6);

-- ----------------------------
-- Records of tipoobra
-- ----------------------------
INSERT INTO "public"."tipoobra" VALUES (1, 'Livro');
INSERT INTO "public"."tipoobra" VALUES (2, 'Revista');
INSERT INTO "public"."tipoobra" VALUES (3, 'CD');

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user" VALUES (1, 1, 'admin@admin', 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO "public"."user" VALUES (3, 1, 'student@unilurio.ac.mz', 'student', 'student', '289ffeb2a745ccf51ca89a297f47e382');
INSERT INTO "public"."user" VALUES (4, 1, 'teacher@unilurio.ac.mz', 'teacher', 'teacher', '289ffeb2a745ccf51ca89a297f47e382');
INSERT INTO "public"."user" VALUES (2, 1, 'fonseca@fonseca', 'Fonseca', 'Fonseca', 'e653d3954be6576488c5ce7a599869de');

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO "public"."user_role" VALUES (1, 1);
INSERT INTO "public"."user_role" VALUES (2, 2);
INSERT INTO "public"."user_role" VALUES (4, 3);
INSERT INTO "public"."user_role" VALUES (3, 2);

-- ----------------------------
-- Records of tiporequisicao
-- ----------------------------
INSERT INTO "public"."tiporequisicao" VALUES (1, 'HOME_REQUISITION');
INSERT INTO "public"."tiporequisicao" VALUES (2, 'INTERNAL_REQUISITION');

