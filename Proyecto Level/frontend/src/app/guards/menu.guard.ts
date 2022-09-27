import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Role } from '../pages/login/models/role';
import { AuthService } from '../pages/login/service/auth.service';
import { TokenService } from '../pages/login/service/token.service';

@Injectable({
  providedIn: 'root'
})
export class MenuGuard implements CanActivate {

  realRol: string;
  roles: Role[] = [];
  permisos: string[] = [];
  

  constructor(private tokenService: TokenService, private router: Router, private authService: AuthService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedPermisos: string[] = route.data.expectedPermisos;
    const roles: Role[] = [];
    let permitido: boolean = false; 
    this.permisos = this.tokenService.getPermisos();

    expectedPermisos.forEach(p => {
      if(this.permisos.includes(p)){
        permitido = true;
      }
    })

    if(!permitido ) {
      Swal.fire('Sin acceso','El rol actual no tiene acceso a este recurso.','info')
      return false;
    }

    if(!permitido || !this.tokenService.getToken()) {
      Swal.fire('Sin acceso','Sin accessos, regresando a página principal.','info')
      this.router.navigate(['/']);
      return false;
    }

    return true
}


}