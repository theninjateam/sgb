-- ----------------------------
-- Records of autor
-- ----------------------------
INSERT INTO "public"."autor" VALUES ('8229a8b31ffcddd530ce6b821313a55a', 'Ricardo Daniel Fedeli');
INSERT INTO "public"."autor" VALUES ('d1b547dcf8f6d14bbeb12619eff97819', 'Enrico Giulio Franco Polloni');
INSERT INTO "public"."autor" VALUES ('ed4518f5dd79dfe71c93738816d642d5', 'Fernando Eduardo Peres');
INSERT INTO "public"."autor" VALUES ('90455afe8bf918c581be908c174a0d30', 'Emerson Cardoso');
INSERT INTO "public"."autor" VALUES ('4bbde07660e5eff90873642cfae9a8dd', 'SSSSSSSS');
INSERT INTO "public"."autor" VALUES ('0b757be795f46a5c37c52e7932e8effc', 'Americo Jose');
INSERT INTO "public"."autor" VALUES ('62b9aef3390384c34a495744df95e8dc', 'Joao Antonio');
INSERT INTO "public"."autor" VALUES ('ebc308f979e135f40483eec4b35feea1', 'WSS');
INSERT INTO "public"."autor" VALUES ('c6bc65d0f994ea20585b895298f9090c', 'emersondd ddd');
INSERT INTO "public"."autor" VALUES ('7a739ded8071c43747c98df60e88c7d1', '45555dd fff');

-- ----------------------------
-- Records of emprestimo
-- ----------------------------
INSERT INTO "public"."emprestimo" VALUES (4, 'WW2', '2018-12-26 12:26:21.673', 3, '2019-03-21', '2019-03-29', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 2, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (2, '544FF', '2018-12-26 12:25:14.634', 3, '2019-03-21', '2019-03-25', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 2, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:21:38.945', 3, '2019-03-21', '2019-03-25', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 2, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:29:20.376', 3, '2019-03-21', '2019-03-25', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 2, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:17:46.228', 3, '2018-05-14', '2018-05-17', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 2, 2, 1, 1);


-- ----------------------------
-- Records of obra
-- ----------------------------
INSERT INTO "public"."obra" VALUES ('545AA', 785, 'Introducao a Estatistica', 1, 'Maputo', 1, 2, 2, NULL, 'digitalLibrary/cover/Introducao-a-Estatistica-Enfoque-Informatico-com-o-Pacote-Estatistico-SPSS-275390.jpg', 2017, NULL);
INSERT INTO "public"."obra" VALUES ('589AF', 78988, 'Introducao a Geografia', 1, 'teste', 1, 120, 2, NULL, 'digitalLibrary/cover/geografia.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('531.4F', 1223, 'Introducao Fisica', 1, 'Pemba', 1, 4, 1, '', 'digitalLibrary/cover/fisica.jpg', 2001, NULL);
INSERT INTO "public"."obra" VALUES ('77788', 555, 'Introducao a Quimica', 5, 'Maputo', 1, 4, 2, 'digitalLibrary/pdf/isbd-cons_2007-en.pdf', 'digitalLibrary/cover/quimica.jpg', 788, NULL);
INSERT INTO "public"."obra" VALUES ('eee2', 1234, 'Introducao a Matematica', 1, 'Beira', 1, 4, 2, 'digitalLibrary/pdf/4.pdf', 'digitalLibrary/cover/Introducao-a-Matematica.jpg', 1298, NULL);
INSERT INTO "public"."obra" VALUES ('544FF', 7887, 'Introducao a Matematica', 1, 'Pemba', 1, 12, 1, NULL, 'digitalLibrary/cover/Introducao-a-Matematica.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('WW2', 7777, 'Introducao a Biologia', 1, 'Nampula', 2, 24, 2, '', 'digitalLibrary/cover/bg4.jpg', 44444, NULL);

-- ----------------------------
-- Records of registroobra
-- ----------------------------
INSERT INTO "public"."registroobra" VALUES ('545AA', 1, '2019-01-10 18:10:23', 'Anadarko', 2, 85);
INSERT INTO "public"."registroobra" VALUES ('77788', 3, '2019-01-31 18:11:26', 'Dois mil meticais', 1, 45);

