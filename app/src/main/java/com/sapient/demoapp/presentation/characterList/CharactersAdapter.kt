package com.sapient.demoapp.presentation.characterList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sapient.demoapp.databinding.ItemCharacterBinding
import com.sapient.demoapp.domain.models.Character

class CharactersAdapter(
    private val listener: CharacterItemListener,
    private val glide: RequestManager
    ) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Int)
    }

    private var items = mutableListOf<Character>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: MutableList<Character>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(items[position])


    inner class CharacterViewHolder(
        private val itemBinding: ItemCharacterBinding,
        private val listener: CharactersAdapter.CharacterItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var character: Character

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Character) {
            this.character = item
            itemBinding.name.text = item.name
            itemBinding.speciesAndStatus.text = """${item.species} - ${item.status}"""
            glide.load(item.image).into(itemBinding.image)
        }

        override fun onClick(v: View?) {
            listener.onClickedCharacter(character.id)
        }
    }
}

