import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PedidoDTO } from 'src/app/models/pedidoDTO';
@Component({
  selector: 'app-cocineros',
  templateUrl: './cocineros.component.html',
  styleUrls: ['./cocineros.component.css']
})
export class CocinerosComponent implements OnInit {
  pedidos: PedidoDTO[] = [];
   
    constructor(private http: HttpClient) { }
    ngOnInit(): void {
      this.http.get("http://localhost:8080/pedidos/cocina",{responseType: 'json'}).subscribe(
        (resp:any) =>{
       
        this.pedidos = resp.data;
        console.log( resp.data);
        })
    }
    
    EditEstado(id:number, estado: String){
      console.log(id);
      console.log(estado);
      //setear el estado con un update en el backend 

    }

}
