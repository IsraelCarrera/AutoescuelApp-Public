package org.example.serverautoescuela.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.dao.modelo.IncidenciaEntity;
import org.example.serverautoescuela.dao.utils.Constantes;
import org.example.serverautoescuela.dao.utils.Converters;
import org.example.serverautoescuela.dao.utils.Querys;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Incidencia;
import org.example.serverautoescuela.domain.Info;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class DaoIncidencia {
    private final DBConnPool dbConnection;

    @Inject
    public DaoIncidencia(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    //GET ALL
    public Either<ApiError, List<Incidencia>> getAllIncidencias() {
        Either<ApiError, List<Incidencia>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Incidencia> incidencias = bd.query(Querys.SELECT_INCIDENCIA_ALL,
                            BeanPropertyRowMapper.newInstance(IncidenciaEntity.class))
                    .stream()
                    .map(Converters::pasarAIncidencia)
                    .collect(Collectors.toList());
            if (!incidencias.isEmpty()) {
                resultado = Either.right(incidencias);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_INCIDENCIAS)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET BY ID
    public Either<ApiError, Incidencia> getIncidenciaById(int id) {
        Either<ApiError, Incidencia> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            Incidencia incidencia = bd.query(Querys.SELECT_INCIDENCIA_BY_ID,
                            BeanPropertyRowMapper.newInstance(IncidenciaEntity.class), id)
                    .stream()
                    .map(Converters::pasarAIncidencia)
                    .findFirst().orElse(null);
            if (incidencia != null) {
                resultado = Either.right(incidencia);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_A_UNA_INCIDENCIA_CON_DICHO_ID)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET ALL Incidencias abiertas
    public Either<ApiError, List<Incidencia>> getAllIncidenciasAbiertas() {
        Either<ApiError, List<Incidencia>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Incidencia> incidencias = bd.query(Querys.SELECT_INCIDENCIA_OPEN,
                            BeanPropertyRowMapper.newInstance(IncidenciaEntity.class))
                    .stream()
                    .map(Converters::pasarAIncidencia)
                    .collect(Collectors.toList());
            if (!incidencias.isEmpty()) {
                resultado = Either.right(incidencias);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_INCIDENCIAS_ABIERTAS)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //POST
    public Either<ApiError, Incidencia> registrarIncidencia(Incidencia incidencia) {
        Either<ApiError, Incidencia> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.INSERT_INCIDENCIA, incidencia.getTitulo(), incidencia.getDescripcion(),
                    incidencia.getUbicacion(), incidencia.getFecha(), incidencia.getDniProfesor(),
                    incidencia.getMatricula());
            resultado = Either.right(incidencia);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //DELETE
    public Either<ApiError, Info> deleteIncidencia(int id) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.DELETE_INCIDENCIA, id);
            resultado = Either.right(Info.builder().info(Constantes.SE_HA_BORRADO_CON_EXITO_LA_INCIDENCIA).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //PUT
    public Either<ApiError, Info> updateIncidencia(Incidencia incidencia) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_INCIDENCIA, incidencia.getTitulo(), incidencia.getDescripcion(),
                    incidencia.getUbicacion(), incidencia.getId());
            resultado = Either.right(Info.builder().info(Constantes.CAMBIOS_REALIZADOS_CON_EXITO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    public Either<ApiError, Info> updateIncidenciaCerrada(int idIncidencia) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_INCIDENCIA_RESUELTA, idIncidencia);
            resultado = Either.right(Info.builder().info(Constantes.LA_INCIDENCIA_SE_HA_MARCADO_COMO_RESUELTA).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

}
