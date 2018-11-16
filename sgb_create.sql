drop schema public cascade;
create schema public;

create sequence hibernate_sequence;

alter sequence hibernate_sequence
  owner to postgres;

create table areacientifica
(
  idarea    serial not null
    constraint areacientifica_pkey
    primary key,
  descricao varchar(255)
);

alter table areacientifica
  owner to postgres;

create table autor
(
  hashcode varchar(255) not null
    constraint autor_pk
    primary key,
  nome     varchar(255)
);

alter table autor
  owner to postgres;

create table estadodevolucao
(
  idestadodevolucao serial not null
    constraint estadodevolucao_pkey
    primary key,
  descricao         varchar(255)
);

alter table estadodevolucao
  owner to postgres;

create table estadopedido
(
  idestadopedido serial not null
    constraint estadopedido_pkey
    primary key,
  descricao      varchar(255)
);

alter table estadopedido
  owner to postgres;

create table formatocd
(
  idformato serial not null
    constraint formatocd_pkey
    primary key,
  descricao varchar(255)
);

alter table formatocd
  owner to postgres;

create table idioma
(
  ididioma  serial not null
    constraint idioma_pkey
    primary key,
  descricao varchar(255)
);

alter table idioma
  owner to postgres;

create table item
(
  item_id   integer      not null
    constraint item_pkey
    primary key,
  descricao varchar(255),
  item      varchar(255) not null
);

alter table item
  owner to postgres;

create table role
(
  role_id     integer not null
    constraint role_pkey
    primary key,
  role        varchar(255),
  qtdmaxobras integer default 0
);

alter table role
  owner to postgres;

create table item_role
(
  item_id integer not null
    constraint item_role_item_id_fkey
    references item,
  role_id integer not null
    constraint item_role_role_id_fkey
    references role,
  constraint item_role_pkey
  primary key (item_id, role_id)
);

alter table item_role
  owner to postgres;

create table roleitem
(
  item   varchar(255) not null,
  idrole integer      not null,
  constraint roleitem_pkey
  primary key (item, idrole)
);

alter table roleitem
  owner to postgres;

create table tipoobra
(
  idtipo    serial not null
    constraint tipoobra_pkey
    primary key,
  descricao varchar(255)
);

alter table tipoobra
  owner to postgres;

create table obra
(
  cota            varchar(255) not null
    constraint obra_key
    primary key,
  registro        integer,
  titulo          varchar(255),
  idarea          integer
    constraint idarea
    references areacientifica,
  localpublicacao varchar(255),
  ididioma        integer
    constraint ididioma
    references idioma,
  quantidade      integer,
  idtipo          integer
    constraint idtipo
    references tipoobra,
  pathpdf         varchar(255),
  pathcapa        varchar(255),
  anopublicacao   integer
);

alter table obra
  owner to postgres;

create table cd
(
  cota      varchar(255) not null
    constraint cd_key
    primary key
    constraint idcd
    references obra,
  descricao varchar(255)
);

alter table cd
  owner to postgres;

create table livro
(
  cota        varchar(255) not null
    constraint livro_key
    primary key
    constraint cota
    references obra,
  isbn        varchar(255),
  editora     varchar(255),
  edicao      varchar(255),
  codigobarra varchar(255)
);

alter table livro
  owner to postgres;

create table obra_autor
(
  hashcode varchar(255) not null
    constraint hashcode
    references autor,
  cota     varchar(255) not null
    constraint cota
    references obra,
  constraint obra_autor_key
  primary key (cota, hashcode)
);

alter table obra_autor
  owner to postgres;

create table revista
(
  cota        varchar(255) not null
    constraint revista_key
    primary key
    constraint cota
    references obra,
  instituicao varchar(255)
);

alter table revista
  owner to postgres;

create table "user"
(
  user_id   integer not null
    constraint user_pkey
    primary key,
  active    integer,
  email     varchar(255),
  last_name varchar(255),
  name      varchar(255),
  password  varchar(255)
);

alter table "user"
  owner to postgres;

create table emprestimo
(
  user_id         integer      not null
    constraint user_id
    references "user",
  cota            varchar(255) not null
    constraint cota
    references obra,
  dataentrada     date,
  estadopedido    integer
    constraint idestadopedido
    references estadopedido,
  dataaprovacao   date,
  datadevolucao   date,
  quantidade      integer,
  comentario      varchar(5000),
  estadodevolucao integer
    constraint idestadodevolucao
    references estadodevolucao,
  constraint "Emprestimo_pkey"
  primary key (user_id, cota)
);

alter table emprestimo
  owner to postgres;

create table registroobra
(
  cota        varchar(255) not null
    constraint "Registro_de_Obra_pkey"
    primary key
    constraint "Cota"
    references obra
    on update cascade on delete cascade,
  iduser      integer      not null
    constraint iduser
    references "user",
  dataregisto date         not null
);

alter table registroobra
  owner to postgres;

create table user_role
(
  user_id integer not null
    constraint fk859n2jvi8ivhui0rl0esws6o
    references "user",
  role_id integer not null
    constraint fka68196081fvovjhkek5m97n3y
    references role,
  constraint user_role_pkey
  primary key (user_id, role_id)
);

alter table user_role
  owner to postgres;


