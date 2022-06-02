import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { NuevoUsuario } from '../../../login/models/nuevo-usuario';
import { Role } from '../../../login/models/role';
import { AuthService } from '../../../login/service/auth.service';

@Component({
  selector: 'app-empleado-form',
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent implements OnInit {

  nuevoUsuario: NuevoUsuario = new NuevoUsuario();
  roles: Role[] = [];
  rolElegido: string = '';
  mensajeError: string;
  mensajeErrorRoles: string;

  confirmarPassword: string = '';
  mensajeErrorPassword: string = '';

  rolesAsignados: Role[] = [];
  nombreUsuario: string;

  show: boolean = false;

  constructor(private authService: AuthService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    

    this.activatedRoute.params
        .subscribe(params => {
          this.nombreUsuario = params['nombreUsuario'];
          this.authService.obtenerHistorialUsuario(this.nombreUsuario)
              .subscribe(response => console.log(response));
          if(this.nombreUsuario) {
            this.authService.buscarPorNonbreUsuario(this.nombreUsuario)
                .subscribe(response => {
                  this.nuevoUsuario = response.data;
                  this.rolesAsignados = response.data.roles;
                  this.confirmarPassword = this.nuevoUsuario.password;
                  

                  this.authService.getRoles().subscribe(
                    response => {
                      this.roles = response.data;
                      this.rolesAsignados.forEach(rol => {
                        rol.asignado = true;
                        this.roles.forEach(r => {
                          if(rol.id === r.id) {
                            r.asignado = true;
                          }
                        })
                      })
                    }
                  )
                })
          }
        })
        if(this.roles.length == 0){
          this.authService.getRoles().subscribe(response => this.roles = response.data);
        }
        

  }

  password() {
    this.show = !this.show;
}

  crearUsuario() {
    this.mensajeErrorPassword = undefined;
    this.mensajeErrorRoles = undefined;
    this.mensajeError = undefined;

    if(this.confirmarPassword != this.nuevoUsuario.password){
      this.mensajeErrorPassword = 'Las contraseñas no coinciden';
      return;
    }

    let format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

    if(!format.test(this.nuevoUsuario.password)){
      this.mensajeErrorPassword = 'La contraseña necesita un caracter especial';
      
      return
    }

    if(this.rolesAsignados.length == 0) {
      this.mensajeErrorRoles = 'Seleccione al menos 1 rol';
      
      return
    }

    this.nuevoUsuario.roles = this.rolesAsignados;
    console.log(this.nuevoUsuario)
    this.authService.nuevo(this.nuevoUsuario)
        .subscribe(response => {
          Swal.fire(
            'Usuario creado',
            'Se creó el usuario exitosamente!',
            'success'
          )
          this.router.navigate(['/administrador/usuarios']);
        }, err => {
          console.log(err.error.mensaje)
          this.mensajeError = err.error.mensaje;
        });
  }


  actualizarUsuario() {
    this.nuevoUsuario.roles.forEach(r=>r.permisos = [])
    this.nuevoUsuario.roles.forEach(r=>r.usuarios = [])
    if(this.rolesAsignados.length == 0) {
      this.mensajeErrorRoles = 'Seleccione al menos 1 rol';
      return
    }
    this.nuevoUsuario.roles = this.rolesAsignados;
    this.authService.actualizarUsuario(this.nuevoUsuario)
        .subscribe(() => {
          Swal.fire(
            'Usuario actualizado',
            'Se actualizó el usuario exitosamente!',
            'success'
          )
          this.router.navigate(['/administrador/usuarios']);
        }, err => {
          this.mensajeError = err.error.mensaje;
        });
        

  }

  asignarRol(rol: Role): void {

    if(this.rolesAsignados.length > 0){
      let encontrado = false;
      this.rolesAsignados.forEach(r => {
        if(r.id == rol.id){
          this.rolesAsignados = this.rolesAsignados.filter(r1 => r1.id != rol.id);
          encontrado = true;
          return
        }
      })
      if(!encontrado){
        this.rolesAsignados.push(rol);
      }
    }else{
      this.rolesAsignados.push(rol);
    }
    console.log(this.rolesAsignados)
  }

}
