import { Component, OnInit, Input } from '@angular/core';
import { Producto } from 'src/app/models/producto';
import { PedidoService } from 'src/app/services/pedido.service';

export interface PieChartDTo {
  producto: Producto,
  cantidad: number
}

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {

  @Input() fecha_desde: Date;
  @Input() fecha_hasta: Date;
  @Input() categoria: string;


  single: any[] = [];
  view: any[] = [700, 400];

  // options
  gradient: boolean = false;
  showLegend: boolean = true;
  showLabels: boolean = false;
  isDoughnut: boolean = true;
  legendPosition: string = 'right';
  scheme: string = 'cool';

  productosVendidos: PieChartDTo[] = [];

  constructor(private pedidoService: PedidoService) { }

  ngOnInit(): void {
    let fechaHoyFormateada = new Date();
    let fechaDesde = new Date(fechaHoyFormateada.getDate() - 7).toISOString().slice(0,10);
    let fechaHasta = fechaHoyFormateada.toISOString().slice(0,10);
    this.obtenerDatosPorRangoDeFecha(fechaDesde,fechaHasta, this.categoria);

    Object.assign(this, { single: this.single });
  }

  ngOnChanges() {
    if(!this.fecha_desde || !this.fecha_hasta) {
      return
    }
    this.single= [];

    this.obtenerDatosPorRangoDeFecha(this.fecha_desde.toString(), this.fecha_hasta.toString(), this.categoria);
    Object.assign(this, { single: this.single })

  }

  obtenerDatosPorRangoDeFecha(fecha_desde: string, fecha_hasta: string, categoria: string) {
    this.pedidoService.topTresProductosMasVendidos(fecha_desde,fecha_hasta)
    .subscribe(response => {
      this.single = []
      response.data.sort(this.compare);
      response.data.reverse();
      this.productosVendidos = response.data;

      switch(categoria) {
        case 'BEBIDAS':
          this.productosVendidos = this.productosVendidos.filter(p => p.producto.categoria.nombre === 'BEBIDAS');
          this.scheme = 'ocean'
          break;
        case 'COMIDA':
          this.productosVendidos = this.productosVendidos
              .filter(p => 
                p.producto.categoria.nombre === 'PLATO PRINCIPAL' || 
                p.producto.categoria.nombre === 'DESAYUNOS' ||
                p.producto.categoria.nombre === 'SANDWICHES'||
                p.producto.categoria.nombre === 'PIZZAS'||
                p.producto.categoria.nombre === 'PASTAS')
          break;
        case 'POSTRES':
          this.productosVendidos = this.productosVendidos.filter(p => p.producto.categoria.nombre === 'POSTRES')
          this.scheme = 'flame' 
          break;
      }

      this.productosVendidos.forEach((data: PieChartDTo, i) => {
        if(i < 5) {
          this.single.push(
            {
              "name": data.producto.nombre,
              "value": data.cantidad
            }
          )
        }
      })
    })
  }



  compare( a: any, b: any ) {
    if ( a.cantidad < b.cantidad ){
      return -1;
    }
    if ( a.cantidad > b.cantidad ){
      return 1;
    }
    return 0;
  }

}

