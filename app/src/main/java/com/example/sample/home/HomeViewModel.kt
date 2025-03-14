package com.example.sample.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.data.api.model.PokemonDetailsModel
import com.example.sample.data.api.model.PokemonListModel
import com.example.sample.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepo: PokemonRepository
) : ViewModel() {
    private val _pokemonList = MutableStateFlow<PokemonListModel?>(null)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(true)
    private val _pokemonDetails = MutableStateFlow<PokemonDetailsModel?>(null)
    private val _gotError = MutableStateFlow<Boolean>(false)


    val pokemonList: StateFlow<PokemonListModel?> get() = _pokemonList.asStateFlow()
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()
    val pokemonDetails: StateFlow<PokemonDetailsModel?> get() = _pokemonDetails.asStateFlow()
    val gotError: StateFlow<Boolean> get() = _gotError.asStateFlow()



     init {
        getPokemonList()
    }

    fun getPokemonList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = pokemonRepo.getPokemonList(0,150)
            if(response.isSuccessful) {
                val body = response.body()
                Log.d("Success", "$body?.size")
                _isLoading.value = false
                _pokemonList.value = body
            } else {
                val error = response.errorBody()
                if(error != null) {
                    Log.d("Pokemon List Error", error.toString())
                    _isLoading.value = false
                    _errorMessage.value = error.toString()
                }
            }
        }
    }

    fun fetchDetails(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = pokemonRepo.getPokemonDetails(name)
            val error = result.errorBody()
            val data = result.body()
            if (error != null || !result.isSuccessful) {
                Log.d("Got an error", "Got an error")
                _isLoading.value = false
                _errorMessage.value = error.toString()
                _gotError.value = true
                return@launch
            }
            if (data != null) {
                Log.d("Got data", "Got data")
                _isLoading.value = false
                _pokemonDetails.value = data
                _gotError.value = false
            } else {
                Log.d("Got nothing", "Got data")
                _isLoading.value = false
            }
        }
    }
}