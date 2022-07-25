package com.example.autoescuelapp.ui.infoPersona

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.usescases.coche.GetCocheByMatriculaUC
import com.example.autoescuelapp.usescases.persona.GetPersonaByDniUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoPersonaVM @Inject constructor(
    private val getPersonaByDni: GetPersonaByDniUC,
    private val getCocheByMatricula: GetCocheByMatriculaUC,
) : ViewModel() {
    private val _uiState: MutableStateFlow<InfoPersonaContract.State> by lazy {
        MutableStateFlow(InfoPersonaContract.State())
    }
    val uiState: StateFlow<InfoPersonaContract.State> = _uiState

    fun handleEvent(event: InfoPersonaContract.Event) {
        when (event) {
            is InfoPersonaContract.Event.BuscarCoche -> {
                viewModelScope.launch {
                    if (event.matricula != null) {
                        getCocheByMatricula.invoke(event.matricula)
                            .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                            .collect { result ->
                                when (result) {
                                    is ResultadoLlamada.Error ->
                                        _uiState.update { it.copy(error = result.message.toString()) }
                                    is ResultadoLlamada.Success -> {
                                        _uiState.update {
                                            it.copy(
                                                coche = result.data!!
                                            )
                                        }
                                    }
                                }
                            }
                    } else {
                        _uiState.update { it.copy(error = "No podemos conseguir la matrícula del coche.") }
                    }
                }
            }
            is InfoPersonaContract.Event.BuscarTutor -> {
                viewModelScope.launch {
                    if (event.dniTutor != null) {
                        getPersonaByDni.invoke(event.dniTutor)
                            .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                            .collect { result ->
                                when (result) {
                                    is ResultadoLlamada.Error ->
                                        _uiState.update { it.copy(error = result.message.toString()) }
                                    is ResultadoLlamada.Success -> {
                                        _uiState.update {
                                            it.copy(
                                                tutor = result.data!!
                                            )
                                        }
                                    }
                                }
                            }
                    } else {
                        _uiState.update { it.copy(error = "No podemos conseguir el DNI del tutor.") }
                    }
                }
            }
            InfoPersonaContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
            InfoPersonaContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }

        }
    }
}