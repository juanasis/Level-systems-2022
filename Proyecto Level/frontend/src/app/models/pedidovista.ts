
import { Producto } from "./producto";
import { ProductoVista } from "./productovista";

export class Pedidovista {
   
    productos: ProductoVista[];
    total:number = 0;

    constructor(){
        this.productos = [];
    }
    agregar(producto: Producto):number {
        let productoVista = new ProductoVista(producto, 1);
        //El método findIndex() devuelve el índice del primer elemento de un array que cumpla con la función
        // de prueba proporcionada. En caso contrario devuelve -1
        let indice = this.productos.findIndex( s => s.producto.nombre === productoVista.producto.nombre )
              
        if(indice === -1){
              this.productos.push(productoVista);
              let indice2 = this.productos.findIndex( s => s.producto.nombre === productoVista.producto.nombre )
              this.total += (this.productos[indice2].producto.precio);
               
          }else{
              
              this.productos[indice].producto.cantidad ++;
              this.total += (this.productos[indice].producto.precio);
             
          }
        return this.total;
    }
    quitar(producto: Producto):number {
      
        let indice = this.productos.findIndex( s => s.producto.nombre === producto.nombre );
              
        this.productos[indice].producto.cantidad --;
        this.total -= (this.productos[indice].producto.precio);
             
        if(this.productos[indice].producto.cantidad === 0){
            this.productos[indice].producto.cantidad = 1;
            this.productos.splice(indice,1);
         }
        return this.total;
    }
}
