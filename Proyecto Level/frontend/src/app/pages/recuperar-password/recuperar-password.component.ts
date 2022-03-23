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
