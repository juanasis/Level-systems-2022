import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MateriaPrimaService } from 'src/app/services/materia-prima.service';
import { RecetaService } from 'src/app/services/receta.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-receta',
  templateUrl: './editar-receta.component.html',
  styleUrls: ['./editar-receta.component.css']
})
export class EditarRecetaComponent implements OnInit {

  idProducto: number;

  recetaDto = {
    nombreRecetaProducto: '',
    listaRecetaItems: []
  }

  recetaDtoIn = {
    productoId: 0,
    listaItemsReceta: []
  }

  listaItemsReceta: any = []
  listaItemRecetaSeleccionada: any = [];

  constructor(private recetaService: RecetaService,
    private activatedRoute: ActivatedRoute,
    private materiaPrimaService: MateriaPrimaService,
    private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.idProducto = +params['idProducto'];

      if(this.idProducto) {
        this.recetaService.buscarRecetaPorProductoId(this.idProducto)
            .subscribe(response => {
              this.recetaDto = response;
              this.recetaDto.listaRecetaItems.forEach(matePrima => this.listaItemRecetaSeleccionada.push(matePrima));
              
              this.materiaPrimaService.listarMateriasPrima()
                      .subscribe({
                        next: response => {
                          let data = this.filtrarNoRepetidos(response.data, this.recetaDto.listaRecetaItems);
                          data.forEach(m => {
                            this.listaItemsReceta.push({materiaPrimaId: m.id, 
                              nombreMateriaPrima: m.nombre, descripcionMateriaPrima: m.descripcion, cantidad: 0})
                          })
                          
                        }
                      });
            });
      }
    });
  }

  filtrarNoRepetidos(listaMateriaPrima: any[], listaRecetaItems: any[]): any[] {
    const nonRepetitiveObjects = listaMateriaPrima
      .filter(obj1 => !listaRecetaItems.some(obj2 => obj2.materiaPrimaId === obj1.id))
      .concat(listaRecetaItems.filter(obj2 => !listaMateriaPrima.some(obj1 => obj1.id === obj2.materiaPrimaId)));
  
    return nonRepetitiveObjects;
  }

  actualizarReceta() {
    this.recetaDtoIn.productoId = this.idProducto;
    this.recetaDtoIn.listaItemsReceta = this.listaItemRecetaSeleccionada;
    if(this.recetaDtoIn.listaItemsReceta.length == 0) {
      Swal.fire('Mensaje','La receta debe tener al menos un ingrediente', 'info')
      return
    }
    this.recetaService.actualizarReceta(this.idProducto, this.recetaDtoIn)
        .subscribe({
          next: response => {
            
            this.router.navigate([`administrador/productos/${this.idProducto}/receta`])
            Swal.fire('Receta actualizada','La receta ha sido actualizada con éxito', 'success')
          },
          error: error => console.log(error)
        })
  }

  agregarItem(item: any){
    if(item.cantidad == 0 || !item.cantidad){
      Swal.fire('Alerta','La cantidad debe ser mayor a 0', 'info')
      return
    }

    this.listaItemRecetaSeleccionada.push(item)
    this.listaItemsReceta = this.listaItemsReceta.filter(i => item.materiaPrimaId != i.materiaPrimaId)
  }

  quitarItem(item: any){
    item.cantidad = 0
    this.listaItemsReceta.push(item)
    this.listaItemRecetaSeleccionada = this.listaItemRecetaSeleccionada.filter(i => item.materiaPrimaId != i.materiaPrimaId)
  }

  regresar() {
    this.router.navigate([`administrador/productos/${this.idProducto}/receta`]);
  }

}
