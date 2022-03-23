import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authService.getRoles().subscribe(
      response => {
        this.roles = response.data;
      }
    )
  }

  crearUsuario() {
    
    this.nuevoUsuario.roles.push(this.rolElegido);
    this.authService.nuevo(this.nuevoUsuario)
        .subscribe(response => {
          this.router.navigate(['/administrador/usuarios']);
        });
  }

}
