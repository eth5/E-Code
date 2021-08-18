package pro.it_dev.e_code.presentation.nav

sealed class Screen(val route:String){
    object MainScreen:Screen("main_screen")
    object ECodeScreen:Screen("ecode_screen")

    fun withArgs(vararg args:String):String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
