-- ----------------------------
-- Records of autor
-- ----------------------------
INSERT INTO "public"."autor" VALUES ('8229a8b31ffcddd530ce6b821313a55a', 'Ricardo Daniel Fedeli');
INSERT INTO "public"."autor" VALUES ('d1b547dcf8f6d14bbeb12619eff97819', 'Enrico Giulio Franco Polloni');
INSERT INTO "public"."autor" VALUES ('ed4518f5dd79dfe71c93738816d642d5', 'Fernando Eduardo Peres');
INSERT INTO "public"."autor" VALUES ('90455afe8bf918c581be908c174a0d30', 'Emerson Cardoso');
INSERT INTO "public"."autor" VALUES ('0b757be795f46a5c37c52e7932e8effc', 'Americo Jose');
INSERT INTO "public"."autor" VALUES ('62b9aef3390384c34a495744df95e8dc', 'Joao Antonio');
INSERT INTO "public"."autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'Cecília Meireles');
INSERT INTO "public"."autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'Monteiro Lobato');
INSERT INTO "public"."autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'José de Alencar');
INSERT INTO "public"."autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'Carlos Drummond de Andrade');
INSERT INTO "public"."autor" VALUES ('f70e3a934c3327ff6b0b0f829a9cda99', 'Stephen King');
INSERT INTO "public"."autor" VALUES ('8306288b152e788442c362936e796f3f', 'Shannon Delany');
INSERT INTO "public"."autor" VALUES ('430044eb1093327e0fc91079468bb91b', 'Andre Vianco');
INSERT INTO "public"."autor" VALUES ('b687b7ca6a24140b9b03305c6ee42260', 'John Green');
INSERT INTO "public"."autor" VALUES ('4bbde07660e5eff90873642cfae9a8dd', 'Clarice Lispector ');
INSERT INTO "public"."autor" VALUES ('7a739ded8071c43747c98df60e88c7d1', 'Ana Maria Machado');
INSERT INTO "public"."autor" VALUES ('c6bc65d0f994ea20585b895298f9090c', 'Charles Dickens');
INSERT INTO "public"."autor" VALUES ('ebc308f979e135f40483eec4b35feea1', 'Jorge Amado');

