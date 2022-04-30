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

  opcionesActivas: boolean = false;

  codigo: string = '';

  rutas = [
    {
      path: '/cajeros',
      name: 'Cajeros',
      permiso: 'ROLE_CAJERO'
    },
    {
      path: '/administrador', name: 'Administracion', permiso: 'ROLE_ADMIN'
    },
    {
      path: '/cocineros',
      name: 'Cocineros',
      permiso: 'ROLE_COCINERO'
    },
    {
      path: '/mozos',
      name: 'Mozos',
      permiso: 'ROLE_MOZO'
    },
    {
      path: '/pedidos-mozo',
      name: 'Pedidos del mozo',
      permiso: 'ROLE_MOZO'
    }
  ];
  
  isLogged = false;
  constructor(private tokenService: TokenService, private router: Router) { }
  onLogOut(): void {
    this.tokenService.logOut();
    this.isLogged = false;
    this.router.navigate(['/']);
    
  }



  redireccionar() {
    if(this.codigo == '12345')this.opcionesActivas = true;
  }

  ngOnInit(): void {
    this.activeRole = this.tokenService.getAuthorities()[0];
    
    this.tokenService.loginStatus$.subscribe(
      data => {
        console.log(data)
        this.isLogged = data.status;
        this.activeRole = data.activeRole;
      }
    )
    if (this.tokenService.getToken()) {
      this.isLogged = true;
    } else {
      this.isLogged = false;
    }
  }

}
