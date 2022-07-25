package com.example.autoescuelapp.ui.clases.masInfoClases

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.autoescuelapp.domain.Feedback
import com.example.autoescuelapp.ui.common.IrAtras
import com.example.autoescuelapp.ui.theme.AzulProxClasesProfesor
import com.example.autoescuelapp.ui.theme.RojoSolicitarClase
import com.example.autoescuelapp.ui.theme.colorLetrasClases
import com.example.autoescuelapp.utils.Utils
import java.time.LocalDate

@Composable
fun MasInfoClasesAlumno(
    onGoBackClick: () -> Unit,
    idClase: Int,
    viewModel: MasInfoClasesVM = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    viewModel.handleEvent(MasInfoClasesContract.Event.BuscarProfesor(idClase))
    viewModel.handleEvent(MasInfoClasesContract.Event.ClasePorId(idClase))
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(MasInfoClasesContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(MasInfoClasesContract.Event.MensajeMostrado)
                }
            }
        }
    }

    //Variables
    val profesor = state.value.profesor
    val clase = state.value.clase
    val feedback = state.value.feedback
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Info de clase") },
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
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text(text = "Clase ${clase?.fecha?.dayOfMonth} ${
                clase?.fecha?.month?.let { it1 ->
                    Utils.mesSp(it1)
                }
            }", fontSize = 30.sp, modifier = Modifier.padding(15.dp))
            Card(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(120.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)) {
                    Column(horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(10.dp)) {
                        Text(text = "Profesor/a",
                            color = colorLetrasClases,
                            modifier = Modifier.padding(2.dp))
                        Text(text = "${profesor?.nombre}", modifier = Modifier.padding(2.dp))
                        Text(text = "${profesor?.apellidos}")

                    }
                    Column(horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(10.dp)) {
                        Text(text = "Inicio de clases",
                            color = colorLetrasClases,
                            modifier = Modifier.padding(2.dp))
                        Text(text = "${clase?.horarioInicio}")
                    }
                }
            }
            clase?.fecha.let {
                if (it != null && it > LocalDate.now()) {
                    BorrarClase(idClase = idClase,
                        viewModel = viewModel
                    )
                } else {
                    viewModel.handleEvent(MasInfoClasesContract.Event.GetFeedback(idClase))
                    if (feedback != null) {
                        ShowFeedback(feedback = feedback)
                    }
                }
            }
        }
    }
}

