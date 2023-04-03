package com.sapient.demoapp.presentation.characterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapient.demoapp.domain.interactor.GetCharacterListUseCase
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
class CharactersViewModel @Inject constructor(
    private val characterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UIState())
    val state: StateFlow<UIState> = _state

    var characterList: MutableList<Character> = mutableListOf()

    init {
        getCharacterList()
    }

    private fun getCharacterList() {
        viewModelScope.launch {
            characterListUseCase(Unit).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = UIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        result.data?.onEach { character -> characterList.add(character) }
                        _state.value = UIState(isLoading = false, characterList = characterList)
                    }

                    is Resource.Error -> {
                        _state.value = UIState(isLoading = false, error = result.message)
                    }

                }
            }.launchIn(this)
        }
    }
}
