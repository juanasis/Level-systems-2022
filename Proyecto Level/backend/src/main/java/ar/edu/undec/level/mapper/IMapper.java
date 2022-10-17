package ar.edu.undec.level.mapper;

import ar.edu.undec.level.storage.entity.*;

public interface IMapper{
    Caja mapInCaja(CajaDtoIn cajaDtoIn);
    CajaDtoOut mapOutCajaDto(Caja caja);
    Receta mapInReceta(RecetaDtoIn dtoIn);
    RecetaDtoOut mapOutRecetaDto(Receta receta);
}
