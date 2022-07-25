package com.example.autoescuelapp.ui.cambiarPass

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.ui.common.IrAtras
import com.example.autoescuelapp.ui.theme.AzulProxClasesProfesor
import com.example.autoescuelapp.ui.theme.VerdeProxClasesAlumno

@Composable
fun CambiarPass(
    onGoBackClick: () -> Unit,
    viewModel: CambiarPassVM = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    var rememberPassVieja by rememberSaveable { mutableStateOf("") }
    var rememberPassNueva by rememberSaveable { mutableStateOf("") }
    var rememberPassNuevaRep by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(CambiarPassContract.Event.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(CambiarPassContract.Event.MensajeMostrado)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Cambio de contraseña") },
                navigationIcon = { IrAtras(onGoBackClick) }
            )
        }
    ) {
        RealizarCambiarPass(
            textPassAntigua = rememberPassVieja,
            onPasSAntiguaChange = { rememberPassVieja = it },
            textPassNueva = rememberPassNueva,
            onPassNuevaChange = { rememberPassNueva = it },
            textPassNuevaRep = rememberPassNuevaRep,
            onPassNuevaRepChange = { rememberPassNuevaRep = it },
            viewModel = viewModel
        )
    }
}

@Composable
fun RealizarCambiarPass(
    textPassAntigua: String,
    onPasSAntiguaChange: (String) -> Unit,
    textPassNueva: String,
    onPassNuevaChange: (String) -> Unit,
    textPassNuevaRep: String,
    onPassNuevaRepChange: (String) -> Unit,
    viewModel: CambiarPassVM,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Text(text = "Cambiar contraseña", fontSize = 30.sp, modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            value = textPassAntigua,
            onValueChange = onPasSAntiguaChange,
            label = { Text(text = "Contraseña actual") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )
        OutlinedTextField(
            value = textPassNueva,
            onValueChange = onPassNuevaChange,
            label = { Text(text = "Nueva contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )
        OutlinedTextField(
            value = textPassNuevaRep,
            onValueChange = onPassNuevaRepChange,
            label = { Text(text = "Repite nueva contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )
        val color: Color = if (UsuarioSing.usuario?.tipoPersona == "alumno") {
            VerdeProxClasesAlumno
        } else {
            AzulProxClasesProfesor
        }
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(5.dp)) {
            Button(onClick = {
                //Lo primero es comprobar que la contraseña actual es igual que la que tenemos en el usuarioSingleton guardada.
                //Despues comprobamos que las dos contraseñas nuevas son iguales.
                if (textPassAntigua == UsuarioSing.pass) {
                    if (textPassAntigua.isNotEmpty() && textPassNuevaRep.isNotEmpty()) {
                        if (textPassNueva == textPassNuevaRep) {
                            //Aqui ya podemos hacer el envio de la contraseña nueva.
                            viewModel.handleEvent(CambiarPassContract.Event.CambiarPass(
                                dni = UsuarioSing.usuario?.dni!!,
                                passAnterior = textPassAntigua,
                                passNueva = textPassNueva
                            ))
                            UsuarioSing.pass = textPassNueva
                        } else {
                            //Aqui el usuario ha escrito mal la contraseña nueva en algún campo de los dos.
                            viewModel.handleEvent(CambiarPassContract.Event.MandarError("La contraseña nueva no es igual en ambos campos."))
                        }
                    } else {
                        viewModel.handleEvent(CambiarPassContract.Event.MandarError("Tiene que escribir una contraseña, no lo deje en blanco."))
                    }
                } else {
                    //Aqui el usuario a escrito mal su pass
                    viewModel.handleEvent(CambiarPassContract.Event.MandarError("Contraseña incorrecta."))
                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = color,
                    contentColor = Color.White)) {
                Text(text = "cambiar contraseña")
            }
        }
    }
}