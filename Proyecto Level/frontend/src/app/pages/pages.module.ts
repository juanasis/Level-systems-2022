import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { ProductoitemComponent } from './clientes/productoitem/productoitem.component';
import { FormsModule } from "@angular/forms";
import { PedidosMozoComponent } from './pedidos-mozo/pedidos-mozo.component';
import { PermisosComponent } from './administrador/permisos/permisos.component';
import { ActualizarPermisoComponent } from './administrador/permisos/actualizar-permiso/actualizar-permiso.component';
import { RecetaComponent } from './productos/receta/receta.component';
@NgModule({
    declarations: [
      
      ProductoitemComponent,
      
      PedidosMozoComponent,
      
      PermisosComponent,
      
      ActualizarPermisoComponent,
      
      RecetaComponent

         ],
    imports: [
      CommonModule,
      BrowserModule,
      FormsModule,
    ],
    exports: [
           
    ]
  })
  export class PagesModule {
  }