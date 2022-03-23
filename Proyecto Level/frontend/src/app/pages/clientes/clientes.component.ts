import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Pedido } from 'src/app/models/pedido';
import { Pedidovista } from 'src/app/models/pedidovista';
import { Producto } from 'src/app/models/producto';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {
  productos: Producto[] ;
  pedido: Pedido;
  pedidovista: Pedidovista;
  total:number = 0.00
  
  constructor(private http: HttpClient, private router: Router ) {// private mozo: Mozo, private) {  
    this.pedido = new Pedido();
    this.pedidovista = new Pedidovista();
    this.pedido.idmozo= 5;
    this.pedido.idmesa = 8;
     
  }

  agregarProducto(producto: Producto){
    /*chequear si el producto ya existe e incrementar uno*/
    //this.pedido.listadeproductos.push(producto.id, 1);
    this.pedido.agregar(producto);
    this.total = this.pedidovista.agregar(producto);
    
  }
  quitarProducto(producto: Producto){
    this.pedido.quitar(producto);
    this.total = this.pedidovista.quitar(producto);
  }
  guardarpedido(){
    if(this.pedidovista.productos.length === 0){
      alert("seleccione uno o mas productos");
       
    }else if(confirm('Confirme su orden')){
    
      this.http.post("http://localhost:8080/pedidos/agregar", this.pedido).subscribe(
      data  => {
      console.log("POST Request is successful ", data);
      alert("Pedido Realizado con Éxito");
      this.pedido = new Pedido();
      this.pedidovista = new Pedidovista();
      this.pedido.idmozo= 1;
      this.pedido.idmesa = 1;
      this.total = 0;
      },
      error  => {
      
      console.log("Error", error);
      
      }
     
    )
    
    }
    
  }
  
    ngOnInit(): void {
    this.http.get("http://localhost:8080/productos",{responseType: 'json'}).subscribe(
      (resp:any) =>{
      this.productos = resp.data;
      console.log(this.productos);
      
     })

  }


 
}

