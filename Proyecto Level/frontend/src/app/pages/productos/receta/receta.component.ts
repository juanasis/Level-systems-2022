import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MateriaPrima } from 'src/app/models/materia-prima';
import { Producto } from 'src/app/models/producto';
import { MateriaPrimaService } from 'src/app/services/materia-prima.service';
import { ProductoService } from 'src/app/services/producto.service';
import { RecetaService } from 'src/app/services/receta.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-receta',
  templateUrl: './receta.component.html',
  styleUrls: ['./receta.component.css']
})
export class RecetaComponent implements OnInit {

  recetaDto = {
    nombreRecetaProducto: '',
    listaRecetaItems: []
  }

  listaMateriaPrima: MateriaPrima[] = [];
  listaItemsReceta: any = []
  listaItemRecetaSeleccionada: any = [];

  receta: any = {}

  product: Producto;

  constructor(private activatedRoute: ActivatedRoute, 
    private recetaService: RecetaService, 
    private materiaPrimaService: MateriaPrimaService,
    private router: Router,
    private productoService: ProductoService) { }

  ngOnInit(): void {
    this.activatedRoute.params
        .subscribe(params => {
          const producto_id = params['idProducto'];

          if(producto_id) {
            this.productoService.detail(producto_id).subscribe((response: any) => this.product = response.data);
            this.receta.productoId = producto_id; 
            this.recetaService.buscarRecetaPorProductoId(producto_id)
                .subscribe({
                  next: response => {
                    this.recetaDto = response;
                    console.log(this.recetaDto)

                  },
                  error: error => {
                    if(error.status == 409){
                      this.materiaPrimaService.listarMateriasPrima()
                      .subscribe({
                        next: response => {
                          this.listaMateriaPrima = response.data;
                          this.listaMateriaPrima.forEach(m => {
                            this.listaItemsReceta.push({materiaPrimaId: m.id, 
                              nombreMateriaPrima: m.nombre, descripcionMateriaPrima: m.descripcion, cantidad: 0})
                          })
                          this.recetaDto = undefined;
                          console.log(this.listaItemsReceta)
                        }
                      })
                    }
                  },
                  complete: () => console.log("Receta obtenida!")
                })
          }
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

  crearReceta() {
    this.receta.listaItemsReceta = this.listaItemRecetaSeleccionada;
    if(this.receta.listaItemsReceta.length == 0) {
      Swal.fire('Mensaje','La receta debe tener al menos un ingrediente', 'info')
      return
    }
    this.recetaService.crearReceta(this.receta)
        .subscribe({
          next: response => {
            console.log(response)
          },
          error: error => console.log(error),
          complete: () => {
            console.log("Receta creada!")
            this.router.navigate(['/administrador/productos/page/0'])
            Swal.fire('Receta creada','La receta ha sido creada con éxito', 'success')
          }
        })
  }

  regresarToProductos() {
    this.router.navigate(['/administrador/productos/page/0'])
  }

  actualizar() {
    this.router.navigate([`/administrador/productos/${this.receta.productoId}/receta/editar`]);
  }

  eliminarReceta(): void {

    Swal.fire({
      title: '¿Está seguro de eliminar la receta?',
      text: "Confirmar acción",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.recetaService.eliminarReceta(this.product.id)
        .subscribe(()=> {
          Swal.fire('Eliminado','La receta ha sido eliminada con éxito', 'success')
          this.router.navigate(['/administrador/productos/page/0'])
        })
      }
    })



    
  }


}
