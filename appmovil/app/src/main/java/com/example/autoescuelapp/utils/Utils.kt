package com.example.autoescuelapp.utils

import java.time.DayOfWeek
import java.time.Month

class Utils {
    companion object {
        fun diaSemanaSp(dia: DayOfWeek): String {
            return when (dia) {
                DayOfWeek.MONDAY -> "Lunes"
                DayOfWeek.TUESDAY -> "Martes"
                DayOfWeek.WEDNESDAY -> "Miércoles"
                DayOfWeek.THURSDAY -> "Jueves"
                DayOfWeek.FRIDAY -> "Viernes"
                DayOfWeek.SATURDAY -> "Sábado"
                DayOfWeek.SUNDAY -> "Domingo"
            }
        }

        fun mesSp(mes: Month): String {
            return when (mes) {
                Month.JANUARY -> "Enero"
                Month.FEBRUARY -> "Febrero"
                Month.MARCH -> "Marzo"
                Month.APRIL -> "Abril"
                Month.MAY -> "Mayo"
                Month.JUNE -> "Junio"
                Month.JULY -> "Julio"
                Month.AUGUST -> "Agosto"
                Month.SEPTEMBER -> "Septiembre"
                Month.OCTOBER -> "Octubre"
                Month.NOVEMBER -> "Noviembre"
                Month.DECEMBER -> "Diciembre"
            }
        }
    }
}