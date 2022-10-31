import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdministradorComponent } from './pages/administrador/administrador.component';
import { CajerosComponent } from './pages/cajeros/cajeros.component';
import { ClientesComponent } from './pages/clientes/clientes.component';
import { CocinerosComponent } from './pages/cocineros/cocineros.component';
import { MozosComponent } from './pages/mozos/mozos.component';
import { LoginComponent } from './pages/login/login.component';
import { NuevoComponent } from './pages/productos/nuevo/nuevo.component';
import { EditarComponent } from './pages/productos/editar/editar.component';
import { EditarPedidoComponent } from './pages/pedidos/editarpedido/editarpedido.component';

import { MenuGuard as guard } from './guards/menu.guard';
import { UsuarioFormComponent } from './pages/administrador/usuarios/usuario-form/usuario-form.component';
import { ProductosComponent } from './pages/productos/productos.component';
import { UsuariosComponent } from './pages/administrador/usuarios/usuarios.component';
import { RecuperarPasswordComponent } from './pages/recuperar-password/recuperar-password.component';
import { PedidosMozoComponent } from './pages/pedidos-mozo/pedidos-mozo.component';
import { RolFormularioComponent } from './pages/administrador/roles/rol-formulario/rol-formulario.component';
import { RolesComponent } from './pages/administrador/roles/roles.component';
import { PermisosComponent } from './pages/administrador/permisos/permisos.component';
import { ActualizarPermisoComponent } from './pages/administrador/permisos/actualizar-permiso/actualizar-permiso.component';
import { HistorialUsuarioComponent } from './pages/administrador/usuarios/historial-usuario/historial-usuario.component';
import { CajaActivaComponent } from './pages/cajeros/caja-activa/caja-activa.component';
import { MateriasPrimaComponent } from './pages/administrador/materias-prima/materias-prima.component';
import { FormMateriaPrimaComponent } from './pages/administrador/materias-prima/form-materia-prima/form-materia-prima.component';
import { HistorialRolComponent } from './pages/administrador/roles/historial-rol/historial-rol.component';
import { RecetaComponent } from './pages/productos/receta/receta.component';
import { DashboardComponent } from './pages/administrador/dashboard/dashboard.component';
 
const routes: Routes = [  
{
  path: 'caja',
  component: CajerosComponent,
  canActivate: [guard], data: {expectedPermisos: ['CAJA']}
},
{
  path: 'caja/caja-activa',
  component: CajaActivaComponent,
  canActivate: [guard], data: {expectedPermisos: ['CAJA']}
},
{
  path: 'caja/caja-activa/:idCaja',
  component: CajaActivaComponent,
  canActivate: [guard], data: {expectedPermisos: ['CAJA']}
},
{
  path: 'mesas',
  component: MozosComponent,
  canActivate: [guard], data: {expectedPermisos: ['MESAS']}
},
{
  path: 'pedidos-mozo',
  component: PedidosMozoComponent,
  canActivate: [guard], data: {expectedPermisos: ['LISTAR_PEDIDOS_MOZO']}
},
{
  path: 'cocina',
  component: CocinerosComponent,
  canActivate: [guard], data: {expectedPermisos: ['COCINA']}
},
{
  path: 'administrador', 
  component: AdministradorComponent, 
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'clientes/:mesaId',
  component: ClientesComponent,
  canActivate: [guard], data: {expectedPermisos: ['CREAR_PEDIDO', 'ACTUALIZAR_PEDIDO']}
},
{
  path: 'login',
  component: LoginComponent
},

{
  path: 'editar/:id',
  component: EditarComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'editarpedido',
  component: EditarPedidoComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/dashboard',
  component: DashboardComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/usuarios',
  component: UsuariosComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/usuarios/:nombreUsuario/historial',
  component: HistorialUsuarioComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/usuarios/crear-usuario',
  component: UsuarioFormComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/usuarios/editar-usuario/:nombreUsuario',
  component: UsuarioFormComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/productos/page/:page',
  component: ProductosComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/productos/crear-producto',
  component: NuevoComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/productos/:idProducto/receta',
  component: RecetaComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/materias-prima',
  component: MateriasPrimaComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/materias-prima/:id',
  component: FormMateriaPrimaComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/materias-prima/crear',
  component: FormMateriaPrimaComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/roles',
  component: RolesComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/roles/crear-rol',
  component: RolFormularioComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/roles/:idRol/historial',
  component: HistorialRolComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/roles/editar-rol/:idRol',
  component: RolFormularioComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/permisos',
  component: PermisosComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'administrador/permisos/actualizar-permiso/:id',
  component: ActualizarPermisoComponent,
  canActivate: [guard], data: {expectedPermisos: ['ADMINISTRACION']}
},
{
  path: 'recuperar-password/:token',
  component: RecuperarPasswordComponent
},
{
  path: '**',
  redirectTo: 'Inicio'
} ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [LoginComponent, NuevoComponent, EditarComponent]
