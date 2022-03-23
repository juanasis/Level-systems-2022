export class ItemPedido {
    producto_id: number;
    cantidad: number;
    precio: number;

    constructor(producto: number, cantidad:number,precio:number){
        this.producto_id = producto;
        this.cantidad = cantidad;
        this.precio = precio;

    }
    
}