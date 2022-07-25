package org.example.serverautoescuela.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.serverautoescuela.dao.DaoIncidencia;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Incidencia;
import org.example.serverautoescuela.domain.Info;

import java.util.List;

public class ServiciosIncidencia {
    private final DaoIncidencia dao;

    @Inject
    public ServiciosIncidencia(DaoIncidencia dao) {
        this.dao = dao;
    }

    //Coger todas las incidencias
    public Either<ApiError, List<Incidencia>> getAllIncidencias() {
        return dao.getAllIncidencias();
    }

    //Coger todas las incidencias abiertas
    public Either<ApiError, List<Incidencia>> getAllIncidenciasAbiertas() {
        return dao.getAllIncidenciasAbiertas();
    }

    //Coger incidencias por ID
    public Either<ApiError, Incidencia> getIncidenciaById(int id) {
        return dao.getIncidenciaById(id);
    }

    //Añadir una incidencia
    public Either<ApiError, Incidencia> registrarIncidencia(Incidencia incidencia) {
        return dao.registrarIncidencia(incidencia);
    }

    //Borrar una incidencia
    public Either<ApiError, Info> deleteIncidencia(int id) {
        return dao.deleteIncidencia(id);
    }

    //Modificar una incidencia
    public Either<ApiError, Info> updateIncidencia(Incidencia incidencia) {
        return dao.updateIncidencia(incidencia);
    }

    //Modificar una incidencia para cerrarla
    public Either<ApiError, Info> updateIncidenciaCerrar(int id) {
        return dao.updateIncidenciaCerrada(id);
    }

}
