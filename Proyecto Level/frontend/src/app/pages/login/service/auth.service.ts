import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NuevoUsuario } from '../models/nuevo-usuario';
import { Observable } from 'rxjs';
import { LoginUsuario } from '../models/login-usuario';
import { JwtDTO } from '../models/jwt-dto';
import { Role } from '../models/role';
import { EmailDto } from '../models/email-dto';
import { ChangePassword } from '../models/change-password';
import { Permiso } from 'src/app/models/permiso';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authURL = 'http://localhost:8080/auth/';

  constructor(private httpClient: HttpClient) { }

  public nuevo(nuevoUsuario: NuevoUsuario): Observable<any> {
    return this.httpClient.post<any>(this.authURL + 'nuevo', nuevoUsuario);
  }

  public login(loginUsuario: LoginUsuario): Observable<JwtDTO> {
    return this.httpClient.post<JwtDTO>(this.authURL + 'login', loginUsuario);
  }

  public getRoles(): Observable<any> {
    return this.httpClient.get<any>(this.authURL + 'roles');
  }

  public getUsuarios(): Observable<any> {
    return this.httpClient.get<any>(this.authURL + 'usuarios');
  }

  public buscarPorNonbreUsuario(nombreUsuario: string): Observable<any> {
    return this.httpClient.get<any>(this.authURL + 'buscar-por-usuario/'+nombreUsuario);
  }

  public sendEmail(emailDto: EmailDto): Observable<any> {
    return this.httpClient.post<any>(this.authURL + 'send-email', emailDto);
  }

  public changePassword(changePassword: ChangePassword): Observable<any> {
    return this.httpClient.post<any>(this.authURL + 'change-password', changePassword);
  }

  public actualizarUsuario(usuarioActualizar: NuevoUsuario): Observable<any> {
    return this.httpClient.put<any>(this.authURL + 'actualizar-usuario', usuarioActualizar);
  }

  public eliminarUsuario(nombreUsuario: string): Observable<any> {
    return this.httpClient.delete<any>(this.authURL + 'eliminar-usuario/'+nombreUsuario);
  }

  public crearRol(rol: Role): Observable<any> {
    return this.httpClient.post<any>(this.authURL + 'crear-rol', rol);
  }
  
  public actualizarRol(rol: Role): Observable<any> {
    return this.httpClient.put<any>(this.authURL + 'actualizar-rol', rol);
  }

  public obtenerRol(idRol: number): Observable<any> {
    return this.httpClient.get<any>(this.authURL + 'obtener-rol/'+idRol);
  }

  public eliminarRol(idRol: number): Observable<any> {
    return this.httpClient.delete<any>(this.authURL + 'eliminar-rol/'+idRol);
  }

  public obtenerHistorialUsuario(nombreUsuario: string): Observable<any> {
    return this.httpClient.get<any>(this.authURL + 'obtener-historial/' + nombreUsuario);
  }

  public obtenerHistorialRol(idRol: number): Observable<any> {
    return this.httpClient.get<any>(this.authURL + 'obtener-historial-rol/' + idRol);
  }

  public listarPermisos(): Observable<Permiso[]> {
    return this.httpClient.get<Permiso[]>(this.authURL + 'permisos');
  }

  public obtenerPermiso(id: number): Observable<any> {
    return this.httpClient.get<any>(this.authURL + 'permisos/'+id);
  }

  public actualizarPermiso(permiso: Permiso): Observable<any> {
    return this.httpClient.put<any>(this.authURL+'permisos', permiso);
  }

}
