package com.example.sample.data.api

import com.example.sample.data.api.model.PokemonDetailsModel
import com.example.sample.data.api.model.PokemonListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET(ApiConstants.POKEMON_END_POINT)
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): Response<PokemonListModel>

    @GET(ApiConstants.POKEMON_DETAILS_END_POINT)
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): Response<PokemonDetailsModel>
}