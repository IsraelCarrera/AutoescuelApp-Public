package com.example.autoescuelapp.ui.loggin

import com.example.autoescuelapp.domain.PersonaLogin

interface HacerLogContract {
    sealed class Event {
        data class HacerLog(val log: PersonaLogin) : Event()
        object ErrorMostrado : Event()
        object PonerLogDone : Event()
    }

    data class State(
        val error: String = "",
        val logOk: Boolean = false,
    )
}