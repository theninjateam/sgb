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

 Date: 27/03/2019 10:17:24
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
-- Table structure for autor
-- ----------------------------
DROP TABLE IF EXISTS "public"."autor";
CREATE TABLE "public"."autor" (
  "hashcode" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "nome" varchar(255) COLLATE "pg_catalog"."default"
)
;

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
-- Table structure for estadomulta
-- ----------------------------
DROP TABLE IF EXISTS "public"."estadomulta";
CREATE TABLE "public"."estadomulta" (
  "idestadomulta" int4 NOT NULL,
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

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
-- Table structure for formatocd
-- ----------------------------
DROP TABLE IF EXISTS "public"."formatocd";
CREATE TABLE "public"."formatocd" (
  "idformato" int4 NOT NULL DEFAULT nextval('formatocd_idformato_seq'::regclass),
  "descricao" varchar(255) COLLATE "pg_catalog"."default"
)
;

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
-- Table structure for obra_autor
-- ----------------------------
DROP TABLE IF EXISTS "public"."obra_autor";
CREATE TABLE "public"."obra_autor" (
  "hashcode" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

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
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_role";
CREATE TABLE "public"."user_role" (
  "user_id" int4 NOT NULL,
  "role_id" int4 NOT NULL
)
;

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
