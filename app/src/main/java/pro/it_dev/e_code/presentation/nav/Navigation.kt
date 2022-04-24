package pro.it_dev.e_code.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pro.it_dev.e_code.presentation.screens.ecode.ECodeDetailScreen
import pro.it_dev.e_code.presentation.screens.main.MainScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route ){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.ECodeScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){ entry->
            val eCodeId = entry.arguments?.getInt("id") ?: -1
            ECodeDetailScreen(eCodeID = eCodeId)
        }
    }
}