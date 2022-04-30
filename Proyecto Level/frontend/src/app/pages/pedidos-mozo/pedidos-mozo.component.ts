import { Component, OnInit } from '@angular/core';
import { Pedido } from 'src/app/models/pedido';
import { PedidoService } from 'src/app/services/pedido.service';

@Component({
  selector: 'app-pedidos-mozo',
  templateUrl: './pedidos-mozo.component.html',
  styleUrls: ['./pedidos-mozo.component.css']
})
export class PedidosMozoComponent implements OnInit {

  pedidosMozo: Pedido[] = [];

  constructor(private pedidoService: PedidoService) { }

  ngOnInit(): void {
    this.pedidoService.obtenerPedidosActivosMozo(1)
        .subscribe(response => {
          this.pedidosMozo = response.data;
          console.log(this.pedidosMozo)
        });
  }

}
