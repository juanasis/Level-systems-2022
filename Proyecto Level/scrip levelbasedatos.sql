insert into usuario(email, nombre,apellido, nombre_usuario, password) values('mozo@gmail.com', 'Andres','Godoy', 'MOZO', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');
insert into usuario(email, nombre, apellido, nombre_usuario, password) values('hoelcroos18@gmail.com', 'Hoel','Gaitan' 'ADMIN', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');
insert into usuario(email, nombre, aperllido, nombre_usuario, password) values('juan.asis47@gmail.com', 'Juan', 'MOZO1','Asis', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');

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

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Coca Cola', '250ML', 250, 20, 0,'',1);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Fanta', '250ML', 250, 20, 0,'',1);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Pepsi', '250ML', 250, 20, 0,'',1); 

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Hamburguesa especial', 'Con carne', 750, 20, 0,'',2);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Sandwich de pollo', 'Con salsas', 750, 20, 0,'',2); 

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Lomito', 'Jugoso', 950, 20, 0,'',3);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Carne Asada', 'Papas sancochadas', 950, 20, 0,'',3); 

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