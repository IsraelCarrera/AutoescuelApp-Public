package org.example.serverautoescuela.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.example.serverautoescuela.dao.DaoAdministrador;
import org.example.serverautoescuela.dao.DaoAlumno;
import org.example.serverautoescuela.dao.DaoProfesor;
import org.example.serverautoescuela.dao.DaoUsuario;
import org.example.serverautoescuela.dao.modelo.UsuarioEntity;
import org.example.serverautoescuela.domain.ApiError;
import org.example.serverautoescuela.domain.Info;
import org.example.serverautoescuela.domain.Persona;
import org.example.serverautoescuela.domain.PersonaLogin;
import org.example.serverautoescuela.servicios.utils.Constantes;
import org.example.serverautoescuela.utils.HashPass;
import org.example.serverautoescuela.utils.MandarMail;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Log4j2
public class ServiciosPersona {
    private final DaoAdministrador daoAdministrador;
    private final DaoAlumno daoAlumno;
    private final DaoProfesor daoProfesor;
    private final DaoUsuario daoUsuario;
    private final HashPass hashPass;
    private final MandarMail mandarMail;

    @Inject
    public ServiciosPersona(DaoAdministrador daoAdministrador, DaoAlumno daoAlumno, DaoProfesor daoProfesor,
                            DaoUsuario daoUsuario, HashPass hashPass, MandarMail mandarMail) {
        this.daoAdministrador = daoAdministrador;
        this.daoAlumno = daoAlumno;
        this.daoProfesor = daoProfesor;
        this.daoUsuario = daoUsuario;
        this.hashPass = hashPass;
        this.mandarMail = mandarMail;
    }

    //Comprobación de la existencia de usuario && pass.
    public Either<ApiError, Persona> hacerLogin(PersonaLogin personaLogin) {
        Either<ApiError, Persona> resultado;
        Either<ApiError, UsuarioEntity> getPersona = daoUsuario.getUsuarioDni(personaLogin.getDni());
        if (getPersona.isRight()) {
            //Ahora comprobamos si la contraseña es correcta.
            if (hashPass.comprobarPass(personaLogin.getPass(), getPersona.get().getPassHash())) {
                //Implica que es correcto, asi que ahora hacemos la llamada correspondiente
                resultado = getPersonas(getPersona);
            } else {
                //La contraseña es incorrecta, ergo mandamos otra vez el mensaje de aviso. No ponemos ue contraseña es
                //incorrecta para no dar información no necesaria.
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.DNI_Y_O_CONTRASENA_INCORRECTOS)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } else {
            resultado = Either.left(getPersona.getLeft());
        }
        return resultado;
    }

    //Registro de una persona
    public Either<ApiError, Info> registrarPersona(Persona persona) {
        Either<ApiError, Info> resultado;
        String pass = generarPassRandom();
        String passHash = hashPass.hashearPass(pass);
        Either<ApiError, Persona> guardarPersona;
        switch (persona.getTipoPersona()) {
            case Constantes.ADMINISTRADOR:
                guardarPersona = daoAdministrador.registrarAdministrador(persona, passHash);
                break;
            case Constantes.PROFESOR:
                guardarPersona = daoProfesor.registrarProfesor(persona, passHash);
                break;
            case Constantes.ALUMNO:
                guardarPersona = daoAlumno.registrarAlumno(persona, passHash);
                break;
            default:
                guardarPersona = Either.left(ApiError.builder()
                        .mensaje(Constantes.EL_USUARIO_NO_TIENE_UN_ROL_ASIGNADO)
                        .fecha(LocalDateTime.now())
                        .build());
                break;
        }
        if (guardarPersona.isRight()) {
            //Se ha guardado correctamente. Ahora ponemos la pass que se mandará por correo a la persona y se lo mandamos.
            //Aqui mandamos el correo para que la persona reciba su contraseña.
            try {
                String cadena = Constantes.EMAIL_SAVE_PERSONA_1 + pass + Constantes.EMAIL_SAVE_PERSONA_2;
                mandarMail.generateAndSendEmail(guardarPersona.get().getCorreo(), cadena, Constantes.EMAIL_DE_INFORMACION);
                resultado = Either.right(Info.builder().info(Constantes.SUJ_SAVE_PERSONA +
                        guardarPersona.get().getNombre() + Constantes.ESPACIO + guardarPersona.get().getApellidos()).build()
                );
            } catch (MessagingException e) {
                log.error(e.getMessage(), e);
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.FALLO_SAVE_PERSONA + pass)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } else {
            resultado = Either.left(guardarPersona.getLeft());
        }
        return resultado;
    }

