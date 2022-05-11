import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { ProductoitemComponent } from './clientes/productoitem/productoitem.component';
import { FormsModule } from "@angular/forms";
import { PedidosMozoComponent } from './pedidos-mozo/pedidos-mozo.component';
@NgModule({
    declarations: [
      
      ProductoitemComponent,
      
      PedidosMozoComponent,

      
      
      
       
      
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