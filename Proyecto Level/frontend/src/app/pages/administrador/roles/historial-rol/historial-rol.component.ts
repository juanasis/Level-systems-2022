import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/pages/login/service/auth.service';

@Component({
  selector: 'app-historial-rol',
  templateUrl: './historial-rol.component.html',
  styleUrls: ['./historial-rol.component.css']
})
export class HistorialRolComponent implements OnInit {

  idRol: number;
  listaHistorial: any = [];

  constructor(private activatedRoute:ActivatedRoute, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params
    .subscribe(params => {
      this.idRol = params['idRol'];
      this.authService.obtenerHistorialRol(this.idRol)
          .subscribe(response => this.listaHistorial = response.data);
        })
  }

  regresar() {
    this.router.navigate(['/administrador/roles'])
  }
}