    //Coger todos los usuarios
    public Either<ApiError, List<Persona>> getAllPersonas() {
        Either<ApiError, List<Persona>> allUsuarios;
        Either<ApiError, List<Persona>> allAdministradores = daoAdministrador.getAllAdministradores();
        Either<ApiError, List<Persona>> allProfesores = daoProfesor.getAllProfesores();
        Either<ApiError, List<Persona>> allAlumnos = daoAlumno.getAllAlumnos();
        List<Persona> all = new ArrayList<>();
        if (allAdministradores.isRight()) {
            all.addAll(allAdministradores.get());
        }
        if (allProfesores.isRight()) {
            all.addAll(allProfesores.get());
        }
        if (allAlumnos.isRight()) {
            all.addAll(allAlumnos.get());
        }
        if (!all.isEmpty()) {
            allUsuarios = Either.right(all);
        } else {
            allUsuarios = Either.left(ApiError.builder()
                    .mensaje(Constantes.NO_SE_HA_ENCONTRADO_A_NINGUN_USUARIO_GUARDADO)
                    .fecha(LocalDateTime.now())
                    .build());
        }
        return allUsuarios;
    }

    //Coger usuario por DNI
    public Either<ApiError, Persona> getPersonaByDni(String dni) {
        Either<ApiError, Persona> resultado;
        //Primero buscamos al usuario en la tabla usuario para que nos de a que tipo pertenece.
        Either<ApiError, UsuarioEntity> getPersona = daoUsuario.getUsuarioDni(dni);
        if (getPersona.isRight()) {
            //Ahora comprobamos si la contraseña es correcta.
            resultado = getPersonas(getPersona);
        } else {
            resultado = Either.left(getPersona.getLeft());
        }
        return resultado;
    }


    //Coger todos los profesores
    public Either<ApiError, List<Persona>> getAllProfesores() {
        return daoProfesor.getAllProfesoresSinInfo();
    }

    //Coger los alumnos no aprobados
    public Either<ApiError, List<Persona>> getAllAlumnosNoAprobados() {
        return daoAlumno.getAllAlumnosNoAprobados();
    }

    //GET alumno por idClase
    public Either<ApiError, Persona> getAlumnoIdClase(int id) {
        return daoAlumno.getAlumnoByIdClass(id);
    }

    //GET profesor por idClase
    public Either<ApiError, Persona> getProfesorIdClase(int id) {
        return daoProfesor.getProfesorByIdClass(id);
    }

    //Borrar usuario
    public Either<ApiError, Info> deletePersona(String dni) {
        Either<ApiError, Info> resultado;
        if (dni.equals(Constantes.DNI_ROOT)) {
            resultado = Either.left(ApiError.builder().mensaje(Constantes.NO_SE_PUEDE_BORRAR_A_ROOT).fecha(LocalDateTime.now()).build());
        } else {
            //Primero buscamos al usuario, para saber que tipo de usuario pertenece.
            Either<ApiError, UsuarioEntity> getPersona = daoUsuario.getUsuarioDni(dni);
            if (getPersona.isRight()) {
                switch (getPersona.get().getTipoUsuario()) {
                    case Constantes.ADMINISTRADOR:
                        resultado = daoAdministrador.deleteAdministrador(dni);
                        break;
                    case Constantes.PROFESOR:
                        resultado = daoProfesor.deleteProfesor(dni);
                        break;
                    case Constantes.ALUMNO:
                        resultado = daoAlumno.deleteAlumno(dni);
                        break;
                    default:
                        resultado = Either.left(ApiError.builder()
                                .mensaje(Constantes.EL_USUARIO_NO_TIENE_UN_ROL_ASIGNADO)
                                .fecha(LocalDateTime.now())
                                .build());
                        break;
                }
            } else {
                resultado = Either.left(getPersona.getLeft());
            }
        }
        return resultado;
    }

    //Modificar usuario
    public Either<ApiError, Info> updatePersona(Persona persona) {
        //Como siempre, primero cojo el usuario para saber que tipo de usuario es.
        Either<ApiError, Info> resultado;
        Either<ApiError, UsuarioEntity> getPersona = daoUsuario.getUsuarioDni(persona.getDni());
        if (getPersona.isRight()) {
            switch (getPersona.get().getTipoUsuario()) {
                case Constantes.ADMINISTRADOR:
                    resultado = daoAdministrador.updateAdministrador(persona);
                    break;
                case Constantes.PROFESOR:
                    resultado = daoProfesor.updateProfesor(persona);
                    break;
                case Constantes.ALUMNO:
                    resultado = daoAlumno.updateAlumno(persona);
                    break;
                default:
                    resultado = Either.left(ApiError.builder()
                            .mensaje(Constantes.EL_USUARIO_NO_TIENE_UN_ROL_ASIGNADO)
                            .fecha(LocalDateTime.now())
                            .build());
                    break;
            }
        } else {
            resultado = Either.left(getPersona.getLeft());
        }
        return resultado;
    }

