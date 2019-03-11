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

 Date: 03/02/2019 16:18:27
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
INSERT INTO "public"."autor" VALUES ('4bbde07660e5eff90873642cfae9a8dd', 'SSSSSSSS');
INSERT INTO "public"."autor" VALUES ('0b757be795f46a5c37c52e7932e8effc', 'Americo Jose');
INSERT INTO "public"."autor" VALUES ('62b9aef3390384c34a495744df95e8dc', 'Joao Antonio');
INSERT INTO "public"."autor" VALUES ('ebc308f979e135f40483eec4b35feea1', 'WSS');
INSERT INTO "public"."autor" VALUES ('c6bc65d0f994ea20585b895298f9090c', 'emersondd ddd');
INSERT INTO "public"."autor" VALUES ('7a739ded8071c43747c98df60e88c7d1', '45555dd fff');

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

-- ----------------------------
-- Table structure for emprestimo
-- ----------------------------
DROP TABLE IF EXISTS "public"."emprestimo";
CREATE TABLE "public"."emprestimo" (
  "user_id" int4 NOT NULL,
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dataentrada" timestamp(6) NOT NULL,
  "estadopedido" int4,
  "dataaprovacao" date,
  "datadevolucao" date,
  "quantidade" int4,
  "comentario" varchar(5000) COLLATE "pg_catalog"."default",
  "estadodevolucao" int4,
  "estadorenovacao" int8,
  "datarenovacao" date,
  "datadevolucaorenovacao" date,
  "idtiporequisicao" int4 NOT NULL
)
;

