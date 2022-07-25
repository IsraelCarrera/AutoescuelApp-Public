package com.example.autoescuelapp.ui.clases.masInfoClases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.usescases.clase.DeleteClaseUC
import com.example.autoescuelapp.usescases.clase.GetClaseIdUC
import com.example.autoescuelapp.usescases.feedback.AddFeedbackUC
import com.example.autoescuelapp.usescases.feedback.DeleteFeedbackUC
import com.example.autoescuelapp.usescases.feedback.GetFeedbackUC
import com.example.autoescuelapp.usescases.persona.GetAlumnoByIdClaseUC
import com.example.autoescuelapp.usescases.persona.GetProfesorByIdClaseUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasInfoClasesVM @Inject constructor(
    private val getAlumnoByIdClase: GetAlumnoByIdClaseUC,
    private val getProfesorByIdClase: GetProfesorByIdClaseUC,
    private val getClaseId: GetClaseIdUC,
    private val deleteClase: DeleteClaseUC,
    private val addFeedback: AddFeedbackUC,
    private val getFeedback: GetFeedbackUC,
    private val deleteFeedback: DeleteFeedbackUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MasInfoClasesContract.State> by lazy {
        MutableStateFlow(MasInfoClasesContract.State())
    }
    val uiState: StateFlow<MasInfoClasesContract.State> = _uiState

    fun handleEvent(event: MasInfoClasesContract.Event) {
        when (event) {
            is MasInfoClasesContract.Event.BuscarAlumno -> {
                viewModelScope.launch {
                    getAlumnoByIdClase.invoke(event.id)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            alumno = result.data!!
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is MasInfoClasesContract.Event.BuscarProfesor -> {
                viewModelScope.launch {
                    getProfesorByIdClase.invoke(event.id)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            profesor = result.data!!
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is MasInfoClasesContract.Event.ClasePorId -> {
                viewModelScope.launch {
                    getClaseId.invoke(event.id)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            clase = result.data!!
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is MasInfoClasesContract.Event.DeleteClaseId -> {
                viewModelScope.launch {
                    deleteClase.invoke(event.id)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            mensaje = result.data
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            MasInfoClasesContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
            MasInfoClasesContract.Event.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
            is MasInfoClasesContract.Event.AddFeedback -> {
                viewModelScope.launch {
                    if (event.feedback == null) {
                        _uiState.update {
                            it.copy(
                                mensaje = "Error al crear el comentario."
                            )
                        }
                    } else {
                        addFeedback.invoke(event.feedback)
                            .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                            .collect { result ->
                                when (result) {
                                    is ResultadoLlamada.Error ->
                                        _uiState.update { it.copy(error = result.message.toString()) }
                                    is ResultadoLlamada.Success -> {
                                        //Al ser respuesta OK, updateo el state.
                                        _uiState.update {
                                            it.copy(
                                                mensaje = "Incidencia registrada correctamente."
                                            )
                                        }
                                    }
                                }
                            }
                    }
                }
            }
            is MasInfoClasesContract.Event.DeleteFeedback -> {
                viewModelScope.launch {
                    deleteFeedback.invoke(event.id)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    _uiState.update {
                                        it.copy(
                                            mensaje = result.data
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is MasInfoClasesContract.Event.GetFeedback -> {
                viewModelScope.launch {
                    getFeedback.invoke(event.id)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error -> {}
//                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Al ser respuesta OK, updateo el state.
                                    if (result.data != null) {
                                        _uiState.update {
                                            it.copy(
                                                feedback = result.data
                                            )
                                        }
                                    }
                                }
                            }
                        }
                }
            }
            is MasInfoClasesContract.Event.MandarError -> {
                _uiState.update {
                    it.copy(
                        error = event.error
                    )
                }
            }
        }
    }
}