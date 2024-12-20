package pt.ipca.news_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pt.ipca.news_app.presentation.news_screen.NewsScreen
import pt.ipca.news_app.presentation.news_screen.NewsScreenViewModel
import pt.ipca.news_app.presentation.theme.Experiencia9Theme
import pt.ipca.news_app.util.NavGraphSetup

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Experiencia9Theme {
                val navController = rememberNavController()
                NavGraphSetup(navController = navController)
                val viewModel: NewsScreenViewModel = hiltViewModel()
                //NewsScreen(

                  //  state = viewModel.state,
                  //  onEvent = viewModel::onEvent
               // )
            }
        }
    }
}

