package com.example.autoescuelapp.ui.infoPersona

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.ui.common.IrAtras
import com.example.autoescuelapp.ui.theme.AzulProxClasesProfesor
import com.example.autoescuelapp.ui.theme.VerdeProxClasesAlumno
import com.example.autoescuelapp.ui.theme.colorLetrasClases

@Composable
fun InfoPersona(
    onNavigate: (String) -> Unit,
    onGoBackClick: () -> Unit,
    viewModel: InfoPersonaVM = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(InfoPersonaContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(InfoPersonaContract.Event.MensajeMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "${UsuarioSing.usuario?.tipoPersona}") },
                navigationIcon = { IrAtras(onGoBackClick) }
            )
        }
    ) {
        PintarMasInfo(
            onNavigate = onNavigate,
            viewModel = viewModel
        )
    }
}

@Composable
fun PintarMasInfo(
    onNavigate: (String) -> Unit,
    viewModel: InfoPersonaVM,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Tu información", fontSize = 30.sp, modifier = Modifier.padding(15.dp))
        Card(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(230.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)) {
                    Column(horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(5.dp)) {
                        Text(text = "DNI",
                            color = colorLetrasClases,
                            modifier = Modifier.padding(2.dp))
                        Text(text = "${UsuarioSing.usuario?.dni}")
                    }
                    Column(horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(5.dp)) {
                        Text(text = "Nombre y apellidos",
                            color = colorLetrasClases,
                            modifier = Modifier.padding(2.dp))
                        Text(text = "${UsuarioSing.usuario?.nombre}")
                        Text(text = "${UsuarioSing.usuario?.apellidos}")
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)) {
                    Column(horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(5.dp)) {
                        Text(text = "Teléfono",
                            color = colorLetrasClases,
                            modifier = Modifier.padding(2.dp))
                        Text(text = "${UsuarioSing.usuario?.telefono}")

                    }
                    Column(horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(5.dp)) {
                        Text(text = "Correo electrónico",
                            color = colorLetrasClases,
                            modifier = Modifier.padding(2.dp))
                        Text(text = "${UsuarioSing.usuario?.correo}")

                    }
                }
                Column(horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)) {
                    Text(text = "Dirección",
                        color = colorLetrasClases,
                        modifier = Modifier.padding(2.dp))
                    Text(text = "${UsuarioSing.usuario?.direccion}")
                }
                if (UsuarioSing.usuario?.tipoPersona == "alumno") {
                    Column(horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)) {
                        Text(text = "Otra información de interes",
                            color = colorLetrasClases,
                            modifier = Modifier.padding(2.dp))
                        if (UsuarioSing.usuario?.aprobado!!) {
                            Text(text = "!Tienes aprobado el examen práctico!")
                        } else {
                            Text(text = "Aun no tienes aprobado el examen práctico.")
                        }
                    }
                } else {
                    Column(horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)) {
                        Text(text = "Coche asignado",
                            color = colorLetrasClases,
                            modifier = Modifier.padding(2.dp))
                        Text(text = "${UsuarioSing.usuario?.matriculaCoche}")
                    }
                }
            }
        }
        val color: Color = if (UsuarioSing.usuario?.tipoPersona == "alumno") {
            PintarProfesor(viewModel = viewModel)
            VerdeProxClasesAlumno
        } else {
            AzulProxClasesProfesor
        }
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(5.dp)) {
            Button(onClick = { onNavigate("usuario/cambioPass") },
                colors = ButtonDefaults.buttonColors(backgroundColor = color,
                    contentColor = Color.White)) {
                Text(text = "Cambiar contraseña")
            }
        }
    }

}

@Composable
fun PintarProfesor(viewModel: InfoPersonaVM) {
    viewModel.handleEvent(InfoPersonaContract.Event.BuscarTutor(UsuarioSing.usuario?.dniTutor))
    val state = viewModel.uiState.collectAsState()
    viewModel.handleEvent(InfoPersonaContract.Event.BuscarCoche(state.value.tutor?.matriculaCoche))
    val tutor = state.value.tutor
    val coche = state.value.coche

    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(180.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)) {
                Text(text = "Nombre y apellidos",
                    color = colorLetrasClases,
                    modifier = Modifier.padding(2.dp))
                Text(text = "${tutor?.nombre}")
                Text(text = "${tutor?.apellidos}")
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)) {
                Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(5.dp)) {
                    Text(text = "Teléfono",
                        color = colorLetrasClases,
                        modifier = Modifier.padding(2.dp))
                    Text(text = "${tutor?.telefono}")


                }
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(5.dp)) {
                    Text(text = "Correo electrónico",
                        color = colorLetrasClases,
                        modifier = Modifier.padding(2.dp))
                    Text(text = "${tutor?.correo}")
                }
            }
            Column(horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)) {
                Text(text = "Coche Asignado",
                    color = colorLetrasClases,
                    modifier = Modifier.padding(2.dp))
                Text(text = "${coche?.matricula} -- ${coche?.marca} ${coche?.modelo}")
            }
        }
    }
}

