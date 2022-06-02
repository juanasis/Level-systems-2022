import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, RoutesRecognized } from '@angular/router';
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
  rutasActivas = [];
  rutas = [
    {
      path: '/cajeros',
      name: 'Cajeros',
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
      path: '/cocineros',
      name: 'Cocineros',
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
    // this.router.events.subscribe((data) => {
    //   if (data instanceof RoutesRecognized) {
    //    console.log(data.state.root.firstChild.data.expectedPermisos)
    //    let permisosEsperados = data.state.root.firstChild.data.expectedPermisos;
    //    let ruta = data.state.root.firstChild.routeConfig.path;
    //    console.log(data.state.root.firstChild.routeConfig.path)
    //     this.rutasActivas.push({path: `/${ruta}`, permisosEsperados: permisosEsperados})
    //   }
    // });
   }
  onLogOut(): void {
    this.tokenService.getAuthorities().forEach(rol => {
      this.desactivarRoles(rol);
    });
    this.tokenService.getPermisos().forEach(rol => {
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
    
    this.tokenService.getPermisos()
        .forEach(p => {
          this.validarRoles(p)
        })
    // this.tokenService.getAuthorities().forEach(rol => {
    //   this.validarRoles(rol);
    // })
    
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
