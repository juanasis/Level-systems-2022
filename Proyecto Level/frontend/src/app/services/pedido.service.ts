import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, JsonpClientBackend } from '@angular/common/http';
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

 public savePedido(pedido: Pedido): Observable<any> {
    return this.httpClient.post<any>(this.pedidoURL + '/agregar', pedido);
  }

  public update(pedido: Pedido): Observable<any> {
    return this.httpClient.put<any>(this.pedidoURL + `/actualizar`, pedido);
  }

  public actualizarItemsPedido(pedido: Pedido): Observable<any> {
    return this.httpClient.put<any>(this.pedidoURL + `/actualizar/items`, pedido);
  }

  public actualizarPedidoEstadoBebida(pedido: Pedido): Observable<any> {
    return this.httpClient.put<any>(this.pedidoURL + `/actualizar/estado-bebida`, pedido);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.pedidoURL + `/delete/${id}`);
  }

  public obtenerPedidosActivosDeMesa(idMesa: number): Observable<any> {
    return this.httpClient.get<any>(`${this.pedidoURL}/pedidos-mesa/${idMesa}`);
  }

  public obtenerPedidosActivosMozo(idMozo: number): Observable<any> {
    return this.httpClient.get<any>(`${this.pedidoURL}/pedidos-mozo/${idMozo}`);
  }

  public obtenerPedidosActivosCocina(): Observable<any> {
    return this.httpClient.get<any>(`${this.pedidoURL}/pedidos-cocina`);
  }

  public obtenerPedidosPorRangoDeFecha(fecha_desde: string, fecha_hasta: string): Observable<any> {
    let params = new HttpParams();
    params = params.set('fecha_desde', fecha_desde);
    params =  params.set('fecha_hasta', fecha_hasta);
    return this.httpClient.get<any>(`${this.pedidoURL}/pedidos-por-rango-fecha`, {params: params});
  }

  public topTresProductosMasVendidos(fecha_desde: string, fecha_hasta: string): Observable<any> {
    let params = new HttpParams();
    params = params.set('fecha_desde', fecha_desde);
    params =  params.set('fecha_hasta', fecha_hasta);
    return this.httpClient.get<any>(`${this.pedidoURL}/productos-mas-vendidos`, {params: params});
  }

}