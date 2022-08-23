import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { NuevoUsuario } from '../../login/models/nuevo-usuario';
import { AuthService } from '../../login/service/auth.service';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsuariosComponent implements OnInit {
  usuarios: NuevoUsuario[] = [];

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.getUsuarios()
        .subscribe(response => this.usuarios = response.data);
  }

  eliminarUsuario(nombreUsuario: string) {
    Swal.fire({
      title: '¿Está seguro de eliminar al usuario?',
      text: "Confirme la acción",
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {

      if (result.isConfirmed) {
        this.authService.eliminarUsuario(nombreUsuario)
        .subscribe(() => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Usuario eliminado',
            showConfirmButton: false,
            timer: 1000
          })
          this.usuarios = this.usuarios.filter(r => r.nombreUsuario != nombreUsuario);
        },err => {
          Swal.fire(
            'Upps',
            'No se puede eliminar un usuario relacionado con pedidos.',
            'error'
          )
        })
      }
    })
  }

  habilitarOrDeshabilitarUsuario(usuario: NuevoUsuario, estado: boolean) {
    let usuarioActualizar = new NuevoUsuario();
    usuarioActualizar.activo = estado;
    usuarioActualizar.id = usuario.id;
    this.authService.actualizarUsuario(usuarioActualizar)
        .subscribe(response => {
          Swal.fire({
            title: 'Usuario actualizado',
            text: "Se actualizó al usuario",
            icon: 'success'
          })
          usuario.activo = estado;
        }, err => {
          Swal.fire({
            title: 'Upss',
            text: "Ocurrió algo inesperado. Inténtelo nuevamente",
            icon: 'error'
          })
        })
  }

}
