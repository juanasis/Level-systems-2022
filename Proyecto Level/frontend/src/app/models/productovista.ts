import { Producto } from "./producto";

export class ProductoVista {
    producto: Producto;
    cantidad: number;

    constructor(producto: Producto, cantidad:number ){
        this.producto = producto;
        this.cantidad = cantidad;
         
    }
}