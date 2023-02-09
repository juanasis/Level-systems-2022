import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, RoutesRecognized } from '@angular/router';
import { TokenService } from 'src/app/pages/login/service/token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  usuarioLogeado: string;

  activeRole: string = '';
  rolMozo: string = '';
  rolCocinero: string = '';
  rolAdmin: string = '';
  rolCajero: string = '';

  rolesUsuario: string[] = [];
  formularioSeguridadActivo: boolean = false;

  opcionesActivas: boolean = false;

  codigo: string = '';
  rutasActivas = [];
  rutas = [
    {
      path: '/caja',
      name: 'Caja',
      permiso: 'CAJA',
      activo: false
    },
    {
      path: '/administrador', 
      name: 'Administración', 
      permiso: 'ADMINISTRACION',
      activo: false
    },
    {
      path: '/cocina',
      name: 'Cocina',
      permiso: 'COCINA',
      activo: false
    },
    {
      path: '/mesas',
      name: 'Mesas',
      permiso: 'MESAS',
      activo: false
    },
    {
      path: '/pedidos-mozo',
      name: 'Pedidos del mozo',
      permiso: 'LISTAR_PEDIDOS_MOZO',
      activo: false
    }
  ];
  
  isLogged = false;
  constructor(private tokenService: TokenService, private router: Router) {
   }
  onLogOut(): void {
    this.rolesUsuario.forEach(rol => {
      this.desactivarRoles(rol);
    });
    this.tokenService.getPermisos().forEach(rol => {
      this.desactivarRoles(rol);
    });
    this.tokenService.logOut();
    this.isLogged = false;
    this.router.navigate(['/']);
    this.formularioSeguridadActivo = false;
    this.opcionesActivas = true;
   }



  redireccionar() {
    if(this.codigo == '12345') {
      this.opcionesActivas = true;
    }
  }

  ngOnInit(): void {

    this.usuarioLogeado = this.tokenService.getUserName();

    
    this.rolesUsuario = this.tokenService.getAuthorities();
    if(this.rolesUsuario.includes('ROLE_MOZO') && this.rolesUsuario.length == 1) {
      this.formularioSeguridadActivo = true;
      this.opcionesActivas = false;
    } else {
      this.opcionesActivas = true;
      this.formularioSeguridadActivo = false;
    }

    this.tokenService.formularioNavBarStatus$
    .subscribe(response => {
      this.codigo = ''
      this.opcionesActivas = response.activo
      this.formularioSeguridadActivo = true;
    })


    
    this.tokenService.getPermisos()
        .forEach(p => {
          this.validarRoles(p)
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
    if(rol == 'CAJA'){
      this.rutas[0].activo = true;
    }
    if(rol == 'ADMINISTRACION'){
      this.rutas[1].activo = true;
    }
    if(rol == 'COCINA'){
      this.rutas[2].activo = true;
    }
    if(rol == 'MESAS'){
      this.rutas[3].activo = true;
    }
    if(rol == 'LISTAR_PEDIDOS_MOZO'){
      this.rutas[4].activo = true;
    }
  }

  desactivarRoles(rol: string) {
    if(rol == 'CAJA'){
      this.rutas[0].activo = false;
      console.log(this.rutas[0].activo)
    }
    if(rol == 'ADMINISTRACION'){
      this.rutas[1].activo = false;
    }
    if(rol == 'COCINA'){
      this.rutas[2].activo = false;
    }
    if(rol == 'MESAS'){
      this.rutas[3].activo = false;
    }
    if(rol == 'LISTAR_PEDIDOS_MOZO'){
      this.rutas[4].activo = false;
    }
  }
  

}
