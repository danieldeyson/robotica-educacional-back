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

CREATE TABLE USUARIO ( ID serial not null constraint USUARIO_pk PRIMARY KEY, NOME VARCHAR , EMAIL VARCHAR, SENHA VARCHAR, CIDADE VARCHAR, SEXO VARCHAR, CATEGORIA VARCHAR, IDADE INTEGER);
CREATE TABLE COMENTARIOS (ID serial not null constraint COMENTARIOS_pk PRIMARY KEY, IDUSER INTEGER,CATEGORIA VARCHAR, CONTEUDO VARCHAR, DATA VARCHAR, PLANO VARCHAR,foreign key (IDUSER) references USUARIO (ID));
CREATE TABLE RECOMENDACOES (ID serial not null constraint RECOMENDACOES_pk PRIMARY KEY, IDUSER INTEGER,AULA VARCHAR,CATEGORIA VARCHAR,foreign key (IDUSER) references USUARIO (ID));
CREATE TABLE VISUALIZACOES (IDUSER INTEGER, AULA VARCHAR, foreign key (IDUSER) references USUARIO (ID));
