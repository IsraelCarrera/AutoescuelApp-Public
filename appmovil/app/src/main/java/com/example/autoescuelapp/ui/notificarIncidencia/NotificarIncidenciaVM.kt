package com.example.autoescuelapp.ui.notificarIncidencia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.usescases.incidencia.PostIncidenciaUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificarIncidenciaVM @Inject constructor(
    private val postIncidencia: PostIncidenciaUC,
) : ViewModel() {
    private val _uiState: MutableStateFlow<NotificarIncidenciaContract.State> by lazy {
        MutableStateFlow(NotificarIncidenciaContract.State())
    }
    val uiState: StateFlow<NotificarIncidenciaContract.State> = _uiState

    fun handleEvent(event: NotificarIncidenciaContract.Event) {
        when (event) {
            is NotificarIncidenciaContract.Event.AddIncidencia -> {
                viewModelScope.launch {
                    postIncidencia.invoke(event.incidencia)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            mensaje = "Se ha registrado la incidencia con éxito.",
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is NotificarIncidenciaContract.Event.MandarError -> {
                _uiState.update {
                    it.copy(
                        error = event.error
                    )
                }
            }
            NotificarIncidenciaContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
            NotificarIncidenciaContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
        }
    }

}