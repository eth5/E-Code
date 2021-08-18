package pro.it_dev.e_code.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import pro.it_dev.e_code.presentation.screens.ecode.ECodeScreen
import pro.it_dev.e_code.presentation.screens.ecode.ECodeViewModel
import pro.it_dev.e_code.presentation.screens.main.MainScreen
import pro.it_dev.e_code.presentation.screens.main.MainScreenViewModel

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route ){
        composable(route = Screen.MainScreen.route){
            val model = hiltViewModel<MainScreenViewModel>()
            MainScreen(navController = navController, model)
        }
        composable(
            route = Screen.ECodeScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                }
            )
        ){ entry->
            val model = hiltViewModel<ECodeViewModel>()
            ECodeScreen(eCodeID = entry.arguments?.getInt("id") ?: -1, model)
        }
    }
}