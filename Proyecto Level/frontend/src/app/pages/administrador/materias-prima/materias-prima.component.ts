import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MateriaPrima } from 'src/app/models/materia-prima';
import { MateriaPrimaService } from 'src/app/services/materia-prima.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-materias-prima',
  templateUrl: './materias-prima.component.html',
  styleUrls: ['./materias-prima.component.css']
})
export class MateriasPrimaComponent implements OnInit {

  listaMateriaPrima: MateriaPrima[] = [];

  constructor(private materiaPrimaService: MateriaPrimaService) { }

  ngOnInit(): void {
    this.materiaPrimaService.listarMateriasPrima()
        .subscribe(response => this.listaMateriaPrima = response.data)
  }

  eliminarMateriaPrima(materia_prima_id: number): void {
    Swal.fire({
      title: '¿Está seguro de eliminar la materia prima?',
      text: "Confirmar acción",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.materiaPrimaService.eliminarMateriaPrima(materia_prima_id)
        .subscribe(()=> {
          Swal.fire('Eliminado','La materia prima ha sido eliminada con éxito', 'success')
          this.listaMateriaPrima = this.listaMateriaPrima.filter(m => m.id !== materia_prima_id);
        }, error => {
          Swal.fire('Alerta',error.error.message, 'warning')
        });
      }
    })
  }

}
