package com.sapient.demoapp.presentation.characterList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapient.demoapp.R
import com.sapient.demoapp.constant.AppConstants
import com.sapient.demoapp.databinding.CharactersFragmentBinding
import com.sapient.demoapp.presentation.extensions.showProgressBar
import com.sapient.demoapp.presentation.extensions.snackBar
import com.sapient.demoapp.presentation.viewState.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: CharactersFragmentBinding
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CharactersAdapter { characterId ->
            findNavController().navigate(
                R.id.action_charactersFragment_to_characterDetailFragment,
                bundleOf(AppConstants.ID to characterId)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getCharacterList()
        initObservers()
    }


    private fun setupRecyclerView() {
        with(binding) {
            charactersRv.layoutManager = LinearLayoutManager(requireContext())
            charactersRv.adapter = adapter
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterList.collect {
                    when (it) {
                        is UIState.Loading -> {
                            binding.progressBar.showProgressBar(true)
                        }
                        is UIState.Success -> {
                            binding.progressBar.showProgressBar(false)
                            it.let { data ->
                                adapter?.differ?.submitList(data.output)
                            }
                        }
                        is UIState.Failure -> {
                            binding.progressBar.showProgressBar(false)
                            snackBar(AppConstants.NETWORK_ERROR)
                        }
                    }
                }
            }
        }
    }
}
