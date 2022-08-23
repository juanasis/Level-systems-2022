import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from 'src/app/pages/login/models/role';
import { AuthService } from 'src/app/pages/login/service/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-rol-formulario',
  templateUrl: './rol-formulario.component.html',
  styleUrls: ['./rol-formulario.component.css']
})
export class RolFormularioComponent implements OnInit {
  rol: Role = new Role();
  idRol: number;
  constructor(private activatedRoute: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.activatedRoute.params
        .subscribe(params => {
          this.idRol = params['idRol'];

          if(this.idRol){
            this.authService.obtenerRol(this.idRol)
                .subscribe(response => {
                  this.rol = response.data;
                  console.log(this.rol)
                })
          }
        })
  }

  crearRol() {
    this.authService.crearRol(this.rol)
        .subscribe(() =>{
          Swal.fire(
            'Rol creado',
            'Se creó el rol exitosamente!',
            'success'
          )
          this.router.navigate(['/administrador/roles']);
        }, err => {

          if(err.error.mensaje){
            Swal.fire(
              'Upps',
              err.error.mensaje,
              'info'
            )
            return
          }
          
          Swal.fire(
            'Upps',
            'Ocurrió algo inesperado, inténtelo nuevamente.',
            'error'
          )
        })
  }

  actualizarRol() {
    this.rol.permisos = [];
    this.rol.usuarios = [];
    this.authService.actualizarRol(this.rol)
        .subscribe(() => {
          Swal.fire(
            'Rol actualizado',
            'Se actualizó el rol exitosamente!',
            'success'
          )
          this.router.navigate(['/administrador/roles']);
        }, err => {

          console.log(err)
          if(err.error.mensaje){
            Swal.fire(
              'Upps',
              err.error.mensaje,
              'info'
            )
            return
          }

          Swal.fire(
            'Upps',
            'Ocurrió algo inesperado, inténtelo nuevamente.',
            'error'
          )
        })
  }

}
