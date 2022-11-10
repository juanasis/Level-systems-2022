import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChangePassword } from '../login/models/change-password';
import { AuthService } from '../login/service/auth.service';

@Component({
  selector: 'app-recuperar-password',
  templateUrl: './recuperar-password.component.html',
  styleUrls: ['./recuperar-password.component.css']
})
export class RecuperarPasswordComponent implements OnInit {

  changePassword: ChangePassword = new ChangePassword();

  errorMessage: string;
  successMessage: string;

  mensajeErrorPassword: string;

  constructor(private activeRoute: ActivatedRoute, private authService: AuthService) { }

  ngOnInit(): void {

    this.activeRoute.params
        .subscribe(params => {
          let token: string = params['token'];

          if(token) {
            this.changePassword.tokenPassword = token;
            this.authService.changePassword(this.changePassword)
                .subscribe(response => {

                })
          }
        })

  }

  changePasswordUser() {



    if(this.changePassword.password != this.changePassword.confirmPassword){
      this.mensajeErrorPassword = 'Las contraseñas no coinciden';
      return;
    }

    if(this.changePassword.password.length < 6) {
      this.mensajeErrorPassword = 'La contraseña debe tener mínimo 6 caracteres.';
      return;
    }

    let format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

    if(!format.test(this.changePassword.password)){
      this.mensajeErrorPassword = 'La contraseña necesita un caracter especial';
      
      return
    }




    this.authService.changePassword(this.changePassword)
        .subscribe(response => {
          this.successMessage = response.mensaje;
          this.errorMessage = undefined;
        },
        err => {
          this.errorMessage = err.error.mensaje;
          this.successMessage = undefined;
        })
  }

}
