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

 Date: 17/10/2018 14:26:26
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
-- Sequence structure for autor_idautor_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."autor_idautor_seq";
CREATE SEQUENCE "public"."autor_idautor_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
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
  "idautor" int4 NOT NULL DEFAULT nextval('autor_idautor_seq'::regclass),
  "nome" varchar(255) COLLATE "pg_catalog"."default",
  "apelido" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for cd
-- ----------------------------
DROP TABLE IF EXISTS "public"."cd";
CREATE TABLE "public"."cd" (
  "idcd" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

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
INSERT INTO "public"."idioma" VALUES (2, 'Portugues');
INSERT INTO "public"."idioma" VALUES (3, 'Ingles');
INSERT INTO "public"."idioma" VALUES (4, 'Espanhol');

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
  "edicao" varchar(255) COLLATE "pg_catalog"."default",
  "codigobarra" varchar(255) COLLATE "pg_catalog"."default"
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
  "datapublicacao" date,
  "ididioma" int4,
  "quantidade" int4,
  "idtipo" int4
)
;

-- ----------------------------
-- Table structure for obra_autor
-- ----------------------------
DROP TABLE IF EXISTS "public"."obra_autor";
CREATE TABLE "public"."obra_autor" (
  "idautor" int4 NOT NULL,
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for registroobra
-- ----------------------------
DROP TABLE IF EXISTS "public"."registroobra";
CREATE TABLE "public"."registroobra" (
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "bibliotecario" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "registro" date
)
;

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
  "role" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO "public"."role" VALUES (1, 'ADMIN');

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
INSERT INTO "public"."user_role" VALUES (2, 1);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."areacientifica_idarea_seq"
OWNED BY "public"."areacientifica"."idarea";
SELECT setval('"public"."areacientifica_idarea_seq"', 7, true);
ALTER SEQUENCE "public"."autor_idautor_seq"
OWNED BY "public"."autor"."idautor";
SELECT setval('"public"."autor_idautor_seq"', 3, false);
ALTER SEQUENCE "public"."formatocd_idformato_seq"
OWNED BY "public"."formatocd"."idformato";
SELECT setval('"public"."formatocd_idformato_seq"', 6, true);
SELECT setval('"public"."hibernate_sequence"', 2, false);
ALTER SEQUENCE "public"."idioma_ididioma_seq"
OWNED BY "public"."idioma"."ididioma";
SELECT setval('"public"."idioma_ididioma_seq"', 5, true);
ALTER SEQUENCE "public"."tipoobra_idtipo_seq"
OWNED BY "public"."tipoobra"."idtipo";
SELECT setval('"public"."tipoobra_idtipo_seq"', 5, true);

-- ----------------------------
-- Primary Key structure for table areacientifica
-- ----------------------------
ALTER TABLE "public"."areacientifica" ADD CONSTRAINT "areacientifica_pkey" PRIMARY KEY ("idarea");

-- ----------------------------
-- Primary Key structure for table autor
-- ----------------------------
ALTER TABLE "public"."autor" ADD CONSTRAINT "autor_pkey" PRIMARY KEY ("idautor");

-- ----------------------------
-- Primary Key structure for table cd
-- ----------------------------
ALTER TABLE "public"."cd" ADD CONSTRAINT "cd_key" PRIMARY KEY ("idcd");

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
-- Primary Key structure for table obra
-- ----------------------------
ALTER TABLE "public"."obra" ADD CONSTRAINT "obra_key" PRIMARY KEY ("cota");

-- ----------------------------
-- Primary Key structure for table obra_autor
-- ----------------------------
ALTER TABLE "public"."obra_autor" ADD CONSTRAINT "obra_autor_key" PRIMARY KEY ("cota", "idautor");

-- ----------------------------
-- Primary Key structure for table registroobra
-- ----------------------------
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "Registro_de_Obra_pkey" PRIMARY KEY ("cota");

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
ALTER TABLE "public"."cd" ADD CONSTRAINT "idcd" FOREIGN KEY ("idcd") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

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
-- Foreign Keys structure for table obra
-- ----------------------------
ALTER TABLE "public"."obra" ADD CONSTRAINT "fk33f34239591205" FOREIGN KEY ("cota") REFERENCES "public"."livro" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra" ADD CONSTRAINT "fk33f342ced73e29" FOREIGN KEY ("cota") REFERENCES "public"."revista" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra" ADD CONSTRAINT "idarea" FOREIGN KEY ("idarea") REFERENCES "public"."areacientifica" ("idarea") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra" ADD CONSTRAINT "ididioma" FOREIGN KEY ("ididioma") REFERENCES "public"."idioma" ("ididioma") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra" ADD CONSTRAINT "idtipo" FOREIGN KEY ("idtipo") REFERENCES "public"."tipoobra" ("idtipo") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table obra_autor
-- ----------------------------
ALTER TABLE "public"."obra_autor" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."obra_autor" ADD CONSTRAINT "idautor" FOREIGN KEY ("idautor") REFERENCES "public"."autor" ("idautor") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table registroobra
-- ----------------------------
ALTER TABLE "public"."registroobra" ADD CONSTRAINT "Cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table revista
-- ----------------------------
ALTER TABLE "public"."revista" ADD CONSTRAINT "cota" FOREIGN KEY ("cota") REFERENCES "public"."obra" ("cota") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table user_role
-- ----------------------------
ALTER TABLE "public"."user_role" ADD CONSTRAINT "fk859n2jvi8ivhui0rl0esws6o" FOREIGN KEY ("user_id") REFERENCES "public"."user" ("user_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."user_role" ADD CONSTRAINT "fka68196081fvovjhkek5m97n3y" FOREIGN KEY ("role_id") REFERENCES "public"."role" ("role_id") ON DELETE NO ACTION ON UPDATE NO ACTION;
