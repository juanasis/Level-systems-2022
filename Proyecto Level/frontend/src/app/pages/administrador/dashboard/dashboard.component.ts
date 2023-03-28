import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import Swal from 'sweetalert2';
import { AuthService } from '../../login/service/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  currentDate: Date;

  fecha_desde: Date;
  fecha_hasta: Date;

  usuarios$: Observable<any>;

  nroTotalPedidos: number;

  totalGananciaDePedidos: number;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.usuarios$ = this.authService.getUsuarios();
  }

  print() {
    window.print();
  }


}
