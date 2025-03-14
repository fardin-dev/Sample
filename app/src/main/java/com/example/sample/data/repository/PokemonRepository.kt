package com.example.sample.data.repository

import com.example.sample.data.api.PokemonApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    suspend fun getPokemonList(limit: Int = 10, offset: Int = 0) = pokemonApi.getPokemonList(limit, offset)

    suspend fun getPokemonDetails(name: String) = pokemonApi.getPokemonDetails(name)
}