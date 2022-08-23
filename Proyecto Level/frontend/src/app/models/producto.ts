import { Categoria } from "./categoria";

export class Producto {
    id: number;
    nombre: String;
    descripcion: String;
    cantidad: number;
    categoria: Categoria;
    precio: number;
    imgpath: String;
    estado: string;
     
}
