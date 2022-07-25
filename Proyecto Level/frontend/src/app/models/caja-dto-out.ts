import { Pedido } from "./pedido";


export class CajaDtoOut {
    idCaja: number;

    estado: string;

    monto_inicial: number;

    monto_final: number;

    fecha_apertura: Date;

    fecha_cierre: Date;

    cajero: any;

    pedidos: Pedido[] = [];
}
