import { Component, OnInit, Input } from '@angular/core';
import { PedidoService } from 'src/app/services/pedido.service';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {

  @Input() fecha_desde: Date;
  @Input() fecha_hasta: Date;


  single: any[] = [];
  view: any[] = [700, 400];

  // options
  gradient: boolean = false;
  showLegend: boolean = true;
  showLabels: boolean = false;
  isDoughnut: boolean = true;
  legendPosition: string = 'right';

  productosVendidos: any[] = [];

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  constructor(private pedidoService: PedidoService) { }

  ngOnInit(): void {

    let fechaHoyFormateada = new Date().toISOString().slice(0,10);

    this.obtenerDatosPorRangoDeFecha(fechaHoyFormateada,fechaHoyFormateada);

    Object.assign(this, { single: this.single });
  }

  ngOnChanges() {
    if(!this.fecha_desde || !this.fecha_hasta) {
      return
    }
    this.single= [];

    this.obtenerDatosPorRangoDeFecha(this.fecha_desde.toString(), this.fecha_hasta.toString());
    Object.assign(this, { single: this.single })

  }

  obtenerDatosPorRangoDeFecha(fecha_desde: string, fecha_hasta: string) {
    this.pedidoService.topTresProductosMasVendidos(fecha_desde,fecha_hasta)
    .subscribe(response => {
      this.single = []
      response.data.sort(this.compare);
      response.data.reverse();
      this.productosVendidos = response.data;
      response.data.forEach((data, i) => {
        if(i < 5) {
          this.single.push(
            {
              "name": data.producto,
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

