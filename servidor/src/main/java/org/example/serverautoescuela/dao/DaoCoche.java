package org.example.serverautoescuela.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.dao.modelo.CocheEntity;
import org.example.serverautoescuela.dao.utils.Constantes;
import org.example.serverautoescuela.dao.utils.Converters;
import org.example.serverautoescuela.dao.utils.Querys;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Coche;
import org.example.serverautoescuela.domain.Info;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class DaoCoche {
    private final DBConnPool dbConnection;

    @Inject
    public DaoCoche(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    //GET ALL
    public Either<ApiError, List<Coche>> getAllCoches() {
        Either<ApiError, List<Coche>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Coche> coches = bd.query(Querys.SELECT_COCHE_ALL,
                            BeanPropertyRowMapper.newInstance(CocheEntity.class))
                    .stream()
                    .map(Converters::pasarACoche)
                    .collect(Collectors.toList());
            if (!coches.isEmpty()) {
                resultado = Either.right(coches);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_COCHES)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET BY MATRICULA
    public Either<ApiError, Coche> getCocheByMatricula(String matricula) {
        Either<ApiError, Coche> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            Coche coche = bd.query(Querys.SELECT_COCHE_BY_MATRICULA,
                            BeanPropertyRowMapper.newInstance(CocheEntity.class), matricula)
                    .stream()
                    .map(Converters::pasarACoche)
                    .findFirst().orElse(null);
            if (coche != null) {
                resultado = Either.right(coche);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_A_UN_COCHE_CON_DICHO_ID)
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
    public Either<ApiError, Coche> registrarCoche(Coche coche) {
        Either<ApiError, Coche> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.INSERT_COCHE, coche.getMatricula(), coche.getMarca(), coche.getModelo(),
                    coche.getProximaItv(), coche.getFechaCreacion(), coche.getFechaCompra(), coche.getDniAdmin());
            resultado = Either.right(coche);
        } catch (DuplicateKeyException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(ApiError.builder().mensaje(Constantes.YA_EXISTE_UN_COCHE_CON_DICHA_MATRICULA).fecha(LocalDateTime.now()).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //DELETE
    public Either<ApiError, Info> deleteCoche(String matricula) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.DELETE_COCHE, matricula);
            resultado = Either.right(Info.builder().info(Constantes.SE_HA_BORRADO_CON_EXITO_AL_COCHE).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //PUT
    public Either<ApiError, Info> updateCoche(Coche coche) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_COCHE, coche.getMarca(), coche.getModelo(), coche.getProximaItv(),
                    coche.getFechaCreacion(), coche.getFechaCompra(), coche.getMatricula());
            resultado = Either.right(Info.builder().info(Constantes.CAMBIOS_REALIZADOS_CON_EXITO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //PUT UPDATE ITV
    public Either<ApiError, Info> updateCocheITV(LocalDate proximaITV, String matricula) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_COCHE_ITV_PASADA, proximaITV, matricula);
            resultado = Either.right(Info.builder().info(Constantes.ITV_REGISTRADA_CON_EXITO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }
}
