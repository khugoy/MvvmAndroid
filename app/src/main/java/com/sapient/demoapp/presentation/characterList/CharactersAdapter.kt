package com.sapient.demoapp.presentation.characterList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sapient.demoapp.databinding.ItemCharacterBinding
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.presentation.image.loadImage

class CharactersAdapter : RecyclerView.Adapter<CharacterViewHolder>() {

    private lateinit var binding: ItemCharacterBinding

    private val differCallback = object : DiffUtil.ItemCallback<CharacterDomainModel>() {
        override fun areItemsTheSame(
            oldItem: CharacterDomainModel,
            newItem: CharacterDomainModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterDomainModel,
            newItem: CharacterDomainModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val characterList = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return characterList.currentList.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList.currentList[position]
        holder.itemView.apply {
            binding.name.text = character.name
            binding.speciesAndStatus.text = "${character.species} - ${character.status}"
            binding.image.loadImage(character.image)

            setOnClickListener {
                onItemClickListener?.let { it(character) }
            }
        }

    }

    private var onItemClickListener: ((CharacterDomainModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (CharacterDomainModel) -> Unit) {
        onItemClickListener = listener

    }
}

class CharacterViewHolder(itemBinding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(itemBinding.root)



