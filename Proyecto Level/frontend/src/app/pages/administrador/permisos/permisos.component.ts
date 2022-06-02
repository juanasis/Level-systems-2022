import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Permiso } from 'src/app/models/permiso';
import { Role } from '../../login/models/role';
import { AuthService } from '../../login/service/auth.service';

@Component({
  selector: 'app-permisos',
  templateUrl: './permisos.component.html',
  styleUrls: ['./permisos.component.css']
})
export class PermisosComponent implements OnInit {

  permisos: Permiso[] = [];
  roles: Role[] = [];

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authService.listarPermisos()
        .subscribe(response => {
          this.permisos = response;
          this.authService.getRoles()
          .subscribe(response => {
            this.roles = response.data
            this.roles.forEach(r => {
              this.permisos.forEach(p => {
                r.asignado = false;
                p.roles.forEach(rolPermiso => {
                  if(rolPermiso.id == r.id){
                    r.asignado = true;
                    rolPermiso.asignado = true;
                    return
                  }
                })
                if(!p.roles.find(role => role.id == r.id)){
                  p.roles.push(r)
                }
                
              })
              r.asignado = false;
            })
            
            this.permisos.forEach(permiso => {
              permiso.roles = permiso.roles.sort((a,b) => a.id -b.id);
            })
          });
        })
  }


  goToActualizar(id: number) {
    this.router.navigate(['administrador/permisos/actualizar-permiso/'+id])
  }

}
