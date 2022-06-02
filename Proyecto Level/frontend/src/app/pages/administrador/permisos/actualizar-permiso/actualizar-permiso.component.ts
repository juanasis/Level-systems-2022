import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Permiso } from 'src/app/models/permiso';
import { Role } from 'src/app/pages/login/models/role';
import { AuthService } from 'src/app/pages/login/service/auth.service';
import { TokenService } from 'src/app/pages/login/service/token.service';

@Component({
  selector: 'app-actualizar-permiso',
  templateUrl: './actualizar-permiso.component.html',
  styleUrls: ['./actualizar-permiso.component.css']
})
export class ActualizarPermisoComponent implements OnInit {
  roles: Role[] = [];
  permiso: Permiso = new Permiso();
  constructor(private authService: AuthService, private router: Router, private activatedRoute: ActivatedRoute, private tokenService: TokenService) { }

  ngOnInit(): void {

    this.activatedRoute.params
        .subscribe(params => {
          let id: number = params['id'];

          if(id){
            this.authService.obtenerPermiso(id)
                .subscribe(response => {
                  this.permiso = response;
                  this.authService.getRoles()
                  .subscribe(response => {
                    this.roles = response.data;
                    this.permiso.roles.forEach(rol => {
                      rol.asignado = true;
                      this.roles.forEach(r => {
                        if(rol.id === r.id) {
                          r.asignado = true;
                        }
                      })
                    })
                  })
                })
          }
        })


  }

  asignarRol(rol: Role): void {

    if(this.permiso.roles.length > 0){
      let encontrado = false;
      this.permiso.roles.forEach(r => {
        if(r.id == rol.id){
          this.permiso.roles = this.permiso.roles.filter(r1 => r1.id != rol.id);
          encontrado = true;
          return
        }
      })
      if(!encontrado){
        this.permiso.roles.push(rol);
      }
    }else{
      this.permiso.roles.push(rol);
    }
    console.log(this.permiso.roles)
  }

  actualizar(): void {
    this.authService.actualizarPermiso(this.permiso)
        .subscribe(() => {
          this.router.navigate(['/administrador/permisos'])
        }
        )
  }

  regresar() {
    this.router.navigate(['/administrador/permisos'])
  }

}
