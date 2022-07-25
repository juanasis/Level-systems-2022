package ar.edu.undec.level.mapper;

import ar.edu.undec.level.storage.entity.Caja;
import ar.edu.undec.level.storage.entity.CajaDtoIn;
import ar.edu.undec.level.storage.entity.CajaDtoOut;

public interface IMapper{
    public Caja mapIn(CajaDtoIn cajaDtoIn);
    public CajaDtoOut mapOut(Caja caja);
}
