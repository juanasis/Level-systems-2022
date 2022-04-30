
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Categoria } from 'src/app/models/categoria';
import { Mesa } from 'src/app/models/mesa';
import { Pedido } from 'src/app/models/pedido';
import { Pedidovista } from 'src/app/models/pedidovista';
import { Producto } from 'src/app/models/producto';
import { PedidoService } from 'src/app/services/pedido.service';
import { ProductoService } from 'src/app/services/producto.service';
import Swal from 'sweetalert2'
import { AuthService } from '../login/service/auth.service';
import { TokenService } from '../login/service/token.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {
  productos: Producto[] ;
  pedido: Pedido = new Pedido();
  pedidovista: Pedidovista;
  total:number = 0.00;
  mesaId: number;

  medioDePago: boolean = false;

  categorias: Categoria[] = [];
  productosPorCategoriaSeleccionado: Producto[] = [];
  
  constructor(
              private productoService: ProductoService,
              private pedidoService: PedidoService,
              private tokenService: TokenService,
              private authService: AuthService,
              private activatedRoute: ActivatedRoute ) {// private mozo: Mozo, private) {  
    this.pedidovista = new Pedidovista();
     
  }

  agregarProducto(producto: Producto){
    /*chequear si el producto ya existe e incrementar uno*/
    //this.pedido.listadeproductos.push(producto.id, 1);
    this.pedido.agregar(producto);
    this.total = this.pedidovista.agregar(producto);
    Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: `Se agrego 1x ${producto.nombre} al carrito`,
      showConfirmButton: false,
      timer: 1500
    })
  }

  agregarProductoEnElCarrito(producto: Producto){
    /*chequear si el producto ya existe e incrementar uno*/
    //this.pedido.listadeproductos.push(producto.id, 1);
    this.pedido.agregar(producto);
    this.total = this.pedidovista.agregar(producto);

  }
  quitarProducto(producto: Producto){
    this.pedido.quitar(producto);
    // this.pedido.items = this.pedido.items.filter((i: ItemPedido) => i.producto_id !=  producto.id)
    // console.log(this.pedido.items)
    let productoInfo = this.pedidovista.quitar(producto);
    this.total = productoInfo[1];
    producto = productoInfo[0];
    if(producto.cantidad <= 0) this.pedido.itemsList.filter(i => i.producto.id != producto.id);
  }
  guardarpedido(){

    
    Swal.fire({
      title: '¿Está seguro de realizar su pedido?',
      text: "Confirme su orden",
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar'
    }).then((result) => {
      if (result.isConfirmed) {
        let nombreUsuario: string = this.tokenService.getUserName();
        this.authService.buscarPorNonbreUsuario(nombreUsuario)
            .subscribe(response => {
              this.pedido.mozo.id = response.data.id;
              this.pedidoService.savePedido(this.pedido)
              .subscribe(response => {
                Swal.fire({
                  position: 'center',
                  icon: 'success',
                  title: 'Pedido Realizado',
                  showConfirmButton: false,
                  timer: 1500
                }
      
                )
                setInterval(() => {
                  window.location.reload()
                }, 1300)
            })
        
          
        },
        error  => {
          Swal.fire(
            'Upps',
            'Ocurrió algo inesperado, inténtelo nuevamente.',
            'error'
          )
          })
      }
    })


    
  }
  
    ngOnInit(): void {

      this.activatedRoute.params.subscribe(params => {
        this.mesaId = params['mesaId'];
        if(this.mesaId) {
          this.productoService.listarCategorias()
          .subscribe(response => this.categorias = response.data);
          this.pedido.mesa = new Mesa();
          this.pedido.mesa.id = this.mesaId;

                this.productoService.lista()
                .subscribe((response: any) => {
                  this.productosPorCategoriaSeleccionado = response.data;
                  
                });
              
        }
      })


  }


  seleccionarCategoria(categoriaId: number) {
    this.productosPorCategoriaSeleccionado = [];

    this.productoService.listaProductosPorCategoria(categoriaId)
        .subscribe(response=> this.productosPorCategoriaSeleccionado = response.data)
  }



}

