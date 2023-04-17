package com.sapient.demoapp.presentation.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sapient.demoapp.databinding.CharacterDetailFragmentBinding
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.presentation.characterList.CharactersFragment
import com.sapient.demoapp.presentation.extensions.showProgressBar
import com.sapient.demoapp.presentation.extensions.snackBar
import com.sapient.demoapp.presentation.image.loadImage
import com.sapient.demoapp.presentation.viewState.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: CharacterDetailFragmentBinding
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(CharactersFragment.ID)?.let {
            viewModel.getCharacter(it)
        }

        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterDetail.collect {
                    when (it) {
                        is UIState.Loading -> {
                            binding.progressBar.showProgressBar(true)
                        }
                        is UIState.Success -> {
                            binding.progressBar.showProgressBar(false)
                            bindCharacter(it.output)
                        }
                        is UIState.Failure -> {
                            binding.progressBar.showProgressBar(false)
                            snackBar(CharactersFragment.NETWORK_ERROR)
                        }
                    }
                }
            }
        }
    }

    private fun bindCharacter(character: CharacterDomainModel) {
        with(binding) {
            name.text = character.name
            species.text = character.species
            status.text = character.status
            gender.text = character.gender
            image.loadImage(character.image)
        }
    }
}
