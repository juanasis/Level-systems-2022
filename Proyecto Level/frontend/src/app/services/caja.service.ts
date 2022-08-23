import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CajaDtoIn } from '../models/caja-dto-in';

@Injectable({
  providedIn: 'root'
})
export class CajaService {

  private urlBackend = 'http://localhost:8080/cajas';

  constructor(private http: HttpClient) { }

  public obtenerCajaActiva(idCajero: number): Observable<any> {
    return this.http.get<any>(`${this.urlBackend}/buscar-caja-activa/cajero/${idCajero}`);
  }

  public obtenerCajaPorId(idCaja: number): Observable<any> {
    return this.http.get<any>(`${this.urlBackend}/buscar-caja/${idCaja}`);
  }

  public cerrarCaja(idCaja: number): Observable<any> {
    return this.http.put<any>(`${this.urlBackend}/cerrar-caja/${idCaja}`,null);
  }

  public abrirCaja(cajaDtoIn: CajaDtoIn): Observable<any> {
    return this.http.post<any>(`${this.urlBackend}/crear`,cajaDtoIn);
  }

  public listarCajas(): Observable<any> {
    return this.http.get<any>(`${this.urlBackend}/listar`);
  }
}
