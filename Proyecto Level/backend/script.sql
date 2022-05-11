insert into usuario(email, nombre, nombre_usuario, password) values('anderson.bengolea@gmail.com', 'Ander', 'MOZO', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');
insert into usuario(email, nombre, nombre_usuario, password) values('pedro.bengolea@gmail.com', 'Pedro', 'ADMIN', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');
insert into usuario(email, nombre, nombre_usuario, password) values('jose.bengolea@gmail.com', 'Jose', 'MOZO1', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');

insert into rol(rol_nombre) values('ROLE_ADMIN');
insert into rol(rol_nombre) values('ROLE_MOZO');
insert into rol(rol_nombre) values('ROLE_CAJERO');
insert into rol(rol_nombre) values('ROLE_COCINERO');

insert into usuario_rol(usuario_id, rol_id) values(1,3);
insert into usuario_rol(usuario_id, rol_id) values(2,1);
insert into usuario_rol(usuario_id, rol_id) values(3,3);


insert into categoria(nombre) values('BEBIDAS');
insert into categoria(nombre) values('SANDWICHES');
insert into categoria(nombre) values('PLATOS DE FONDO');

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Coca Cola', '250ML', 2500, 20, 0,'',1);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Inka Kola', '250ML', 2500, 20, 0,'',1);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Pepsi', '250ML', 2500, 20, 0,'',1); 

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Hamburguesa especial', 'Con carne', 7500, 20, 0,'',2);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Sandwich de pollo', 'Con salsas', 7500, 20, 0,'',2); 

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Lomito', 'Jugoso', 9500, 20, 0,'',3);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Carne Asada', 'Papas sancochadas', 9500, 20, 0,'',3); 

insert into mesa (estado) values(1);
insert into mesa (estado) values(1);
insert into mesa (estado) values(1);
insert into mesa (estado) values(1);
insert into mesa (estado) values(1);
insert into mesa (estado) values(1);
insert into mesa (estado) values(1);
insert into mesa (estado) values(1);
insert into mesa (estado) values(1);
insert into mesa (estado) values(1);