package com.example.dictionaryapp.ui.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.data.local.History

class HistoryAdapter: ListAdapter<History,HistoryAdapter.HistoryViewHolder>(HistoryCallback()) {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val word: TextView = itemView.findViewById(R.id.history_word)
        val copy: ImageView = itemView.findViewById(R.id.copy_button)

        fun bind(history: History) {
            word.text = history.word
        }

        companion object {
            fun create(parent: ViewGroup) : HistoryViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.history_item,parent,false)
                return HistoryViewHolder(view)
            }
        }
    }

    class HistoryCallback: ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

        holder.copy.setOnClickListener {
            val clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label",current.word)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(it.context,"Copied successfully",Toast.LENGTH_SHORT).show()
        }
    }
}