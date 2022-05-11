import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/pages/login/service/token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  activeRole: string = '';
  rolMozo: string = '';
  rolCocinero: string = '';
  rolAdmin: string = '';
  rolCajero: string = '';

  opcionesActivas: boolean = false;

  codigo: string = '';

  rutas = [
    {
      path: '/cajeros',
      name: 'Cajeros',
      permiso: 'ROLE_CAJERO',
      activo: false
    },
    {
      path: '/administrador', name: 'Administracion', permiso: 'ROLE_ADMIN',
      activo: false
    },
    {
      path: '/cocineros',
      name: 'Cocineros',
      permiso: 'ROLE_COCINERO',
      activo: false
    },
    {
      path: '/mozos',
      name: 'Mozos',
      permiso: 'ROLE_MOZO',
      activo: false
    },
    {
      path: '/pedidos-mozo',
      name: 'Pedidos del mozo',
      permiso: 'ROLE_MOZO',
      activo: false
    }
  ];
  
  isLogged = false;
  constructor(private tokenService: TokenService, private router: Router) { }
  onLogOut(): void {
    this.tokenService.getAuthorities().forEach(rol => {
      this.desactivarRoles(rol);
    });
    this.tokenService.logOut();
    this.isLogged = false;
    this.router.navigate(['/']);
    
   }



  redireccionar() {
    if(this.codigo == '12345') {
      this.opcionesActivas = true;
    }
  }

  ngOnInit(): void {
    this.tokenService.getAuthorities().forEach(rol => {
      this.validarRoles(rol);
    })
    
    this.tokenService.loginStatus$.subscribe(
      data => {
        this.isLogged = data.status;
        data.roles.forEach(rol => {
          this.validarRoles(rol);
        });
      }
    )
    if (this.tokenService.getToken()) {
      this.isLogged = true;
    } else {
      this.isLogged = false;
    }
  }

  validarRoles(rol: string) {
    if(rol == 'ROLE_CAJERO'){
      this.rutas[0].activo = true;
    }
    if(rol == 'ROLE_ADMIN'){
      this.rutas[1].activo = true;
    }
    if(rol == 'ROLE_COCINERO'){
      this.rutas[2].activo = true;
    }
    if(rol == 'ROLE_MOZO'){
      this.rutas[3].activo = true;
      this.rutas[4].activo = true;
    }
  }

  desactivarRoles(rol: string) {
    if(rol == 'ROLE_CAJERO'){
      this.rutas[0].activo = false;
      console.log(this.rutas[0].activo)
    }
    if(rol == 'ROLE_ADMIN'){
      this.rutas[1].activo = false;
    }
    if(rol == 'ROLE_COCINERO'){
      this.rutas[2].activo = false;
    }
    if(rol == 'ROLE_MOZO'){
      this.rutas[3].activo = false;
      this.rutas[4].activo = false;
    }
  }
  

}
