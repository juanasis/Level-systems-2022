import { Component, OnInit } from '@angular/core';
import { MateriaPrima } from 'src/app/models/materia-prima';
import { MateriaPrimaService } from 'src/app/services/materia-prima.service';

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

}
