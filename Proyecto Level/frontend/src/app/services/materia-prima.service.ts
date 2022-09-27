import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MateriaPrima } from '../models/materia-prima';

@Injectable({
  providedIn: 'root'
})
export class MateriaPrimaService {

  private urlBackend = 'http://localhost:8080/materias-prima';

  constructor(private http: HttpClient) { }

  public listarMateriasPrima(): Observable<any> {
    return this.http.get<any>(`${this.urlBackend}/listar`);
  }

  public buscarMateriaPrimaPorId(materia_prima_id: number): Observable<any> {
    return this.http.get<any>(`${this.urlBackend}/buscar/${materia_prima_id}`);
  }

  public crearMateriaPrima(materiaPrima: MateriaPrima): Observable<any> {
    return this.http.post<any>(`${this.urlBackend}/crear`, materiaPrima);
  }

  public actualizarMateriaPrima(materia_prima_id: number, materiaPrima: MateriaPrima): Observable<any> {
    return this.http.put<any>(`${this.urlBackend}/actualizar/${materia_prima_id}`, materiaPrima);
  }

}
