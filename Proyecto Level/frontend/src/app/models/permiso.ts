import { Role } from "../pages/login/models/role";

export class Permiso {
    permiso_id: number;
    nombre: string;
    roles: Role[] = [];
}
