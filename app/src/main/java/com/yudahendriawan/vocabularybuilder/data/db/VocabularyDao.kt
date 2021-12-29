package com.yudahendriawan.vocabularybuilder.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yudahendriawan.vocabularybuilder.data.model.Vocabulary

@Dao
interface VocabularyDao {
    @Query("SELECT * FROM vocabulary_table ORDER BY id DESC")
    fun getAllVocabulary(): LiveData<List<Vocabulary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(vocabulary: Vocabulary)

    @Update
    suspend fun updateData(vocabulary: Vocabulary)

    @Delete
    suspend fun deleteData(vocabulary: Vocabulary)

    @Query("SELECT * FROM vocabulary_table WHERE vocabulary LIKE :searchQuery")
    fun searchVocabulary(searchQuery: String): LiveData<List<Vocabulary>>
}