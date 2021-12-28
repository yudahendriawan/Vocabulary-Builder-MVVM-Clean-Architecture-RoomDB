package com.yudahendriawan.vocabularybuilder.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.yudahendriawan.vocabularybuilder.data.db.VocabularyDatabase
import com.yudahendriawan.vocabularybuilder.data.model.Vocabulary
import com.yudahendriawan.vocabularybuilder.data.repository.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VocabularyViewModel(application: Application) : AndroidViewModel(application) {

    private val vocabularyDao = VocabularyDatabase.getDatabase(application).vocabularyDao()
    private val repository: VocabularyRepository = VocabularyRepository(vocabularyDao)

    val getAllVocabulary: LiveData<List<Vocabulary>> = repository.getAllVocabulary

    fun insertData(vocabulary: Vocabulary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(vocabulary)
        }
    }

    fun updateData(vocabulary: Vocabulary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(vocabulary)
        }
    }

    fun deleteData(vocabulary: Vocabulary) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(vocabulary)
        }
    }

    fun searchVocabulary(searchQuery: String): LiveData<List<Vocabulary>> {
        return repository.searchVocabulary(searchQuery)
    }

}