-- ----------------------------
-- Records of emprestimo
-- ----------------------------
INSERT INTO "public"."emprestimo" VALUES (4, 'BBB1', '2019-03-27 20:16:24.677', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (4, 'AAA9', '2019-03-27 20:16:24.402', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (4, 'AAA8', '2019-03-27 20:16:24.244', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (4, 'AAA7', '2019-03-27 20:16:24.091', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (2, 'AAA3', '2019-03-27 20:14:21.75', 6, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (2, 'AAA2', '2019-03-27 20:14:21.585', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (2, 'AAA6', '2019-03-27 20:14:21.365', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'AAA6', '2019-03-27 20:05:50.171', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2019-03-27 20:05:49.94', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'AAA1', '2019-03-27 20:05:49.694', 1, NULL, NULL, 1, '--', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:17:46.228', 1, '2018-05-14', '2018-05-17', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:29:20.376', 1, '2019-03-21', '2019-03-25', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:21:38.945', 1, '2019-03-21', '2019-03-25', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (2, '544FF', '2018-12-26 12:25:14.634', 1, '2019-03-21', '2019-03-25', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 1, 2, 1, 1);
INSERT INTO "public"."emprestimo" VALUES (2, 'WW2', '2018-12-26 12:26:21.673', 1, '2019-03-21', '2019-03-29', 1, 'Cancelado Pelo Sistema, excedeu o deadline', 1, 2, 1, 1);


-- ----------------------------
-- Records of obra
-- ----------------------------
INSERT INTO "public"."obra" VALUES ('545AA', 785, 'Introducao a Estatistica', 1, 'Maputo', 1, 2, 2, NULL, 'digitalLibrary/cover/Introducao-a-Estatistica-Enfoque-Informatico-com-o-Pacote-Estatistico-SPSS-275390.jpg', 2017, NULL);
INSERT INTO "public"."obra" VALUES ('589AF', 78988, 'Introducao a Geografia', 1, 'teste', 1, 120, 2, NULL, 'digitalLibrary/cover/geografia.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('531.4F', 1223, 'Introducao Fisica', 1, 'Pemba', 1, 4, 1, '', 'digitalLibrary/cover/fisica.jpg', 2001, NULL);
INSERT INTO "public"."obra" VALUES ('77788', 555, 'Introducao a Quimica', 5, 'Maputo', 1, 4, 2, 'digitalLibrary/pdf/isbd-cons_2007-en.pdf', 'digitalLibrary/cover/quimica.jpg', 788, NULL);
INSERT INTO "public"."obra" VALUES ('eee2', 1234, 'Introducao a Matematica', 1, 'Beira', 1, 4, 2, 'digitalLibrary/pdf/4.pdf', 'digitalLibrary/cover/Introducao-a-Matematica.jpg', 1298, NULL);
INSERT INTO "public"."obra" VALUES ('AAA5', 123, 'O Homem mais inteligente da historia', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/capa-o-homem-mais-inteligente-da-historia.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB2', 123, 'Sao Cipriano o Bruxo', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/livro_de_sao_cipriano_.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB3', 123, 'Harry Potter e a Camara Secreta', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/livro-harry-potter-e-a-cmara-secreta-.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB4', 123, 'O Pequeno Principe', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/Livro-–-O-Pequeno-Principe.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB5', 123, 'A Torre da Andorinha', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/livros-coleco-completa-witcher-capa-tradicional-8-livros-D_NQ_NP_739361-MLB26040880873_092017-O.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB6', 123, 'Simplesmente Aconteceu', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/Livro-Simplesmente-Acontece-Edicao.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB7', 123, 'Diario de Um Vampiro - Meia Noite', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/meia-noite-dv.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB8', 123, 'O Mundo dos Dragoes', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/mundo-dragoes.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB9', 123, 'O Iluminado', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/O iluminado.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('CCC1', 123, 'O Segredo sas Sombras', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/o segredo das sombras.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('CCC2', 123, 'O Sete', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/O setes.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('CCC3', 123, 'O Hobbit', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/O-Hobbit-Edicao-Especial-Capa-do-Filme-J-R-R-Tolkien-1768460.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('CCC4', 123, 'Quem e voce, Alasca ?', 4, 'Pemba', 1, 100, 2, NULL, 'digitalLibrary/cover/Quem é Você, Alasca.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('BBB1', 123, 'Harry Potter e as Criancas Amaldicoadas', 4, 'Pemba', 1, 99, 2, NULL, 'digitalLibrary/cover/Harry Potter e as Criancas amaldicoadas.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('AAA4', 123, 'A Ultima musica', 4, 'Pemba', 1, 99, 2, NULL, 'digitalLibrary/cover/a-ultima-musica-capa-livro1.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('AAA1', 123, 'A Cabana', 4, 'Pemba', 1, 99, 2, NULL, 'digitalLibrary/cover/A Cabana.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('WW2', 7777, 'Introducao a Biologia', 1, 'Nampula', 2, 23, 2, '', 'digitalLibrary/cover/bg4.jpg', 44444, NULL);
INSERT INTO "public"."obra" VALUES ('544FF', 7887, 'Introducao a Matematica', 1, 'Pemba', 1, 12, 1, NULL, 'digitalLibrary/cover/Introducao-a-Matematica.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('AAA6', 123, 'A Cidade de Ossos', 4, 'Pemba', 1, 96, 2, NULL, 'digitalLibrary/cover/cidade-dos-ossos1.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('AAA2', 123, 'A primeira Vista', 4, 'Pemba', 1, 98, 2, NULL, 'digitalLibrary/cover/A primeira Vista.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('AAA3', 123, 'Ackson e os Olimpianos', 4, 'Pemba', 1, 99, 2, NULL, 'digitalLibrary/cover/ackson & os olimpianos.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('AAA7', 123, 'A Cidade de Papel', 4, 'Pemba', 1, 99, 2, NULL, 'digitalLibrary/cover/CIDADES_DE_PAPEL_1374703616B.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('AAA8', 123, 'As Cronicas de Gelo e de Fogo', 4, 'Pemba', 1, 99, 2, NULL, 'digitalLibrary/cover/Coleção As Crônicas De Gelo E Fogo.jpg', 2018, NULL);
INSERT INTO "public"."obra" VALUES ('AAA9', 123, 'O Esplendor da Honra', 4, 'Pemba', 1, 99, 2, NULL, 'digitalLibrary/cover/esplendor-da-honra-julie-garwood-editora-universo-dos-livros-romance-de-epoca-as-7-melhores-e-mais-bonitas-capas-de-2017-mademoisellelovesbooks.jpg', 2018, NULL);

-- ----------------------------
-- Records of obra_autor
-- ----------------------------
INSERT INTO "public"."obra_autor" VALUES ('8229a8b31ffcddd530ce6b821313a55a', '531.4F');
INSERT INTO "public"."obra_autor" VALUES ('ed4518f5dd79dfe71c93738816d642d5', '531.4F');
INSERT INTO "public"."obra_autor" VALUES ('90455afe8bf918c581be908c174a0d30', 'WW2');
INSERT INTO "public"."obra_autor" VALUES ('4bbde07660e5eff90873642cfae9a8dd', 'eee2');
INSERT INTO "public"."obra_autor" VALUES ('0b757be795f46a5c37c52e7932e8effc', 'eee2');
INSERT INTO "public"."obra_autor" VALUES ('62b9aef3390384c34a495744df95e8dc', '77788');
INSERT INTO "public"."obra_autor" VALUES ('ebc308f979e135f40483eec4b35feea1', 'WW2');
INSERT INTO "public"."obra_autor" VALUES ('ebc308f979e135f40483eec4b35feea1', '77788');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA1');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA1');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA1');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA2');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA2');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA2');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'AAA2');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA3');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA3');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA3');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'AAA3');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA4');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA4');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA4');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'AAA4');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA5');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA5');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA5');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'AAA5');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA6');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA6');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA6');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'AAA6');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA7');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA7');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA7');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'AAA7');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA8');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA8');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA8');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'AAA8');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'AAA9');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'AAA9');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'AAA9');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'AAA9');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB1');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB1');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB1');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB1');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB2');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB2');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB2');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB2');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB3');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB3');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB3');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB3');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB4');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB4');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB4');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB4');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB5');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB5');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB5');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB5');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB6');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB6');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB6');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB6');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB7');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB7');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB7');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB7');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB8');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB8');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB8');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB8');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'BBB9');
INSERT INTO "public"."obra_autor" VALUES ('f70e3a934c3327ff6b0b0f829a9cda99', 'BBB9');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'BBB9');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'BBB9');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'BBB9');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'CCC1');
INSERT INTO "public"."obra_autor" VALUES ('f70e3a934c3327ff6b0b0f829a9cda99', 'CCC1');
INSERT INTO "public"."obra_autor" VALUES ('8306288b152e788442c362936e796f3f', 'CCC1');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'CCC1');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'CCC1');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'CCC1');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'CCC2');
INSERT INTO "public"."obra_autor" VALUES ('f70e3a934c3327ff6b0b0f829a9cda99', 'CCC2');
INSERT INTO "public"."obra_autor" VALUES ('8306288b152e788442c362936e796f3f', 'CCC2');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'CCC2');
INSERT INTO "public"."obra_autor" VALUES ('430044eb1093327e0fc91079468bb91b', 'CCC2');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'CCC2');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'CCC2');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'CCC3');
INSERT INTO "public"."obra_autor" VALUES ('f70e3a934c3327ff6b0b0f829a9cda99', 'CCC3');
INSERT INTO "public"."obra_autor" VALUES ('8306288b152e788442c362936e796f3f', 'CCC3');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'CCC3');
INSERT INTO "public"."obra_autor" VALUES ('430044eb1093327e0fc91079468bb91b', 'CCC3');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'CCC3');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'CCC3');
INSERT INTO "public"."obra_autor" VALUES ('ad8cc9439f4078432c3a1ac916879580', 'CCC4');
INSERT INTO "public"."obra_autor" VALUES ('f70e3a934c3327ff6b0b0f829a9cda99', 'CCC4');
INSERT INTO "public"."obra_autor" VALUES ('8306288b152e788442c362936e796f3f', 'CCC4');
INSERT INTO "public"."obra_autor" VALUES ('0c2bdde760f064e8de5806fa5d04a761', 'CCC4');
INSERT INTO "public"."obra_autor" VALUES ('430044eb1093327e0fc91079468bb91b', 'CCC4');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', 'CCC4');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', 'CCC4');
INSERT INTO "public"."obra_autor" VALUES ('b687b7ca6a24140b9b03305c6ee42260', 'CCC4');
INSERT INTO "public"."obra_autor" VALUES ('0b757be795f46a5c37c52e7932e8effc', '545AA');
INSERT INTO "public"."obra_autor" VALUES ('f6b6fe0e0847f01b9a71511cbb3be063', '545AA');
INSERT INTO "public"."obra_autor" VALUES ('cb90597609ca97413350bc3fa0576828', '589AF');

