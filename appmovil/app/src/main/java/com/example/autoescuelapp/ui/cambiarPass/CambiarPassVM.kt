package com.example.autoescuelapp.ui.cambiarPass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.usescases.persona.CambiarPassUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CambiarPassVM @Inject constructor(
    private val cambiarPass: CambiarPassUC,
) : ViewModel() {
    private val _uiState: MutableStateFlow<CambiarPassContract.State> by lazy {
        MutableStateFlow(CambiarPassContract.State())
    }
    val uiState: StateFlow<CambiarPassContract.State> = _uiState

    fun handleEvent(event: CambiarPassContract.Event) {
        when (event) {
            is CambiarPassContract.Event.CambiarPass -> {
                viewModelScope.launch {
                    cambiarPass.invoke(event.dni, event.passAnterior, event.passNueva)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            mensaje = result.data,
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is CambiarPassContract.Event.MandarError -> {
                _uiState.update {
                    it.copy(
                        error = event.error
                    )
                }
            }
            CambiarPassContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
            CambiarPassContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
        }
    }

}