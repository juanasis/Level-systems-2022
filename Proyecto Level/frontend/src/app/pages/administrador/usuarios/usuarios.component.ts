import { Component, OnInit } from '@angular/core';
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

}
