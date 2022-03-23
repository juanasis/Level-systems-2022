import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Pedido } from 'src/app/models/pedido';
import { PedidoDTO } from 'src/app/models/pedidoDTO';
import { PedidoService } from 'src/app/services/pedido.service';
import { EditarPedidoComponent } from './editarpedido/editarpedido.component';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css']
})
export class PedidosComponent implements OnInit {

  pedidosArray: Pedido[];
  pedidosDTO: PedidoDTO[];
  oldpedido: Pedido = new Pedido();
  selectedPedido: Pedido = new Pedido();

  openForEdit(pedido: Pedido){
    this.selectedPedido = pedido;
  }
  borrar(){
    if(confirm('Estas seguro de eliminar?')){
      this.pedidosArray = this.pedidosArray.filter(x => x != this.selectedPedido);
      
      this.http.delete("http://localhost:8080/pedidos/"+this.selectedPedido.id).subscribe(
        data  => {
        console.log("DELETE Request is successful ", data);
        },
        error  => {
        
        console.log("Error", error);
        
        }
        
        );
        this.selectedPedido=new Pedido();
    }

  }
  constructor(private http: HttpClient, private router: Router,private pedidoService: PedidoService ) { }
  ngOnInit(): void {
    this.http.get("http://localhost:8080/pedidos",{responseType: 'json'}).subscribe(
      (resp:any) =>{
      this.pedidosArray = resp.data;
      this.pedidosDTO = resp.data;
      console.log(this.pedidosDTO);
       
        })
}
 
 
  total(pedido:PedidoDTO):number{
    let result:number;
     
    result = 0;
    
     
    pedido.itemProductoDtoList.forEach(item => {
       
      result += (item.precio * item.cantidad);
    });
    return result;
  }
  editarPedido(pedido: PedidoDTO){
    this.pedidoService.setPedido(pedido);
    this.router.navigate(['editarpedido']);

  }

}
