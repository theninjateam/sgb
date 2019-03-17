drop schema public cascade;
create schema public;

create table areacientifica
(
	idarea serial not null
		constraint areacientifica_pkey
			primary key,
	descricao varchar(255)
)
;

alter table areacientifica owner to postgres
;

create table autor
(
	hashcode varchar(255) not null
		constraint autor_pk
			primary key,
	nome varchar(255)
)
;

alter table autor owner to postgres
;

create table estadodevolucao
(
	idestadodevolucao serial not null
		constraint estadodevolucao_pkey
			primary key,
	descricao varchar(255)
)
;

alter table estadodevolucao owner to postgres
;

create table estadopedido
(
	idestadopedido serial not null
		constraint estadopedido_pkey
			primary key,
	descricao varchar(255)
)
;

alter table estadopedido owner to postgres
;

create table estadorenovacao
(
	idestadorenovacao bigserial not null
		constraint estadorenovacao_pkey
			primary key,
	descricao varchar(255)
)
;

alter table estadorenovacao owner to postgres
;

create table formatocd
(
	idformato serial not null
		constraint formatocd_pkey
			primary key,
	descricao varchar(255)
)
;

alter table formatocd owner to postgres
;

create table idioma
(
	ididioma serial not null
		constraint idioma_pkey
			primary key,
	descricao varchar(255)
)
;

alter table idioma owner to postgres
;

create table item
(
	item_id integer not null
		constraint item_pkey
			primary key,
	descricao varchar(255),
	item varchar(255) not null
)
;

alter table item owner to postgres
;

create table role
(
	role_id integer not null
		constraint role_pkey
			primary key,
	role varchar(255),
	qtdmaxobras integer default 0
)
;

alter table role owner to postgres
;

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
)
;

alter table item_role owner to postgres
;

create table roleitem
(
	item varchar(255) not null,
	idrole integer not null,
	constraint roleitem_pkey
		primary key (item, idrole)
)
;

alter table roleitem owner to postgres
;

create table tipoobra
(
	idtipo serial not null
		constraint tipoobra_pkey
			primary key,
	descricao varchar(255)
)
;

alter table tipoobra owner to postgres
;

create table obraConcurrenceController
(
	cota varchar(255) not null
		constraint obra_key
			primary key,
	registro integer,
	titulo varchar(255),
	idarea integer
		constraint idarea
			references areacientifica,
	localpublicacao varchar(255),
	ididioma integer
		constraint ididioma
			references idioma,
	quantidade integer,
	idtipo integer
		constraint idtipo
			references tipoobra,
	pathpdf varchar(255),
	pathcapa varchar(255),
	anopublicacao integer,
	domiciliarqueue bytea
)
;

alter table obraConcurrenceController owner to postgres
;

create table cd
(
	cota varchar(255) not null
		constraint cd_key
			primary key
		constraint idcd
			references obraConcurrenceController,
	descricao varchar(255)
)
;

alter table cd owner to postgres
;

create table livro
(
	cota varchar(255) not null
		constraint livro_key
			primary key
		constraint cota
			references obraConcurrenceController,
	isbn varchar(255),
	editora varchar(255),
	edicao integer,
	codigobarra varchar(255),
	volume integer
)
;

alter table livro owner to postgres
;

create table livrocd
(
	cota varchar(255) not null
		constraint livrocd_pkey
			primary key
		constraint fkb022d77f183e4c5
			references obraConcurrenceController,
	codigobarra varchar(255),
	descricaocd varchar(255),
	edicao varchar(255),
	editora varchar(255),
	isbn varchar(255),
	volume integer
)
;

alter table livrocd owner to postgres
;

create table obra_autor
(
	hashcode varchar(255) not null
		constraint hashcode
			references autor,
	cota varchar(255) not null
		constraint cota
			references obraConcurrenceController,
	constraint obra_autor_key
		primary key (cota, hashcode)
)
;

alter table obra_autor owner to postgres
;

create table revista
(
	cota varchar(255) not null
		constraint revista_key
			primary key
		constraint cota
			references obraConcurrenceController,
	instituicao varchar(255)
)
;

alter table revista owner to postgres
;

create table "user"
(
	user_id integer not null
		constraint user_pkey
			primary key,
	active integer,
	email varchar(255),
	last_name varchar(255),
	name varchar(255),
	password varchar(255)
)
;

alter table "user" owner to postgres
;

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
)
;

alter table user_role owner to postgres
;

create table formaaquisicao
(
	formaaquisicao integer not null
		constraint formaaquisicao_pkey
			primary key,
	descricao varchar(255)
)
;

alter table formaaquisicao owner to postgres
;

create table registroobra
(
	cota varchar(255) not null
		constraint "Cota"
			references obraConcurrenceController
				on update cascade on delete cascade,
	user_id integer not null
		constraint iduser
			references "user",
	dataregisto timestamp not null,
	observacao varchar(255),
	formaaquisicao integer not null
		constraint fkb046d1f59a0d88fe
			references formaaquisicao,
	quantidade integer,
	constraint registroobra_pk
		primary key (cota, dataregisto)
)
;

alter table registroobra owner to postgres
;

create table obraeliminadas
(
	dataregisto timestamp not null,
	descricao varchar(255),
	quantidade integer,
	cota varchar(255) not null
		constraint fkddb9eec7f183e4c5
			references obraConcurrenceController,
	user_id integer not null
		constraint fkddb9eec730e86fad
			references "user",
	constraint obraeliminadas_pkey
		primary key (dataregisto, cota)
)
;

alter table obraeliminadas owner to postgres
;

create table config
(
	nome varchar(128) not null
		constraint config_pkey
			primary key,
	descricao varchar(255),
	valor varchar(128)
)
;

alter table config owner to postgres
;

create table tiporequisicao
(
	idtiporequisicao integer not null
		constraint tiporequisicao_pkey
			primary key,
	descricao varchar(128) not null
)
;

comment on table tiporequisicao is 'especifica os tipos de requisicoes disponiveis'
;

alter table tiporequisicao owner to postgres
;

create table emprestimo
(
	user_id integer not null
		constraint user_id
			references "user",
	cota varchar(255) not null
		constraint cota
			references obraConcurrenceController,
	dataentrada timestamp not null,
	estadopedido integer
		constraint idestadopedido
			references estadopedido,
	dataaprovacao date,
	datadevolucao date,
	quantidade integer,
	comentario varchar(5000),
	estadodevolucao integer
		constraint idestadodevolucao
			references estadodevolucao,
	estadorenovacao bigint
		constraint idestadorenovacao
			references estadorenovacao,
	datarenovacao date,
	datadevolucaorenovacao date,
	idtiporequisicao integer not null
		constraint emprestimo_tiporequisicao_idtiporequisicao_fk
			references tiporequisicao,
	constraint emprestimo_embeddepk
		primary key (user_id, cota, dataentrada)
)
;

alter table emprestimo owner to postgres
;


