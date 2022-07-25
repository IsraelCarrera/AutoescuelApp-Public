package com.example.autoescuelapp.ui.clases.clasesAnteriores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.usescases.clase.GetClasesAlumnoDateBeforeUC
import com.example.autoescuelapp.usescases.clase.GetClasesProfesorDateBeforeUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClasesAnterioresMV @Inject constructor(
    private val clasesAlumnoDateBefore: GetClasesAlumnoDateBeforeUC,
    private val clasesProfesorDateBefore: GetClasesProfesorDateBeforeUC,
) : ViewModel() {
    init {
        if (UsuarioSing.usuario?.tipoPersona == "alumno") {
            handleEvent(ClasesAnterioresContract.Event.ClasesAlumnoDateBefore(UsuarioSing.usuario?.dni!!))
        } else if (UsuarioSing.usuario?.tipoPersona == "profesor") {
            handleEvent(ClasesAnterioresContract.Event.ClasesProfesorDateBefore(UsuarioSing.usuario?.dni!!))
        }
    }

    private val _uiState: MutableStateFlow<ClasesAnterioresContract.State> by lazy {
        MutableStateFlow(ClasesAnterioresContract.State())
    }
    val uiState: StateFlow<ClasesAnterioresContract.State> = _uiState

    fun handleEvent(event: ClasesAnterioresContract.Event) {
        when (event) {
            is ClasesAnterioresContract.Event.ClasesAlumnoDateBefore -> {
                val dniAlumno = event.dniAlumno
                viewModelScope.launch {
                    clasesAlumnoDateBefore.invoke(dniAlumno)
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
            is ClasesAnterioresContract.Event.ClasesProfesorDateBefore -> {
                val dniProfesor = event.dniProfesor
                viewModelScope.launch {
                    clasesProfesorDateBefore.invoke(dniProfesor)
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
            ClasesAnterioresContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
            ClasesAnterioresContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
        }
    }
}