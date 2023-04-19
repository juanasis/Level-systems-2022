import { Component, Input, OnInit } from '@angular/core';
import { Pedido } from 'src/app/models/pedido';
import { PedidoService } from 'src/app/services/pedido.service';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {

  @Input() fecha_desde: Date;
  @Input() fecha_hasta: Date;

  single: any[] = [];
  multi: any[];

  view: any[] = [700, 400];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Método de pago';
  showYAxisLabel = true;
  yAxisLabel = 'Cantidad';

  pedidosTotales: Pedido[] = [];


  constructor(private pedidoService: PedidoService) { 
    Object.assign(this, { single: this.single })
  }

  ngOnInit(): void {
    let fechaHoyFormateada = new Date();
    let fechaHasta = fechaHoyFormateada.toISOString().slice(0,10);
    fechaHoyFormateada.setDate(fechaHoyFormateada.getDate() - 7);
    const fechaDesde = fechaHoyFormateada.toISOString().substr(0, 10);
    this.obtenerPedidosPorRangoFecha(fechaDesde, fechaHasta);
  }

  obtenerPedidosPorRangoFecha(fechaInicio: string, fechaFin: string) {
    this.pedidoService.obtenerPedidosPorRangoDeFecha(fechaInicio,fechaFin)
        .subscribe(response => {
          this.single = []
          let nroPagoPorTarjetaDebito = 0;
          let nroPagoPorTarjetaCredito = 0;
          let nroPagoMercadoPago = 0;
          let nroPagoPorEfectivo = 0;

          response.data.forEach(data => {
            data.pedidos.forEach(p => this.pedidosTotales.push(p))
          })

          this.pedidosTotales = this.pedidosTotales.filter(p => p.estado === 'PAGADO')

          this.pedidosTotales.forEach(pedido => {
            if(pedido.tipoPago != null && pedido.tipoPago == 'EFECTIVO') {
              nroPagoPorEfectivo++;
            }

            if(pedido.tipoPago != null && pedido.tipoPago == 'TARJETA_DEBITO') {
              nroPagoPorTarjetaDebito++;
            }

            if(pedido.tipoPago != null && pedido.tipoPago == 'TARJETA_CREDITO') {
              nroPagoPorTarjetaCredito++;
            }

            if(pedido.tipoPago != null && pedido.tipoPago == 'MERCADO_PAGO') {
              nroPagoMercadoPago++;
            }


          })

           this.single.push(
              {
                "name": 'Efectivo',
                "value": nroPagoPorEfectivo
              },
              {
                "name": 'T. Debito',
                "value": nroPagoPorTarjetaDebito
              },
              {
                "name": 'T. crédito',
                "value": nroPagoPorTarjetaCredito
              },
              {
                "name": 'Mercado Pago',
                "value": nroPagoMercadoPago
              }
            )
        })
  }

  ngOnChanges() {
    if(!this.fecha_desde || !this.fecha_hasta) {
      return
    }
    this.single= [];

    this.obtenerPedidosPorRangoFecha(this.fecha_desde.toString(), this.fecha_hasta.toString());
    Object.assign(this, { single: this.single })

  }

}
