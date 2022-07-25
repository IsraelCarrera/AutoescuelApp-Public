package com.example.autoescuelapp.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.autoescuelapp.ui.cambiarPass.CambiarPass
import com.example.autoescuelapp.ui.clases.clasesAnteriores.ClasesAnterioresAlumno
import com.example.autoescuelapp.ui.clases.clasesAnteriores.ClasesAnterioresProfesor
import com.example.autoescuelapp.ui.clases.clasesProximas.ClasesProximasAlumno
import com.example.autoescuelapp.ui.clases.clasesProximas.ClasesProximasProfesor
import com.example.autoescuelapp.ui.clases.masInfoClases.MasInfoClasesAlumno
import com.example.autoescuelapp.ui.clases.masInfoClases.MasInfoClasesProfesor
import com.example.autoescuelapp.ui.clases.solicitarClases.SolicitarClases
import com.example.autoescuelapp.ui.infoCoche.InfoCoche
import com.example.autoescuelapp.ui.infoPersona.InfoPersona
import com.example.autoescuelapp.ui.loggin.HacerLoggin
import com.example.autoescuelapp.ui.notificarIncidencia.NotificarIncidencia
import com.example.autoescuelapp.ui.principal.PantallaPrincipal
import com.example.autoescuelapp.ui.solicitarPass.SolicitarPass


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "log"
    ) {
        //Genericas
        composable("log") {
            HacerLoggin(onNavigate = { navController.navigate(it) })
        }
        composable("usuario/principal") {
            PantallaPrincipal(onNavigate = { navController.navigate(it) })
        }
        composable("usuario/info") {
            InfoPersona(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }
        composable("usuario/cambioPass") {
            CambiarPass(onGoBackClick = { navController.popBackStack() })
        }
        composable("usuario/solicitarPass") {
            SolicitarPass(onGoBackClick = { navController.popBackStack() })
        }
        //Alumno
        composable("alumno/clases/proximas") {
            ClasesProximasAlumno(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }
        composable("alumno/clases/anteriores") {
            ClasesAnterioresAlumno(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }
        composable("alumno/clases/solicitar") {
            SolicitarClases(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }
        //Profesor
        composable("profesor/clases/proximas") {
            ClasesProximasProfesor(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }
        composable("profesor/clases/anteriores") {
            ClasesAnterioresProfesor(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }
        composable("profesor/coche/info") {
            InfoCoche(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }
        composable("profesor/coche/incidencia") {
            NotificarIncidencia(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }

        composable(
            route = "alumno/clases/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            requireNotNull(id)
            MasInfoClasesAlumno(
                onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() },
                idClase = id)
        }

        composable(
            route = "profesor/clases/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            requireNotNull(id)
            MasInfoClasesProfesor(
                onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() },
                idClase = id)
        }
    }
}