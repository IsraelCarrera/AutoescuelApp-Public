package com.example.autoescuelapp.ui.clases.solicitarClases

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.domain.Clase
import com.example.autoescuelapp.ui.common.IrAtras
import com.example.autoescuelapp.ui.theme.Blanco
import com.example.autoescuelapp.ui.theme.RojoSolicitarClase
import com.example.autoescuelapp.ui.theme.VerdeProxClasesAlumno
import com.example.autoescuelapp.ui.theme.VerdeSolicitarClase
import com.example.autoescuelapp.utils.Utils
import java.time.DayOfWeek
import java.time.LocalDate


@Composable
fun SolicitarClases(
    onGoBackClick: () -> Unit,
    viewModel: SolicitarClasesVM = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val stateSolicitarClases = viewModel.uiState.collectAsState()
    //Los avisos del snackBar
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(SolicitarClasesContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(SolicitarClasesContract.Event.MensajeMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Solicitar clases") },
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
        PintarCalendario(
            viewModel = viewModel,
            stateSolicitarClases = stateSolicitarClases
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PintarCalendario(
    viewModel: SolicitarClasesVM,
    stateSolicitarClases: State<SolicitarClasesContract.State>,
) {
    //Variables para controlar los botones.
    var selectedBoton = false
    var selectedBotonText = ""
    var selectedBotonFecha: LocalDate? = null
    val clasesCogidas = stateSolicitarClases.value.clases


    //Lo primero es crear un array de 7 elementos, serán 7 fechas, desde la del now, hasta la siguiente
    val listFechas = mutableListOf<LocalDate>()
    //Además, tendremos dos variables de ayuda para posicionar las horas.
    val horarioManana =
        mutableListOf("07:00", "07:45", "08:30", "09:15", "10:00", "10:45", "11:30", "12:15")
    val horarioTarde =
        mutableListOf("15:00", "15:45", "16:30", "17:15", "18:00", "18:45", "19:30", "20:15")
    for (i in 0..6) {
        listFechas.add(LocalDate.now().plusDays(i.toLong()))
    }

    Column {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(550.dp)
            .padding(5.dp)) {
            items(listFechas) { dia ->
                if (dia.dayOfWeek != DayOfWeek.SATURDAY && dia.dayOfWeek != DayOfWeek.SUNDAY) {
                    Card(modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Column(modifier = Modifier.padding(7.dp)) {
                                Text(text = "${dia.dayOfMonth} ${Utils.diaSemanaSp(dia.dayOfWeek)} de ${
                                    Utils.mesSp(dia.month)
                                }",
                                    fontSize = 30.sp)
                            }
                            Column(modifier = Modifier.padding(5.dp)) {
                                Text(text = "Mañana", fontSize = 20.sp)
                                for (j in 0..1) {
                                    Row(modifier = Modifier.padding(5.dp),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        verticalAlignment = Alignment.CenterVertically) {
                                        for (k in 0..3) {
                                            //Variable para los colores
                                            var remColorBoton by remember { mutableStateOf(Blanco) }
                                            //Para comprobar que está o no cogida:
                                            val claseCogida = clasesCogidas.stream()
                                                .filter { c -> (c.fecha == dia) && (c.horarioInicio == horarioManana[(j * 4) + k]) }
                                                .findFirst().orElse(null)
                                            Row(modifier = Modifier.padding(end = 10.dp,
                                                start = 10.dp)) {
                                                if (claseCogida != null) {
                                                    remColorBoton = RojoSolicitarClase
                                                    Card(
                                                        modifier = Modifier
                                                            .size(width = 70.dp,
                                                                height = 30.dp),
                                                        backgroundColor = remColorBoton,
                                                        border = BorderStroke(1.dp, Color.Black),
                                                    ) {
                                                        Row(verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center) {
                                                            Text(text = horarioManana[(j * 4) + k],
                                                                textAlign = TextAlign.Center)
                                                        }
                                                    }
                                                } else {
                                                    Card(
                                                        onClick = ({
                                                            //Primero, se comprueba que el selected es false.
                                                            if (!selectedBoton) {
                                                                selectedBotonText =
                                                                    horarioManana[(j * 4) + k]
                                                                selectedBotonFecha = dia
                                                                selectedBoton = true
                                                                remColorBoton = VerdeSolicitarClase
                                                            } else if (selectedBoton && selectedBotonText == horarioManana[(j * 4) + k]) {
                                                                //Esto implica que si está seleccionado y lo va a deseleccionar
                                                                selectedBotonText = ""
                                                                selectedBoton = false
                                                                remColorBoton = Blanco
                                                                selectedBotonFecha = null
                                                            }
                                                        }),
                                                        modifier = Modifier.size(width = 70.dp,
                                                            height = 30.dp),
                                                        backgroundColor = remColorBoton,
                                                        border = BorderStroke(1.dp, Color.Black),
                                                    ) {
                                                        Row(verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center) {
                                                            Text(text = horarioManana[(j * 4) + k],
                                                                textAlign = TextAlign.Center)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Column(modifier = Modifier.padding(5.dp)) {
                                Text(text = "tarde", fontSize = 20.sp)
                                for (j in 0..1) {
                                    Row(modifier = Modifier.padding(5.dp),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        verticalAlignment = Alignment.CenterVertically) {
                                        for (k in 0..3) {
                                            //Variable para los colores
                                            var remColorBoton by remember { mutableStateOf(Blanco) }
                                            val claseCogida = clasesCogidas.stream()
                                                .filter { c -> (c.fecha == dia) && (c.horarioInicio == horarioTarde[(j * 4) + k]) }
                                                .findFirst().orElse(null)
                                            Row(modifier = Modifier.padding(end = 10.dp,
                                                start = 10.dp)) {
                                                if (claseCogida != null) {
                                                    remColorBoton = RojoSolicitarClase
                                                    Card(
                                                        modifier = Modifier.size(width = 70.dp,
                                                            height = 30.dp),
                                                        backgroundColor = remColorBoton,
                                                        border = BorderStroke(1.dp, Color.Black),
                                                    ) {
                                                        Row(verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center) {
                                                            Text(text = horarioTarde[(j * 4) + k],
                                                                textAlign = TextAlign.Center)
                                                        }
                                                    }
                                                } else {
                                                    Card(
                                                        onClick = ({
                                                            //Primero, se comprueba que el selected es false.
                                                            if (!selectedBoton) {
                                                                selectedBotonText =
                                                                    horarioTarde[(j * 4) + k]
                                                                selectedBoton = true
                                                                selectedBotonFecha = dia
                                                                remColorBoton = VerdeSolicitarClase
                                                            } else if (selectedBoton && selectedBotonText == horarioTarde[(j * 4) + k]) {
                                                                //Esto implica que si está seleccionado y lo va a deseleccionar
                                                                selectedBotonText = ""
                                                                selectedBoton = false
                                                                remColorBoton = Blanco
                                                                selectedBotonFecha = null

                                                            }
                                                        }),
                                                        modifier = Modifier.size(width = 70.dp,
                                                            height = 30.dp),
                                                        backgroundColor = remColorBoton,
                                                        border = BorderStroke(1.dp, Color.Black),

                                                        ) {
                                                        Row(verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.Center) {
                                                            Text(text = horarioTarde[(j * 4) + k],
                                                                textAlign = TextAlign.Center)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(5.dp)) {
            Button(onClick = {
                //Mandamos la acción hacia arriba. Si no hubiera datos, la interceptará el VM
                val clase = Clase(
                    fecha = selectedBotonFecha,
                    horarioInicio = selectedBotonText,
                    duracion = "45",
                    dniProfesor = UsuarioSing.usuario?.dniTutor!!,
                    dniAlumno = UsuarioSing.usuario?.dni!!)
                viewModel.handleEvent(SolicitarClasesContract.Event.SolicitarClase(clase = clase,
                    selected = selectedBoton))
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = VerdeProxClasesAlumno,
                    contentColor = Color.White)) {
                Text(text = "Solicitar clase")
            }
        }
    }
}


