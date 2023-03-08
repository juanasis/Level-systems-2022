import { Component, Input, OnInit } from '@angular/core';
import { PedidoService } from 'src/app/services/pedido.service';

export interface MozoReporte {
  mozo: string,
  montoTotalPedidos: number
}

@Component({
  selector: 'app-reporte-mozo-chart',
  templateUrl: './reporte-mozo-chart.component.html',
  styleUrls: ['./reporte-mozo-chart.component.css']
})
export class ReporteMozoChartComponent implements OnInit {

  @Input() fecha_desde: Date;
  @Input() fecha_hasta: Date;

  multi: any[] = [];
  view: any[] = [700, 300];

  reporteMozos: MozoReporte[] = [];

  // options
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = true;
  showLegend: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Mozos';
  showYAxisLabel: boolean = true;
  yAxisLabel: string = '$ Pesos';
  legendTitle: string = 'Mozos';


  constructor(private pedidoService: PedidoService) { }

  ngOnInit(): void {
    let fechaHoyFormateada = new Date().toISOString().slice(0,10);

    this.obtenerDatosPorRangoDeFecha(fechaHoyFormateada,fechaHoyFormateada);

    Object.assign(this, { single: this.multi });
  }

  ngOnChanges() {
    if(!this.fecha_desde || !this.fecha_hasta) {
      return
    }
    this.multi= [];

    this.obtenerDatosPorRangoDeFecha(this.fecha_desde.toString(), this.fecha_hasta.toString());
    Object.assign(this, { single: this.multi })

  }

  obtenerDatosPorRangoDeFecha(fecha_desde: string, fecha_hasta: string) {
    this.pedidoService.getReporteMozos(fecha_desde,fecha_hasta)
    .subscribe(response => {
      this.reporteMozos = response.data;
      this.obtenerDatosReporteMozoChart(this.reporteMozos);
    })
  }

  obtenerDatosReporteMozoChart(reportes: MozoReporte[]) {
    const dataPedidos = reportes.reduce((resultado, reporte) => {
      resultado.push([ reporte.mozo, reporte.montoTotalPedidos]);

      return resultado;
    }, []);

    let aux = [];

    const fechaPedidosAcumulado: any[] = dataPedidos.reduce((resultado, fecha) => {
      if (!aux[fecha]) {
        aux[fecha] = fecha;
        resultado.push(aux[fecha]);
      } else {
        aux[fecha][1] += fecha[1];
        
      }
      return resultado;
    }, []);

    let itemsPush = [];
    fechaPedidosAcumulado.forEach(item => {
      let itemPush = {name: item[0], value: item[1]} 
      itemsPush.push(itemPush);
    })
    this.multi = itemsPush;
    this.multi.sort(this.compare)
    this.multi.reverse()
  }


  compare( a: any, b: any ) {
    if ( a.value < b.value ){
      return -1;
    }
    if ( a.value > b.value ){
      return 1;
    }
    return 0;
  }
}
