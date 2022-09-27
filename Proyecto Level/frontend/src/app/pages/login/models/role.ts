import { Permiso } from "src/app/models/permiso";

export enum RolNombre {
    ROLE_ADMIN, ROLE_USER, ROLE_MOZO, ROLE_COCINERO, ROLE_CAJERO, ROLE_CLIENTE
}

export class Role {
    id: number;
    rolNombre: string;
    fecha_creacion: string;
    fecha_actualizacion: string;
    asignado: boolean;
    permisos: Permiso[];
    usuarios: any[];
}
