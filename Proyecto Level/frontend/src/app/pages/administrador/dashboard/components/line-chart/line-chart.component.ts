import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Pedido } from 'src/app/models/pedido';
import { PedidoService } from 'src/app/services/pedido.service';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent implements OnInit {

  @Input() fecha_desde: Date;
  @Input() fecha_hasta: Date;

  multi: any[];

  // options
  legend: boolean = false;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  showYAxisLabel: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Fecha';
  yAxisLabel: string = 'Pesos $';
  timeline: boolean = false;

  colorScheme = {
    domain: ['rgb(122, 163, 229)']
  };

  pedidosPorFecha: any[] = [];

  
  @Output() totalPedidos = new EventEmitter<number>();

  @Output() totalGananciaDePedidos = new EventEmitter<number>();

  constructor(private pedidoService: PedidoService) { }

  ngOnInit(): void {
    let fechaHoyFormateada = new Date().toISOString().slice(0,10);
    this.obtenerDatosPorRangoDeFecha(fechaHoyFormateada,fechaHoyFormateada);
  }

  ngOnChanges() {
    if(!this.fecha_desde || !this.fecha_hasta) {
      return
    }
    this.multi= [];

    this.obtenerDatosPorRangoDeFecha(this.fecha_desde.toString(), this.fecha_hasta.toString());
    Object.assign(this, { multi: this.multi })

  }

  obtenerDatosPorRangoDeFecha(fecha_desde: string, fecha_hasta: string) {
    this.pedidoService.obtenerPedidosPorRangoDeFecha(fecha_desde,fecha_hasta)
    .subscribe(response => {
      this.pedidosPorFecha = response.data
      console.log(response.data.length,'data')
      this.multi = [];
      
      this.multi.push({
        "name": "$",
        "series": []
      })

      this.cargarDatosSeries(response.data)

      this.multi[0].series.sort(this.compare)

      

      
      let pedidosTotales: Pedido[] = [];
      
      response.data.forEach(data => {
        data.pedidos.forEach(p => pedidosTotales.push(p))
      })
      this.enviarGananciaDelosPedidosTerminados(pedidosTotales)
      console.log(pedidosTotales)

      this.enviarNumeroTotalPedidos(pedidosTotales.length)

      Object.assign(this, { multi: this.multi });
    })
  }

  cargarDatosSeries(data: any) {
    data.forEach(data => {
      this.multi[0].series.push(
        {
          "name": this.convertirFecha(data.fecha.substring(5, data.fecha.length)),
          "value": this.obtenerMontoTotalPorFecha(data.pedidos)
        }
      )
    })
  }

  convertirFecha(fecha: string): string {
    let fechaDividida: string[] = fecha.split('-');
    return fechaDividida[1]+'-'+fechaDividida[0];
  }


  obtenerMontoTotalPorFecha(pedidos: Pedido[]): number {
    let total = 0;
    pedidos.forEach(p => {
      p.itemsList.forEach(i => {
        total = total + (i.cantidad * i.precio)
      })
    })

    return total;
  }

  enviarNumeroTotalPedidos(nroTotalPedidos: number) {
    console.log('emitiendo totalPedidos', nroTotalPedidos)
    this.totalPedidos.emit(nroTotalPedidos)
  }

  enviarGananciaDelosPedidosTerminados(pedidos: Pedido[]){
    
    let total = 0;
    pedidos.forEach(p => {
      if(p.estado.includes('PAGADO')){
          p.itemsList.forEach(i => {
            total = total + (i.cantidad * i.precio)
          })
      }
    })
    console.log(total, 'emitiendo ganancia')
    this.totalGananciaDePedidos.emit(total);
  }

  compare( a: any, b: any ) {
    if ( a.name < b.name ){
      return -1;
    }
    if ( a.name > b.name ){
      return 1;
    }
    return 0;
  }

}
