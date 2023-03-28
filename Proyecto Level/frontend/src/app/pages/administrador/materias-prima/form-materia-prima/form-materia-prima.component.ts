import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MateriaPrima } from 'src/app/models/materia-prima';
import { MateriaPrimaService } from 'src/app/services/materia-prima.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form-materia-prima',
  templateUrl: './form-materia-prima.component.html',
  styleUrls: ['./form-materia-prima.component.css']
})
export class FormMateriaPrimaComponent implements OnInit {

  materiaPrima: MateriaPrima = new MateriaPrima();

  listaDescripcion: string[] = ['Kilos', 'Litros', 'Unidad', 'Gramos'];

  constructor(private router: Router, 
    private activatedRoute: ActivatedRoute,
    private materiaPrimaService: MateriaPrimaService) { }

  ngOnInit(): void {

    this.activatedRoute.params
        .subscribe(params => {
          let materia_prima_id = params['id'];

          if(materia_prima_id) {
            this.buscarMateriaPrimaPorId(materia_prima_id)
          }
          
        })
  }

  crearMateriaPrima() {
    this.materiaPrimaService.crearMateriaPrima(this.materiaPrima)
        .subscribe(response => {
          Swal.fire(
            'Materia prima creada',
            response.mensaje,
            'success'
          )
          this.router.navigate(['/administrador/materias-prima'])
        }, error => {
          Swal.fire(
            'Alerta',
            error.error.message,
            'warning'
          )
        })
  }

  buscarMateriaPrimaPorId(materia_prima_id: number) {
    this.materiaPrimaService.buscarMateriaPrimaPorId(materia_prima_id)
        .subscribe(response => {
          this.materiaPrima = response.data;
          this.verificarStockMinimo(this.materiaPrima.alertaCantidad);
        })
  }

  actualizarMateriaPrima() {
    this.materiaPrimaService.actualizarMateriaPrima(this.materiaPrima.id, this.materiaPrima)
        .subscribe(response => {
          Swal.fire(
            'Materia prima actualizada',
            'Se actualizó correctamente la materia prima',
            'success'
          )
          this.router.navigate(['/administrador/materias-prima'])
        }, error => {
          Swal.fire(
            'Alerta',
            error.error.message,
            'warning'
          )
        })
  }

  verificarStockMinimo(alertarStockMinimo: boolean) {
    if(alertarStockMinimo) {
      Swal.fire(
        'Aumentar stock',
        'El stock es igual o menor al mínimo',
        'info'
      )
    }
  }

}
