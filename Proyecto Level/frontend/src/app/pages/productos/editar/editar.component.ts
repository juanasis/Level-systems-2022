import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
 
import { ProductoI } from '../../../models/producto.interface';
 
import { FormGroup, FormControl, Validator } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ProductoService } from 'src/app/services/producto.service';
import { Producto } from 'src/app/models/producto';
import { Categoria } from 'src/app/models/categoria';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit {
  producto: Producto = new Producto();
  categorias: Categoria[] = [];


  constructor(private productoService: ProductoService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router ) { }

   
  
  ngOnInit() {

    this.productoService.listarCategorias()
        .subscribe(response => this.categorias = response.data)

    const id = this.activatedRoute.snapshot.params.id;
    this.productoService.detail(id).subscribe(
      (data: any) => {
        this.producto = data.data;
        console.log(data.data)
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        this.router.navigate(['/']);
      }
    );
  }

  onUpdate(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.productoService.update(id, this.producto).subscribe(
      data => {
        Swal.fire(
          'Producto Actualizado',
          'El producto se actualizó con éxito',
          'success'
        )
        this.router.navigate(['/administrador/productos/page/0']);
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        // this.router.navigate(['/']);
      }
    );
  }

  compararCategoria(o1: Categoria, o2:Categoria): boolean{
    if(o1 === undefined && o2 === undefined) return true;
    return o1 === null || o2 === null || o1 === undefined || o2 === undefined ? false: o1.id == o2.id;
  }

  prdocutoEstado(estado: string) {
    this.producto.estado = estado;
  }
  

}
