import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Mesa } from '../models/mesa';

@Injectable({
  providedIn: 'root'
})
export class MesaServiceService {

  mesaUrl = 'http://localhost:8080/mesas';

  constructor(private httpClient: HttpClient) { }

  public buscarMesaPorId(idMesa: number): Observable<any> {
    return this.httpClient.get<any>(`${this.mesaUrl}/buscar/${idMesa}`);
  }

  listarMesas(): Observable<any> {
    return this.httpClient.get<any>(`${this.mesaUrl}`);
  }

  crearMesa(mesa: Mesa): Observable<any> {
    return this.httpClient.post<any>(`${this.mesaUrl}/agregar`, mesa);
  }

  actualizarMesa(mesa: Mesa): Observable<any> {
    return this.httpClient.put<any>(`${this.mesaUrl}/actualizar`, mesa);
  }
}
