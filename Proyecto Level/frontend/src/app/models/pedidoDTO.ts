import { ItemPedidoDTO } from "./itempedidoDTO";

 
export class PedidoDTO {
    id: number;
    idmozo: number;
    idmesa: number;    
    itemProductoDtoList: ItemPedidoDTO[];
    estado: String;
    fecha: Date;
}