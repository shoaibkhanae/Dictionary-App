package com.example.dictionaryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.databinding.FragmentHistoryBinding
import com.example.dictionaryapp.di.MyApplication
import com.example.dictionaryapp.ui.MainViewModel
import com.example.dictionaryapp.ui.MainViewModelFactory
import com.example.dictionaryapp.ui.adapters.HistoryAdapter
import com.google.android.material.snackbar.Snackbar


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding
        get() = _binding!!

    private val shareViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((requireActivity().application as MyApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HistoryAdapter()
        binding.recyclerview.adapter = adapter


        val itemTouchCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val word = adapter.currentList[position]
                shareViewModel.delete(word)

                Snackbar.make(view,"Word deleted successfully.", Snackbar.LENGTH_LONG).apply {
                    setAction("undo") {
                        shareViewModel.insert(word)
                    }
                }.show()
            }
        }

        ItemTouchHelper(itemTouchCallBack).apply {
            attachToRecyclerView(binding.recyclerview)
        }

        shareViewModel.allHistory.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}