import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Mesa } from 'src/app/models/mesa';
import { Pedido } from 'src/app/models/pedido';
import { PedidoService } from 'src/app/services/pedido.service';
import Swal from 'sweetalert2';
import { AuthService } from '../login/service/auth.service';
import { TokenService } from '../login/service/token.service';

@Component({
  selector: 'app-mozos',
  templateUrl: './mozos.component.html',
  styleUrls: ['./mozos.component.css']
})
export class MozosComponent implements OnInit {
  mesas: Mesa[] = [];

  tipoPago: string = '';

  idMozo: number;

  rolesDelUsuario: string[] = [];
  rolCajeroOk: boolean = false;
  
  pedidosActivosMesa: Pedido[] = [];

  constructor(private http: HttpClient,
     private pedidoService: PedidoService,
     private router: Router,
     private tokenService: TokenService,
     private authService: AuthService) { }
  ngOnInit(): void {

    if(this.rolesDelUsuario.includes('ROLE_MOZO') && this.rolesDelUsuario.length == 1) {
      this.tokenService.activarFormularioNavBar();
    }

    let nombreUsuario: string = this.tokenService.getUserName();
    
    this.rolesDelUsuario = this.tokenService.getAuthorities();

    this.rolesDelUsuario.forEach(rol => {
      if(rol.includes('ROLE_CAJERO')){
        this.rolCajeroOk = true;
      }
    })
    
    


    this.authService.buscarPorNonbreUsuario(nombreUsuario)
        .subscribe(response => {
          this.idMozo = response.data.id
        })


    this.http.get("http://localhost:8080/mesas").subscribe(
      (resp:any) =>{
     
      this.mesas = resp.data;
      })
  }


  obtenerPedidosPorMesa(mesa: Mesa) {

    if(this.rolesDelUsuario.includes('ROLE_MOZO') && this.rolesDelUsuario.length == 1) {
      this.tokenService.activarFormularioNavBar();
    }

    if(mesa.mozoId == this.idMozo || mesa.mozoId == null) {
      this.pedidoService.obtenerPedidosActivosDeMesa(mesa.id)
      .subscribe(response => {
          console.log(response)
        if(response.data.length == 0) {
          this.router.navigate(['/clientes',mesa.id]);
          return 
        }
        this.pedidosActivosMesa = response.data;
        this.pedidosActivosMesa.forEach(p => {
          p['total'] = 0;
          p.itemsList.forEach(i => {
            p['total'] += (i.cantidad * i.precio);
          })
        })
      })
      return
    }



    if(mesa.mozoId != this.idMozo) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Esta no es su mesa',
        showConfirmButton: false,
        timer: 1500
      })
      return
    }




    

  }

  regresarAMesas() {
    this.pedidosActivosMesa = [];
  }

  actualizarPedido(pedido: Pedido) {
    let pedidoActualizar : Pedido = new Pedido();
    pedidoActualizar.tipoPago = pedido.tipoPago;
    pedidoActualizar.estado = 'PAGADO';
    pedidoActualizar.id = pedido.id;
    this.pedidoService.update(pedidoActualizar)
        .subscribe(response => {

          this.pedidosActivosMesa = this.pedidosActivosMesa.filter(p => p.id != pedido.id)
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Se realizó el pago',
            showConfirmButton: false,
            timer: 1500
          })

          
          this.http.get("http://localhost:8080/mesas").subscribe(
            (resp:any) =>{
           console.log(resp)
            this.mesas = resp.data;
            })
        })
  }

  pagarTodosLosPedidos() {
    this.pedidosActivosMesa.forEach((p, index) => {
      let pedidoActualizar : Pedido = new Pedido();
      pedidoActualizar.tipoPago = this.tipoPago;
      pedidoActualizar.estado = 'PAGADO';
      pedidoActualizar.id = p.id;
      pedidoActualizar.itemsList = p.itemsList;
      pedidoActualizar.mozo = null;
      this.pedidoService.update(pedidoActualizar)
          .subscribe(response => {
            if(this.pedidosActivosMesa.length -1 == index){
              Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'Se realizó el pago',
                showConfirmButton: false,
                timer: 1500
              })
              this.pedidosActivosMesa=[];
              

              this.http.get("http://localhost:8080/mesas").subscribe(
                (resp:any) =>{
               
                this.mesas = resp.data;
                })
            }
          })
    })
  }

}
