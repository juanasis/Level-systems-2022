export enum RolNombre {
    ROLE_ADMIN, ROLE_USER, ROLE_MOZO, ROLE_COCINERO, ROLE_CAJERO, ROLE_CLIENTE
}

export class Role {
    id: number;
    rolNombre: string;
    asignado: boolean;
}
