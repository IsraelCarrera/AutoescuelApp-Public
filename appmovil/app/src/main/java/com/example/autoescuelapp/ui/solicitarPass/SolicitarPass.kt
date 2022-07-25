package com.example.autoescuelapp.ui.solicitarPass

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autoescuelapp.ui.common.IrAtras

@Composable
fun SolicitarPass(
    onGoBackClick: () -> Unit,
    viewModel: SolicitarPassVM = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    var rememberDni by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(SolicitarPassContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(SolicitarPassContract.Event.MensajeMostrado)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Solicitar nueva contraseña") },
                navigationIcon = { IrAtras(onGoBackClick) }
            )
        }
    ) {
        HacerSolicitudPass(textDni = rememberDni,
            onDniChange = { rememberDni = it },
            viewModel = viewModel
        )
    }
}

@Composable
fun HacerSolicitudPass(
    textDni: String,
    onDniChange: (String) -> Unit,
    viewModel: SolicitarPassVM,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Solicitar nueva contraseña",
            fontSize = 30.sp,
            modifier = Modifier.padding(15.dp))
        Text(text = "Indique el DNI.")
        OutlinedTextField(
            value = textDni,
            onValueChange = onDniChange,
            label = { Text(text = "dni") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true
        )
        Text(text = "Se le mandará un correo a la dirección asignada a dicho DNI.")
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(5.dp)) {
            Button(onClick = {
                if (textDni.isNotEmpty()) {
                    //Mandamos la solicitud.
                    viewModel.handleEvent(SolicitarPassContract.Event.ReiniciarPass(textDni))
                }
            }) {
                Text(text = "Solicitar envio de pass")
            }
        }
    }
}