import { Component, OnInit } from '@angular/core';

import { LoginUsuario } from './models/login-usuario';
import { TokenService } from './service/token.service';
import { AuthService } from './service/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EmailDto } from './models/email-dto';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  forgotPasswordActive: boolean = false;
  emailDto: EmailDto = new EmailDto();
  emailSentMessageSuccess: string;
  emailSentMessageError: string;

  isLogged = false;
  isLoginFail = false;
  loginUsuario: LoginUsuario;
  nombreUsuario: string;
  password: string;
  roles: string[] = [];
  errMsj: string;
  constructor( private tokenService: TokenService,
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.isLoginFail = false;
      this.roles = this.tokenService.getAuthorities();
    }
  }
  
  onLogin(): void {
    this.loginUsuario = new LoginUsuario(this.nombreUsuario, this.password);
    this.authService.login(this.loginUsuario).subscribe(
      data => {
        this.isLogged = true;
        console.log("adentro login");
        console.log(data)
        this.tokenService.setToken(data.token);
        this.tokenService.setUserName(data.nombreUsuario);
        this.tokenService.setAuthorities(data.authorities);
        this.roles = data.authorities;
        this.toastr.success('Bienvenido ' + data.nombreUsuario, 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });


        
        this.router.navigate(['/']);
        this.tokenService.sendLoginStatus(true, this.tokenService.getAuthorities());
        
        this.authService.buscarPorNonbreUsuario(data.nombreUsuario)
        .subscribe(response => {
          let permisos: string[] = [];
          response.data.roles.forEach(rol => {
            console.log(rol)
            rol.permisos.forEach(permiso => {
              if(permisos.includes(permiso.nombre)) return
              permisos.push(permiso.nombre);
            })
          })
          this.tokenService.setPermisos(permisos);
          this.tokenService.sendLoginStatus(true, this.tokenService.getPermisos());
        })
      },
      err => {
        this.isLogged = false;
        console.log(err)
        this.errMsj = "Usuario y/o contraseña inválido."
        this.toastr.error(this.errMsj, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
        // console.log(err.error.message);
      }
    );
  }

  goToForgotPassword(){
    this.forgotPasswordActive = true;
  }

  goToLogin(){
    this.forgotPasswordActive = false;
  }

  sendEmail() {
    this.authService.sendEmail(this.emailDto)
        .subscribe(response => {
          console.log(response)
          this.emailSentMessageSuccess = response.mensaje;
          this.emailSentMessageError = undefined;
        },
        err => {
          console.log(err.error.mensaje);
          this.emailSentMessageError = err.error.mensaje;
          this.emailSentMessageSuccess = undefined;
        })
  }


}
