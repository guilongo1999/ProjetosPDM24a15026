package pt.ipca.experiencia9.presentation.news_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import pt.ipca.experiencia9.domain.model.Data
import pt.ipca.experiencia9.presentation.component.CategoryTabRow
import pt.ipca.experiencia9.presentation.component.NewsArticleCard
import pt.ipca.experiencia9.presentation.component.NewsScreenTopBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(

    state:NewsScreenState,
    onEvent: (NewsScreenEvent) -> Unit


) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState()
    val categories = listOf("general", "business", "health", "science", "sports", "tech", "entertainment", "politics", "food", "travel")

    LaunchedEffect(key1 = pagerState) {

        snapshotFlow { pagerState.currentPage }.collect {page -> onEvent(NewsScreenEvent.onCategoryChange(category = categories[page]))

        }
    }

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NewsScreenTopBar(
                scrollBehavior = scrollBehavior,
                onSearchIconClicked = {})

        }
    ) {

            padding ->
            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                CategoryTabRow(pagerState = pagerState,
                    categories = categories,
                    onTabSelected = {index ->

                        coroutineScope.launch {pagerState.animateScrollToPage(index)}
                    }

                )


         HorizontalPager(

             pageCount = categories.size,
             state = pagerState,

             )

         {

            NewsArticlesList(state = state, onCardClicked = {})

                 }
             }
        }

    }


@Composable
fun NewsArticlesList(

    state: NewsScreenState,
    onCardClicked: (Data) -> Unit
) {



    LazyColumn(

        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)

    ) {

        items(state.datas) {

                data ->
            NewsArticleCard(data = data, onCarClicked = onCardClicked)

        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if(state.isLoading) {

            CircularProgressIndicator()
        }

    }
}

/*
  LazyColumn(

                         verticalArrangement = Arrangement.spacedBy(16.dp),
                         contentPadding = PaddingValues(16.dp)

                     ) {

                         items(state.datas) {

                                 data ->
                             NewsArticleCard(data = data, onCarClicked = {})

                         }
                     }
 */