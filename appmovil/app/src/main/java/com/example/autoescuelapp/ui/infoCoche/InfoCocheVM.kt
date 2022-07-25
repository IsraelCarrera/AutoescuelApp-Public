package com.example.autoescuelapp.ui.infoCoche

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.usescases.coche.GetCocheByMatriculaUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoCocheVM @Inject constructor(
    private val getCocheByMatricula: GetCocheByMatriculaUC,
) : ViewModel() {
    private val _uiState: MutableStateFlow<InfoCocheContract.State> by lazy {
        MutableStateFlow(InfoCocheContract.State())
    }
    val uiState: StateFlow<InfoCocheContract.State> = _uiState

    fun handleEvent(event: InfoCocheContract.Event) {
        when (event) {
            is InfoCocheContract.Event.BuscarCoche -> {
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
            InfoCocheContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
            InfoCocheContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }

        }
    }
}