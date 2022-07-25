package com.example.autoescuelapp.ui.clases.clasesAnteriores

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
import com.example.autoescuelapp.ui.theme.AzulAnteriorClasesProfesor
import com.example.autoescuelapp.ui.theme.VerdeAnteriorClasesAlumno
import java.time.LocalDate

@Composable
fun ClasesAnterioresAlumno(
    onNavigate: (String) -> Unit,
    onGoBackClick: () -> Unit,
    viewModel: ClasesAnterioresMV = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val stateAnterioresClases = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ClasesAnterioresContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ClasesAnterioresContract.Event.MensajeMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Clases anteriores") },
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
                items(stateAnterioresClases.value.clasesAlumno) { clase ->
                    PintarClasesAlumno(clase = clase,
                        color = VerdeAnteriorClasesAlumno,
                        onNavigate = onNavigate)
                }
            }
        }
    }
}

@Composable
fun ClasesAnterioresProfesor(
    onNavigate: (String) -> Unit,
    onGoBackClick: () -> Unit,
    viewModel: ClasesAnterioresMV = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val stateAnterioresClases = viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ClasesAnterioresContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ClasesAnterioresContract.Event.MensajeMostrado)
                }
            }
        }
    }
    //Tema fechas del día
    val listFechas = mutableListOf<LocalDate>()
    for (i in 0..30) {
        listFechas.add(LocalDate.now().minusDays(i.toLong()))
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Clases anteriores") },
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
                        clases = stateAnterioresClases.value.clasesProfesor,
                        dia = dia,
                        color = AzulAnteriorClasesProfesor,
                        onNavigate = onNavigate,
                        viewModel = null)
                }
            }
        }
    }
}