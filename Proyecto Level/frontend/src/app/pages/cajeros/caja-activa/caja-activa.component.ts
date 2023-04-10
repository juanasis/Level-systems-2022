import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CajaDtoOut } from 'src/app/models/caja-dto-out';
import { ItemPedido } from 'src/app/models/itempedido';
import { Pedido } from 'src/app/models/pedido';
import { CajaService } from 'src/app/services/caja.service';
import { PedidoService } from 'src/app/services/pedido.service';
import Swal from 'sweetalert2';
import { AuthService } from '../../login/service/auth.service';
import { TokenService } from '../../login/service/token.service';
import { timer, of, Observable } from 'rxjs';

@Component({
  selector: 'app-caja-activa',
  templateUrl: './caja-activa.component.html',
  styleUrls: ['./caja-activa.component.css']
})
export class CajaActivaComponent implements OnInit {

  cajaDtoOut: CajaDtoOut = new CajaDtoOut();
  pedidoSeleccionado: Pedido;
  pedidoSeleccionadoAux: Pedido;
  pedidoEstadoAux: string;

  estados = ['PAGADO', 'CANCELADO'];
  tiposPagos = ['EFECTIVO', 'TARJETA'];
  estadoSeleccionado: string;
  tipoPagoSeleccionado: string;
  botonActivado: boolean = false;

  sendEmalActivo: boolean = false;
  comentarios: any[];
  idCaja: number;

  $refrescarPedidosActivo: Observable<boolean> = of(false);

  constructor(private cajaService: CajaService, 
    private router: Router, 
    private pedidoService: PedidoService, 
    private authService: AuthService, 
    private tokenService: TokenService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(params => {
      this.idCaja = params['idCaja'];

      if(!this.idCaja) {
        this.authService.buscarPorNonbreUsuario(this.tokenService.getUserName())
        .subscribe(response => {
          this.cajaService.obtenerCajaActiva(response.data.id)
          .subscribe(response => {
            this.cajaDtoOut = response.data;
            this.obtenerPedidosPorMesa();
            this.cajaDtoOut.pedidos.reverse();
            this.$refrescarPedidosActivo = of(true);
          }, err => {
            Swal.fire('Sin permisos', err.error.message, 'info');
            this.router.navigate(['/caja']);
          })
        })
      } else {
        this.cajaService.obtenerCajaPorId(this.idCaja)
            .subscribe(response => {
              this.cajaDtoOut = response.data
              this.obtenerPedidosPorMesa();
              this.$refrescarPedidosActivo = of(false);
            });
      }
    })

    timer(0, 5000).subscribe(() => {
      this.$refrescarPedidosActivo.subscribe(response => {
        if(response){
          this.refrescarCaja();
        }
      })
      
    })  




  }

