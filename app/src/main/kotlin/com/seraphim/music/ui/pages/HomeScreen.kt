package com.seraphim.music.ui.pages

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ArtistScreenDestination
import com.seraphim.music.model.SimplifiedPlaylistObject
import com.seraphim.music.shared.database.entity.FeaturedPlayEntity
import com.seraphim.music.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>(start = true)
@Composable
fun HomeScreen() {
    val navigator = LocalDestinationsNavigator.current
    val viewModel = koinViewModel<HomeViewModel>()
    val featurePlayLists: LazyPagingItems<FeaturedPlayEntity> =
        viewModel.featurePlayListPagingFlow.collectAsLazyPagingItems()
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            navigationIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "TobBar Search Music",
                    modifier = Modifier.clickable {
                        navigator.navigate(ArtistScreenDestination)
                    })
            },
            title = {
//                navigator.navigate(ArtistScreenDestination)
            },
            actions = {
                Icon(Icons.Default.MoreVert, contentDescription = "TobBar More Action")
            })
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(featurePlayLists.itemCount) { index ->
                val item = featurePlayLists[index]
                item?.let {
                    PlayListItem(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
//                                navigator.navigate(
//                                    ArtistScreenDestination(
//                                        artistId = it.id,
//                                        artistName = it.name
//                                    )
//                                )
                            },
                        item = it
                    )
                }
            }
        }
    }
}

@Composable
fun PlayListItem(
    modifier: Modifier = Modifier,
    item: FeaturedPlayEntity
) {
    // Implementation of PlayListItem goes here
    LoadCoilImage(item.uri.orEmpty(), item.name.orEmpty())
}

@Composable
fun LoadCoilImage(url: String, name: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .diskCachePolicy(CachePolicy.ENABLED) // 启用磁盘缓存
            .networkCachePolicy(CachePolicy.ENABLED) // 启用网络缓存
            .build(),
        contentDescription = name,
        modifier = Modifier.fillMaxWidth()

    )
}