@Composable
fun MasInfoClasesProfesor(
    onGoBackClick: () -> Unit,
    idClase: Int,
    viewModel: MasInfoClasesVM = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    viewModel.handleEvent(MasInfoClasesContract.Event.BuscarAlumno(idClase))
    viewModel.handleEvent(MasInfoClasesContract.Event.ClasePorId(idClase))

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(MasInfoClasesContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(MasInfoClasesContract.Event.MensajeMostrado)
                }
            }
        }
    }
    val state = viewModel.uiState.collectAsState()
    val alumno = state.value.alumno
    val clase = state.value.clase
    val feedback = state.value.feedback


    //Variable del add
    var rememberTitulo by rememberSaveable { mutableStateOf("") }
    var rememberCuerpo by rememberSaveable { mutableStateOf("") }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Info de clase") },
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
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)) {
            Text(text = "Clase ${clase?.fecha?.dayOfMonth} ${
                clase?.fecha?.month?.let { it1 ->
                    Utils.mesSp(it1)
                }
            }", fontSize = 30.sp, modifier = Modifier.padding(15.dp))
            Card(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(130.dp)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)) {
                        Column(horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(5.dp)) {
                            Text(text = "Alumno",
                                color = colorLetrasClases,
                                modifier = Modifier.padding(2.dp))
                            Text(text = "${alumno?.nombre} ${alumno?.apellidos}")

                        }
                        Column(horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(5.dp)) {
                            Text(text = "Inicio de clases",
                                color = colorLetrasClases,
                                modifier = Modifier.padding(2.dp))
                            Text(text = "${clase?.horarioInicio}",
                                modifier = Modifier.padding(2.dp))

                        }
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)) {
                        Column(horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(5.dp)) {
                            Text(text = "Telefono",
                                color = colorLetrasClases,
                                modifier = Modifier.padding(2.dp))
                            Text(text = "${alumno?.telefono}")
                        }
                        Column(horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(5.dp)) {
                            Text(text = "Correo electrónico",
                                color = colorLetrasClases,
                                modifier = Modifier.padding(2.dp))
                            Text(text = "${alumno?.correo}")
                        }
                    }
                }
            }
            clase?.fecha.let {
                if (it != null && it > LocalDate.now()) {
                    BorrarClase(idClase = idClase,
                        viewModel = viewModel
                    )
                } else {
                    viewModel.handleEvent(MasInfoClasesContract.Event.GetFeedback(idClase))
                    if (feedback == null) {
                        AddFeedback(textTitulo = rememberTitulo,
                            onTituloChange = { rememberTitulo = it },
                            textCuerpo = rememberCuerpo,
                            onCuerpoChange = { rememberCuerpo = it },
                            viewModel = viewModel,
                            idClase = idClase,
                            dniAlumno = alumno?.dni
                        )
                    } else {
                        //Aqui iria si tiene datos, el rellenarlo y el borrado.
                        ShowFeedback(feedback = feedback)
                        BorrarFeedback(idClase = idClase,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BorrarClase(
    idClase: Int,
    viewModel: MasInfoClasesVM,
) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(5.dp)) {
            Button(onClick = {
                viewModel.handleEvent(MasInfoClasesContract.Event.DeleteClaseId(idClase))
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = RojoSolicitarClase,
                    contentColor = Color.White)) {
                Text(text = "Cancelar")
            }
        }
    }
}

@Composable
fun AddFeedback(
    textTitulo: String,
    onTituloChange: (String) -> Unit,
    textCuerpo: String,
    onCuerpoChange: (String) -> Unit,
    idClase: Int,
    dniAlumno: String?,
    viewModel: MasInfoClasesVM,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Text(text = "Comentario del profesor", fontSize = 30.sp, modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            value = textTitulo,
            onValueChange = onTituloChange,
            label = { Text(text = "Titulo del comentario") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = textCuerpo,
            onValueChange = onCuerpoChange,
            label = { Text(text = "Cuerpo del comentario") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .height(230.dp)
                .fillMaxWidth()
        )
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(5.dp)) {
            Button(onClick = {
                //Comprobamos que los campos no están vacios.
                if (textCuerpo.isNotEmpty() && textTitulo.isNotEmpty()) {
                    val feedback = dniAlumno?.let {
                        Feedback(
                            titulo = textTitulo,
                            cuerpo = textCuerpo,
                            dniAlumno = it,
                            idClase = idClase,
                            puntuacion = 3
                        )
                    }
                    //Con la incidencia creada, la mandamos al servidor.
                    viewModel.handleEvent(MasInfoClasesContract.Event.AddFeedback(feedback))

                } else {
                    //Al estar vacios, notificamos de ello.
                    viewModel.handleEvent(MasInfoClasesContract.Event.MandarError("Hay algún campo vacio. Rellene todos."))
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = AzulProxClasesProfesor,
                    contentColor = Color.White)) {
                Text(text = "Añadir")
            }
        }
    }
}

@Composable
fun ShowFeedback(
    feedback: Feedback,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Text(text = "Comentario del profesor", fontSize = 30.sp, modifier = Modifier.padding(15.dp))
        Card(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(200.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)) {
                    Text(text = "Título",
                        color = colorLetrasClases,
                        modifier = Modifier.padding(2.dp))
                    Text(text = "${feedback.titulo}")
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)) {
                    Text(text = "Comentario",
                        color = colorLetrasClases,
                        modifier = Modifier.padding(2.dp))
                    Text(text = "${feedback.cuerpo}")
                }
            }
        }
    }
}


@Composable
fun BorrarFeedback(
    idClase: Int,
    viewModel: MasInfoClasesVM,
) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(5.dp)) {
            Button(onClick = {
                viewModel.handleEvent(MasInfoClasesContract.Event.DeleteFeedback(idClase))
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = RojoSolicitarClase,
                    contentColor = Color.White)) {
                Text(text = "Cancelar")
            }
        }
    }
}
