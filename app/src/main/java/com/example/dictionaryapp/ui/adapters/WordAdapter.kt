package com.example.dictionaryapp.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.local.Define

class WordAdapter: ListAdapter<Define, WordAdapter.WordViewHolder>(WordDiffCallback()) {


    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val word: TextView = itemView.findViewById(R.id.main_word)
        val definition: TextView = itemView.findViewById(R.id.main_definition)
        var shareButton: ImageView = itemView.findViewById(R.id.favorite_share_button)

        fun bind(define: Define) {
            word.text = define.word
            definition.text = define.definitionOfWord
        }

        companion object {

            fun create(parent: ViewGroup): WordViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item,parent,false)
                return WordViewHolder(view)
            }
        }
    }
    class WordDiffCallback: DiffUtil.ItemCallback<Define>() {
        override fun areItemsTheSame(oldItem: Define, newItem: Define): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Define, newItem: Define): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

        holder.shareButton.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,"${current.word}\n ${current.definitionOfWord}")
                type = "plain/text"
            }
            val shareIntent = Intent.createChooser(sendIntent,null)
            it.context.startActivity(shareIntent)
        }
    }
}