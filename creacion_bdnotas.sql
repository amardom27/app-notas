-- Script creación de la base de datos
-- BDnotas
drop database if exists BDnotas;
create database BDnotas;

use BDnotas;

-- Usuarios
drop table if exists usuarios;
create table usuarios(
	idUsuario int auto_increment,
    nomUsuario varchar(15) not null unique,
    passUsuario varchar(15) not null,
    constraint pk_usuarios primary key (idUsuario)
);

-- Notas
drop table if exists notas;
create table notas(
	idNota int auto_increment,
    titulo varchar(20),
    descripcion varchar(80),
    fechaCreacion date,
    idUsuario int,
    constraint pk_notas primary key (idNota),
    constraint fK_notas_usuarios foreign key (idUsuario) 
		references usuarios(idUsuario) on update cascade on delete no action
);

-- Categorias
drop table if exists categorias;
create table categorias(
	idCategoria int auto_increment,
    nombre varchar(20) unique,
    constraint pk_categorias primary key (idCategoria)
);

-- DetNotasCategorias
drop table if exists detNotasCategorias;
create table detNotasCategorias(
	idNota int,
    idCategoria int,
    constraint pk_detNotasCategorias primary key (idNota, idCategoria),
    constraint fk_detNotasCategorias_notas foreign key (idNota)
		references notas(idNota) on update cascade on delete no action,
	constraint fk_detNotasCategorias_categorias foreign key (idCategoria)
		references categorias(idCategoria) on update cascade on delete no action
);




