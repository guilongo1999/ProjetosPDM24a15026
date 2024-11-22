package pt.ipca.experiencia9.presentation.news_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsScreen(

    viewModel: NewsScreenViewModel = hiltViewModel()
){

    LazyColumn(

        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(viewModel.datas) {

            data -> Text(text = data.title)
        }
    }
}

