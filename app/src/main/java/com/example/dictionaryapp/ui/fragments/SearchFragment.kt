package com.example.dictionaryapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dictionaryapp.data.local.Define
import com.example.dictionaryapp.data.local.History
import com.example.dictionaryapp.databinding.FragmentSearchBinding
import com.example.dictionaryapp.ui.MainViewModel
import com.example.dictionaryapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    private var currentWord: String = ""
    private var currentWordDefinition: String = ""

    private val shareViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    shareViewModel.searchWord(query)
                    currentWord = query
                    val history = History(word = currentWord)
                    shareViewModel.insert(history)
                    shareViewModel.definition.observe(viewLifecycleOwner) {
                        when(it) {
                            is Resource.Success -> {
                                currentWordDefinition = it.data?.get(0)?.meanings?.get(0)?.definitions?.get(0)?.definition.toString()
                                binding.apply {
                                    progressBar.visibility = View.GONE
                                    wordCard.visibility = View.VISIBLE
                                    nounMeaningCard.visibility = View.VISIBLE
                                    verbMeaningCard.visibility = View.VISIBLE
                                }
                                binding.word.text = it.data?.get(0)?.word
                                binding.phonetic.text = it.data?.get(0)?.phonetic
                                when(it.data?.get(0)?.meanings?.get(0)?.definitions?.size!!) {
                                    1 -> {
                                        binding.definitionTitle1.visibility = View.VISIBLE
                                        binding.definition1.visibility = View.VISIBLE
                                        binding.definition1.text = it.data[0].meanings[0].definitions[0].definition
                                        binding.definitionTitle2.visibility = View.GONE
                                        binding.definition2.visibility = View.GONE
                                        binding.definitionTitle3.visibility = View.GONE
                                        binding.definition3.visibility = View.GONE
                                    }
                                    2 -> {
                                        binding.definition2.visibility = View.VISIBLE
                                        binding.definitionTitle2.visibility = View.VISIBLE
                                        binding.definition1.text = it.data[0].meanings[0].definitions[0].definition
                                        binding.definition2.text = it.data[0].meanings[0].definitions[1].definition
                                        binding.definitionTitle3.visibility = View.GONE
                                        binding.definition3.visibility = View.GONE
                                    }
                                    3 -> {
                                        binding.definition2.visibility = View.VISIBLE
                                        binding.definitionTitle2.visibility = View.VISIBLE
                                        binding.definition3.visibility = View.VISIBLE
                                        binding.definitionTitle3.visibility = View.VISIBLE
                                        binding.definition1.text = it.data[0].meanings[0].definitions[0].definition
                                        binding.definition2.text = it.data[0].meanings[0].definitions[1].definition
                                        binding.definition3.text = it.data[0].meanings[0].definitions[2].definition
                                    }
                                    else -> {
                                        binding.definitionTitle1.visibility = View.GONE
                                        binding.definition1.visibility = View.GONE
                                        binding.definitionTitle2.visibility = View.GONE
                                        binding.definition2.visibility = View.GONE
                                        binding.definitionTitle3.visibility = View.GONE
                                        binding.definition3.visibility = View.GONE
                                    }
                                }
                                val verbSize = it.data?.get(0)?.meanings?.get(1)?.definitions!!.size

                                when(verbSize) {
                                    1 -> {
                                        binding.apply {
                                            verbDefinitionTitle1.visibility = View.VISIBLE
                                            verbDefinition.visibility = View.VISIBLE
                                            verbDefinition.text = it.data[0].meanings[1].definitions[0].definition
                                            verbDefinitionTitle2.visibility = View.GONE
                                            verbDefinition2.visibility = View.GONE
                                        }
                                    }
                                    2 -> {
                                        binding.apply {
                                            verbDefinitionTitle1.visibility = View.VISIBLE
                                            verbDefinition.visibility = View.VISIBLE
                                            verbDefinitionTitle2.visibility = View.VISIBLE
                                            verbDefinition2.visibility = View.VISIBLE
                                            verbDefinition.text = it.data[0].meanings[1].definitions[0].definition
                                            verbDefinition2.text = it.data[0].meanings[1].definitions[1].definition

                                        }
                                    }
                                }

                            }
                            is Resource.Error -> {
                                binding.apply {
                                    wordCard.visibility = View.GONE
                                    nounMeaningCard.visibility = View.GONE
                                    verbMeaningCard.visibility = View.GONE
                                }
                                Toast.makeText(requireContext(),it.error,Toast.LENGTH_LONG).show()
                            }
                            is Resource.Loading -> {
                                binding.apply {
                                    progressBar.visibility = View.VISIBLE
                                    wordCard.visibility = View.GONE
                                    nounMeaningCard.visibility = View.GONE
                                    verbMeaningCard.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.favouriteButton.setOnClickListener {
            val define = Define(word = currentWord, definitionOfWord = currentWordDefinition)
            shareViewModel.insert(define)
            Toast.makeText(requireContext(),"Word saved successfully.",Toast.LENGTH_LONG).show()
        }

        binding.btnShare.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,"$currentWord\n$currentWordDefinition")
                type = "plain/text"
            }
            val shareIntent = Intent.createChooser(sendIntent,null)
            startActivity(shareIntent)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}