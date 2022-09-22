package yehor.tkachuk.mycv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import yehor.tkachuk.mycv.model.CVProfile
import yehor.tkachuk.mycv.ui.screen.home.HomeScreen
import yehor.tkachuk.mycv.ui.screen.project.ProjectScreen

@Composable
fun MyCvApp(profile: CVProfile) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController, profile = profile)
        }
        composable(
            "project?projectId={projectId}",
            arguments = listOf(navArgument("projectId") { type = NavType.IntType })
        ) {
            val projectId = it.arguments?.getInt("projectId")
            val project = profile.projectList.firstOrNull { it.id == projectId }
            if(project != null){
                ProjectScreen(navController = navController, project = project)
            } else {
                navController.navigate("home"){
                    this.popUpTo(it.id){
                        inclusive = true
                    }
                }
            }
        }
    }
}

fun NavController.navigateHome(){
    navigate("home"){
        popUpTo("home"){
            inclusive = true
        }
    }
}

fun NavController.navigateToProject(id: Int){
    navigate("project?projectId=$id")
}