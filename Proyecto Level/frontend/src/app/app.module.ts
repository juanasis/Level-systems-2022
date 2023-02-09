import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule,FormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule, routingComponents} from "./app-routing.module";
import { AppComponent } from "./app.component";
import { MenuComponent } from "./components/menu/menu.component";
import { AdministradorComponent } from "./pages/administrador/administrador.component";
import { CajerosComponent } from "./pages/cajeros/cajeros.component";
import { ClientesComponent } from "./pages/clientes/clientes.component";
import { CocinerosComponent } from "./pages/cocineros/cocineros.component";
import { MozosComponent } from "./pages/mozos/mozos.component";
import { PagesModule } from "./pages/pages.module";
import { PedidosComponent } from "./pages/pedidos/pedidos.component";
import { ProductosComponent } from "./pages/productos/productos.component";
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { interceptorProvider } from "src/environments/universal-appInterceptor";
import { EditarPedidoComponent } from "./pages/pedidos/editarpedido/editarpedido.component";
import { UploadFilesComponent } from 'src/app/components/upload-files/upload-files.component';
import { UsuariosComponent } from "./pages/administrador/usuarios/usuarios.component";
import { UsuarioFormComponent } from "./pages/administrador/usuarios/usuario-form/usuario-form.component";
import { RecuperarPasswordComponent } from "./pages/recuperar-password/recuperar-password.component";
import { RolesComponent } from "./pages/administrador/roles/roles.component";
import { RolFormularioComponent } from "./pages/administrador/roles/rol-formulario/rol-formulario.component";
import { CajaActivaComponent } from "./pages/cajeros/caja-activa/caja-activa.component";
import { PaginatorComponent } from "./pages/shared/paginator/paginator.component";
import { MateriasPrimaComponent } from "./pages/administrador/materias-prima/materias-prima.component";
import { FormMateriaPrimaComponent } from "./pages/administrador/materias-prima/form-materia-prima/form-materia-prima.component";
import { HistorialRolComponent } from "./pages/administrador/roles/historial-rol/historial-rol.component";
import { HistorialUsuarioComponent } from "./pages/administrador/usuarios/historial-usuario/historial-usuario.component";
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { DashboardComponent } from "./pages/administrador/dashboard/dashboard.component";
import { LineChartComponent } from "./pages/administrador/dashboard/components/line-chart/line-chart.component";
import { PieChartComponent } from "./pages/administrador/dashboard/components/pie-chart/pie-chart.component";
import { BarChartComponent } from "./pages/administrador/dashboard/components/bar-chart/bar-chart.component";
import { MesasComponent } from "./pages/administrador/mesas/mesas.component";

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    PedidosComponent,
    EditarPedidoComponent,
    ProductosComponent,
    CajerosComponent,
    UsuarioFormComponent,
    MozosComponent,
    UsuariosComponent,
    CocinerosComponent,
    ClientesComponent,
    routingComponents,
    AdministradorComponent,
    UploadFilesComponent,
    RecuperarPasswordComponent,
    RolesComponent,
    RolFormularioComponent,
    CajaActivaComponent,
    PaginatorComponent,
    MateriasPrimaComponent,
    FormMateriaPrimaComponent,
    HistorialRolComponent,
    HistorialUsuarioComponent,
    DashboardComponent,
    LineChartComponent,
    PieChartComponent,
    BarChartComponent,
    MesasComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,  
    PagesModule,
    ToastrModule.forRoot(),
    FormsModule,    
    BrowserAnimationsModule,
    NgxChartsModule,
    HttpClientModule,ReactiveFormsModule
  ],
  providers: [interceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
