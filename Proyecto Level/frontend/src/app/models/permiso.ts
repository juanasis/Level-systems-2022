import { Role } from "../pages/login/models/role";

export class Permiso {
    permisoId: number;
    nombre: string;
    roles: Role[] = [];
}
