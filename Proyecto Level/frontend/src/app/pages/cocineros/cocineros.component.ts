import { Component, OnInit } from '@angular/core';
import { PedidoService } from 'src/app/services/pedido.service';
import { Pedido } from 'src/app/models/pedido';
import { Observable, of, timer } from 'rxjs';
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

  $refrescarPedidosActivo: Observable<boolean> = of(false);
   
    constructor(private pedidoService: PedidoService, public authService: AuthService) { }
    ngOnInit(): void {
      this.pedidoService.obtenerPedidosActivosCocina()
          .subscribe(response => {
            this.pedidos = response.data;
            this.pedidos.reverse();
            this.pedidosAuxiliar = this.pedidos;
            this.$refrescarPedidosActivo = of(true);
          })


    timer(0, 1000).subscribe(() => {
      this.fechaActual = new Date();
    })
    timer(0, 5000).subscribe(() => {
      this.$refrescarPedidosActivo.subscribe(response => {
        if(response){
          this.pedidoService.obtenerPedidosActivosCocina()
          .subscribe(response => {
            this.pedidos = response.data;
            this.pedidos.reverse();
            this.pedidosAuxiliar = this.pedidos;
          })
        }
      })
      
    }) 

    }

    pedidosPorEstado(estado: string) {
      this.pedidosFiltradoActivo = true;
      this.pedidosFiltrados = this.pedidos.filter(p => p.estado == estado);
    }

    refrescarCocina(){
      this.pedidoService.obtenerPedidosActivosCocina()
      .subscribe(response => {
        this.pedidos = response.data;
        this.pedidos.reverse();
        this.pedidosAuxiliar = this.pedidos;
        Swal.fire('Pedidos actualizados','','success');
      })
    }

    activarTodosLosPedidos() {
      this.pedidosFiltradoActivo = false;
    }

    actualizarEstadoPedido(pedido: Pedido) {
      if(pedido.estado === 'ENTREGADO') {
        return
      }
      Swal.fire({
        title: 'Cambiar de estado',
        text: "¿Está seguro de cambiar el estado?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          let validacionHecha = false;

          if(pedido.estado === 'EN_COLA' && !validacionHecha) {
            pedido.estado = 'EN_PREPARACION';
            validacionHecha = true;
          }

          if(pedido.estado === 'EN_PREPARACION' && !validacionHecha) {
            pedido.estado = 'LISTO';
            validacionHecha = true;
          }

          if(pedido.estado === 'LISTO' && !validacionHecha) {
            pedido.estado = 'ENTREGADO';
            validacionHecha = true;
          }
    
          let pedidoActualizar = new Pedido();
          pedidoActualizar.id = pedido.id;
          pedidoActualizar.estado = pedido.estado;
          pedidoActualizar.itemsList = pedido.itemsList;
          pedidoActualizar.pedidoEstadoBebida = pedido.pedidoEstadoBebida;
          this.pedidoService.update(pedidoActualizar)
              .subscribe(response => {
                this.pedidoService.obtenerPedidosActivosCocina()
                    .subscribe(response => {
                      this.pedidos = response.data;
                      this.pedidos.reverse();
                      this.pedidosFiltrados = this.pedidosFiltrados.filter(p => {
                        if(p.estado != pedido.estado && p.id != pedido.id) {
                          return true;
                        }
                        return false;
                      })
                      Swal.fire('Pedido actualizado',`Pedido ${pedido.estado}`, 'success')
                    })
              });
        }
      })


    }

    ngOnDestroy(): void {
      this.$refrescarPedidosActivo = of(false);
    }

}
