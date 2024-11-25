package pt.ipca.experiencia9.presentation.component


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
import pt.ipca.experiencia9.domain.model.Data

@Composable
fun NewsArticleCard(

    modifier: Modifier = Modifier,
    data:Data,
    onCarClicked: (Data) -> Unit

) {


    Card(
        modifier = modifier.clickable { onCarClicked(data) }
    ) {

        Column(

            modifier = modifier.padding(12.dp)
        ) {
            ImageHolder(ImageUrl = data.imageUrl)
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

                    text = data.publishedAt ?: "",
                    style = MaterialTheme.typography.bodySmall,

                    )

                Text(

                    text = data.publishedAt ?: "",
                    style = MaterialTheme.typography.bodySmall,

                )

            }
        }
    }

}