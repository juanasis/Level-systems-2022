import { Producto } from "./producto";

export class ItemPedido {
    id: number;
    cantidad: number;
    precio: number;
    producto: Producto = new Producto();

    constructor(producto: number, cantidad:number,precio:number){
        this.producto.id = producto;
        this.cantidad = cantidad;
        this.precio = precio;

    }
    
}