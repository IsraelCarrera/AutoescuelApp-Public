package com.example.autoescuelapp.ui.clases.clasesProximas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autoescuelapp.ui.clases.PintarClasesAlumno
import com.example.autoescuelapp.ui.clases.PintarClasesProfesor
import com.example.autoescuelapp.ui.common.IrAtras
import com.example.autoescuelapp.ui.theme.AzulProxClasesProfesor
import com.example.autoescuelapp.ui.theme.VerdeProxClasesAlumno
import java.time.LocalDate

@Composable
fun ClasesProximasAlumno(
    onNavigate: (String) -> Unit,
    onGoBackClick: () -> Unit,
    viewModel: ProximasClasesVM = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val stateProxClases = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ProximasClasesContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ProximasClasesContract.Event.MensajeMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Clases proximas") },
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
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                //En este, los items serán los días de la semana, como siempre, solamente los 5 siguientes.
                items(stateProxClases.value.clasesAlumno) { clase ->
                    PintarClasesAlumno(clase = clase,
                        color = VerdeProxClasesAlumno,
                        onNavigate = onNavigate)
                }
            }
        }
    }
}

@Composable
fun ClasesProximasProfesor(
    onNavigate: (String) -> Unit,
    onGoBackClick: () -> Unit,
    viewModel: ProximasClasesVM = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val stateProxClases = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ProximasClasesContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ProximasClasesContract.Event.MensajeMostrado)
                }
            }
        }
    }
    //Tema fechas del día
    val listFechas = mutableListOf<LocalDate>()
    for (i in 0..6) {
        listFechas.add(LocalDate.now().plusDays(i.toLong()))
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Clases proximas") },
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
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                //Los items serán los días de clase.
                items(listFechas) { dia ->
                    PintarClasesProfesor(
                        clases = stateProxClases.value.clasesProfesor,
                        dia = dia,
                        color = AzulProxClasesProfesor,
                        onNavigate = onNavigate,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