-- ----------------------------
-- Records of emprestimo
-- ----------------------------
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:21:38.945', 5, NULL, NULL, 1, 'Pedido Cancelado Pelo Sistema, excedeu o limite maximo de levantamenteo', 1, NULL, NULL, NULL, 1);
INSERT INTO "public"."emprestimo" VALUES (2, '544FF', '2018-12-26 12:25:14.634', 5, NULL, NULL, 1, 'Pedido Cancelado Pelo Sistema, excedeu o limite maximo de levantamenteo', 1, NULL, NULL, NULL, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:17:46.228', 5, NULL, NULL, 1, 'Pedido Cancelado Pelo Sistema, excedeu o limite maximo de levantamenteo', 1, NULL, NULL, NULL, 1);
INSERT INTO "public"."emprestimo" VALUES (2, 'WW2', '2018-12-26 12:26:21.673', 4, NULL, NULL, 1, 'wq', 1, NULL, NULL, NULL, 1);
INSERT INTO "public"."emprestimo" VALUES (3, 'WW2', '2018-12-26 12:29:20.376', 4, NULL, NULL, 1, 'd', 1, NULL, NULL, NULL, 1);

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
INSERT INTO "public"."estadodevolucao" VALUES (2, 'nao devolvido');
INSERT INTO "public"."estadodevolucao" VALUES (3, 'devolvido');
INSERT INTO "public"."estadodevolucao" VALUES (1, 'Indeterminado');

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
INSERT INTO "public"."estadopedido" VALUES (1, 'PENDING');
INSERT INTO "public"."estadopedido" VALUES (3, 'ACCEPTED');
INSERT INTO "public"."estadopedido" VALUES (5, 'CANCELED');
INSERT INTO "public"."estadopedido" VALUES (2, 'REJECTED');
INSERT INTO "public"."estadopedido" VALUES (4, 'IN_QUEUE');

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
-- Table structure for obraConcurrenceController
-- ----------------------------
DROP TABLE IF EXISTS "public"."obraConcurrenceController";
CREATE TABLE "public"."obraConcurrenceController" (
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
-- Records of obraConcurrenceController
-- ----------------------------
INSERT INTO "public"."obraConcurrenceController" VALUES ('545AA', 785, 'Introducao a Estatistica', 1, 'Maputo', 1, 2, 2, NULL, 'digitalLibrary/cover/Introducao-a-Estatistica-Enfoque-Informatico-com-o-Pacote-Estatistico-SPSS-275390.jpg', 2017, NULL);
INSERT INTO "public"."obraConcurrenceController" VALUES ('589AF', 78988, 'Introducao a Geografia', 1, 'teste', 1, 120, 2, NULL, 'digitalLibrary/cover/geografia.jpg', 2018, NULL);
INSERT INTO "public"."obraConcurrenceController" VALUES ('531.4F', 1223, 'Introducao Fisica', 1, 'Pemba', 1, 4, 1, '', 'digitalLibrary/cover/fisica.jpg', 2001, NULL);
INSERT INTO "public"."obraConcurrenceController" VALUES ('77788', 555, 'Introducao a Quimica', 5, 'Maputo', 1, 4, 2, 'digitalLibrary/pdf/isbd-cons_2007-en.pdf', 'digitalLibrary/cover/quimica.jpg', 788, NULL);
INSERT INTO "public"."obraConcurrenceController" VALUES ('eee2', 1234, 'Introducao a Matematica', 1, 'Beira', 1, 4, 2, 'digitalLibrary/pdf/4.pdf', 'digitalLibrary/cover/Introducao-a-Matematica.jpg', 1298, NULL);
INSERT INTO "public"."obraConcurrenceController" VALUES ('WW2', 7777, 'Introducao a Biologia', 1, 'Nampula', 2, 5, 2, '', 'digitalLibrary/cover/bg4.jpg', 44444, NULL);
INSERT INTO "public"."obraConcurrenceController" VALUES ('544FF', 7887, 'Introducao a Matematica', 1, 'Pemba', 1, 8, 1, NULL, 'digitalLibrary/cover/Introducao-a-Matematica.jpg', 2018, NULL);

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
INSERT INTO "public"."role" VALUES (3, 'teacher', 4);
INSERT INTO "public"."role" VALUES (1, 'ADMIN', 0);
INSERT INTO "public"."role" VALUES (2, 'student', 6);

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
INSERT INTO "public"."tiporequisicao" VALUES (1, 'requisicao dominciliar');
INSERT INTO "public"."tiporequisicao" VALUES (2, 'requisicao ');

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
INSERT INTO "public"."user" VALUES (2, 1, 'fonseca@fonseca', 'fonseca', 'fonseca', 'e653d3954be6576488c5ce7a599869de');
INSERT INTO "public"."user" VALUES (3, 1, 'student@unilurio.ac.mz', 'student', 'student', '289ffeb2a745ccf51ca89a297f47e382');
INSERT INTO "public"."user" VALUES (4, 1, 'teacher@unilurio.ac.mz', 'teacher', 'teacher', '289ffeb2a745ccf51ca89a297f47e382');

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
SELECT setval('"public"."estadopedido_idestadopedido_seq"', 2, false);
ALTER SEQUENCE "public"."estadorenovacao_idestadorenovacao_seq"
OWNED BY "public"."estadorenovacao"."idestadorenovacao";
SELECT setval('"public"."estadorenovacao_idestadorenovacao_seq"', 2, false);
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
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "emprestimo_embeddepk" PRIMARY KEY ("user_id", "cota", "dataentrada");

-- ----------------------------
-- Primary Key structure for table estadodevolucao
-- ----------------------------
ALTER TABLE "public"."estadodevolucao" ADD CONSTRAINT "estadodevolucao_pkey" PRIMARY KEY ("idestadodevolucao");

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
-- Primary Key structure for table obraConcurrenceController
-- ----------------------------
ALTER TABLE "public"."obraConcurrenceController" ADD CONSTRAINT "obra_key" PRIMARY KEY ("cota");

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
ALTER TABLE "public"."cd" ADD CONSTRAINT "idcd" FOREIGN KEY ("cota") REFERENCES "public"."obraConcurrenceController" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table emprestimo
-- ----------------------------
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obraConcurrenceController" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "emprestimo_tiporequisicao_idtiporequisicao_fk" FOREIGN KEY ("idtiporequisicao") REFERENCES "public"."tiporequisicao" ("idtiporequisicao") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "idestadodevolucao" FOREIGN KEY ("estadodevolucao") REFERENCES "public"."estadodevolucao" ("idestadodevolucao") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "idestadopedido" FOREIGN KEY ("estadopedido") REFERENCES "public"."estadopedido" ("idestadopedido") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "idestadorenovacao" FOREIGN KEY ("estadorenovacao") REFERENCES "public"."estadorenovacao" ("idestadorenovacao") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."emprestimo" ADD CONSTRAINT "user_id" FOREIGN KEY ("user_id") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table item_role
-- ----------------------------
ALTER TABLE "public"."item_role" ADD CONSTRAINT "item_role_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "public"."item" ("item_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."item_role" ADD CONSTRAINT "item_role_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."role" ("role_id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table livro
-- ----------------------------
ALTER TABLE "public"."livro" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obraConcurrenceController" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table livrocd
-- ----------------------------
ALTER TABLE "public"."livrocd" ADD CONSTRAINT "fkb022d77f183e4c5" FOREIGN KEY ("cota") REFERENCES "public"."obraConcurrenceController" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table obraConcurrenceController
-- ----------------------------
ALTER TABLE "public"."obraConcurrenceController" ADD CONSTRAINT "idarea" FOREIGN KEY ("idarea") REFERENCES "public"."areacientifica" ("idarea") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obraConcurrenceController" ADD CONSTRAINT "ididioma" FOREIGN KEY ("ididioma") REFERENCES "public"."idioma" ("ididioma") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obraConcurrenceController" ADD CONSTRAINT "idtipo" FOREIGN KEY ("idtipo") REFERENCES "public"."tipoobra" ("idtipo") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table obra_autor
-- ----------------------------
ALTER TABLE "public"."obra_autor" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obraConcurrenceController" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra_autor" ADD CONSTRAINT "hashcode" FOREIGN KEY ("hashcode") REFERENCES "public"."autor" ("hashcode") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table obraeliminadas
-- ----------------------------
ALTER TABLE "public"."obraeliminadas" ADD CONSTRAINT "fkddb9eec730e86fad" FOREIGN KEY ("user_id") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obraeliminadas" ADD CONSTRAINT "fkddb9eec7f183e4c5" FOREIGN KEY ("cota") REFERENCES "public"."obraConcurrenceController" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table registroobra
-- ----------------------------
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "Cota" FOREIGN KEY ("cota") REFERENCES "public"."obraConcurrenceController" ("cota") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "fkb046d1f59a0d88fe" FOREIGN KEY ("formaaquisicao") REFERENCES "public"."formaaquisicao" ("formaaquisicao") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "iduser" FOREIGN KEY ("user_id") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table revista
-- ----------------------------
ALTER TABLE "public"."revista" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obraConcurrenceController" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table user_role
-- ----------------------------
ALTER TABLE "public"."user_role" ADD CONSTRAINT "fk859n2jvi8ivhui0rl0esws6o" FOREIGN KEY ("user_id") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_role" ADD CONSTRAINT "fka68196081fvovjhkek5m97n3y" FOREIGN KEY ("role_id") REFERENCES "public"."role" ("role_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
