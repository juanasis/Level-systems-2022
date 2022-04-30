import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NuevoUsuario } from '../models/nuevo-usuario';
import { Observable } from 'rxjs';
import { LoginUsuario } from '../models/login-usuario';
import { JwtDTO } from '../models/jwt-dto';
import { Role } from '../models/role';
import { EmailDto } from '../models/email-dto';
import { ChangePassword } from '../models/change-password';

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
}
