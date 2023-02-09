import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/models/producto';
import { Router } from '@angular/router';
import { ProductoService } from 'src/app/services/producto.service';
import { Categoria } from 'src/app/models/categoria';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-nuevo',
  templateUrl: './nuevo.component.html',
  styleUrls: ['./nuevo.component.css']
})
export class NuevoComponent implements OnInit {
   selectedProducto: Producto = new Producto();
  categorias: Categoria[] = [];
  
  constructor(private productoService: ProductoService, private router: Router) {
    this.productoService.listarCategorias()
        .subscribe(response => {
          this.categorias = response.data;
        })
   }
  crearProducto(){    
    // console.log(this.selectedProducto)
    this.productoService.save(this.selectedProducto)
        .subscribe(response => {
          Swal.fire('Producto creado', 'El producto fue creado con éxito', 'success');
          this.router.navigate(['/administrador/productos/page/0']);
        }, error => {
          if(error.status == 400) {
            Swal.fire('Producto existente', `${error.error.mensaje}`, 'info');
          }
          
        })
    
  }


  ngOnInit(): void {
  }

}
