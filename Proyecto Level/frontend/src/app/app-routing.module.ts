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
 
const routes: Routes = [  {
  path: 'cajeros',
  component: CajerosComponent,
  canActivate: [guard], data: {expectedRol: ['CAJERO']}
},
{
  path: 'mozos',
  component: MozosComponent,
  canActivate: [guard], data: {expectedRol: ['MOZO']}
},
{
  path: 'pedidos-mozo',
  component: PedidosMozoComponent,
  canActivate: [guard], data: {expectedRol: ['MOZO']}
},
{
  path: 'cocineros',
  component: CocinerosComponent,
  canActivate: [guard], data: {expectedRol: ['COCINERO']}
},
{
  path: 'administrador', component: AdministradorComponent, canActivate: [guard], data: {expectedRol: ['ADMIN']}
},
{
  path: 'clientes/:mesaId',
  component: ClientesComponent,
  canActivate: [guard], data: {expectedRol: ['MOZO']}
},
{
  path: 'login',
  component: LoginComponent
},

{
  path: 'editar/:id',
  component: EditarComponent,
  canActivate: [guard], data: {expectedRol: ['ADMIN']}
},
{
  path: 'editarpedido',
  component: EditarPedidoComponent,
  canActivate: [guard], data: {expectedRol: ['ADMIN']}
},
{
  path: 'administrador/usuarios',
  component: UsuariosComponent,
  canActivate: [guard], data: {expectedRol: ['ADMIN']}
},
{
  path: 'administrador/usuarios/crear-usuario',
  component: UsuarioFormComponent,
  canActivate: [guard], data: {expectedRol: ['ADMIN']}
},
{
  path: 'administrador/productos',
  component: ProductosComponent,
  canActivate: [guard], data: {expectedRol: ['ADMIN']}
},
{
  path: 'administrador/productos/crear-producto',
  component: NuevoComponent,
  canActivate: [guard], data: {expectedRol: ['ADMIN']}
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
