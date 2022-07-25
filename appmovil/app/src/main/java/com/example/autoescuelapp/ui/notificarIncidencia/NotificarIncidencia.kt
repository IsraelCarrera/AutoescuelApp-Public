package com.example.autoescuelapp.ui.notificarIncidencia

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.domain.Incidencia
import com.example.autoescuelapp.ui.common.IrAtras
import com.example.autoescuelapp.ui.theme.AzulProxClasesProfesor

@Composable
fun NotificarIncidencia(
    onGoBackClick: () -> Unit,
    viewModel: NotificarIncidenciaVM = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    var rememberTitulo by rememberSaveable { mutableStateOf("") }
    var rememberCuerpo by rememberSaveable { mutableStateOf("") }
    var rememberUbicacion by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(NotificarIncidenciaContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(NotificarIncidenciaContract.Event.MensajeMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Añadir incidencia") },
                actions = {
                    IconButton(onClick = { onNavigate("usuario/info") }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Más info de tus datos."
                        )
                    }
                },
                navigationIcon = { IrAtras(onGoBackClick) }
            )
        }
    ) {
        BodyIncidencia(textTitulo = rememberTitulo,
            onTituloChange = { rememberTitulo = it },
            textCuerpo = rememberCuerpo,
            onCuerpoChange = { rememberCuerpo = it },
            textUbicacion = rememberUbicacion,
            onUbicacionChange = { rememberUbicacion = it },
            viewModel = viewModel
        )
    }
}

@Composable
fun BodyIncidencia(
    textTitulo: String,
    onTituloChange: (String) -> Unit,
    textCuerpo: String,
    onCuerpoChange: (String) -> Unit,
    textUbicacion: String,
    onUbicacionChange: (String) -> Unit,
    viewModel: NotificarIncidenciaVM,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Text(text = "Notificar incidencia", fontSize = 30.sp, modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            value = textTitulo,
            onValueChange = onTituloChange,
            label = { Text(text = "Titulo incidencia") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = textCuerpo,
            onValueChange = onCuerpoChange,
            label = { Text(text = "Cuerpo de la incidencia") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .height(230.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = textUbicacion,
            onValueChange = onUbicacionChange,
            label = { Text(text = "Lugar de la incidencia") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
        )
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(5.dp)) {
            Button(onClick = {
                //Comprobamos que los campos no están vacios.
                if (textCuerpo.isNotEmpty() && textTitulo.isNotEmpty() && textUbicacion.isNotEmpty()) {
                    //Creamos la incidencia
                    val incidencia = Incidencia(
                        tituloIncidencia = textTitulo,
                        descripcion = textCuerpo,
                        ubicacion = textUbicacion,
                        dniProfesor = UsuarioSing.usuario?.dni!!,
                        matriculaCoche = UsuarioSing.usuario?.matriculaCoche!!
                    )
                    //Con la incidencia creada, la mandamos al servidor.
                    viewModel.handleEvent(NotificarIncidenciaContract.Event.AddIncidencia(incidencia))
                } else {
                    //Al estar vacios, notificamos de ello.
                    viewModel.handleEvent(NotificarIncidenciaContract.Event.MandarError("Hay algún campo vacio. Rellene todos."))
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = AzulProxClasesProfesor,
                    contentColor = Color.White)) {
                Text(text = "Añadir")
            }
        }
    }
}