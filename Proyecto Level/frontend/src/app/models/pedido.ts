import { ItemPedido } from "./itempedido";
import { Mesa } from "./mesa";
import { Producto } from "./producto";



 
export class Pedido {
    id: number;
    mozo: any = {id: ''};
    mesa: Mesa;    
    itemsList: ItemPedido[] = [];
    estado: string;
    fecha: Date;
    tipoPago: string;
    emailUsuario: string;
    pedidoEstadoBebida: string;

    constructor(){
        this.itemsList = [];
        
    }


    agregar(producto : Producto){
      let item = new ItemPedido(producto.id, 1,producto.precio);
      item.producto = producto;
      let indice = this.itemsList.findIndex( s => s.producto.id === item.producto.id )
        if(indice === -1){
            this.itemsList.push(item);          
        }else{
              
            this.itemsList[indice].cantidad += 1; 
        }
        
    }
    quitar(producto: Producto) {
        let indice = this.itemsList.findIndex( s => s.producto.id === producto.id );
        this.itemsList[indice].cantidad -= 1; 
        
        if(this.itemsList[indice].cantidad === 0){
            this.itemsList[indice].cantidad = 1;
            this.itemsList.splice(indice,1);
         }
      }
}

