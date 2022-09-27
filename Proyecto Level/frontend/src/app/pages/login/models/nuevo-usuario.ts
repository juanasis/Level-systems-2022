import { Role } from "./role";

export class NuevoUsuario {
    id: number;
    nombre: string;
    apellido: string;
    nombreUsuario: string;
    email: string;
    password: string;
    roles: Role[] = [];
    activo: boolean;
    fecha_creacion: string;
    fecha_actualizacion: string;
    // constructor(nombre: string, nombreUsuario: string, email: string, password: string, roles: any[]) {
    //     this.nombre = nombre;
    //     this.nombreUsuario = nombreUsuario;
    //     this.email = email;
    //     this.password = password;
    //     this.roles = roles;
    // }
}
