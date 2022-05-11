import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { Role } from '../../login/models/role';
import { AuthService } from '../../login/service/auth.service';

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {

  roles: Role[] = [];

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.getRoles()
        .subscribe(response => this.roles = response.data)
  }

  eliminarRol(id: number) {
    
    Swal.fire({
      title: '¿Está seguro de eliminar?',
      text: "Confirme la acción",
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.authService.eliminarRol(id)
        .subscribe(() => {
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Rol eliminado',
            showConfirmButton: true
          })

          this.roles = this.roles.filter(r => r.id != id);
        },err => {
          Swal.fire(
            'Upps',
            'Ocurrió algo inesperado, inténtelo nuevamente.',
            'error'
          )
        }
        )

      }
    
    })
  }
}
