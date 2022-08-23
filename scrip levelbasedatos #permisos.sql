insert into usuario(email, nombre, nombre_usuario, password) values('ricardo@gmail.com', 'Ricardo', 'MOZO', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');
insert into usuario(email, nombre, nombre_usuario, password) values('levelsystems23@gmail.com', 'Martin', 'ADMIN', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');
insert into usuario(email, nombre, nombre_usuario, password) values('pablo@gmail.com', 'Pablo', 'MOZO1', '$2a$10$dVVPhvHQvc4T/iE7MW.QTebeTudxKoonuIFcAXJCsT05cUMDWbA3e');

insert into rol(rol_nombre) values('ROLE_ADMIN');
insert into rol(rol_nombre) values('ROLE_MOZO');
insert into rol(rol_nombre) values('ROLE_CAJERO');
insert into rol(rol_nombre) values('ROLE_COCINERO');

insert into usuario_rol(usuario_id, rol_id) values(1,2);
insert into usuario_rol(usuario_id, rol_id) values(2,1);
insert into usuario_rol(usuario_id, rol_id) values(3,2);


insert into categoria(nombre) values('BEBIDAS');
insert into categoria(nombre) values('SANDWICHES');
insert into categoria(nombre) values('PLATO PRINCIPAL');
insert into categoria(nombre) values('DESAYUNOS');
insert into categoria(nombre) values('PASTAS');
insert into categoria(nombre) values('PIZZAS');
insert into categoria(nombre) values('GUARNICION');
insert into categoria(nombre) values('POSTRES');

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Coca Cola', '250ML', 250, 20, 0,'https://www.bembos.com.pe/_ipx/q_85,w_290,f_webp/https://d31npzejelj8v1.cloudfront.net/media/catalog/product/c/o/coca-cola-sin-azucar-450ml_1.png',1);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Sprite', '250ML', 250, 20, 0,'https://www.bembos.com.pe/_ipx/q_85,w_290,f_webp/https://d31npzejelj8v1.cloudfront.net/media/catalog/product/s/p/sprite-con-azucar_1_1_1.png',1);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Fanta', '250ML', 250, 20, 0,'https://pngimg.com/uploads/fanta/fanta_PNG46.png',1);

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Hamburguesa especial', 'Con carne', 750, 20, 0,'https://www.bembos.com.pe/_ipx/q_85,w_290,f_webp/https://d31npzejelj8v1.cloudfront.net/media/catalog/product/h/a/hamburguesa-bembos-churrita_1_1_1.jpg',2);
INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Sandwich de pollo', 'Con salsas', 750, 20, 0,'https://www.bembos.com.pe/_ipx/q_85,w_290,f_webp/https://d31npzejelj8v1.cloudfront.net/media/catalog/product/t/a/tartara-base.jpg',2); 

INSERT INTO producto(nombre, descripcion, precio,cantidad, estado, imgPath,categoria_id)values('Lomito', 'Jugoso', 950, 20, 0,'https://betos.com.ar/wp-content/uploads/2019/08/Lomo.png',3);
INSERT INTO producto(categoria_id,nombre, descripcion, precio,cantidad, estado, imgPath)values(3,'Carne Asada', 'Papas sancochadas', 950, 20, 0,'https://tofuu.getjusto.com/orioneat-prod-resized/5McsE4PJCRWX3bTQb-1200-1200.webp'); 

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
insert into permiso(nombre) values('COCINA');

insert into permisos_roles(id, permiso_id) values(1,1);
insert into permisos_roles(id, permiso_id) values(1,2);
insert into permisos_roles(id, permiso_id) values(1,3);
insert into permisos_roles(id, permiso_id) values(1,4);
insert into permisos_roles(id, permiso_id) values(1,5);
insert into permisos_roles(id, permiso_id) values(1,15);
insert into permisos_roles(id, permiso_id) values(1,16);
insert into permisos_roles(id, permiso_id) values(1,17);
insert into permisos_roles(id, permiso_id) values(1,18);
insert into permisos_roles(id, permiso_id) values(1,19);
insert into permisos_roles(id, permiso_id) values(1,20);