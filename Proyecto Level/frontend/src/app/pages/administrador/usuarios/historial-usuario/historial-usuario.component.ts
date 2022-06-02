import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/pages/login/service/auth.service';

@Component({
  selector: 'app-historial-usuario',
  templateUrl: './historial-usuario.component.html',
  styleUrls: ['./historial-usuario.component.css']
})
export class HistorialUsuarioComponent implements OnInit {

  nombreUsuario: string = '';
  listaHistorial: any = [];

  constructor(private activatedRoute:ActivatedRoute, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params
    .subscribe(params => {
      this.nombreUsuario = params['nombreUsuario'];
      this.authService.obtenerHistorialUsuario(this.nombreUsuario)
          .subscribe(response => this.listaHistorial = response.data);
        })
  }

  regresar() {
    this.router.navigate(['/administrador/usuarios'])
  }

}
