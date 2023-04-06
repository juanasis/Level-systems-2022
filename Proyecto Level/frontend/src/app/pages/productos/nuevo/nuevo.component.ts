import { switchMap } from 'rxjs/operators';
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

  archivo: File | undefined;
  
  constructor(private productoService: ProductoService, private router: Router) {
    this.productoService.listarCategorias()
        .subscribe(response => {
          this.categorias = response.data;
        })
   }
  crearProducto(){    
    this.productoService.save(this.selectedProducto)
        .pipe(switchMap(response => this.productoService.subirImagen(this.archivo, response.id))
        ).subscribe(() => {
          Swal.fire('Producto creado', 'El producto fue creado con éxito', 'success');
          this.router.navigate(['/administrador/productos/page/0'])
        });
    
  }

  cargarImagen(event: any) {
    let fileInput = event.target.files[0];
    if(!this.validarImagen(fileInput)){
      Swal.fire('Solo está permitido subir imágenes','','info');
      this.archivo = undefined;
      return
    }
    this.archivo = fileInput;
  }

  validarImagen(file: File): boolean {
    const tiposPermitidos = ['image/png', 'image/jpeg', 'image/jpg'];
    if (tiposPermitidos.includes(file.type)) {
      return true;
    }
    return false;
  }


  ngOnInit(): void {
  }

}
