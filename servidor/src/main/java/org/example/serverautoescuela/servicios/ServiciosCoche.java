package org.example.serverautoescuela.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.serverautoescuela.dao.DaoCoche;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Coche;
import org.example.serverautoescuela.domain.Info;

import java.time.LocalDate;
import java.util.List;

public class ServiciosCoche {
    private final DaoCoche dao;

    @Inject
    public ServiciosCoche(DaoCoche dao) {
        this.dao = dao;
    }

    //Coger todos los coches
    public Either<ApiError, List<Coche>> getAllCoches() {
        return dao.getAllCoches();
    }

    //Coger un coche por su matricula
    public Either<ApiError, Coche> getCocheByMatricula(String matricula) {
        return dao.getCocheByMatricula(matricula);
    }

    //Agregar un coche
    public Either<ApiError, Coche> registrarCoche(Coche coche) {
        return dao.registrarCoche(coche);
    }

    //Borrar un coche
    public Either<ApiError, Info> deleteCoche(String matricula) {
        return dao.deleteCoche(matricula);
    }

    //Modificar datos de un coche
    public Either<ApiError, Info> updateCoche(Coche coche) {
        return dao.updateCoche(coche);
    }

    //Revisión de ITV hecha. Se modifica la fecha de la ITV.
    public Either<ApiError, Info> updateCocheITV(LocalDate proximaITV, String matricula) {
        return dao.updateCocheITV(proximaITV, matricula);
    }
}
