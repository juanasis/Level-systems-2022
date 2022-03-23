import { Injectable } from '@angular/core';
import { HttpClient, JsonpClientBackend } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pedido } from '../models/pedido';
import { ItemPedidoDTO } from '../models/itempedidoDTO';
import { PedidoDTO } from '../models/pedidoDTO';
 
@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  pedidoURL = 'http://localhost:8080/pedidos';
  pedidoSelected: PedidoDTO;
  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<Pedido[]> {
    return this.httpClient.get<Pedido[]>(this.pedidoURL);
  }


  public setPedido(pedido:PedidoDTO){
    this.pedidoSelected = pedido;

  }
  public getPedido(): PedidoDTO{
    return this.pedidoSelected;
  }

  public detail(id: number) {
    return this.httpClient.get<Pedido>(this.pedidoURL + `/${id}`);
  }
  public detailCaja(id: number) {
    return this.httpClient.get<ItemPedidoDTO[]>(this.pedidoURL + `/nro/${id}`,{responseType: 'json'});
  }

 public save(pedido: Pedido): Observable<any> {
    return this.httpClient.post<any>(this.pedidoURL + '/agregar', pedido);
  }

  public update(id: number, pedido: Pedido): Observable<any> {
    return this.httpClient.put<any>(this.pedidoURL + `/update/${id}`, pedido);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.pedidoURL + `/delete/${id}`);
  }
}