package com.meksconway.compose_sample_rickandmorty.ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.meksconway.compose_sample_rickandmorty.CharactersQuery
import com.meksconway.compose_sample_rickandmorty.data.CharactersSource
import com.meksconway.compose_sample_rickandmorty.networking.ApiService
import com.meksconway.compose_sample_rickandmorty.ui.component.TopAppBar
import com.meksconway.compose_sample_rickandmorty.ui.theme.shapes
import com.meksconway.compose_sample_rickandmorty.ui.theme.typography
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.flow.Flow

class CharactersViewModel @ViewModelInject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val characters: Flow<PagingData<CharactersQuery.Result>> =
        Pager(
            config = PagingConfig(
                initialLoadSize = 1,
                enablePlaceholders = true,
                pageSize = 10
            )
        ) {
            CharactersSource(apiService = apiService)
        }.flow.cachedIn(viewModelScope)

}

@Composable
fun CharacterPage(characterList: Flow<PagingData<CharactersQuery.Result>>) {

    val lazyCharacterItem: LazyPagingItems<CharactersQuery.Result> =
        characterList.collectAsLazyPagingItems()

    Column {
        TopAppBar()

        LazyColumn(
            contentPadding = PaddingValues(
                10.dp
            )
        ) {
            items(lazyCharacterItem) { item ->
                CharacterCard(item)
            }

            lazyCharacterItem.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = lazyCharacterItem.loadState.refresh as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage!!,
                                modifier = Modifier.fillParentMaxSize(),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = lazyCharacterItem.loadState.append as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage!!,
                                onClickRetry = { retry() }
                            )
                        }
                    }
                }
            }


        }

    }

}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}



@Composable
fun CharacterCard(listItem: CharactersQuery.Result?) {

    Box(modifier = Modifier.padding(
        bottom = 8.dp
    )) {
        Card(
            shape = shapes.medium.copy(CornerSize(8.dp)),
            modifier = Modifier.shadow(5.dp, RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier =
                Modifier.fillMaxWidth()
                    .clickable {}
                    .padding(16.dp)

            ) {

                Card(
                    shape = shapes.medium.copy(CornerSize(8.dp)),
                    modifier = Modifier.preferredSize(84.dp).shadow(5.dp, RoundedCornerShape(8.dp))
                ) {

                    CoilImage(
                        imageModel = listItem?.image ?: "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically).padding(
                        horizontal = 16.dp
                    )
                ) {
                    Text(text = "${listItem?.name}", style = typography.h6)
                    Text(text = "Status: ${listItem?.status}", style = typography.subtitle1)
                }
            }
        }
    }


}

