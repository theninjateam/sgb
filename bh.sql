/*
 Navicat Premium Data Transfer

 Source Server         : Emerson
 Source Server Type    : PostgreSQL
 Source Server Version : 100005
 Source Host           : localhost:5432
 Source Catalog        : bhnew
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100005
 File Encoding         : 65001

 Date: 03/10/2018 17:34:58
*/


-- ----------------------------
-- Sequence structure for Tipo_de_Obra_IdTipoddd_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."Tipo_de_Obra_IdTipoddd_seq";
CREATE SEQUENCE "public"."Tipo_de_Obra_IdTipoddd_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 32767
START 1
CACHE 1;

-- ----------------------------
-- Table structure for Livro
-- ----------------------------
DROP TABLE IF EXISTS "public"."Livro";
CREATE TABLE "public"."Livro" (
  "Cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ISBN" varchar(255) COLLATE "pg_catalog"."default",
  "Editora" varchar(255) COLLATE "pg_catalog"."default",
  "Edicao" varchar(255) COLLATE "pg_catalog"."default",
  "CodigoBarra" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for Obra
-- ----------------------------
DROP TABLE IF EXISTS "public"."Obra";
CREATE TABLE "public"."Obra" (
  "Cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "Registro" int4,
  "NomeAutor" varchar(255) COLLATE "pg_catalog"."default",
  "TituloObra" varchar(255) COLLATE "pg_catalog"."default",
  "AreaCientifica" varchar(255) COLLATE "pg_catalog"."default",
  "LocalPublicacao" varchar(255) COLLATE "pg_catalog"."default",
  "DataPublicacao" date,
  "Idioma" varchar(255) COLLATE "pg_catalog"."default",
  "Quantidade" int4,
  "Tipo_Obra" int4
)
;

-- ----------------------------
-- Table structure for Registro_de_Obra
-- ----------------------------
DROP TABLE IF EXISTS "public"."Registro_de_Obra";
CREATE TABLE "public"."Registro_de_Obra" (
  "Cota" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "Nome_Bibliotecario" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "Data_Registro" date
)
;

-- ----------------------------
-- Table structure for Tipo_de_Obra
-- ----------------------------
DROP TABLE IF EXISTS "public"."Tipo_de_Obra";
CREATE TABLE "public"."Tipo_de_Obra" (
  "Descricao" varchar(300)[] COLLATE "pg_catalog"."default" NOT NULL,
  "IdTipo" int2 NOT NULL DEFAULT nextval('"Tipo_de_Obra_IdTipoddd_seq"'::regclass)
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."Tipo_de_Obra_IdTipoddd_seq"
OWNED BY "public"."Tipo_de_Obra"."IdTipo";
SELECT setval('"public"."Tipo_de_Obra_IdTipoddd_seq"', 2, false);

-- ----------------------------
-- Primary Key structure for table Livro
-- ----------------------------
ALTER TABLE "public"."Livro" ADD CONSTRAINT "Livro_pkey" PRIMARY KEY ("Cota");

-- ----------------------------
-- Primary Key structure for table Obra
-- ----------------------------
ALTER TABLE "public"."Obra" ADD CONSTRAINT "Obra_pkey" PRIMARY KEY ("Cota");

-- ----------------------------
-- Primary Key structure for table Registro_de_Obra
-- ----------------------------
ALTER TABLE "public"."Registro_de_Obra" ADD CONSTRAINT "Registro_de_Obra_pkey" PRIMARY KEY ("Cota");

-- ----------------------------
-- Primary Key structure for table Tipo_de_Obra
-- ----------------------------
ALTER TABLE "public"."Tipo_de_Obra" ADD CONSTRAINT "Tipo_de_Obra_pkey" PRIMARY KEY ("IdTipo");

-- ----------------------------
-- Foreign Keys structure for table Livro
-- ----------------------------
ALTER TABLE "public"."Livro" ADD CONSTRAINT "Cota" FOREIGN KEY ("Cota") REFERENCES "public"."Obra" ("Cota") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table Obra
-- ----------------------------
ALTER TABLE "public"."Obra" ADD CONSTRAINT "IdTipo" FOREIGN KEY ("Tipo_Obra") REFERENCES "public"."Tipo_de_Obra" ("IdTipo") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table Registro_de_Obra
-- ----------------------------
ALTER TABLE "public"."Registro_de_Obra" ADD CONSTRAINT "Cota" FOREIGN KEY ("Cota") REFERENCES "public"."Obra" ("Cota") ON DELETE CASCADE ON UPDATE CASCADE;
