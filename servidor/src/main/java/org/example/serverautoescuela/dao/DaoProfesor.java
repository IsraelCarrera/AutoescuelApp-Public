package org.example.serverautoescuela.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.dao.modelo.ProfesorEntity;
import org.example.serverautoescuela.dao.utils.Constantes;
import org.example.serverautoescuela.dao.utils.Converters;
import org.example.serverautoescuela.dao.utils.Querys;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.domain.Persona;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class DaoProfesor {
    private final DBConnPool dbConnection;

    @Inject
    public DaoProfesor(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    //GET ALL
    public Either<ApiError, List<Persona>> getAllProfesores() {
        Either<ApiError, List<Persona>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Persona> persona = bd.query(Querys.SELECT_PROFESOR_ALL,
                            BeanPropertyRowMapper.newInstance(ProfesorEntity.class))
                    .stream()
                    .map(Converters::pasarAPersona)
                    .collect(Collectors.toList());
            if (!persona.isEmpty()) {
                resultado = Either.right(persona);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_SE_HA_ENCONTRADO_A_NINGUN_PROFESOR)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET BY DNI
    public Either<ApiError, Persona> getProfesorByDni(String dni) {
        Either<ApiError, Persona> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            Persona persona = bd.query(Querys.SELECT_PROFESOR_BY_DNI,
                            BeanPropertyRowMapper.newInstance(ProfesorEntity.class), dni)
                    .stream()
                    .map(Converters::pasarAPersona)
                    .findFirst().orElse(null);
            if (persona != null) {
                resultado = Either.right(persona);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_A_NINGUN_PROFESOR_CON_DICHO_DNI)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET ALL Profesores
    public Either<ApiError, List<Persona>> getAllProfesoresSinInfo() {
        Either<ApiError, List<Persona>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Persona> persona = bd.query(Querys.SELECT_PROFESOR_NODETALLES,
                            BeanPropertyRowMapper.newInstance(ProfesorEntity.class))
                    .stream()
                    .map(Converters::pasarAPersona)
                    .collect(Collectors.toList());
            if (!persona.isEmpty()) {
                resultado = Either.right(persona);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_A_NINGUN_PROFESOR)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET BY ID clase
    public Either<ApiError, Persona> getProfesorByIdClass(int id) {
        Either<ApiError, Persona> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            Persona persona = bd.query(Querys.SELECT_PROFESOR_BY_CLASE,
                            BeanPropertyRowMapper.newInstance(ProfesorEntity.class), id)
                    .stream()
                    .map(Converters::pasarAPersona)
                    .findFirst().orElse(null);
            resultado = Either.right(persona);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //POST
    public Either<ApiError, Persona> registrarProfesor(Persona persona, String passHash) {
        Either<ApiError, Persona> resultado;
        //Primero registramos los datos en la BBDD de usuario, para ello, preparamos una transacción.
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            //Ahora hacemos el update.
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            bd.update(Querys.INSERT_USUARIO, persona.getDni(), passHash, persona.getTipoPersona());
            //Y posteriormente, hacemos el del profesor
            bd.update(Querys.INSERT_PROFESOR, persona.getDni(), persona.getNombre(), persona.getApellidos(),
                    persona.getTelefono(), persona.getCorreo(), persona.getDireccion(), persona.getMatriculaCoche());
            transactionManager.commit(txStatus);
            resultado = Either.right(persona);
        } catch (DuplicateKeyException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(ApiError.builder().mensaje(Constantes.YA_EXISTE_UN_USUARIO_CON_DICHO_DNI).fecha(LocalDateTime.now()).build());
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //DELETE
    public Either<ApiError, Info> deleteProfesor(String dni) {
        Either<ApiError, Info> resultado;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dbConnection.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate bd = new JdbcTemplate(transactionManager.getDataSource());
            //Primero borramos la del profesor
            bd.update(Querys.DELETE_PROFESOR, dni);
            //Ahora borramos la del usuario.
            bd.update(Querys.DELETE_USUARIO, dni);
            transactionManager.commit(txStatus);
            resultado = Either.right(Info.builder().info(Constantes.SE_HA_BORRADO_CON_EXITO_AL_PROFESOR).build());
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //PUT
    public Either<ApiError, Info> updateProfesor(Persona persona) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_PROFESOR, persona.getNombre(), persona.getApellidos(), persona.getCorreo(),
                    persona.getDireccion(), persona.getMatriculaCoche(), persona.getTelefono(), persona.getDni());
            resultado = Either.right(Info.builder().info(Constantes.CAMBIOS_REALIZADOS_CON_EXITO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }


}
