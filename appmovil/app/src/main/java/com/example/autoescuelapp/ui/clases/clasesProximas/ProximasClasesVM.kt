package com.example.autoescuelapp.ui.clases.clasesProximas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.usescases.clase.DeleteClaseByDatesUC
import com.example.autoescuelapp.usescases.clase.GetClasesAlumnoDateUC
import com.example.autoescuelapp.usescases.clase.GetClasesProfesorDateUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProximasClasesVM @Inject constructor(
    private val getClasesAlumnoDate: GetClasesAlumnoDateUC,
    private val getClasesProfesorDate: GetClasesProfesorDateUC,
    private val deleteClaseByDates: DeleteClaseByDatesUC,
) : ViewModel() {
    init {
        if (UsuarioSing.usuario?.tipoPersona == "alumno") {
            handleEvent(ProximasClasesContract.Event.ClasesAlumnoDate(UsuarioSing.usuario?.dni!!))
        } else if (UsuarioSing.usuario?.tipoPersona == "profesor") {
            handleEvent(ProximasClasesContract.Event.ClasesProfesorDate(UsuarioSing.usuario?.dni!!))

        }
    }

    private val _uiState: MutableStateFlow<ProximasClasesContract.State> by lazy {
        MutableStateFlow(ProximasClasesContract.State())
    }
    val uiState: StateFlow<ProximasClasesContract.State> = _uiState

    fun handleEvent(event: ProximasClasesContract.Event) {
        when (event) {
            is ProximasClasesContract.Event.ClasesAlumnoDate -> {
                val dniAlumno = event.dniAlumno
                viewModelScope.launch {
                    getClasesAlumnoDate.invoke(dniAlumno)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            clasesAlumno = result.data!!
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is ProximasClasesContract.Event.ClasesProfesorDate -> {
                val dniProfesor = event.dniProfesor
                viewModelScope.launch {
                    getClasesProfesorDate.invoke(dniProfesor)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            clasesProfesor = result.data!!
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            ProximasClasesContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
            ProximasClasesContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
            is ProximasClasesContract.Event.DeleteClasesByDate -> {
                viewModelScope.launch {
                    deleteClaseByDates.invoke(event.dniProfesor, event.date)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            mensaje = result.data!!
                                        )
                                    }
                                }
                            }
                        }
                }
            }
        }
    }
}