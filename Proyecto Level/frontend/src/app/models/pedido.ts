import { ItemPedido } from "./itempedido";
import { Producto } from "./producto";
 
export class Pedido {
    id: number;
    idmozo: number;
    idmesa: number = 0;    
    items: ItemPedido[];
    estado: String;
    fecha: Date;

    constructor(){
        this.items = [];
    }
    agregar(producto : Producto){
      let item = new ItemPedido(producto.id, 1,producto.precio);
      let indice = this.items.findIndex( s => s.producto_id === item.producto_id )
        if(indice === -1){
            this.items.push(item);          
        }else{
              
            this.items[indice].cantidad += 1; 
        }
        
    }
    quitar(producto: Producto) {
        let indice = this.items.findIndex( s => s.producto_id === producto.id );
        this.items[indice].cantidad -= 1; 
        
        if(this.items[indice].cantidad === 0){
            this.items[indice].cantidad = 1;
            this.items.splice(indice,1);
         }
      }
}

