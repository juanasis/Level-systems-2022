import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../pages/login/service/token.service';

@Injectable({
  providedIn: 'root'
})
export class MenuGuard implements CanActivate {

  realRol: string;

  constructor(private tokenService: TokenService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRol = route.data.expectedRol;
    const roles = this.tokenService.getAuthorities();
    this.realRol = 'ADMIN';

    roles.forEach(rol => {
      switch (rol) {
        case 'ROLE_ADMIN':
          this.realRol = 'ADMIN'
          break;
        case 'ROLE_CAJERO':
          this.realRol = 'CAJERO'
          break;
        case 'ROLE_CLIENTE':
          this.realRol = 'CLIENTE'
          break;
        case 'ROLE_COCINERO':
          this.realRol = 'COCINERO'
          break; 
        default:
          this.realRol = 'MOZO'
          break;  
      }
    });



    if(!this.tokenService.getToken() || expectedRol.indexOf(this.realRol) === -1){
      this.router.navigate(['/']);
      return false;
    }

    return true;

  }
  
}
