package com.example.autoescuelapp.ui.infoCoche

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import com.example.autoescuelapp.ui.theme.colorLetrasClases

@Composable
fun InfoCoche(
    onNavigate: (String) -> Unit,
    onGoBackClick: () -> Unit,
    viewModel: InfoCocheVM = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.uiState.collectAsState()
    viewModel.handleEvent(InfoCocheContract.Event.BuscarCoche(UsuarioSing.usuario?.matriculaCoche))
    val coche = state.value.coche
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(InfoCocheContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(InfoCocheContract.Event.MensajeMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Info coche") },
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
            Text(text = "Tu Coche", fontSize = 30.sp, modifier = Modifier.padding(15.dp))
            Card(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(150.dp)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)) {
                        Column(horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(5.dp)) {
                            Text(text = "Matrícula", color = colorLetrasClases,
                                modifier = Modifier.padding(2.dp))
                            Text(text = "${coche?.matricula}")
                        }
                        Column(horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(5.dp)) {
                            Text(text = "Próxima ITV", color = colorLetrasClases,
                                modifier = Modifier.padding(2.dp))
                            Text(text = "${coche?.proximaItv.toString()}")

                        }
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)) {
                        Column(horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(5.dp)) {
                            Text(text = "Modelo", color = colorLetrasClases,
                                modifier = Modifier.padding(2.dp))
                            Text(text = "${coche?.modelo}")
                        }
                        Column(horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(5.dp)) {
                            Text(text = "Marca", color = colorLetrasClases,
                                modifier = Modifier.padding(2.dp))
                            Text(text = "${coche?.marca}")
                        }
                    }
                }
            }
            Box(modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)) {
                Button(onClick = { onNavigate("profesor/coche/incidencia") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = AzulProxClasesProfesor,
                        contentColor = Color.White)) {
                    Text(text = "Notificar incidencia")
                }
            }
        }
    }
}