package com.yudahendriawan.vocabularybuilder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yudahendriawan.vocabularybuilder.R
import com.yudahendriawan.vocabularybuilder.data.model.Vocabulary
import com.yudahendriawan.vocabularybuilder.data.viewmodel.VocabularyViewModel
import com.yudahendriawan.vocabularybuilder.databinding.ActivityAddVocabularyDialogBinding

class AddVocabularyDialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddVocabularyDialogBinding
    private lateinit var viewModel: VocabularyViewModel
    private lateinit var type: String
    private var vocabulary: Vocabulary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVocabularyDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(
                VocabularyViewModel::class.java
            )

        type = intent.extras?.getString(TYPE) as String

        if (intent.hasExtra(VOCABULARY)) {
            vocabulary = intent.getParcelableExtra<Vocabulary>(VOCABULARY)
            if (vocabulary != null) {
                with(binding) {
                    tietAddVocabulary.setText(vocabulary?.vocabulary)
                    tietMeaningVocabulary.setText(vocabulary?.meaning)
                }
            }
        }

        if (type == TYPE_ADD) {
            binding.buttonExecute.text = "Add"
            binding.tvRemoveVocabulary.visibility = View.GONE
            supportActionBar?.title = "Add"
        } else if (type == TYPE_VIEW) {
            binding.buttonExecute.text = "Update"
            binding.tvRemoveVocabulary.visibility = View.VISIBLE
            supportActionBar?.title = "Update"
        }

        binding.buttonExecute.setOnClickListener {
            if (type == TYPE_ADD) {
                insertData()
            } else if (type == TYPE_VIEW) {
                if (vocabulary != null) {
                    updateData()
                }
            }
        }

        binding.tvRemoveVocabulary.setOnClickListener {
            if (vocabulary != null) {
                viewModel.deleteData(vocabulary = vocabulary!!)
                showToast("Deleted! ${vocabulary!!.vocabulary}")
                finish()
            }
        }


    }

    private fun insertData() {
        if (isFieldValid()) {
            val vocab = binding.tietAddVocabulary.text.toString().trim()
            val meaning = binding.tietMeaningVocabulary.text.toString().trim()
            val newVocabulary = Vocabulary(0, vocab, meaning)
            viewModel.insertData(newVocabulary)
            showToast("Added ${vocab}!")
            finish()
        } else {
            if (binding.tietAddVocabulary.text.toString().trim().isEmpty()) {
                binding.tietAddVocabulary.error = "Field must be filled"
                binding.tietAddVocabulary.requestFocus()
            }

            if (binding.tietMeaningVocabulary.text.toString().trim().isEmpty()) {
                binding.tietMeaningVocabulary.error = "Field must be filled"
                binding.tietMeaningVocabulary.requestFocus()
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this@AddVocabularyDialogActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun updateData() {
        if (isFieldValid()) {
            val vocab = binding.tietAddVocabulary.text.toString().trim()
            val meaning = binding.tietMeaningVocabulary.text.toString().trim()
            val newVocabulary = vocabulary?.id?.let { Vocabulary(it, vocab, meaning) }
            if (newVocabulary != null) {
                viewModel.updateData(newVocabulary)
                finish()
                Toast.makeText(
                    this@AddVocabularyDialogActivity,
                    "Updated ${vocab}!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun isFieldValid(): Boolean {
        return binding.tietAddVocabulary.text.toString().trim()
            .isNotEmpty() && binding.tietMeaningVocabulary.text.toString().trim().isNotEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TYPE = "type"
        const val TYPE_VIEW = "type_view"
        const val TYPE_ADD = "type_add"
        const val VOCABULARY = "vocabulary"
    }
}