package com.example.autoescuelapp.ui.loggin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoescuelapp.data.utils.ResultadoLlamada
import com.example.autoescuelapp.data.utils.UsuarioSing
import com.example.autoescuelapp.usescases.persona.HacerLogUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HacerLogVM @Inject constructor(
    private val hacerLogUC: HacerLogUC,
) : ViewModel() {
    //Aquí se emitirá un true/false y un error.
    //Una vez recibamos los datos si son true, se guardan en un singleton para mantener los datos del usuario almacenados
    private val _uiState: MutableStateFlow<HacerLogContract.State> by lazy {
        MutableStateFlow(HacerLogContract.State())
    }
    val uiState: StateFlow<HacerLogContract.State> = _uiState

    fun handleEvent(event: HacerLogContract.Event) {
        when (event) {
            is HacerLogContract.Event.HacerLog -> {
                //Primero guardo el dni y la pass.
                UsuarioSing.dni = event.log.dni
                UsuarioSing.pass = event.log.pass
                val personaLogin = event.log
                viewModelScope.launch {
                    hacerLogUC.invoke(personaLogin)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message.toString()) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message.toString()) }
                                is ResultadoLlamada.Success -> {
                                    //Guardo al usuario en el singleton
                                    UsuarioSing.usuario = result.data
                                    //Devuelvo un boolean true
                                    _uiState.update {
                                        it.copy(logOk = true)
                                    }
                                }
                            }
                        }
                }
            }
            HacerLogContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = ""
                    )
                }
            }
            HacerLogContract.Event.PonerLogDone -> _uiState.update { it.copy(logOk = false) }
        }
    }

}