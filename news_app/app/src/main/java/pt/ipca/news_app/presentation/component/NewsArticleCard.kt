package pt.ipca.news_app.presentation.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
//import pt.ipca.news_app.domain.model.Data
import pt.ipca.news_app.domain.model.Multimedia
import pt.ipca.news_app.domain.model.Result
import pt.ipca.news_app.util.DateFormatter


@Composable
fun NewsArticleCard(

    modifier: Modifier = Modifier,
    data:Result,
    onCarClicked: (Result) -> Unit

) {


    val date = DateFormatter(data.updatedDate)
    Card(
        modifier = modifier.clickable { onCarClicked(data) }
    ) {

        Column(

            modifier = modifier.padding(12.dp)
        ) {
            ImageHolder(ImageUrl = data.multimedia.firstOrNull()?.url ?: "")
            Text(

                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,

            )
            Spacer(modifier = modifier.height(8.dp))
            Row(

                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(

                    text = date,
                    style = MaterialTheme.typography.bodySmall,

                    )

                Text(

                    text = data.section ?: "",
                    style = MaterialTheme.typography.bodySmall,

                )

            }
        }
    }

}