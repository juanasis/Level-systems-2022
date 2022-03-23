import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
 
import { ProductoI } from '../../../models/producto.interface';
 
import { FormGroup, FormControl, Validator } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ProductoService } from 'src/app/services/producto.service';
import { Producto } from 'src/app/models/producto';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit {
  producto: Producto = null;
  constructor(private productoService: ProductoService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router ) { }

   
  
  ngOnInit() {
    const id = this.activatedRoute.snapshot.params.id;
    this.productoService.detail(id).subscribe(
      data => {
        this.producto = data;
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
        this.toastr.success('Producto Actualizado', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.router.navigate(['/administrador/productos']);
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        // this.router.navigate(['/']);
      }
    );
  }
  

}
