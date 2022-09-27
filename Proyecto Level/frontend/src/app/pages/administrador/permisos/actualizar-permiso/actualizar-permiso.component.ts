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
                      rol.usuarios = []
                      this.roles.forEach(r => {
                        
                        if(rol.id === r.id) {
                          r.asignado = true;
                          
                        }
                      })
                    })
                    console.log(this.roles)
                    console.log(this.permiso)
                  })
                })
          }
        })


  }

  asignarRol(rol: Role): void {
    let rolNuevo: Role = new Role();
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
        rolNuevo.id = rol.id;
        rolNuevo.rolNombre = rol.rolNombre
        this.permiso.roles.push(rolNuevo);
      }
    }else{
      rolNuevo.id = rol.id;
      rolNuevo.rolNombre = rol.rolNombre
      this.permiso.roles.push(rolNuevo);
    }
    console.log(this.permiso.roles)
  }

  actualizar(): void {
    let permisoActualizar = new Permiso();
    permisoActualizar.permisoId = this.permiso.permisoId;
    permisoActualizar.roles = this.mapRolToRolId(this.permiso.roles)
    console.log(permisoActualizar)
    this.authService.actualizarPermiso(permisoActualizar)
        .subscribe(() => {
          this.router.navigate(['/administrador/permisos'])
        }
        )
  }

  mapRolToRolId(roles: Role[]): Role[] {
    return roles.map(r => {
      let rol = new Role();
      rol.id = r.id;
      rol.fecha_creacion = r.fecha_creacion;
      rol.fecha_actualizacion = r.fecha_actualizacion;
      rol.rolNombre = r.rolNombre
      return rol;
    })
  }

  regresar() {
    this.router.navigate(['/administrador/permisos'])
  }

}
