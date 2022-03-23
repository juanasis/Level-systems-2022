import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ItemPedido } from 'src/app/models/itempedido';
import { ItemPedidoDTO } from 'src/app/models/itempedidoDTO';
import { Pedido } from 'src/app/models/pedido';
import { PedidoDTO } from 'src/app/models/pedidoDTO';
import { PedidoService } from "../../../services/pedido.service";
import { TokenService } from '../../login/service/token.service';
 


@Component({
  selector: 'app-editar',
  templateUrl: './editarpedido.component.html'
   
})
export class EditarPedidoComponent implements OnInit {

  constructor(   
    private pedidoService: PedidoService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
    private tokenService: TokenService) { 
    
  }
  pedido: PedidoDTO;

  items: ItemPedidoDTO[] = null;
  roles: string[];
  isAdmin = false;
   

  
    ngOnInit(): void {
    //this.cargarItems();
    this.items = this.pedidoService.getPedido().itemProductoDtoList;
    this.pedido = this.pedidoService.getPedido();
    this.roles = this.tokenService.getAuthorities();
    this.roles.forEach(rol => {
      if (rol === 'ROLE_ADMIN') {
        this.isAdmin = true;
      }
    });

     }
  
  nuevo(){
    this.router.navigate(['clientes']);
   }
   salir(){
     this.router.navigate(['cajeros']);
   }


   borrar(id: number) {
    this.pedidoService.delete(id).subscribe(
      data => {
        this.toastr.success('Pedido Eliminado', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.cargarItems();
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000, positionClass: 'toast-top-center',
        });
      }
    );
  }
  cargarItems() {
    const id = this.activatedRoute.snapshot.params.id;
    this.pedidoService.detailCaja(id).subscribe(
      data => {
        
        this.items = data;
        console.log(this.items);
        console.log("dentro de carga de items");
        
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        this.router.navigate(['/']);
      }
    );
  }

  total(a:number, b: number){
    return a*b;
  }
  totalAPagar():number{
    let result:number;
     
    result = 0;
    
     
    this.pedido.itemProductoDtoList.forEach(item => {
       
      result += (item.precio * item.cantidad);
    });
    return result;
  }
}
