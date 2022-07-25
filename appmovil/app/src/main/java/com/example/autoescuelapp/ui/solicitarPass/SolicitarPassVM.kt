package com.example.autoescuelapp.ui.solicitarPass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.usescases.persona.ReiniciarPassUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolicitarPassVM @Inject constructor(
    private val reiniciarPassUC: ReiniciarPassUC,
) : ViewModel() {
    private val _uiState: MutableStateFlow<SolicitarPassContract.State> by lazy {
        MutableStateFlow(SolicitarPassContract.State())
    }
    val uiState: StateFlow<SolicitarPassContract.State> = _uiState

    fun handleEvent(event: SolicitarPassContract.Event) {
        when (event) {
            is SolicitarPassContract.Event.ReiniciarPass -> {
                viewModelScope.launch {
                    reiniciarPassUC.invoke(event.dni)
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
            SolicitarPassContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
            SolicitarPassContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
        }

    }
}