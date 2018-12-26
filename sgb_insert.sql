INSERT INTO public.tiporequisicao (idtiporequisicao, descricao) VALUES (1, 'requisicao dominciliar');
INSERT INTO public.tiporequisicao (idtiporequisicao, descricao) VALUES (2, 'requisicao inter

INSERT INTO public."user" (user_id, active, email, last_name, name, password) VALUES (1, 1, 'admin@admin', 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO public."user" (user_id, active, email, last_name, name, password) VALUES (2, 1, 'fonseca@fonseca', 'fonseca', 'fonseca', 'e653d3954be6576488c5ce7a599869de');

INSERT INTO public.tipoobra (idtipo, descricao) VALUES (1, 'Livro');
INSERT INTO public.tipoobra (idtipo, descricao) VALUES (2, 'Revista');
INSERT INTO public.tipoobra (idtipo, descricao) VALUES (3, 'CD');

INSERT INTO public.areacientifica (idarea, descricao) VALUES (1, 'Fisica');
INSERT INTO public.areacientifica (idarea, descricao) VALUES (2, 'Matematica');
INSERT INTO public.areacientifica (idarea, descricao) VALUES (3, 'Quimica');
INSERT INTO public.areacientifica (idarea, descricao) VALUES (4, 'Literatura');
INSERT INTO public.areacientifica (idarea, descricao) VALUES (5, 'Biologia');

INSERT INTO public.autor (hashcode, nome) VALUES ('8229a8b31ffcddd530ce6b821313a55a', 'Ricardo Daniel Fedeli');
INSERT INTO public.autor (hashcode, nome) VALUES ('d1b547dcf8f6d14bbeb12619eff97819', 'Enrico Giulio Franco Polloni');
INSERT INTO public.autor (hashcode, nome) VALUES ('ed4518f5dd79dfe71c93738816d642d5', 'Fernando Eduardo Peres');
INSERT INTO public.autor (hashcode, nome) VALUES ('90455afe8bf918c581be908c174a0d30', 'Emerson Cardoso');
INSERT INTO public.autor (hashcode, nome) VALUES ('4bbde07660e5eff90873642cfae9a8dd', 'SSSSSSSS');
INSERT INTO public.autor (hashcode, nome) VALUES ('0b757be795f46a5c37c52e7932e8effc', 'Americo Jose');
INSERT INTO public.autor (hashcode, nome) VALUES ('62b9aef3390384c34a495744df95e8dc', 'Joao Antonio');
INSERT INTO public.autor (hashcode, nome) VALUES ('ebc308f979e135f40483eec4b35feea1', 'WSS');
INSERT INTO public.autor (hashcode, nome) VALUES ('c6bc65d0f994ea20585b895298f9090c', 'emersondd ddd');
INSERT INTO public.autor (hashcode, nome) VALUES ('7a739ded8071c43747c98df60e88c7d1', '45555dd fff');
INSERT INTO public.autor (hashcode, nome) VALUES ('8f26e54c201a6f5ffdc79100f2126faf', 'b b');
INSERT INTO public.autor (hashcode, nome) VALUES ('1bbda9b94de9eafe906b75b30834a8b0', 'a a');

INSERT INTO public.estadodevolucao (idestadodevolucao, descricao) VALUES (2, 'nao devolvido');
INSERT INTO public.estadodevolucao (idestadodevolucao, descricao) VALUES (3, 'devolvido');
INSERT INTO public.estadodevolucao (idestadodevolucao, descricao) VALUES (1, 'Indeterminado');' ||

INSERT INTO public.estadopedido (idestadopedido, descricao) VALUES (1, 'PENDING');
INSERT INTO public.estadopedido (idestadopedido, descricao) VALUES (3, 'ACCEPTED');
INSERT INTO public.estadopedido (idestadopedido, descricao) VALUES (5, 'CANCELED');
INSERT INTO public.estadopedido (idestadopedido, descricao) VALUES (2, 'REJECTED');
INSERT INTO public.estadopedido (idestadopedido, descricao) VALUES (4, 'IN_QUEUE');

INSERT INTO public.formatocd (idformato, descricao) VALUES (1, 'Audio');
INSERT INTO public.formatocd (idformato, descricao) VALUES (2, 'Texto');
INSERT INTO public.formatocd (idformato, descricao) VALUES (3, 'Video');
INSERT INTO public.formatocd (idformato, descricao) VALUES (4, 'Sistema');

INSERT INTO public.idioma (ididioma, descricao) VALUES (1, 'Portugues');
INSERT INTO public.idioma (ididioma, descricao) VALUES (2, 'Ingles');
INSERT INTO public.idioma (ididioma, descricao) VALUES (3, 'Espanhol');

INSERT INTO public.obra (cota, registro, titulo, idarea, localpublicacao, ididioma, quantidade, idtipo, pathpdf, pathcapa, anopublicacao) VALUES ('WW2', 7777, 'Introducao a Biologia', 1, 'Nampula', 2, 4, 2, '', 'digitalLibrary/cover/bg4.jpg', 44444);
INSERT INTO public.obra (cota, registro, titulo, idarea, localpublicacao, ididioma, quantidade, idtipo, pathpdf, pathcapa, anopublicacao) VALUES ('77788', 555, 'Introducao a Quimica', 5, 'Maputo', 1, 4, 2, 'digitalLibrary/pdf/isbd-cons_2007-en.pdf', 'digitalLibrary/cover/bg.png', 788);
INSERT INTO public.obra (cota, registro, titulo, idarea, localpublicacao, ididioma, quantidade, idtipo, pathpdf, pathcapa, anopublicacao) VALUES ('ww23', 11, '11', 5, 'teste', null, 0, 1, null, null, 2015);
INSERT INTO public.obra (cota, registro, titulo, idarea, localpublicacao, ididioma, quantidade, idtipo, pathpdf, pathcapa, anopublicacao) VALUES ('531.4F', 1223, 'Introducao Fisica', 1, 'Pemba', 1, 4, 1, '', 'digitalLibrary/cover/bg4.jpg', 2001);
INSERT INTO public.obra (cota, registro, titulo, idarea, localpublicacao, ididioma, quantidade, idtipo, pathpdf, pathcapa, anopublicacao) VALUES ('eee2', 1234, 'Introducao a Matematica', 1, 'Beira', 1, 4, 2, 'digitalLibrary/pdf/4.pdf', 'digitalLibrary/cover/bg4.jpg', 1298);
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('8229a8b31ffcddd530ce6b821313a55a', '531.4F');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('ed4518f5dd79dfe71c93738816d642d5', '531.4F');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('90455afe8bf918c581be908c174a0d30', 'WW2');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('4bbde07660e5eff90873642cfae9a8dd', 'eee2');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('0b757be795f46a5c37c52e7932e8effc', 'eee2');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('62b9aef3390384c34a495744df95e8dc', '77788');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('ebc308f979e135f40483eec4b35feea1', 'WW2');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('ebc308f979e135f40483eec4b35feea1', '77788');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('8f26e54c201a6f5ffdc79100f2126faf', 'ww23');
INSERT INTO public.obra_autor (hashcode, cota) VALUES ('1bbda9b94de9eafe906b75b30834a8b0', 'ww23');

INSERT INTO public.registroobra (cota, user_id, dataregisto) VALUES ('WW2', 1, '2018-11-05');
INSERT INTO public.registroobra (cota, user_id, dataregisto) VALUES ('eee2', 1, '2018-11-05');
INSERT INTO public.registroobra (cota, user_id, dataregisto) VALUES ('77788', 1, '2018-11-05');
INSERT INTO public.registroobra (cota, user_id, dataregisto) VALUES ('ww23', 2, '2018-11-08');
INSERT INTO public.revista (cota, instituicao) VALUES ('WW2', 'WWWWWWWWWWWWWWWWWWWW');
INSERT INTO public.revista (cota, instituicao) VALUES ('eee2', 'ddddddd');
INSERT INTO public.revista (cota, instituicao) VALUES ('77788', 'rtrrrrrrrrrrrr');

INSERT INTO public.config (nome, descricao, valor) VALUES ('MAXIMUM_TIME', '"TEMPO MAXIMO". EM MINUTOS QUE UM EXEMPLAR FICA A DISPONIBILIDADE DE UM UTENTE, APOS A REQUISICAO', '60');
INSERT INTO public.config (nome, descricao, valor) VALUES ('MINIMUM_NUMBER_OF_COPIES', '"QUANTIDADE MINIMA DE EXEMPLARES" QUE DEVEM FICAR NA BIBLIOTECA', '2');
INSERT INTO public.config (nome, descricao, valor) VALUES ('ENTRY_TIME_ON_WEEKDAYS', 'HORARIO INICIAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOS DIAS DA SEMANA', '7');
INSERT INTO public.config (nome, descricao, valor) VALUES ('ENTRY_TIME_ON_SATURDAY', 'HORARIO INICIAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOE SABADO', '9');
INSERT INTO public.config (nome, descricao, valor) VALUES ('EXIT_TIME_ON_WEEKDAYS', 'HORARIO FINAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOS DIAS DA SEMANA', '17');
INSERT INTO public.config (nome, descricao, valor) VALUES ('EXIT_TIME_ON_SATURDAY', 'HORARIO FINAL DE FUNCIONAMENTO DOS SERVICOS BIBLIOTECARIO NOE SABADO', '13');