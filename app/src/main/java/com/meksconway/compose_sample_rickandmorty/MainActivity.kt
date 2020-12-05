package com.meksconway.compose_sample_rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.meksconway.compose_sample_rickandmorty.ui.pages.CharacterPage
import com.meksconway.compose_sample_rickandmorty.ui.pages.CharactersViewModel
import com.meksconway.compose_sample_rickandmorty.ui.theme.ComposeSampleRickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleRickAndMortyTheme {
                Surface(color = MaterialTheme.colors.background) {
                   CharacterPage(viewModel.characters)
                }
            }
        }
    }
}