    //Cambio de pass de un usuario.
    public Either<ApiError, Info> cambioPassUsuario(String dni, String passVieja, String passNueva) {
        Either<ApiError, Info> resultado;
        //Primero se llama al usuario, y se comprueba
        Either<ApiError, UsuarioEntity> eitherPersona = daoUsuario.getUsuarioDni(dni);
        if (eitherPersona.isRight()) {
            //Comprobamos que la pass mandada es correcta, por si acaso.
            if (hashPass.comprobarPass(passVieja, eitherPersona.get().getPassHash())) {
                //Implica que es correcto, asi que ahora hacemos el cambio de pass.
                //Primero hasheamos
                String passHash = hashPass.hashearPass(passNueva);
                //Ahora la mandamos al cambio de pass
                resultado = daoUsuario.updateCambioPass(dni, passHash);
            } else {
                //La contraseña es incorrecta, ergo mandamos otra vez el mensaje de aviso. No ponemos ue contraseña es
                //incorrecta para no dar información no necesaria.
                resultado = Either.left(ApiError.builder()
                        .mensaje(Constantes.DNI_Y_O_CONTRASENA_INCORRECTOS)
                        .fecha(LocalDateTime.now())
                        .build());
            }
        } else {
            resultado = Either.left(eitherPersona.getLeft());
        }
        return resultado;
    }

    public Either<ApiError, Info> reiniciarPass(String dni) {
        Either<ApiError, Info> realizado;
        String correo;
        String pass = generarPassRandom();
        String passHash = hashPass.hashearPass(pass);
        //Primero cojo una persona, y de paso, su correo
        Either<ApiError, Persona> getPersona = getPersonas(daoUsuario.getUsuarioDni(dni));
        if (getPersona.isRight()) {
            correo = getPersona.get().getCorreo();
            Either<ApiError, Info> resultado = daoUsuario.updateCambioPass(dni, passHash);
            if (resultado.isRight()) {
                try {
                    String cadena = Constantes.EMAIL_REINICIAR_PASS_1 + pass + Constantes.EMAIL_REINICIAR_PASS_2;
                    mandarMail.generateAndSendEmail(correo, cadena, Constantes.SUJ_REINICIAR_PASS);
                    realizado = Either.right(Info.builder().info(Constantes.SE_HA_MANDANDO_AL_CORREO_INDICADO_LA_NUEVA_CONTRASENA).build());
                } catch (MessagingException e) {
                    log.error(e.getMessage(), e);
                    realizado = Either.left(ApiError.builder().mensaje(Constantes.FALLO_REINICIAR_PASS + pass).fecha(LocalDateTime.now()).build());
                }
            } else {
                realizado = Either.left(resultado.getLeft());
            }
        } else {
            realizado = Either.left(getPersona.getLeft());
        }
        return realizado;
    }

    public Either<ApiError, Info> marcarAprobado(String dni) {
        return daoAlumno.updateAlumnoAprobado(dni);
    }

    private String generarPassRandom() {
        byte[] passByte = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(passByte);
        return new String(Base64.getUrlEncoder().encode(passByte));
    }

    private Either<ApiError, Persona> getPersonas(Either<ApiError, UsuarioEntity> getPersona) {
        Either<ApiError, Persona> resultado;
        if (getPersona.isRight()) {
            switch (getPersona.get().getTipoUsuario()) {
                case Constantes.ADMINISTRADOR:
                    resultado = daoAdministrador.getAdministradorByDni(getPersona.get().getDni());
                    break;
                case Constantes.PROFESOR:
                    resultado = daoProfesor.getProfesorByDni(getPersona.get().getDni());
                    break;
                case Constantes.ALUMNO:
                    resultado = daoAlumno.getAlumnoByDni(getPersona.get().getDni());
                    break;
                default:
                    resultado = Either.left(ApiError.builder()
                            .mensaje(Constantes.EL_USUARIO_NO_TIENE_UN_ROL_ASIGNADO)
                            .fecha(LocalDateTime.now())
                            .build());
                    break;
            }
        } else {
            resultado = Either.left(getPersona.getLeft());
        }
        return resultado;
    }
}
