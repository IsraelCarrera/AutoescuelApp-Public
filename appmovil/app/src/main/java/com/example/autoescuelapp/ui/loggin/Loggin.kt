package com.example.autoescuelapp.ui.loggin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.autoescuelapp.R
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.domain.PersonaLogin


@Composable
fun HacerLoggin(
    onNavigate: (String) -> Unit,
    viewmodel: HacerLogVM = hiltViewModel(),
) {
    var rememberUser by rememberSaveable { mutableStateOf("") }
    var rememberPass by rememberSaveable { mutableStateOf("") }
    val aviso = ""

    checkLoggin(
        textUser = rememberUser,
        onUserChange = { rememberUser = it },
        textPass = rememberPass,
        onPassChange = { rememberPass = it },
        aviso = aviso,
        onNavigate = onNavigate,
        viewmodel = viewmodel,
    )
}

@Composable
fun checkLoggin(
    textUser: String,
    onUserChange: (String) -> Unit,
    textPass: String,
    onPassChange: (String) -> Unit,
    aviso: String,
    onNavigate: (String) -> Unit,
    viewmodel: HacerLogVM,
) {
    var avisazo by rememberSaveable { mutableStateOf(aviso) }
    LaunchedEffect(key1 = true) {
        viewmodel.uiState.collect { value ->
            value.error.let {
                avisazo = it.toString()
            }
            if (value.logOk) {
                onNavigate("usuario/principal")
                HacerLogContract.Event.PonerLogDone
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.autoescuelapp_icon_v2),
                contentDescription = "Logo de la autoescuela",
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "Iniciar sesión",
                fontSize = 36.sp
            )
            OutlinedTextField(
                value = textUser,
                onValueChange = onUserChange,
                label = { Text(text = "Usuario/dni") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            OutlinedTextField(
                value = textPass,
                onValueChange = onPassChange,
                label = { Text(text = "Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Button(onClick = {
                //Compruebo si no es empty y procedo.
                if (textUser.isEmpty() && textPass.isEmpty()) {
                    //Como es vacio no hace nada, aviso de que campos vacios.
                    avisazo = "Campos vacios."
                } else {
                    //Construyo a la persona log y lo mando al VM.
                    val pl = PersonaLogin(textUser, textPass)
                    viewmodel.handleEvent(HacerLogContract.Event.HacerLog(pl))
                }
            }) {
                Text(text = "Iniciar sesión")
            }
            Text(text = avisazo)
            Column {
                Text(text = "Si se ha olvidado su contraseña ")
                Text(modifier = Modifier.clickable { onNavigate("usuario/solicitarPass") },
                    text = "pulse aquí.",
                    textDecoration = TextDecoration.Underline,
                    color = Color.Blue
                )
            }
        }
    }
}
