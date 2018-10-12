create table if not exists areacientifca
(
	"idAreaCientifica" smallint not null
		constraint "AreaCientifca_pkey"
			primary key,
	titulo varchar(255)
)
;

alter table areacientifca owner to postgres
;

create table if not exists formatocd
(
	idformato smallserial not null
		constraint formatocd_pkey
			primary key,
	formato varchar(255)
)
;

alter table formatocd owner to postgres
;

create table if not exists item
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

create table if not exists role
(
	role_id integer not null
		constraint role_pkey
			primary key,
	role varchar(255)
)
;

alter table role owner to postgres
;

create table if not exists item_role
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

create table if not exists roleitem
(
	item varchar(255) not null,
	idrole integer not null,
	constraint roleitem_pkey
		primary key (item, idrole)
)
;

alter table roleitem owner to postgres
;

create table if not exists tipoobra
(
	descricao varchar(300) not null,
	idtipo smallserial not null
		constraint "Tipo_de_Obra_pkey"
			primary key
)
;

alter table tipoobra owner to postgres
;

create table if not exists obra
(
	cota varchar(255) not null
		constraint "Obra_pkey"
			primary key,
	registro integer,
	autores varchar(255),
	titulo varchar(255),
	areacientifica smallint
		constraint "idAreacientifca"
			references areacientifca,
	localpublicacao varchar(255),
	datapublicacao date,
	idioma varchar(255),
	quantidade integer,
	tipoobra integer
		constraint "idTipo"
			references tipoobra
)
;

alter table obra owner to postgres
;

create table if not exists cd
(
	idcd varchar(255) not null
		constraint cd_pkey
			primary key
		constraint idcd
			references obra,
	comprimento varchar(255) not null
)
;

alter table cd owner to postgres
;

create table if not exists formato_cd
(
	idcd varchar(255) not null
		constraint idcd
			references cd,
	idformato smallint not null
		constraint idformato
			references formatocd,
	constraint formato_cd_pkey
		primary key (idcd, idformato)
)
;

alter table formato_cd owner to postgres
;

create table if not exists livro
(
	cota varchar(255) not null
		constraint "Livro_pkey"
			primary key
		constraint cota
			references obra
				on update cascade on delete cascade,
	isbn varchar(255),
	editora varchar(255),
	edicao varchar(255),
	codigobarra varchar(255)
)
;

alter table livro owner to postgres
;

create table if not exists registroobra
(
	cota varchar(255) not null
		constraint "Registro_de_Obra_pkey"
			primary key
		constraint "Cota"
			references obra
				on update cascade on delete cascade,
	bibliotecario varchar(255) not null,
	registro date
)
;

alter table registroobra owner to postgres
;

create table if not exists revista
(
	cota varchar(255) not null
		constraint revista_pkey
			primary key
		constraint cota
			references obra
)
;

alter table revista owner to postgres
;

create table if not exists topico
(
	idtopico smallserial not null
		constraint topico_pkey
			primary key,
	titulo varchar(255),
	autores varchar(255)
)
;

alter table topico owner to postgres
;

create table if not exists topico_revista
(
	idtopico smallint not null
		constraint idtopico
			references topico,
	idrevista varchar(16) not null
		constraint idrevista
			references revista,
	constraint topico_revista_pkey
		primary key (idtopico, idrevista)
)
;

alter table topico_revista owner to postgres
;

create table if not exists "user"
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

create table if not exists user_role
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

