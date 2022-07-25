package com.example.autoescuelapp.ui.principal

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.ui.theme.AzulProxClasesProfesor
import com.example.autoescuelapp.ui.theme.VerdeProxClasesAlumno


@Composable
fun PantallaPrincipal(
    onNavigate: (String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    when (UsuarioSing.usuario?.tipoPersona) {
        "alumno" -> {
            Scaffold(
                scaffoldState = scaffoldState,
                floatingActionButton = {
                    FloatingActionButton(onClick = { onNavigate("alumno/clases/solicitar") },
                        backgroundColor = VerdeProxClasesAlumno, contentColor = Color.White
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Ir a la pantalla de solicitar clases."
                        )
                    }
                },
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Inicio") },
                        actions = {
                            IconButton(onClick = { onNavigate("usuario/info") }) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Más info de tus datos."
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                UsuarioSing.pass = null
                                UsuarioSing.dni = null
                                UsuarioSing.token = null
                                onNavigate("log") }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            ) {
                PintarInicioAlumno(
                    onNavigate = onNavigate,
                )
            }
        }
        "profesor" -> {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Inicio") },
                        actions = {
                            IconButton(onClick = { onNavigate("usuario/info") }) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Más info de tus datos."
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { onNavigate("log") }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            ) {
                PintarInicioProfesor(
                    onNavigate = onNavigate,
                )
            }
        }
        else -> {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Inicio") },
                        navigationIcon = {
                            IconButton(onClick = { onNavigate("log") }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            ) {
                PintarInicioOtros()
            }
        }
    }
}


@Composable
fun PintarInicioAlumno(
    onNavigate: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Text(text = "Hola, " + UsuarioSing.usuario?.nombre,
                fontSize = 30.sp,
                modifier = Modifier.padding(15.dp)
            )
            Button(onClick = { onNavigate("alumno/clases/proximas") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = VerdeProxClasesAlumno)
            ) {
                Text(text = "Proximas clases             >",
                    fontSize = 30.sp, color = Color.White)
            }
            Button(onClick = { onNavigate("alumno/clases/anteriores") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = VerdeProxClasesAlumno)
            ) {
                Text(text = "Clases anteriores           >",
                    fontSize = 30.sp, color = Color.White
                )
            }
        }

    }
}

@Composable
fun PintarInicioProfesor(
    onNavigate: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Text(text = "Hola, " + UsuarioSing.usuario?.nombre,
                fontSize = 30.sp,
                modifier = Modifier.padding(15.dp)
            )
            Button(onClick = { onNavigate("profesor/clases/proximas") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = AzulProxClasesProfesor)) {
                Text(text = "Proximas clases             >",
                    fontSize = 30.sp, color = Color.White)
            }
            Button(onClick = { onNavigate("profesor/clases/anteriores") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = AzulProxClasesProfesor)) {
                Text(text = "Clases anteriores           >",
                    fontSize = 30.sp, color = Color.White)
            }
            Button(onClick = { onNavigate("profesor/coche/info") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = AzulProxClasesProfesor)) {
                Text(text = "Coche asignado              >",
                    fontSize = 30.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun PintarInicioOtros() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Esta app es solamente para alumnos y profesores.")
    }
}


