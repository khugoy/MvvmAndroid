package com.sapient.demoapp.presentation.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.RequestManager
import com.sapient.demoapp.databinding.CharacterDetailFragmentBinding
import com.sapient.demoapp.domain.models.Character
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: CharacterDetailFragmentBinding
    private val viewModel: CharacterDetailViewModel by viewModels()
    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { result ->
                    if (result.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.characterCl.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        if (!result.error.isNullOrEmpty())
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT)
                                .show()
                        else {
                            bindCharacter(result.character)
                            binding.characterCl.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun bindCharacter(character: Character?) {
        binding.name.text = character?.name
        binding.species.text = character?.species
        binding.status.text = character?.status
        binding.gender.text = character?.gender
        glide.load(character?.image).into(binding.image)
    }

}
