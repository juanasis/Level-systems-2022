import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MesaServiceService {

  mesaUrl = 'http://localhost:8080/mesas';

  constructor(private httpClient: HttpClient) { }

  public buscarMesaPorId(idMesa: number): Observable<any> {
    return this.httpClient.get<any>(`${this.mesaUrl}/buscar/${idMesa}`);
  }
}
