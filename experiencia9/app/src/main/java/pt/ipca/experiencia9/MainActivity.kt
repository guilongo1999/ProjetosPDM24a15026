package pt.ipca.experiencia9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import pt.ipca.experiencia9.presentation.news_screen.NewsScreen
import pt.ipca.experiencia9.presentation.news_screen.NewsScreenViewModel
import pt.ipca.experiencia9.presentation.theme.Experiencia9Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Experiencia9Theme {
                val viewModel: NewsScreenViewModel = hiltViewModel()
                NewsScreen(

                    state = viewModel.state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

