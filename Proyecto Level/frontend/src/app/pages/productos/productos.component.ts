import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Categoria } from 'src/app/models/categoria';
import { Producto } from 'src/app/models/producto';
import { ProductoService } from 'src/app/services/producto.service';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {

  productosArray: Producto[];
  bebidasArray: Producto[];
  oldproducto: Producto = new Producto();
  selectedProducto: Producto = new Producto();
  categorias: Categoria[] = [];
  productosPorCategoriaSeleccionado: Producto[] = [];
  filtroActivado: boolean = false;

  paginador: any;

  constructor(private http: HttpClient, private router: Router, private productoService: ProductoService, private activatedRoute: ActivatedRoute) { }

  
  ngOnInit(): void {

    this.activatedRoute.paramMap
    .subscribe(params => {

      let page: number = +params.get('page');

      if(!page) {page = 0}

      this.productoService.listaProductodPageable(page)
      .subscribe(response => {
        this.productoService.listarCategorias()
          .subscribe(response => this.categorias = response.data);
        this.productosArray = response.data.content as Producto[];
        this.paginador = response.data;
      })
    });
  }

  filtrarProductosPorNombre(filtroNombre: string) {
    this.productoService.filtrarProductosPorNombre(filtroNombre)
    .subscribe(response => {
      this.filtroActivado = true;
      this.productosPorCategoriaSeleccionado = [];
      this.productosPorCategoriaSeleccionado = response.data;
    });
  }

  seleccionarCategoria(categoriaId: number) {

    this.productoService.listaProductosPorCategoria(categoriaId)
        .subscribe(response=> {
          this.filtroActivado = true;
          this.productosPorCategoriaSeleccionado = [];
          this.productosPorCategoriaSeleccionado = response.data;
        })
  }

  mostrarTodosLosProductos(){
    this.filtroActivado = false;
  }

  editarProducto(id){
    this.router.navigate(['editar',id]);
  }

  eliminarProducto(event: Event, productoId: number) {
    event.stopPropagation();
    Swal.fire({
      title: '¿Está seguro de eliminar el producto?',
      text: "Confirmar acción",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.productoService.delete(productoId)
        .subscribe(()=> {
          Swal.fire('Eliminado','El producto ha sido eliminado con éxito', 'success')
          this.router.navigate(['/administrador/productos/page/0'])
          this.productoService.listaProductodPageable(0).subscribe(response => {
            this.productosArray = response.data.content as Producto[];
            this.paginador = response.data;
          });
        }, error => {
          Swal.fire('Alerta',error.error.message, 'warning')
        });
      }
    })
  }
}
