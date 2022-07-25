package org.example.serverautoescuela.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.dao.modelo.UsuarioEntity;
import org.example.serverautoescuela.dao.utils.Constantes;
import org.example.serverautoescuela.dao.utils.Converters;
import org.example.serverautoescuela.dao.utils.Querys;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Info;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

@Log4j2
public class DaoUsuario {
    private final DBConnPool dbConnection;

    @Inject
    public DaoUsuario(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    // --------- USUARIO ---------//
    //GET Para login.
    public Either<ApiError, UsuarioEntity> getUsuarioDni(String dni) {
        Either<ApiError, UsuarioEntity> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            UsuarioEntity persona = bd.query(Querys.SELECT_USUARIO_BY_DNI,
                    BeanPropertyRowMapper.newInstance(UsuarioEntity.class),
                    dni).stream().findFirst().orElse(null);
            if (persona != null) {
                //Aquí buscamos a la persona en concreto para llevarla.
                resultado = Either.right(persona);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.USUARIO_NO_ENCONTRADO)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //PUT UPDATE CAMBIO PASS
    public Either<ApiError, Info> updateCambioPass(String dni, String passHash) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_USUARIO_CAMBIO_PASS, passHash, dni);
            resultado = Either.right(Info.builder().info(Constantes.CONTRASENIA_CAMBIADA_CON_EXITO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

}
