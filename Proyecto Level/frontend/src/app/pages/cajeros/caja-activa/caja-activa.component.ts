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

@Component({
  selector: 'app-caja-activa',
  templateUrl: './caja-activa.component.html',
  styleUrls: ['./caja-activa.component.css']
})
export class CajaActivaComponent implements OnInit {

  cajaDtoOut: CajaDtoOut = new CajaDtoOut();
  pedidoSeleccionado: Pedido;
  pedidoSeleccionadoAux: Pedido;

  estados = ['PAGADO', 'CANCELADO','EN_PREPARACION', 'EN_COLA', 'LISTO'];
  tiposPagos = ['EFECTIVO', 'TARJETA'];
  estadoSeleccionado: string;
  tipoPagoSeleccionado: string;
  botonActivado: boolean = false;

  sendEmalActivo: boolean = false;


  constructor(private cajaService: CajaService, 
    private router: Router, 
    private pedidoService: PedidoService, 
    private authService: AuthService, 
    private tokenService: TokenService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(params => {
      let idCaja = +params['idCaja'];

      if(!idCaja) {
        this.authService.buscarPorNonbreUsuario(this.tokenService.getUserName())
        .subscribe(response => {
          this.cajaService.obtenerCajaActiva(response.data.id)
          .subscribe(response => {
            this.cajaDtoOut = response.data;
            this.obtenerPedidosPorMesa();
          }, err => {
            Swal.fire('Sin permisos', err.error.message, 'info');
            this.router.navigate(['/cajeros']);
          })
        })
      } else {
        this.cajaService.obtenerCajaPorId(idCaja)
            .subscribe(response => {
              this.cajaDtoOut = response.data
              this.obtenerPedidosPorMesa();
            });
      }
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
  this.pedidoSeleccionado = undefined;
  this.pedidoSeleccionado = pedido;
}

actualizarPedido() {
  let pedidoActualizar : Pedido = new Pedido();

  if(!this.pedidoSeleccionado.estado.includes('CANCELADO') && !this.pedidoSeleccionado.estado.includes('PAGADO')) {
    Swal.fire('Alerta', 'Seleccione CANCELADO O PAGADO','info')
    return
  }

  if(!this.pedidoSeleccionado.tipoPago) {
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
        if(pedidoActualizar.estado == 'CANCELADO'){
          Swal.fire('Pedido cancelado', 'Se canceló el pedido','success')
        }else {
          Swal.fire('Pedido pagado', 'Se hizo el pago del pedido','success')
        }
        
        setInterval(() => {
          window.location.reload()
        }, 1300)
      }, err => {
        console.log(err)
      })
}

quitarItemPedido(item: ItemPedido){
  Swal.fire({
    title: '¿Está seguro de eliminar el item?',
    text: "Se eliminará el item del pedido",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    cancelButtonText: 'Cancelar',
    confirmButtonText: 'Sí, eliminar!'
  }).then((result) => {
    if (result.isConfirmed) {
      this.pedidoSeleccionado.itemsList = this.pedidoSeleccionado.itemsList.filter(i => i.id != item.id);
    }
  })
  
}

cerrarCaja() {
  this.cajaService.cerrarCaja(this.cajaDtoOut.idCaja)
      .subscribe(response => {
        Swal.fire('Caja cerrada', 'Se cerró la caja con éxito','success')
        this.router.navigate(['/cajeros']);
      }, err => {
        Swal.fire('Caja cerrada', err.error.message,'info')
      })
}

}
