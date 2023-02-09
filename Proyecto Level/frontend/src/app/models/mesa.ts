import { Pedido } from "./pedido";

export class Mesa {
    id: number;
    estado: String;
    pedidos: Pedido[] = [];
    mozoId: number;
    nombre: string;
    activo: boolean;
}