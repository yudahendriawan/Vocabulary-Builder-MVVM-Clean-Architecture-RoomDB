package com.yudahendriawan.vocabularybuilder.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yudahendriawan.vocabularybuilder.data.model.Vocabulary

@Database(entities = [Vocabulary::class], version =  1, exportSchema = false)
abstract class VocabularyDatabase : RoomDatabase() {

    abstract fun vocabularyDao(): VocabularyDao

    companion object{

        @Volatile
        private var INSTANCE: VocabularyDatabase? = null

        fun getDatabase(context: Context) : VocabularyDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VocabularyDatabase::class.java,
                    "vocabulary_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}