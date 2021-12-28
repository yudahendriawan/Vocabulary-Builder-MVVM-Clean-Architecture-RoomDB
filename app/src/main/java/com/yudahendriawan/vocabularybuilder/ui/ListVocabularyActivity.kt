package com.yudahendriawan.vocabularybuilder.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudahendriawan.vocabularybuilder.R
import com.yudahendriawan.vocabularybuilder.adapter.ListVocabularyAdapter
import com.yudahendriawan.vocabularybuilder.data.model.Vocabulary
import com.yudahendriawan.vocabularybuilder.data.viewmodel.VocabularyViewModel
import com.yudahendriawan.vocabularybuilder.databinding.ActivityListVocabularyBinding

class ListVocabularyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListVocabularyBinding
    private val adapter = ListVocabularyAdapter()
    private lateinit var viewModel: VocabularyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVocabularyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Vocabulary"

        setupRecyclerView()

        viewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(
                VocabularyViewModel::class.java
            )

        viewModel.getAllVocabulary.observe(this, {
            adapter.setData(it)
            adapter.setOnVocabListener(object : ListVocabularyAdapter.IVocabularyClickListener {
                override fun onVocabClick(vocabulary: Vocabulary) {
                    startActivity(
                        Intent(
                            this@ListVocabularyActivity,
                            AddVocabularyDialogActivity::class.java
                        ).apply {
                            putExtra(
                                AddVocabularyDialogActivity.TYPE,
                                AddVocabularyDialogActivity.TYPE_VIEW
                            )
                            putExtra(AddVocabularyDialogActivity.VOCABULARY, vocabulary)
                        })
                }

                override fun onVocabLongClick(meaning: String) {
                    Toast.makeText(this@ListVocabularyActivity, meaning, Toast.LENGTH_SHORT).show()
                }

            })
        })

        binding.fabAdd.setOnClickListener {
            startActivity(
                Intent(
                    this@ListVocabularyActivity,
                    AddVocabularyDialogActivity::class.java
                ).apply {
                    putExtra(AddVocabularyDialogActivity.TYPE, AddVocabularyDialogActivity.TYPE_ADD)
                })
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_menu -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu.findItem(R.id.search_menu)
        val searchView = search.actionView as? SearchView
        searchView?.queryHint = "Find Vocab"
        /*searchView?.isSubmitButtonEnabled = true*/
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchVocabulary(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchVocabulary(newText)
                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun searchVocabulary(query: String) {
        viewModel.searchVocabulary("%$query%").observe(this, {
            adapter.setData(it)
        })
    }
}