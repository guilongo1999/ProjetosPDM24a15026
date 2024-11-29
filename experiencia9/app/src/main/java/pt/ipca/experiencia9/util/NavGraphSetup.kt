package pt.ipca.experiencia9.util

import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pt.ipca.experiencia9.presentation.article_Screen.ArticleScreen
import pt.ipca.experiencia9.presentation.news_screen.NewsScreen
import pt.ipca.experiencia9.presentation.news_screen.NewsScreenViewModel

@Composable
fun NavGraphSetup(

    navController: NavHostController
) {


    val argKey = "web_url"
    NavHost(navController = navController, startDestination = "news_screen") {

        composable(route = "news_screen") {

            val viewModel: NewsScreenViewModel = hiltViewModel()
            NewsScreen(state = viewModel.state,
                onEvent = viewModel::onEvent,
                onReadFullStoryButtonClicked = {url ->

                    navController.navigate("article_screen?web_url=$url")
                })
        }
        composable(

            route = "article_screen?$argKey={$argKey}",
            arguments = listOf(navArgument(name = argKey) {

                type = NavType.StringType
            })
        ) {

            BackStackEntry ->

            ArticleScreen(url = BackStackEntry.arguments?.getString(argKey), onBackPressed = {navController.navigateUp()})
        }
    }

}