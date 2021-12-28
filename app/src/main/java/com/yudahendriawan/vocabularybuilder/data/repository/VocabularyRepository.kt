package com.yudahendriawan.vocabularybuilder.data.repository

import androidx.lifecycle.LiveData
import com.yudahendriawan.vocabularybuilder.data.db.VocabularyDao
import com.yudahendriawan.vocabularybuilder.data.model.Vocabulary
import com.yudahendriawan.vocabularybuilder.ui.ListVocabularyActivity

class VocabularyRepository(private val vocabularyDao: VocabularyDao) {

    val getAllVocabulary: LiveData<List<Vocabulary>> = vocabularyDao.getAllVocabulary()

    suspend fun insertData(vocabulary: Vocabulary){
        vocabularyDao.insertData(vocabulary)
    }

    suspend fun updateData(vocabulary: Vocabulary){
        vocabularyDao.updateData(vocabulary)
    }

    suspend fun deleteData(vocabulary: Vocabulary){
        vocabularyDao.deleteData(vocabulary)
    }

    fun searchVocabulary(searchQuery: String): LiveData<List<Vocabulary>>{
        return vocabularyDao.searchVocabulary(searchQuery)
    }

}