import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  productoURL = 'http://localhost:8080/productos';

  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<Producto[]> {
    return this.httpClient.get<Producto[]>(this.productoURL);
  }

  public listaProductodPageable(page: number): Observable<any> {
    return this.httpClient.get<any>(`${this.productoURL}/page/${page}`);
  }

  public filtrarProductosPorNombre(filtroNombre: string): Observable<any> {
    return this.httpClient.get<any>(`${this.productoURL}/filtrar/${filtroNombre}`);
  }

  public listaProductosPorCategoria(idCategoria: number): Observable<any> {
    return this.httpClient.get<any>(`${this.productoURL}/productos-por-categoria/${idCategoria}`);
  }

  public listarCategorias(): Observable<any> {
    return this.httpClient.get<any>(`${this.productoURL}/listar-categorias`);
  }

  public detail(id: number): Observable<Producto> {
    return this.httpClient.get<Producto>(this.productoURL + `/${id}`);
  }

  public detailName(nombre: string): Observable<Producto> {
    return this.httpClient.get<Producto>(this.productoURL + `nombre/${nombre}`);
  }

  public save(producto: Producto): Observable<any> {
    return this.httpClient.post<any>(this.productoURL + '/agregar', producto).pipe(
      catchError(error => {
        if (error.status === 400) {
          Swal.fire('Producto existente', `${error.error.mensaje}`, 'info');
          return throwError('Product already exists');
        }
      })
      
    );
  }

  public update(id: number, producto: Producto): Observable<any> {
    return this.httpClient.put<any>(this.productoURL + `/update/${id}`, producto);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.productoURL + `/delete/${id}`);
  }

  public subirImagen(file: File, productoId: number): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('productoId', productoId.toString());
    return this.httpClient.post<any>(`http://localhost:8080/api/imagenes/subir`, formData);
  }
}