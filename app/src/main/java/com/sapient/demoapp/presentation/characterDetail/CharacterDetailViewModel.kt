package com.sapient.demoapp.presentation.characterDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapient.demoapp.domain.interactor.GetCharacterByIdUseCase
import com.sapient.demoapp.domain.models.Character
import com.sapient.demoapp.domain.util.Resource
import com.sapient.demoapp.presentation.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UIState(isLoading = true))
    val state: StateFlow<UIState> = _state

    fun start(id: Int) {
        getCharacter(id)
    }

    private fun getCharacter(characterId: Int) {
        var character: Character? = null
        viewModelScope.launch {
            characterByIdUseCase(characterId).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = UIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        result.data?.let { it-> character = it }
                        _state.value = UIState(isLoading = false, character = character)
                    }

                    is Resource.Error -> {
                        _state.value = UIState(isLoading = false, error = result.message)
                    }

                }
            }.launchIn(this)
        }
    }

}
