insert into usuario(email, nombre, nombre_usuario, password) values('andres@gmail.com', 'Andrez', 'MOZO', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');
insert into usuario(email, nombre, nombre_usuario, password) values('pedro@gmail.com', 'Pedro', 'ADMIN', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');
insert into usuario(email, nombre, nombre_usuario, password) values('jose.soria@gmail.com', 'Jose', 'MOZO1', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');

insert into rol(rol_nombre) values('ROLE_ADMIN');
insert into rol(rol_nombre) values('ROLE_MOZO');
insert into rol(rol_nombre) values('ROLE_CAJERO');
insert into rol(rol_nombre) values('ROLE_COCINERO');

insert into usuario_rol(usuario_id, rol_id) values(1,2);
insert into usuario_rol(usuario_id, rol_id) values(2,1);
insert into usuario_rol(usuario_id, rol_id) values(3,2);


insert into categoria(nombre) values('BEBIDAS');
insert into categoria(nombre) values('SANDWICHES');
insert into categoria(nombre) values('PLATOS DE FONDO');

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Coca Cola', '250ML', 250, 20, 0,'',1);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Fanta', '250ML', 250, 20, 0,'',1);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Pepsi', '250ML', 250, 20, 0,'',1); 

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Hamburguesa especial', 'Con carne', 750, 20, 0,'',2);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Sandwich de pollo', 'Con salsas', 750, 20, 0,'',2); 

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Lomito', 'Jugoso', 950, 20, 0,'',3);
INSERT INTO producto(categoria_id,nombre, descripcion, precio,cantidad, estado, imgPath)values(3,'Carne Asada', 'Papas sancochadas', 950, 20, 0,''); 

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

insert into permiso(nombre) values('LISTAR_PRODUCTOS');
insert into permiso(nombre) values('LISTAR_PEDIDOS');
insert into permiso(nombre) values('ACTUALIZAR_PRODUCTO');
insert into permiso(nombre) values('ELIMINAR_PRODUCTO');
insert into permiso(nombre) values('LISTAR_USUARIOS');
insert into permiso(nombre) values('CREAR_USUARIO');
insert into permiso(nombre) values('ACTUALIZAR_USUARIO');
insert into permiso(nombre) values('ELIMINAR_USUARIO');
insert into permiso(nombre) values('LISTAR_ROLES');
insert into permiso(nombre) values('CREAR_ROL');
insert into permiso(nombre) values('ACTUALIZAR_ROL');
insert into permiso(nombre) values('ELIMINAR_ROL');
insert into permiso(nombre) values('CREAR_PEDIDO');
insert into permiso(nombre) values('ACTUALIZAR_PEDIDO');
insert into permiso(nombre) values('LISTAR_MESAS');
insert into permiso(nombre) values('LISTAR_PEDIDOS_MOZO');
insert into permiso(nombre) values('CAJA');
insert into permiso(nombre) values('ADMINISTRACION');
insert into permiso(nombre) values('MESAS');

insert into permisos_roles(id, permiso_id) values(1,15);
insert into permisos_roles(id, permiso_id) values(1,14);
insert into permisos_roles(id, permiso_id) values(1,18);