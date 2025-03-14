package com.example.sample.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val pokemonList: StateFlow<PokemonListModel?> get() = _pokemonList.asStateFlow()
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

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
}