import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Role } from '../models/role';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUserName';
const AUTHORITIES_KEY = 'AuthAuthorities';
const PERMISOS_KEY = 'AuthPermisos';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  roles: Array<string> = [];

  private _sendLoginStatusSource = new Subject<any>();
  loginStatus$ = this._sendLoginStatusSource.asObservable();

  constructor() { }

  public setToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public setUserName(userName: string): void {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, userName);
  }

  public getUserName(): string {
    return sessionStorage.getItem(USERNAME_KEY);
  }

  public setAuthorities(authorities: string[]): void {
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  public setPermisos(permisos: string[]): void {
    window.sessionStorage.removeItem(PERMISOS_KEY);
    window.sessionStorage.setItem(PERMISOS_KEY, JSON.stringify(permisos));
  }

  public getAuthorities(): string[] {
    this.roles = [];
    if (sessionStorage.getItem(AUTHORITIES_KEY)) {
      JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
        this.roles.push(authority.authority);
      });
    }
    return this.roles;
  }

  
  public getPermisos(): string[] {
    let permisos: string[] = [];
    if (sessionStorage.getItem(PERMISOS_KEY)) {
      JSON.parse(sessionStorage.getItem(PERMISOS_KEY)).forEach(permiso => {
        permisos.push(permiso);
      });
    }
    return permisos;
  }
  

  public logOut(): void {
    window.sessionStorage.clear();
  }

  sendLoginStatus( status: boolean, roles: string[]) {
    let params= {status, roles}
    console.log(params)
    this._sendLoginStatusSource.next(params);
  }


}
