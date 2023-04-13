package com.sapient.demoapp.presentation.characterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapient.demoapp.domain.interactor.GetCharacterListUseCase
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.domain.util.Resource
import com.sapient.demoapp.presentation.viewState.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterListUseCase: GetCharacterListUseCase
) : ViewModel() {

    private val _characterList =
        MutableStateFlow<UIState<List<CharacterDomainModel>>>(UIState.Loading(true))
    val characterList get() = _characterList

    fun getCharacterList() {
        viewModelScope.launch {
            characterListUseCase().map {
                when (it) {
                    is Resource.OnSuccess -> UIState.Success(it.data)
                    is Resource.OnFailure -> UIState.Failure(it.throwable)
                }
            }.collect {
                when (it) {
                    is UIState.Loading -> _characterList.value = UIState.Loading(false)
                    is UIState.Failure -> _characterList.value = it
                    is UIState.Success -> {
                        it.output.let { characters ->
                            _characterList.value = UIState.Success(characters)
                        }
                    }
                }
            }

        }
    }
}
