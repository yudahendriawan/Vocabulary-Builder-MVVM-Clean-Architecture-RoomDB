package com.yudahendriawan.vocabularybuilder.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "vocabulary_table")
@Parcelize
data class Vocabulary(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var vocabulary: String,
    var meaning: String
) : Parcelable
