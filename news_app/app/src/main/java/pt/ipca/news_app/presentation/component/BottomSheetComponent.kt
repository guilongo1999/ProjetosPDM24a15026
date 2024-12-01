package pt.ipca.news_app.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.ipca.news_app.domain.model.Multimedia
import pt.ipca.news_app.domain.model.Result


@Composable
fun BottomSheetComponent(

    article: Result,
    //multimedia: Multimedia,
    onReadFullStoryButtonClicked: () -> Unit

) {

    Surface(modifier = Modifier.padding(16.dp)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = article.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.byline ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            ImageHolder(ImageUrl = article.url)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.abstract ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(

                modifier = Modifier
                    .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = article.orgFacet.joinToString(", "),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onReadFullStoryButtonClicked,
                modifier = Modifier.fillMaxWidth()

            ) {

                Text(text = "Read full Story")
            }
        }
    }
}