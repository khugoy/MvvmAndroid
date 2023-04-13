package com.sapient.demoapp.presentation.characterList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sapient.demoapp.databinding.ItemCharacterBinding
import com.sapient.demoapp.domain.models.CharacterDomainModel
import com.sapient.demoapp.presentation.image.loadImage

typealias CharacterItemListener = (Int) -> Unit

class CharactersAdapter(private val listener: CharacterItemListener) :
    RecyclerView.Adapter<CharacterViewHolder>() {

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

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.bind(character)
    }
}

class CharacterViewHolder(
    private val itemBinding: ItemCharacterBinding,
    private val listener: CharacterItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var character: CharacterDomainModel

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: CharacterDomainModel) {
        this.character = item
        with(itemBinding) {
            name.text = item.name
            speciesAndStatus.text = "${item.species} - ${item.status}"
            image.loadImage(item.image)
        }
    }

    override fun onClick(v: View?) {
        listener.invoke(character.id)
    }

}

