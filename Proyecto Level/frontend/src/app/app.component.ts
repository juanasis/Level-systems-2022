import { Component, OnChanges, SimpleChanges } from '@angular/core';
import { MesaServiceService } from './services/mesa-service.service';
import { PedidoService } from './services/pedido.service';
import { map, filter } from 'rxjs/operators';
import { Mesa } from './models/mesa';
import { NavigationEnd, Router } from '@angular/router';
import { TokenService } from './pages/login/service/token.service';
 
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  porcentajeMesas = 35;

  cantidadPedidos = 124;

  ingresosHoy = 123220;

  rutaPrincipalActiva = true;
  
  isLogged = false;
  cantidadRoles = 0;

  rol_mozo = false;
  fechaHoy: Date;
  
  constructor(private mesaService: MesaServiceService,
    private pedidoService: PedidoService,
    private router: Router,
    private tokenService: TokenService){
      this.fechaHoy = new Date();

      this.mesaService.listarMesas()
          .pipe(
            map(response => {
              let cantidadMesaOcupada = 0;
              let cantidadMesaTotalActivo = 0;
              let mesas: Mesa[] = response.data;
              mesas.forEach(m => {
                if(m.estado == 'OCUPADA'){
                  cantidadMesaOcupada++;
                }
                if(m.activo) {
                  cantidadMesaTotalActivo++;
                }
              })
              this.porcentajeMesas = (cantidadMesaOcupada / cantidadMesaTotalActivo)*100;
              return mesas;
            })
          )
          .subscribe();

      this.pedidoService.getCantidadPedidosHoy()
          .subscribe(response => this.cantidadPedidos = response.data);

      this.pedidoService.getTotalPedidosIngresos()
          .subscribe(response => this.ingresosHoy = response.data);    
  }


  ngOnInit(): void {
    
    

    this.tokenService.loginStatus$
    .subscribe(
      data => {
        this.isLogged = data.status;
        console.log('status', this.isLogged)
      }
)

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.cantidadRoles = this.tokenService.getAuthorities().length;
        this.tokenService.getAuthorities().forEach(rol => rol === 'ROLE_MOZO' ? this.rol_mozo = true : this.rol_mozo = false);
          event.url === '/' ? this.rutaPrincipalActiva = true : this.rutaPrincipalActiva = false;
      });
  }


}
