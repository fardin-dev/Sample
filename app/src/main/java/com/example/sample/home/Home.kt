package com.example.sample.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.sample.data.api.model.PokemonDetailsModel
import com.example.sample.data.api.model.PokemonListModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val pokemonList = viewModel.pokemonList.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            Text(
                text = "Pokemon List",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Button(
                onClick = { viewModel.getPokemonList() }
            ) {
                Text(text = "Reload")
            }

            if (errorMessage.value != null) {
                ErrorState()
            } else if (isLoading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = Color.Blue,
                        trackColor = Color.Red
                    )
                }
            } else {
                LazyColumn {
                    pokemonList.value?.let { results ->
                        items(results.results.size) { index ->
                            val name = results.results[index].name
                            PokemonCell(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("details/$name")
                                    },
                                index = "$index",
                                name = name
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonCell(
    modifier: Modifier = Modifier,
    index: String,
    name: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = index, fontSize = 20.sp)
            Text(text = name, fontSize = 20.sp, modifier = Modifier.padding(start = 16.dp))
        }
        HorizontalDivider(color = Color.Gray, modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    name: String,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // MARK: - State
    val pokemonDetails = viewModel.pokemonDetails.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val gotError = viewModel.gotError.collectAsState()

    LaunchedEffect(pokemonDetails) {
        viewModel.fetchDetails(name)
    }
    Content(
        isLoading = isLoading.value,
        gotError = gotError.value,
        pokemonDetails = pokemonDetails.value
    )
}

@Composable
private fun Content(
    isLoading: Boolean,
    gotError: Boolean,
    pokemonDetails: PokemonDetailsModel?
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(200.dp),
                color = Color.Blue,
                trackColor = Color.Red
            )
        } else if(gotError) {
            ErrorState()
        } else {
            pokemonDetails.let {details ->
                if (details != null) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(url = details.sprite.imageURL)
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            text = details.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AsyncImage(url: String) {
    val painter: Painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url).apply(block = fun ImageRequest.Builder.() {
                transformations(CircleCropTransformation())
            }).build()
        )
    Image(
        modifier = Modifier.size(200.dp),
        painter = painter,
        contentDescription = null
    )
}

























