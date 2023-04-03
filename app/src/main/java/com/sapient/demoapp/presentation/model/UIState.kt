package com.sapient.demoapp.presentation.model

import com.sapient.demoapp.domain.models.Character


data class UIState(
    val characterList: MutableList<Character> = mutableListOf(),
    val isLoading: Boolean = false,
    val character: Character? = null,
    val error: String? = null
)
