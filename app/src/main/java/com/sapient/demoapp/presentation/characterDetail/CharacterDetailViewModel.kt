package com.sapient.demoapp.presentation.characterDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapient.demoapp.domain.interactor.GetCharacterByIdUseCase
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.domain.util.Resource
import com.sapient.demoapp.presentation.viewState.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterByIdUseCase: GetCharacterByIdUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _characterDetail =
        MutableStateFlow<UIState<CharacterDomainModel>>(UIState.Loading(true))
    val characterDetail get() = _characterDetail

    fun getCharacter(characterId: Int) {
        viewModelScope.launch(defaultDispatcher) {
            characterByIdUseCase(characterId)
                .map {
                    when (it) {
                        is Resource.OnSuccess -> UIState.Success(it.data)
                        is Resource.OnFailure -> UIState.Failure(it.throwable)
                    }
                }.collect {
                    when (it) {
                        is UIState.Loading -> _characterDetail.value = UIState.Loading(false)
                        is UIState.Failure -> _characterDetail.value = it
                        is UIState.Success -> {
                            it.output.let { character ->
                                _characterDetail.value = UIState.Success(character)
                            }
                        }
                    }
                }
        }
    }

}
