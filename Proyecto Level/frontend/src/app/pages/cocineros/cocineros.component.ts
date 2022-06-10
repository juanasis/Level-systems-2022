import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PedidoDTO } from 'src/app/models/pedidoDTO';
import { Pedido } from 'src/app/models/pedido';
import { PedidoService } from 'src/app/services/pedido.service';
@Component({
  selector: 'app-cocineros',
  templateUrl: './cocineros.component.html',
  styleUrls: ['./cocineros.component.css']
})
export class CocinerosComponent implements OnInit {
 // pedidos: PedidoDTO[] = [];
   
  pedidos: Pedido[] = [];
    constructor(private pedidosService : PedidoService) { }
    ngOnInit(): void {
      
       
        this.pedidosService.lista().subscribe(
          (resp:any) =>{
            this.pedidos = resp.data;
         
          console.log(this.pedidos);
           
            });
        console.log( this.pedidos);
        
    }
    
    EditEstado(id:number, estado: String){
      console.log(id);
      console.log(estado);
      //setear el estado con un update en el backend 

    }

}
