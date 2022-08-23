import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Categoria } from 'src/app/models/categoria';
import { Producto } from 'src/app/models/producto';
import { ProductoService } from 'src/app/services/producto.service';



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

  listarBebidas(){
    // this.productosArray.forEach(element => {
    //   if (element.categoria = 'bebidas') {
    //     this.bebidasArray.push(element)        
    //   }
    //   console.log("click");
    // });
    
  }
  ngOnInit(): void {
    // this.http.get("http://localhost:8080/productos",{responseType: 'json'}).subscribe(
    //   (resp:any) =>{
    //   this.productosArray = resp.data;
    //  })



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
}
