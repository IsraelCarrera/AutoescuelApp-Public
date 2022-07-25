package org.example.serverautoescuela.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.dao.modelo.ClaseEntity;
import org.example.serverautoescuela.dao.utils.Constantes;
import org.example.serverautoescuela.dao.utils.Converters;
import org.example.serverautoescuela.dao.utils.Querys;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Clase;
import org.example.serverautoescuela.domain.Info;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class DaoClase {
    private final DBConnPool dbConnection;

    @Inject
    public DaoClase(DBConnPool dbConnection) {
        this.dbConnection = dbConnection;
    }

    //GET ALL
    public Either<ApiError, List<Clase>> getAllClases() {
        Either<ApiError, List<Clase>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Clase> clases = bd.query(Querys.SELECT_CLASE_ALL,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class))
                    .stream()
                    .map(Converters::pasarAClase)
                    .collect(Collectors.toList());
            if (!clases.isEmpty()) {
                resultado = Either.right(clases);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_NINGUNA_CLASE)
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
    public Either<ApiError, Clase> getClaseById(int id) {
        Either<ApiError, Clase> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            Clase clase = bd.query(Querys.SELECT_CLASE_BY_ID,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class), id)
                    .stream()
                    .map(Converters::pasarAClase)
                    .findFirst().orElse(null);
            if (clase != null) {
                resultado = Either.right(clase);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.NO_HA_ENCONTRADO_A_UNA_CLASE_CON_DICHO_ID)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET BY IDALUMNO
    public Either<ApiError, List<Clase>> getClaseByDniAlumno(String dniAlumno) {
        Either<ApiError, List<Clase>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Clase> clase = bd.query(Querys.SELECT_CLASE_BY_IDALUMNO,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class), dniAlumno)
                    .stream()
                    .map(Converters::pasarAClase)
                    .collect(Collectors.toList());
            if (!clase.isEmpty()) {
                resultado = Either.right(clase);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.EL_ALUMNO_CON_DICHO_ID_NO_TIENE_CLASES_ASOCIADAS)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //get all clases by two dates (Para ver si hay o no clases en dichas horas)
    public Either<ApiError, List<Clase>> getClaseByDates(LocalDate fecha1, LocalDate fecha2) {
        Either<ApiError, List<Clase>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Clase> clase = bd.query(Querys.SELECT_CLASE_BY_TWO_DATES_OCCUPED,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class),
                            Date.valueOf(fecha1), Date.valueOf(fecha2))
                    .stream()
                    .map(Converters::pasarAClase)
                    .collect(Collectors.toList());
            resultado = Either.right(clase);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //Get all clases by two dates by dniProfesor. Para coger las clases que tiene ese profesor en un determinado espacio de tiempo.
    public Either<ApiError, List<Clase>> getClaseByDatesAndProfesor(String dniProfesor) {
        Either<ApiError, List<Clase>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Clase> clase = bd.query(Querys.SELECT_CLASE_BY_TWO_DATES_PROFESOR,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class), dniProfesor,
                            Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusWeeks(1)))
                    .stream()
                    .map(Converters::pasarAClase)
                    .collect(Collectors.toList());
            resultado = Either.right(clase);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //Get al clases entre dos fechas by alumno.
    public Either<ApiError, List<Clase>> getClaseByDatesAndAlumno(String dniAlumno) {
        Either<ApiError, List<Clase>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Clase> clase = bd.query(Querys.SELECT_CLASE_BY_TWO_DATES_ALUMNO,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class), dniAlumno,
                            Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusWeeks(1)))
                    .stream()
                    .map(Converters::pasarAClase)
                    .collect(Collectors.toList());
            resultado = Either.right(clase);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET BY IDPROFESOR
    public Either<ApiError, List<Clase>> getClaseByDniProfesor(String dniProfesor) {
        Either<ApiError, List<Clase>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Clase> clase = bd.query(Querys.SELECT_CLASE_BY_IDPROFESOR,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class), dniProfesor)
                    .stream()
                    .map(Converters::pasarAClase)
                    .collect(Collectors.toList());
            if (!clase.isEmpty()) {
                resultado = Either.right(clase);
            } else {
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.EL_PROFESOR_CON_DICHO_ID_NO_TIENE_CLASES_ASOCIADAS)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //GET IdProfesorBefore horario actual
    public Either<ApiError, List<Clase>> getClaseByDniProfesorBeforeDate(String dniProfesor) {
        Either<ApiError, List<Clase>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Clase> clase = bd.query(Querys.SELECT_CLASE_BY_IDPROFESOR_BEFORE,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class), dniProfesor,
                            Date.valueOf(LocalDate.now().minusMonths(1)), Date.valueOf(LocalDate.now()))
                    .stream()
                    .map(Converters::pasarAClase)
                    .collect(Collectors.toList());
            resultado = Either.right(clase);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //Get idAlumnoBefore horario actual
    public Either<ApiError, List<Clase>> getClaseByDniAlumnoBeforeDate(String dniAlumno) {
        Either<ApiError, List<Clase>> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            List<Clase> clase = bd.query(Querys.SELECT_CLASE_BY_IDALUMNO_BEFORE,
                            BeanPropertyRowMapper.newInstance(ClaseEntity.class), dniAlumno, Date.valueOf(LocalDate.now()))
                    .stream()
                    .map(Converters::pasarAClase)
                    .collect(Collectors.toList());
            resultado = Either.right(clase);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //POST
    public Either<ApiError, Clase> registrarClase(Clase clase) {
        Either<ApiError, Clase> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.INSERT_CLASE, Date.valueOf(clase.getFecha()), clase.getHorarioInicio(),
                    clase.getDuracion(), clase.getDniProfesor(), clase.getDniAlumno());
            resultado = Either.right(clase);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //DELETE
    public Either<ApiError, Info> deleteClase(int id) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.DELETE_CLASE, id);
            resultado = Either.right(Info.builder().info(Constantes.SE_HA_BORRADO_CON_EXITO_LA_CLASE_INDICADA).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //DELETE
    public Either<ApiError, Info> deleteClaseByDate(String dni, LocalDate fecha) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.DELETE_CLASE_DATE, dni, Date.valueOf(fecha));
            resultado = Either.right(Info.builder().info(Constantes.SE_HA_BORRADO_CON_EXITO_LA_CLASE_INDICADA).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }

    //PUT
    public Either<ApiError, Info> updateClase(Clase clase) {
        Either<ApiError, Info> resultado;
        try {
            JdbcTemplate bd = new JdbcTemplate(dbConnection.getDataSource());
            bd.update(Querys.UPDATE_CLASE, clase.getHorarioInicio(), clase.getDuracion(), clase.getId());
            resultado = Either.right(Info.builder().info(Constantes.CAMBIOS_REALIZADOS_CON_EXITO).build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Converters.apiErrorGeneral());
        }
        return resultado;
    }
}
