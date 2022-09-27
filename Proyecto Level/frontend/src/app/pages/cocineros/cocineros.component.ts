import { Component, OnInit } from '@angular/core';
import { PedidoService } from 'src/app/services/pedido.service';
import { Pedido } from 'src/app/models/pedido';
import { timer } from 'rxjs';
import { AuthService } from '../login/service/auth.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-cocineros',
  templateUrl: './cocineros.component.html',
  styleUrls: ['./cocineros.component.css']
})
export class CocinerosComponent implements OnInit {
  pedidos: Pedido[] = [];
  fechaActual: Date;

  pedidosFiltrados: Pedido[] = [];
  pedidosAuxiliar: Pedido[] = [];

  pedidosFiltradoActivo: boolean = false;
   
    constructor(private pedidoService: PedidoService, public authService: AuthService) { }
    ngOnInit(): void {
      this.pedidoService.obtenerPedidosActivosCocina()
          .subscribe(response => {
            this.pedidos = response.data;
            this.pedidosAuxiliar = this.pedidos;
          })


    timer(0, 1000).subscribe(() => {
      this.fechaActual = new Date();
    })       
    }

    pedidosPorEstado(estado: string) {
      this.pedidosFiltradoActivo = true;
      this.pedidosFiltrados = this.pedidos.filter(p => p.estado == estado);
    }

    activarTodosLosPedidos() {
      this.pedidosFiltradoActivo = false;
    }

    actualizarEstadoPedido(pedido: Pedido, estado: string) {

      if(pedido.estado == estado) {
        return
      }

      let pedidoActualizar = new Pedido();
      pedidoActualizar.id = pedido.id;
      pedidoActualizar.estado = estado;
      pedidoActualizar.itemsList = pedido.itemsList;
      pedidoActualizar.pedidoEstadoBebida = pedido.pedidoEstadoBebida;
      this.pedidoService.update(pedidoActualizar)
          .subscribe(response => {
            this.pedidoService.obtenerPedidosActivosCocina()
                .subscribe(response => {
                  this.pedidos = response.data;

                  this.pedidosFiltrados = this.pedidosFiltrados.filter(p => {
                    if(p.estado != estado && p.id != pedido.id) {
                      return true;
                    }
                    return false;
                  })
                  Swal.fire('Pedido actualizado',`Pedido ${estado}`, 'success')
                })
          });
    }

}
