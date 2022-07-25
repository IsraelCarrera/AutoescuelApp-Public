package com.example.autoescuelapp.ui.clases.solicitarClases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.usescases.clase.GetClasesProfesorDateUC
import com.example.autoescuelapp.usescases.clase.SaveClaseUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolicitarClasesVM @Inject constructor(
    private val saveClaseUC: SaveClaseUC,
    private val getClasesProfesorDateUC: GetClasesProfesorDateUC,
) : ViewModel() {
    init {
        handleEvent(SolicitarClasesContract.Event.GetClasesFecha)
    }

    private val _uiState: MutableStateFlow<SolicitarClasesContract.State> by lazy {
        MutableStateFlow(SolicitarClasesContract.State())
    }
    val uiState: StateFlow<SolicitarClasesContract.State> = _uiState

    fun handleEvent(event: SolicitarClasesContract.Event) {
        when (event) {
            is SolicitarClasesContract.Event.GetClasesFecha -> {
                viewModelScope.launch {
                    getClasesProfesorDateUC.invoke(UsuarioSing.usuario?.dniTutor!!)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            clases = result.data!!
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is SolicitarClasesContract.Event.SolicitarClase -> {
                if (event.selected) {
                    viewModelScope.launch {
                        saveClaseUC.invoke(event.clase)
                            .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                            .collect { result ->
                                when (result) {
                                    is ResultadoLlamada.Error ->
                                        _uiState.update { it.copy(error = result.message.toString()) }
                                    is ResultadoLlamada.Success -> {
                                        //Se manda un mensaje indicando que se ha guardado con éxito la clase.
                                        _uiState.update {
                                            it.copy(
                                                mensaje = "Se ha registrado su solicitud de clase con éxito."
                                            )
                                        }
                                    }
                                }
                            }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            mensaje = "No has seleccionado ninguna hora."
                        )
                    }
                }
            }
            SolicitarClasesContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
            SolicitarClasesContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
        }
    }
}