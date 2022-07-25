import { Component, OnInit } from '@angular/core';
import { Pedido } from 'src/app/models/pedido';
import { PedidoService } from 'src/app/services/pedido.service';
import { AuthService } from '../login/service/auth.service';
import { TokenService } from '../login/service/token.service';

@Component({
  selector: 'app-pedidos-mozo',
  templateUrl: './pedidos-mozo.component.html',
  styleUrls: ['./pedidos-mozo.component.css']
})
export class PedidosMozoComponent implements OnInit {

  pedidosMozo: Pedido[] = [];

  constructor(private pedidoService: PedidoService, private tokenService: TokenService, private authService: AuthService) { }

  ngOnInit(): void {

    let nombreUsuario: string =this.tokenService.getUserName();
    this.authService.buscarPorNonbreUsuario(nombreUsuario)
        .subscribe(response => {
          this.pedidoService.obtenerPedidosActivosMozo(response.data.id)
          .subscribe(response => {
            this.pedidosMozo = response.data;
          });
        })


  }

}
