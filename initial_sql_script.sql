create database users_management;

\c users_management;

create table users_table
(
	id serial not null
		constraint users_table_pk
			primary key,
	name varchar(50),
	login varchar(50),
	email varchar(40)
);

-- criando uma tabela
create table users_table ( id serial not null constraint users_table_pk primary key, name varchar(50), login varchar(50), email varchar(40));
CREATE TABLE USUARIO ( ID serial not null constraint usuario_pk PRIMARY KEY, NOME VARCHAR, EMAIL VARCHAR, SENHA VARCHAR, CIDADE VARCHAR, SEXO VARCHAR, CATEGORIA VARCHAR, IDADE INTEGER);
CREATE TABLE COMENTARIOS ( ID serial not null constraint comentarios_pk PRIMARY KEY, IDUSER INTEGER, CONTEUDO VARCHAR, DATA VARCHAR, PLANO VARCHAR,foreign key (IDUSER) references USUARIO (ID));
CREATE TABLE RECOMENDACOES (ID serial not null constraint recomendacoes_pk PRIMARY KEY, IDUSER INTEGER,AULA VARCHAR,CATEGORIA VARCHAR,foreign key (IDUSER) references USUARIO (ID));
CREATE TABLE VISUALIZACOES (IDUSER INTEGER, AULA VARCHAR,foreign key (IDUSER) references USUARIO (ID));


