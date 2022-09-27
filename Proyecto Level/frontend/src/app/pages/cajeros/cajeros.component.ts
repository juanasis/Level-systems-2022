import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CajaDtoIn } from 'src/app/models/caja-dto-in';
import { CajaDtoOut } from 'src/app/models/caja-dto-out';
import { CajaService } from 'src/app/services/caja.service';
import { PedidoService } from 'src/app/services/pedido.service';
import Swal from 'sweetalert2';
import { AuthService } from '../login/service/auth.service';
import { TokenService } from '../login/service/token.service';

@Component({
  selector: 'cajeros',
  templateUrl: './cajeros.component.html',
  styleUrls: ['./cajeros.component.css']
})
export class CajerosComponent implements OnInit {

  cajas: CajaDtoOut[] = [];

  cajaDtoIn: CajaDtoIn = new CajaDtoIn();
  cajaIsActiva: boolean = false;
  cajaFormularioActivo: boolean = false;

  constructor(private cajaService: CajaService, private router: Router, private pedidoService: PedidoService, private tokenService: TokenService, private authService: AuthService) { }

  ngOnInit(): void {
    this.cajaDtoIn.monto_inicial = 0;
    this.cajaService.listarCajas()
        .subscribe(response => {
          this.cajas = response.data;
          this.cajas.reverse()
          this.cajas.forEach(c => {
            if(c.estado == 'ABIERTO'){
              this.cajaIsActiva = true;
            }
          });
          
        })

  }

  abrirCaja(){
    let username: string = this.tokenService.getUserName();
    this.authService.buscarPorNonbreUsuario(username)
        .subscribe(response => {
          let cajero: any = {id: response.data.id};
          this.cajaDtoIn.cajero = cajero;
          this.cajaService.abrirCaja(this.cajaDtoIn)
          .subscribe(response => {
            Swal.fire('Caja Aperturada', 'La caja se aperturó con éxito','success');
            this.cajaFormularioActivo = false;
            this.cajaIsActiva = true;
          }, err => {
            Swal.fire('Alerta', err.error.message,'info')
          })
        })

  }





}