  obtenerPedidosPorMesa() {

    if(this.cajaDtoOut.pedidos.length == 0) {
      return
    }


    this.cajaDtoOut.pedidos.forEach(p => {
      p['total'] = 0;
      p.itemsList.forEach(i => {
        p['total'] += (i.cantidad * i.precio);
      })
    })
}

mostrarDetallePedido(pedido: Pedido){
  this.pedidoEstadoAux = undefined;
  this.pedidoSeleccionado = undefined;
  this.pedidoSeleccionado = pedido;
  this.pedidoEstadoAux = this.pedidoSeleccionado.estado
}

actualizarPedidoEstadoBebida(estadoBebida: string) {
  let pedidoActualizar : Pedido = new Pedido();
  this.pedidoSeleccionado.pedidoEstadoBebida = estadoBebida;
  pedidoActualizar.id = this.pedidoSeleccionado.id;
  pedidoActualizar.pedidoEstadoBebida = this.pedidoSeleccionado.pedidoEstadoBebida;
  this.pedidoService.actualizarPedidoEstadoBebida(pedidoActualizar)
      .subscribe(response => {
        Swal.fire('Estado actualizado', 'Se actualizó el estado de la bebida','success')
      })
}

actualizarPedido() {
  let pedidoActualizar : Pedido = new Pedido();

  if((this.pedidoEstadoAux && !this.pedidoEstadoAux.includes('EN_COLA')) && this.pedidoSeleccionado.estado.includes('CANCELADO')) {
    Swal.fire('Alerta', 'El pedido solo se puede cancelar si está EN_COLA','info')
    return
  }

  if(this.pedidoSeleccionado.estado == undefined) {
    Swal.fire('Alerta', 'Seleccione CANCELADO O PAGADO','info')
    return
  }

  if(!this.pedidoSeleccionado.estado.includes('CANCELADO') && !this.pedidoSeleccionado.estado.includes('PAGADO')) {
    Swal.fire('Alerta', 'Seleccione CANCELADO O PAGADO','info')
    return
  }

  if(!this.pedidoSeleccionado.tipoPago && this.pedidoSeleccionado.estado.includes('PAGADO')) {
    Swal.fire('Alerta', 'Seleccione un tipo de pago','info')
    return
  }

  pedidoActualizar.tipoPago = this.pedidoSeleccionado.tipoPago;
  pedidoActualizar.estado = this.pedidoSeleccionado.estado;
  pedidoActualizar.id = this.pedidoSeleccionado.id;
  pedidoActualizar.itemsList = this.pedidoSeleccionado.itemsList;
  pedidoActualizar.emailUsuario = this.pedidoSeleccionado.emailUsuario;



  this.pedidoService.update(pedidoActualizar)
      .subscribe(response => {
        this.sendEmalActivo = false;
        if(pedidoActualizar.estado == 'CANCELADO'){
          Swal.fire('Pedido cancelado', 'Se canceló el pedido','success')
        }else {

          if(pedidoActualizar.emailUsuario != null) {
            Swal.fire('Pedido pagado', 'Se hizo el pago del pedido. Se envió el comprobante al email ingresado.','success')
          }else {
            Swal.fire('Pedido pagado', 'Se hizo el pago del pedido','success')
          }
        }
        this.pedidoSeleccionado = undefined;
        setInterval(() => {
          window.location.reload()
        }, 1300)
      }, err => {
        console.log(err)
      })
}

async quitarItemPedido(item: ItemPedido){
  const { value: text } = await Swal.fire({
    title: '¿Está seguro de eliminar el item?',
    input: 'text',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Confirmar',
    inputPlaceholder: 'Ingrese el motivo',
    inputValidator: (value) => {
      if (!value) {
        return 'Debes ingresar un motivo para quitar el item'
      }
    }
  })

  if(text) {
    this.pedidoSeleccionado.itemsList = this.pedidoSeleccionado.itemsList.filter(i => i.id != item.id);

    this.cajaDtoOut.pedidos.forEach(p => {
      p['total'] = 0;
      p.itemsList.forEach(i => {
        p['total'] += (i.cantidad * i.precio);
      })
    })
    let pedidoActualizar = new Pedido();
    pedidoActualizar.id = this.pedidoSeleccionado.id;
    pedidoActualizar.itemsList = this.pedidoSeleccionado.itemsList;
    pedidoActualizar.comentarios = this.pedidoSeleccionado.comentarios;
    pedidoActualizar.comentarios.push(this.agregarComentario(text, item.producto.nombre, item.cantidad))
    this.pedidoSeleccionado.comentarios = pedidoActualizar.comentarios;
    this.pedidoService.actualizarItemsPedido(pedidoActualizar)
        .subscribe()
  }
  
}

agregarComentario(descripcion: string, nombreProducto: String, cantidad: number): any {
  return {descripcion: descripcion, producto: nombreProducto, cantidad: cantidad};
}

mostrarComentarios(pedido: Pedido) {
  this.comentarios = pedido.comentarios;
}

refrescarCaja() {
    this.authService.buscarPorNonbreUsuario(this.tokenService.getUserName())
    .subscribe(response => {
      this.cajaService.obtenerCajaActiva(response.data.id)
      .subscribe(response => {
        this.cajaDtoOut = response.data;
        this.obtenerPedidosPorMesa();
        this.cajaDtoOut.pedidos.reverse();
      }, err => {
        Swal.fire('Sin permisos', err.error.message, 'info');
        this.router.navigate(['/caja']);
      })
    })
  


}

cerrarCaja() {

  Swal.fire({
    title: '¿Está seguro de cerrar caja?',
    text: "Está apunto de cerrar caja",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    cancelButtonText: 'Cancelar',
    confirmButtonText: 'Sí, cerrar!'
  }).then((result) => {
    if (result.isConfirmed) {
      this.cajaService.cerrarCaja(this.cajaDtoOut.idCaja)
      .subscribe(response => {
        Swal.fire('Caja cerrada', 'Se cerró la caja con éxito','success')
        this.router.navigate(['/caja']);
      }, err => {
        Swal.fire('Caja cerrada', err.error.message,'info')
      })
    }
  })
}

generarBoletaDeCompra(pedidoId: number) {
  let link = document.createElement("a");
    link.href= `http://localhost:8080/pedidos/boleta/${pedidoId}`;
    link.click();
}

ngOnDestroy(): void {
  this.$refrescarPedidosActivo = of(false);
  console.log("se cerro todo")
}

}
