import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecetaService {
  private backendURL = 'http://localhost:8080/recetas';

  constructor(private httpClient: HttpClient) { }

  buscarRecetaPorProductoId(producto_id: number): Observable<any> {
    return this.httpClient.get<any>(`${this.backendURL}/buscar-por-producto-id/${producto_id}`);
  }

  crearReceta(receta: any): Observable<any> {
    return this.httpClient.post<any>(`${this.backendURL}/crear`, receta)
  }
}