-- ----------------------------
-- Records of obraeliminadas
-- ----------------------------
INSERT INTO "public"."obraeliminadas" VALUES ('2019-01-10 20:40:01.468', 'dddd', 8, '544FF', 3);

-- ----------------------------
-- Records of registroobra
-- ----------------------------
INSERT INTO "public"."registroobra" VALUES ('545AA', 1, '2019-01-10 18:10:23', 'Anadarko', 2, 85);
INSERT INTO "public"."registroobra" VALUES ('77788', 3, '2019-01-31 18:11:26', 'Dois mil meticais', 1, 45);
INSERT INTO "public"."registroobra" VALUES ('AAA1', 1, '2019-03-27 18:58:13.842', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('AAA2', 1, '2019-03-27 18:58:52.762', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('AAA3', 1, '2019-03-27 18:59:54.765', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('AAA4', 1, '2019-03-27 19:00:45.722', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('AAA5', 1, '2019-03-27 19:01:42.331', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('AAA6', 1, '2019-03-27 19:03:03.114', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('AAA7', 1, '2019-03-27 19:03:21.602', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('AAA8', 1, '2019-03-27 19:03:54.947', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('AAA9', 1, '2019-03-27 19:04:25.186', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB1', 1, '2019-03-27 19:05:02.437', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB2', 1, '2019-03-27 19:05:26.83', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB3', 1, '2019-03-27 19:05:50.461', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB4', 1, '2019-03-27 19:06:19.474', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB5', 1, '2019-03-27 19:06:48.176', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB6', 1, '2019-03-27 19:07:08.216', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB7', 1, '2019-03-27 19:07:37.305', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB8', 1, '2019-03-27 19:08:05.778', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('BBB9', 1, '2019-03-27 19:08:43.346', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('CCC1', 1, '2019-03-27 19:09:40.489', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('CCC2', 1, '2019-03-27 19:10:09.575', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('CCC3', 1, '2019-03-27 19:11:13.796', NULL, 1, NULL);
INSERT INTO "public"."registroobra" VALUES ('CCC4', 1, '2019-03-27 19:11:53.782', NULL, 1, NULL);

-- ----------------------------
-- Records of revista
-- ----------------------------
INSERT INTO "public"."revista" VALUES ('AAA1', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('AAA2', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('AAA3', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('AAA4', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('AAA5', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('AAA6', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('AAA7', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('AAA8', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('AAA9', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB1', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB2', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB3', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB4', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB5', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB6', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB7', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB8', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('BBB9', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('CCC1', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('CCC2', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('CCC3', 'Unilurio');
INSERT INTO "public"."revista" VALUES ('CCC4', 'Unilurio');
