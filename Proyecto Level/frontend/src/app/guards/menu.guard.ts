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
    
    for (let index = 0; index < roles.length; index++) {
      if(expectedRol.indexOf(roles[index].replace('ROLE_','')) > -1) {
        this.realRol = roles[index].replace('ROLE_','')
        break;
      }
    }

    if(!this.tokenService.getToken() || expectedRol.indexOf(this.realRol) === -1){
      this.router.navigate(['/']);
      return false;
    }

    return true;

  }

  verificarRol(realRol: string, expectedRol: string): boolean {
    if(realRol == expectedRol){
      return true;
    }
    return false
  }
  
}
