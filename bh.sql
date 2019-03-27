/*
 Navicat Premium Data Transfer

 Source Server         : Emerson
 Source Server Type    : PostgreSQL
 Source Server Version : 100005
 Source Host           : localhost:5432
 Source Catalog        : sgb
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100005
 File Encoding         : 65001

 Date: 27/03/2019 20:51:20
*/


-- ----------------------------
-- Sequence structure for areacientifica_idarea_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."areacientifica_idarea_seq";
CREATE SEQUENCE "public"."areacientifica_idarea_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for estadodevolucao_idestadodevolucao_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."estadodevolucao_idestadodevolucao_seq";
CREATE SEQUENCE "public"."estadodevolucao_idestadodevolucao_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for estadopedido_idestadopedido_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."estadopedido_idestadopedido_seq";
CREATE SEQUENCE "public"."estadopedido_idestadopedido_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for estadorenovacao_idestadorenovacao_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."estadorenovacao_idestadorenovacao_seq";
CREATE SEQUENCE "public"."estadorenovacao_idestadorenovacao_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for formatocd_idformato_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."formatocd_idformato_seq";
CREATE SEQUENCE "public"."formatocd_idformato_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for hibernate_sequence
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."hibernate_sequence";
CREATE SEQUENCE "public"."hibernate_sequence"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for idioma_ididioma_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."idioma_ididioma_seq";
CREATE SEQUENCE "public"."idioma_ididioma_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tipoobra_idtipo_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tipoobra_idtipo_seq";
CREATE SEQUENCE "public"."tipoobra_idtipo_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for areacientifica
-- ----------------------------
DROP TABLE IF EXISTS "public"."areacientifica";
CREATE TABLE "public"."areacientifica" (
  "idarea" int4 NOT NULL DEFAULT nextval('areacientifica_idarea_seq'::regclass),
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of areacientifica
-- ----------------------------
INSERT INTO "public"."areacientifica" VALUES (1, 'Fisica');
INSERT INTO "public"."areacientifica" VALUES (2, 'Matematica');
INSERT INTO "public"."areacientifica" VALUES (3, 'Quimica');
INSERT INTO "public"."areacientifica" VALUES (4, 'Literatura');
INSERT INTO "public"."areacientifica" VALUES (5, 'Biologia');

-- ----------------------------
-- Table structure for autor
-- ----------------------------
DROP TABLE IF EXISTS "public"."autor";
CREATE TABLE "public"."autor" (
  "hashcode" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "nome" varchar(255) COLLATE "pg_catalog"."default"
)
;

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
-- Table structure for cd
-- ----------------------------
DROP TABLE IF EXISTS "public"."cd";
CREATE TABLE "public"."cd" (
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS "public"."config";
CREATE TABLE "public"."config" (
  "nome" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "descricao" varchar(255) COLLATE "pg_catalog"."default",
  "valor" varchar(128) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
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
-- Table structure for emprestimo
-- ----------------------------
DROP TABLE IF EXISTS "public"."emprestimo";
CREATE TABLE "public"."emprestimo" (
  "utente" int4 NOT NULL,
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dataentradapedido" timestamp(6) NOT NULL,
  "estadopedido" int4,
  "dataaprovacao" date,
  "datadevolucao" date,
  "quantidade" int4,
  "comentario" varchar(5000) COLLATE "pg_catalog"."default",
  "estadodevolucao" int4,
  "estadorenovacao" int8,
  "idtiporequisicao" int4 NOT NULL,
  "bibliotecario" int8
)
;

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
-- Table structure for estadod
-- ----------------------------
DROP TABLE IF EXISTS "public"."estadod";
CREATE TABLE "public"."estadod" (
  "id" int4 NOT NULL,
  "dddd" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for estadodevolucao
-- ----------------------------
DROP TABLE IF EXISTS "public"."estadodevolucao";
CREATE TABLE "public"."estadodevolucao" (
  "idestadodevolucao" int4 NOT NULL DEFAULT nextval('estadodevolucao_idestadodevolucao_seq'::regclass),
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of estadodevolucao
-- ----------------------------
INSERT INTO "public"."estadodevolucao" VALUES (1, 'UNDETERMINED');
INSERT INTO "public"."estadodevolucao" VALUES (3, 'RETURNED');
INSERT INTO "public"."estadodevolucao" VALUES (2, 'NOT_RETURNED');

-- ----------------------------
-- Table structure for estadomulta
-- ----------------------------
DROP TABLE IF EXISTS "public"."estadomulta";
CREATE TABLE "public"."estadomulta" (
  "idestadomulta" int4 NOT NULL,
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of estadomulta
-- ----------------------------
INSERT INTO "public"."estadomulta" VALUES (1, 'Indeferido');
INSERT INTO "public"."estadomulta" VALUES (3, 'NOT_PAID');
INSERT INTO "public"."estadomulta" VALUES (2, 'PAID');
INSERT INTO "public"."estadomulta" VALUES (4, 'REVOKED');

-- ----------------------------
-- Table structure for estadopedido
-- ----------------------------
DROP TABLE IF EXISTS "public"."estadopedido";
CREATE TABLE "public"."estadopedido" (
  "idestadopedido" int4 NOT NULL DEFAULT nextval('estadopedido_idestadopedido_seq'::regclass),
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

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
-- Table structure for estadorenovacao
-- ----------------------------
DROP TABLE IF EXISTS "public"."estadorenovacao";
CREATE TABLE "public"."estadorenovacao" (
  "idestadorenovacao" int8 NOT NULL DEFAULT nextval('estadorenovacao_idestadorenovacao_seq'::regclass),
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of estadorenovacao
-- ----------------------------
INSERT INTO "public"."estadorenovacao" VALUES (1, 'Renovado');
INSERT INTO "public"."estadorenovacao" VALUES (2, 'Indeferido');

-- ----------------------------
-- Table structure for formaaquisicao
-- ----------------------------
DROP TABLE IF EXISTS "public"."formaaquisicao";
CREATE TABLE "public"."formaaquisicao" (
  "formaaquisicao" int4 NOT NULL,
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of formaaquisicao
-- ----------------------------
INSERT INTO "public"."formaaquisicao" VALUES (1, 'Compra');
INSERT INTO "public"."formaaquisicao" VALUES (2, 'Doacao');
INSERT INTO "public"."formaaquisicao" VALUES (3, 'Permuta');

-- ----------------------------
-- Table structure for formatocd
-- ----------------------------
DROP TABLE IF EXISTS "public"."formatocd";
CREATE TABLE "public"."formatocd" (
  "idformato" int4 NOT NULL DEFAULT nextval('formatocd_idformato_seq'::regclass),
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of formatocd
-- ----------------------------
INSERT INTO "public"."formatocd" VALUES (1, 'Audio');
INSERT INTO "public"."formatocd" VALUES (2, 'Texto');
INSERT INTO "public"."formatocd" VALUES (3, 'Video');
INSERT INTO "public"."formatocd" VALUES (4, 'Sistema');

-- ----------------------------
-- Table structure for idioma
-- ----------------------------
DROP TABLE IF EXISTS "public"."idioma";
CREATE TABLE "public"."idioma" (
  "ididioma" int4 NOT NULL DEFAULT nextval('idioma_ididioma_seq'::regclass),
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of idioma
-- ----------------------------
INSERT INTO "public"."idioma" VALUES (1, 'Portugues');
INSERT INTO "public"."idioma" VALUES (2, 'Ingles');
INSERT INTO "public"."idioma" VALUES (3, 'Espanhol');

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS "public"."item";
CREATE TABLE "public"."item" (
  "item_id" int4 NOT NULL,
  "descricao" varchar(255) COLLATE "pg_catalog"."default",
  "item" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for item_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."item_role";
CREATE TABLE "public"."item_role" (
  "item_id" int4 NOT NULL,
  "role_id" int4 NOT NULL
)
;

-- ----------------------------
-- Table structure for livro
-- ----------------------------
DROP TABLE IF EXISTS "public"."livro";
CREATE TABLE "public"."livro" (
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "isbn" varchar(255) COLLATE "pg_catalog"."default",
  "editora" varchar(255) COLLATE "pg_catalog"."default",
  "edicao" int4,
  "codigobarra" varchar(255) COLLATE "pg_catalog"."default",
  "volume" int4
)
;

-- ----------------------------
-- Table structure for livrocd
-- ----------------------------
DROP TABLE IF EXISTS "public"."livrocd";
CREATE TABLE "public"."livrocd" (
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "codigobarra" varchar(255) COLLATE "pg_catalog"."default",
  "descricaocd" varchar(255) COLLATE "pg_catalog"."default",
  "edicao" varchar(255) COLLATE "pg_catalog"."default",
  "editora" varchar(255) COLLATE "pg_catalog"."default",
  "isbn" varchar(255) COLLATE "pg_catalog"."default",
  "volume" int4
)
;

-- ----------------------------
-- Table structure for multa
-- ----------------------------
DROP TABLE IF EXISTS "public"."multa";
CREATE TABLE "public"."multa" (
  "dataemissao" timestamp(0) NOT NULL,
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "utente" int4 NOT NULL,
  "diasatraso" int4,
  "valorpago" float4,
  "bibliotecario" int4,
  "dataemprestimo" timestamp(6),
  "idestadomulta" int4,
  "dataentradapedido" timestamp(6) NOT NULL,
  "taxadiaria" float4
)
;

-- ----------------------------
-- Table structure for obra
-- ----------------------------
DROP TABLE IF EXISTS "public"."obra";
CREATE TABLE "public"."obra" (
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "registro" int4,
  "titulo" varchar(255) COLLATE "pg_catalog"."default",
  "idarea" int4,
  "localpublicacao" varchar(255) COLLATE "pg_catalog"."default",
  "ididioma" int4,
  "quantidade" int4,
  "idtipo" int4,
  "pathpdf" varchar(255) COLLATE "pg_catalog"."default",
  "pathcapa" varchar(255) COLLATE "pg_catalog"."default",
  "anopublicacao" int4,
  "domiciliarqueue" bytea
)
;

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
-- Table structure for obra_autor
-- ----------------------------
DROP TABLE IF EXISTS "public"."obra_autor";
CREATE TABLE "public"."obra_autor" (
  "hashcode" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

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
-- Table structure for obraeliminadas
-- ----------------------------
DROP TABLE IF EXISTS "public"."obraeliminadas";
CREATE TABLE "public"."obraeliminadas" (
  "dataregisto" timestamp(6) NOT NULL,
  "descricao" varchar(255) COLLATE "pg_catalog"."default",
  "quantidade" int4,
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of obraeliminadas
-- ----------------------------
INSERT INTO "public"."obraeliminadas" VALUES ('2019-01-10 20:40:01.468', 'dddd', 8, '544FF', 3);

-- ----------------------------
-- Table structure for registroobra
-- ----------------------------
DROP TABLE IF EXISTS "public"."registroobra";
CREATE TABLE "public"."registroobra" (
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" int4 NOT NULL,
  "dataregisto" timestamp(6) NOT NULL,
  "observacao" varchar(255) COLLATE "pg_catalog"."default",
  "formaaquisicao" int4 NOT NULL,
  "quantidade" int4
)
;

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
-- Table structure for revista
-- ----------------------------
DROP TABLE IF EXISTS "public"."revista";
CREATE TABLE "public"."revista" (
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "instituicao" varchar(255) COLLATE "pg_catalog"."default"
)
;

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

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS "public"."role";
CREATE TABLE "public"."role" (
  "role_id" int4 NOT NULL,
  "role" varchar(255) COLLATE "pg_catalog"."default",
  "qtdmaxobras" int4 DEFAULT 0
)
;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO "public"."role" VALUES (1, 'ADMIN', 0);
INSERT INTO "public"."role" VALUES (2, 'STUDENT', 6);
INSERT INTO "public"."role" VALUES (3, 'TEACHER', 4);

-- ----------------------------
-- Table structure for roleitem
-- ----------------------------
DROP TABLE IF EXISTS "public"."roleitem";
CREATE TABLE "public"."roleitem" (
  "item" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "idrole" int4 NOT NULL
)
;

-- ----------------------------
-- Table structure for tipoobra
-- ----------------------------
DROP TABLE IF EXISTS "public"."tipoobra";
CREATE TABLE "public"."tipoobra" (
  "idtipo" int4 NOT NULL DEFAULT nextval('tipoobra_idtipo_seq'::regclass),
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of tipoobra
-- ----------------------------
INSERT INTO "public"."tipoobra" VALUES (1, 'Livro');
INSERT INTO "public"."tipoobra" VALUES (2, 'Revista');
INSERT INTO "public"."tipoobra" VALUES (3, 'CD');

-- ----------------------------
-- Table structure for tiporequisicao
-- ----------------------------
DROP TABLE IF EXISTS "public"."tiporequisicao";
CREATE TABLE "public"."tiporequisicao" (
  "idtiporequisicao" int4 NOT NULL,
  "descricao" varchar(128) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON TABLE "public"."tiporequisicao" IS 'especifica os tipos de requisicoes disponiveis';

-- ----------------------------
-- Records of tiporequisicao
-- ----------------------------
INSERT INTO "public"."tiporequisicao" VALUES (1, 'HOME_REQUISITION');
INSERT INTO "public"."tiporequisicao" VALUES (2, 'INTERNAL_REQUISITION');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
  "user_id" int4 NOT NULL,
  "active" int4,
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "last_name" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user" VALUES (1, 1, 'admin@admin', 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3');
INSERT INTO "public"."user" VALUES (3, 1, 'student@unilurio.ac.mz', 'student', 'student', '289ffeb2a745ccf51ca89a297f47e382');
INSERT INTO "public"."user" VALUES (4, 1, 'teacher@unilurio.ac.mz', 'teacher', 'teacher', '289ffeb2a745ccf51ca89a297f47e382');
INSERT INTO "public"."user" VALUES (2, 1, 'fonseca@fonseca', 'Fonseca', 'Fonseca', 'e653d3954be6576488c5ce7a599869de');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_role";
CREATE TABLE "public"."user_role" (
  "user_id" int4 NOT NULL,
  "role_id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO "public"."user_role" VALUES (1, 1);
INSERT INTO "public"."user_role" VALUES (2, 2);
INSERT INTO "public"."user_role" VALUES (4, 3);
INSERT INTO "public"."user_role" VALUES (3, 2);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."areacientifica_idarea_seq"
OWNED BY "public"."areacientifica"."idarea";
SELECT setval('"public"."areacientifica_idarea_seq"', 2, false);
ALTER SEQUENCE "public"."estadodevolucao_idestadodevolucao_seq"
OWNED BY "public"."estadodevolucao"."idestadodevolucao";
SELECT setval('"public"."estadodevolucao_idestadodevolucao_seq"', 2, false);
ALTER SEQUENCE "public"."estadopedido_idestadopedido_seq"
OWNED BY "public"."estadopedido"."idestadopedido";
SELECT setval('"public"."estadopedido_idestadopedido_seq"', 2, true);
ALTER SEQUENCE "public"."estadorenovacao_idestadorenovacao_seq"
OWNED BY "public"."estadorenovacao"."idestadorenovacao";
SELECT setval('"public"."estadorenovacao_idestadorenovacao_seq"', 2, true);
ALTER SEQUENCE "public"."formatocd_idformato_seq"
OWNED BY "public"."formatocd"."idformato";
SELECT setval('"public"."formatocd_idformato_seq"', 2, false);
SELECT setval('"public"."hibernate_sequence"', 2, false);
ALTER SEQUENCE "public"."idioma_ididioma_seq"
OWNED BY "public"."idioma"."ididioma";
SELECT setval('"public"."idioma_ididioma_seq"', 2, false);
ALTER SEQUENCE "public"."tipoobra_idtipo_seq"
OWNED BY "public"."tipoobra"."idtipo";
SELECT setval('"public"."tipoobra_idtipo_seq"', 2, false);

-- ----------------------------
-- Primary Key structure for table areacientifica
-- ----------------------------
ALTER TABLE "public"."areacientifica" ADD CONSTRAINT "areacientifica_pkey" PRIMARY KEY ("idarea");

-- ----------------------------
-- Primary Key structure for table autor
-- ----------------------------
ALTER TABLE "public"."autor" ADD CONSTRAINT "autor_pk" PRIMARY KEY ("hashcode");

-- ----------------------------
-- Primary Key structure for table cd
-- ----------------------------
ALTER TABLE "public"."cd" ADD CONSTRAINT "cd_key" PRIMARY KEY ("cota");

-- ----------------------------
-- Primary Key structure for table config
-- ----------------------------
ALTER TABLE "public"."config" ADD CONSTRAINT "config_pkey" PRIMARY KEY ("nome");

-- ----------------------------
-- Primary Key structure for table emprestimo
-- ----------------------------
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "emprestimo_embeddepk" PRIMARY KEY ("utente", "cota", "dataentradapedido");

-- ----------------------------
-- Primary Key structure for table estadod
-- ----------------------------
ALTER TABLE "public"."estadod" ADD CONSTRAINT "estadod_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table estadodevolucao
-- ----------------------------
ALTER TABLE "public"."estadodevolucao" ADD CONSTRAINT "estadodevolucao_pkey" PRIMARY KEY ("idestadodevolucao");

-- ----------------------------
-- Primary Key structure for table estadomulta
-- ----------------------------
ALTER TABLE "public"."estadomulta" ADD CONSTRAINT "estadomulta_pkey" PRIMARY KEY ("idestadomulta");

-- ----------------------------
-- Primary Key structure for table estadopedido
-- ----------------------------
ALTER TABLE "public"."estadopedido" ADD CONSTRAINT "estadopedido_pkey" PRIMARY KEY ("idestadopedido");

-- ----------------------------
-- Primary Key structure for table estadorenovacao
-- ----------------------------
ALTER TABLE "public"."estadorenovacao" ADD CONSTRAINT "estadorenovacao_pkey" PRIMARY KEY ("idestadorenovacao");

-- ----------------------------
-- Primary Key structure for table formaaquisicao
-- ----------------------------
ALTER TABLE "public"."formaaquisicao" ADD CONSTRAINT "formaaquisicao_pkey" PRIMARY KEY ("formaaquisicao");

-- ----------------------------
-- Primary Key structure for table formatocd
-- ----------------------------
ALTER TABLE "public"."formatocd" ADD CONSTRAINT "formatocd_pkey" PRIMARY KEY ("idformato");

-- ----------------------------
-- Primary Key structure for table idioma
-- ----------------------------
ALTER TABLE "public"."idioma" ADD CONSTRAINT "idioma_pkey" PRIMARY KEY ("ididioma");

-- ----------------------------
-- Primary Key structure for table item
-- ----------------------------
ALTER TABLE "public"."item" ADD CONSTRAINT "item_pkey" PRIMARY KEY ("item_id");

-- ----------------------------
-- Primary Key structure for table item_role
-- ----------------------------
ALTER TABLE "public"."item_role" ADD CONSTRAINT "item_role_pkey" PRIMARY KEY ("item_id", "role_id");

-- ----------------------------
-- Primary Key structure for table livro
-- ----------------------------
ALTER TABLE "public"."livro" ADD CONSTRAINT "livro_key" PRIMARY KEY ("cota");

-- ----------------------------
-- Primary Key structure for table livrocd
-- ----------------------------
ALTER TABLE "public"."livrocd" ADD CONSTRAINT "livrocd_pkey" PRIMARY KEY ("cota");

-- ----------------------------
-- Primary Key structure for table multa
-- ----------------------------
ALTER TABLE "public"."multa" ADD CONSTRAINT "multa_pkey" PRIMARY KEY ("cota", "utente", "dataentradapedido");

-- ----------------------------
-- Primary Key structure for table obra
-- ----------------------------
ALTER TABLE "public"."obra" ADD CONSTRAINT "obra_key" PRIMARY KEY ("cota");

-- ----------------------------
-- Primary Key structure for table obra_autor
-- ----------------------------
ALTER TABLE "public"."obra_autor" ADD CONSTRAINT "obra_autor_key" PRIMARY KEY ("cota", "hashcode");

-- ----------------------------
-- Primary Key structure for table obraeliminadas
-- ----------------------------
ALTER TABLE "public"."obraeliminadas" ADD CONSTRAINT "obraeliminadas_pkey" PRIMARY KEY ("dataregisto", "cota");

-- ----------------------------
-- Primary Key structure for table registroobra
-- ----------------------------
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "registroobra_pk" PRIMARY KEY ("cota", "dataregisto");

-- ----------------------------
-- Primary Key structure for table revista
-- ----------------------------
ALTER TABLE "public"."revista" ADD CONSTRAINT "revista_key" PRIMARY KEY ("cota");

-- ----------------------------
-- Primary Key structure for table role
-- ----------------------------
ALTER TABLE "public"."role" ADD CONSTRAINT "role_pkey" PRIMARY KEY ("role_id");

-- ----------------------------
-- Primary Key structure for table roleitem
-- ----------------------------
ALTER TABLE "public"."roleitem" ADD CONSTRAINT "roleitem_pkey" PRIMARY KEY ("item", "idrole");

-- ----------------------------
-- Primary Key structure for table tipoobra
-- ----------------------------
ALTER TABLE "public"."tipoobra" ADD CONSTRAINT "tipoobra_pkey" PRIMARY KEY ("idtipo");

-- ----------------------------
-- Primary Key structure for table tiporequisicao
-- ----------------------------
ALTER TABLE "public"."tiporequisicao" ADD CONSTRAINT "tiporequisicao_pkey" PRIMARY KEY ("idtiporequisicao");

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD CONSTRAINT "user_pkey" PRIMARY KEY ("user_id");

-- ----------------------------
-- Primary Key structure for table user_role
-- ----------------------------
ALTER TABLE "public"."user_role" ADD CONSTRAINT "user_role_pkey" PRIMARY KEY ("user_id", "role_id");

-- ----------------------------
-- Foreign Keys structure for table cd
-- ----------------------------
ALTER TABLE "public"."cd" ADD CONSTRAINT "idcd" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table emprestimo
-- ----------------------------
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "bibliotecario" FOREIGN KEY ("bibliotecario") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "emprestimo_tiporequisicao_idtiporequisicao_fk" FOREIGN KEY ("idtiporequisicao") REFERENCES "public"."tiporequisicao" ("idtiporequisicao") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "idestadodevolucao" FOREIGN KEY ("estadodevolucao") REFERENCES "public"."estadodevolucao" ("idestadodevolucao") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "idestadopedido" FOREIGN KEY ("estadopedido") REFERENCES "public"."estadopedido" ("idestadopedido") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "idestadorenovacao" FOREIGN KEY ("estadorenovacao") REFERENCES "public"."estadorenovacao" ("idestadorenovacao") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "utente" FOREIGN KEY ("utente") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table item_role
-- ----------------------------
ALTER TABLE "public"."item_role" ADD CONSTRAINT "item_role_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "public"."item" ("item_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."item_role" ADD CONSTRAINT "item_role_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."role" ("role_id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table livro
-- ----------------------------
ALTER TABLE "public"."livro" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table livrocd
-- ----------------------------
ALTER TABLE "public"."livrocd" ADD CONSTRAINT "fkb022d77f183e4c5" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table multa
-- ----------------------------
ALTER TABLE "public"."multa" ADD CONSTRAINT "bibliotecario" FOREIGN KEY ("bibliotecario") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."multa" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."multa" ADD CONSTRAINT "fk636d5319b43d611" FOREIGN KEY ("idestadomulta") REFERENCES "public"."estadomulta" ("idestadomulta") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."multa" ADD CONSTRAINT "utente" FOREIGN KEY ("utente") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table obra
-- ----------------------------
ALTER TABLE "public"."obra" ADD CONSTRAINT "idarea" FOREIGN KEY ("idarea") REFERENCES "public"."areacientifica" ("idarea") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra" ADD CONSTRAINT "ididioma" FOREIGN KEY ("ididioma") REFERENCES "public"."idioma" ("ididioma") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra" ADD CONSTRAINT "idtipo" FOREIGN KEY ("idtipo") REFERENCES "public"."tipoobra" ("idtipo") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table obra_autor
-- ----------------------------
ALTER TABLE "public"."obra_autor" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra_autor" ADD CONSTRAINT "hashcode" FOREIGN KEY ("hashcode") REFERENCES "public"."autor" ("hashcode") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table obraeliminadas
-- ----------------------------
ALTER TABLE "public"."obraeliminadas" ADD CONSTRAINT "fkddb9eec730e86fad" FOREIGN KEY ("user_id") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obraeliminadas" ADD CONSTRAINT "fkddb9eec7f183e4c5" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table registroobra
-- ----------------------------
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "Cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "fkb046d1f59a0d88fe" FOREIGN KEY ("formaaquisicao") REFERENCES "public"."formaaquisicao" ("formaaquisicao") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "iduser" FOREIGN KEY ("user_id") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table revista
-- ----------------------------
ALTER TABLE "public"."revista" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table user_role
-- ----------------------------
ALTER TABLE "public"."user_role" ADD CONSTRAINT "fk859n2jvi8ivhui0rl0esws6o" FOREIGN KEY ("user_id") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_role" ADD CONSTRAINT "fka68196081fvovjhkek5m97n3y" FOREIGN KEY ("role_id") REFERENCES "public"."role" ("role_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
