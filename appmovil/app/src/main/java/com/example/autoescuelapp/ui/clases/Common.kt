package com.example.autoescuelapp.ui.clases

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.domain.Clase
import com.example.autoescuelapp.ui.clases.clasesProximas.ProximasClasesContract
import com.example.autoescuelapp.ui.clases.clasesProximas.ProximasClasesVM
import com.example.autoescuelapp.ui.theme.RojoSolicitarClase
import com.example.autoescuelapp.utils.Utils
import java.time.LocalDate

@Composable
fun PintarClasesAlumno(
    clase: Clase,
    color: Color,
    onNavigate: (String) -> Unit,
) {
    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(70.dp)
        .clickable {
            //Función de ir al item en concreto.
            onNavigate("alumno/clases/" + clase.id)
        },
        backgroundColor = color.copy(alpha = 0.35f)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(15.dp),
                horizontalAlignment = Alignment.Start) {
                Text(text = "${clase.fecha?.dayOfMonth}",
                    textAlign = TextAlign.Center)
                Text(text = Utils.mesSp(clase.fecha?.month!!),
                    textAlign = TextAlign.Center)
            }
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(15.dp),
                horizontalAlignment = Alignment.End) {
                Text(text = clase.horarioInicio,
                    textAlign = TextAlign.Center)
                Text(text = "duración: ${clase.duracion}",
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun PintarClasesProfesor(
    clases: List<Clase>,
    dia: LocalDate,
    color: Color,
    onNavigate: (String) -> Unit,
    viewModel: ProximasClasesVM?,
) {
    //De la lista que tenemos, filtramos por los que sea la misma fecha.
    val clasesDiaIndicado = clases.filter { c -> (c.fecha == dia) }.toList()
    if (clasesDiaIndicado.isNotEmpty()) {
        Card(modifier = Modifier
            .padding(10.dp)) {
            Column {
                Text(text = "${dia.dayOfMonth} ${Utils.mesSp(dia.month)}", fontSize = 30.sp)
                for (clase in clasesDiaIndicado) {
                    Card(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(70.dp)
                        .clickable {
                            //Función de ir al item en concreto.
                            onNavigate("profesor/clases/" + clase.id)
                        },
                        backgroundColor = color.copy(alpha = 0.35f)
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)) {
                            Column(verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(15.dp),
                                horizontalAlignment = Alignment.Start) {
                                Text(text = clase.horarioInicio)
                                Text(text = "Duracion: ${clase.duracion}")
                            }
                            Column(verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(15.dp),
                                horizontalAlignment = Alignment.End) {
                                Text(text = "Alumno",
                                    textAlign = TextAlign.Right)
                                Text(text = "${clase.dniAlumno}",
                                    textAlign = TextAlign.Right)
                            }
                        }
                    }
                }
                if (dia > LocalDate.now()) {
                    Box(modifier = Modifier
                        .align(Alignment.End)
                        .padding(5.dp)) {
                        Button(onClick = {
                            viewModel.let {
                                it?.handleEvent(ProximasClasesContract.Event.DeleteClasesByDate(
                                    UsuarioSing.usuario?.dni!!, dia))
                            }
                        },
                            colors = ButtonDefaults.buttonColors(backgroundColor = RojoSolicitarClase,
                                contentColor = Color.White)) {
                            Text(text = "Cancelar clases")
                        }
                    }

                }
            }
        }
    }


}